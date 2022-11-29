import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.polydome.backup.BackupService;
import com.github.polydome.model.MoodEntry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class JsonBackupService implements BackupService {

    private static final String filepath = "json.json";
    private final ObjectMapper mapper = buildMoodEntryMapper();

    /**
     * Serializes entries into a JSON format and writes to a file.
     *
     * @param entries Mood entries to be backed up
     */
    @Override
    public void write(List<MoodEntry> entries) {
        String jsonString;

        try {
            jsonString = mapper.writeValueAsString(entries);
        } catch (JsonProcessingException e) {
            throw new BackupException("File processing failed", e);
        }

        Path fPath = Paths.get(filepath);

        try {
            Files.writeString(fPath, jsonString);
        } catch (IOException e) {
            throw new BackupException("File writing failed", e);
        }
    }

    /**
     * Reads a JSON file and deserializes its content.
     *
     * @return A list of backed up entries
     */
    @Override
    public List<MoodEntry> read() {
        Path path = Paths.get(filepath);

        String json;
        try {
            json = Files.readString(path);
        } catch (IOException e) {
            throw new BackupException("File reading failed", e);
        }

        try {
            return mapper.readValue(json, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new BackupException("File deserialization failed", e);
        }
    }

    private static ObjectMapper buildMoodEntryMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
}
