import com.github.polydome.backup.BackupService;
import com.github.polydome.model.MoodEntry;

import java.util.List;

public class GoogleDriveBackupService implements BackupService {
    /**
     * Creates a backup file and uploads it to Google Drive.
     * @param entries Mood entries to be backed up
     */
    @Override
    public void write(List<MoodEntry> entries) {
        throw new InternalError("Not implemented");
    }

    /**
     * Downloads a backup file from Google Drive and deserializes its content.
     * @return A list of backed up entries
     */
    @Override
    public List<MoodEntry> read() {
        throw new InternalError("Not implemented");
    }
}
