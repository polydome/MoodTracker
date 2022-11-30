import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;


import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.*;

import exception.GoogleDriveBackupNotFoundException;

import com.google.api.services.drive.Drive;

import com.github.polydome.backup.BackupService;
import com.github.polydome.model.MoodEntry;

import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GoogleDriveBackupService implements BackupService {
    /** Application name. */
    private static final String APPLICATION_NAME = "MoodTracker";
    private final String filepath;

    public GoogleDriveBackupService(String filePath) {
        this.filepath = filePath;
    }

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
        System.getProperty("user.home"), ".credentials/drive-credentials");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final GsonFactory JSON_FACTORY =
        GsonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    private static final List<String> SCOPES =
        Arrays.asList(DriveScopes.DRIVE_FILE);
        static {
            try {
                HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
                DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
            } catch (Throwable t) {
                t.printStackTrace();
                System.exit(1);
            }
        }
    
        /**
         * Creates an authorized Credential object.
         * @return an authorized Credential object.
         * @throws IOException
         */
        public static Credential authorize() throws IOException {
            // Load client secrets.
            InputStream in =
                GoogleDriveBackupService.class.getResourceAsStream("/client_secret.json");
            GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
    
            // Build flow and trigger user authorization request.
            GoogleAuthorizationCodeFlow flow =
                    new GoogleAuthorizationCodeFlow.Builder(
                            HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                    .setDataStoreFactory(DATA_STORE_FACTORY)
                    .setAccessType("offline")
                    .build();
            Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user");
            System.out.println(
                    "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
            return credential;
        }
    
        /**
         * Build and return an authorized Drive client service.
         * @return an authorized Drive client service
         * @throws IOException
         */
        public static Drive getDriveService() throws IOException {
            Credential credential = authorize();
            return new Drive.Builder(
                    HTTP_TRANSPORT, JSON_FACTORY, credential)
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        }
    
        /**
         * Create a folder and return a Folder Id.
         * @param folderName Name of folder to create
         * @param service an authorized Drive client service
         * @return a folder Id
         * @throws IOException
         */
        public static String createFolderAndGetID(Drive service, String folderName) throws IOException {
            File fileMetadata = new File();
            fileMetadata.setName(folderName);
            fileMetadata.setMimeType("application/vnd.google-apps.folder");
    
            File file = service.files().create(fileMetadata)
                    .setFields("id")
                    .execute();
            System.out.println("Folder ID: " + file.getId());
    
            return file.getId();
        }
    
        /**
         * Set permissions to a file in Google Drive.
         * @param fileId Id of the file to modify
         * @param service an authorized Drive client service
         * @throws IOException
         */
        public static void setPermissionsToFile(Drive service, String fileId) throws IOException {
    
            JsonBatchCallback<Permission> callback = new JsonBatchCallback<Permission>() {
                @Override
                public void onFailure(GoogleJsonError e,
                                      HttpHeaders responseHeaders)
                        throws IOException {
                    // Handle error
                    System.err.println(e.getMessage());
                }
    
                @Override
                public void onSuccess(Permission permission,
                                      HttpHeaders responseHeaders)
                        throws IOException {
                    System.out.println("Permission ID: " + permission.getId());
                }
            };
            BatchRequest batch = service.batch();
            Permission userPermission = new Permission()
                    .setType("anyone")
                    .setRole("reader")
                    .setAllowFileDiscovery(false);
            service.permissions().create(fileId, userPermission)
                    .setFields("id")
                    .queue(batch, callback);
    
            batch.execute();
        }
   
   

    /**
     * Creates a backup file and uploads it to Google Drive.
     * @param entries Mood entries to be backed up
     */
    @Override
    public void write(List<MoodEntry> entries) {
        JsonBackupService jsonbackup = new JsonBackupService(filepath);
        jsonbackup.write(entries);

        try {
            // Build a new authorized API client service.
            Drive service = getDriveService();
            /* Create a folder and get Folder Id */
            String folderId = createFolderAndGetID(service, "MoodTracker");
            /* Upload file to Google Drive */
            File fileMetadata = new File();
            fileMetadata.setName("MoodBackup.json");
            fileMetadata.setParents(Collections.singletonList(folderId));
            java.io.File filePath = new java.io.File(filepath);
            FileContent mediaContent = new FileContent("application/json", filePath);
            File file = service.files().create(fileMetadata, mediaContent)
                    .setFields("id, name, webContentLink, webViewLink")
                    .execute();

            /* Set permissions to file */
            setPermissionsToFile(service, file.getId());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Downloads a backup file from Google Drive and deserializes its content.
     * @return A list of backed up entries
     */
    @Override
    public List<MoodEntry> read() {
        try{
            Drive service = getDriveService();
            List<File> files = new ArrayList<File>();

            String pageToken = null;
            do {
              FileList result = service.files().list()
                  .setQ("mimeType='application/json' and name='MoodBackup.json' and trashed=false")
                  .setSpaces("drive")
                  .setFields("nextPageToken, files(id, name)")
                  .setPageToken(pageToken)
                  .execute();
                if(result.isEmpty()){
                    throw new GoogleDriveBackupNotFoundException("");
                }
              for (File file : result.getFiles()) {
                System.out.printf("Found file: %s (%s)\n",
                    file.getName(), file.getId());
                    try {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        service.files().get(file.getId())
                            .executeMediaAndDownloadTo(byteArrayOutputStream);
                            try(OutputStream outputStream = new FileOutputStream("./MoodBackup.json")) {
                                byteArrayOutputStream.writeTo(outputStream);
                            }
                      } catch (GoogleJsonResponseException e) {
                        // TODO(developer) - handle error appropriately
                        System.err.println("Unable to move file: " + e.getDetails());
                        throw e;
                      }
              }
              files.addAll(result.getFiles());
            } while (pageToken != null);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        JsonBackupService jsonbackup = new JsonBackupService("./MoodBackup.json");
        return jsonbackup.read();
    }
    


}
