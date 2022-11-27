import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.github.polydome.backup.BackupService;
import com.github.polydome.model.MoodEntry;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class JsonBackupService implements BackupService {

    private static final String filepath="json.json";
    
    /**
     * Serializes entries into a JSON format and writes to a file.
     * @param entries Mood entries to be backed up
     */
    @Override
    @SneakyThrows
    public void write(List<MoodEntry> entries) {
        ObjectMapper objectMapper=new ObjectMapper();
        String jsonString=objectMapper.writeValueAsString(entries);
        Path fPath=Paths.get(filepath);
        Files.writeString(fPath, jsonString);
    }

    /**
     * Reads a JSON file and deserializes its content.
     * @return A list of backed up entries
     */
    @Override
    @SneakyThrows
    public List<MoodEntry> read() {
         Path path = Paths.get(filepath);
         String json = Files.readString(path);
         ObjectMapper objjsonMapper=new ObjectMapper();

        return objjsonMapper.readValue(json, new TypeReference<List<MoodEntry>>() {
    });
}
}
