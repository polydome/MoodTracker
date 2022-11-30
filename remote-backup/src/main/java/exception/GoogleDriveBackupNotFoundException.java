package exception;

public class GoogleDriveBackupNotFoundException extends RuntimeException {
    public GoogleDriveBackupNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
