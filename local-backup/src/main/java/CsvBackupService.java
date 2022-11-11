import com.github.polydome.backup.BackupService;
import com.github.polydome.model.MoodEntry;

import java.util.List;

public class CsvBackupService implements BackupService {
    /**
     * Serializes entries into a CSV format and writes to a file.
     * @param entries Mood entries to be backed up
     */
    @Override
    public void write(List<MoodEntry> entries) {
        throw new InternalError("Not implemented");
    }

    /**
     * Reads a CSV file and deserializes its content
     * @return A list of backed up entries
     */
    @Override
    public List<MoodEntry> read() {
        throw new InternalError("Not implemented");
    }
}
