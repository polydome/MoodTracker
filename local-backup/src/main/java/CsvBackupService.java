import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.github.polydome.backup.BackupService;
import com.github.polydome.model.MoodEntry;

import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.annotation.processing.SupportedAnnotationTypes;

public class CsvBackupService implements BackupService {

    private static final String filepath="csv.csv";
    /**
     * Serializes entries into a CSV format and writes to a file.
     * @param entries Mood entries to be backed up
     */
    @Override
    @SneakyThrows
    public void write(List<MoodEntry> entries) {
        CsvMapper objectMapper=new CsvMapper();
        String jsonString=objectMapper.writeValueAsString(entries);
        Path fPath=Paths.get(filepath);
        Files.writeString(fPath, jsonString);
    }

    /**
     * Reads a CSV file and deserializes its content
     * @return A list of backed up entries
     */
    @Override
    @SneakyThrows
    public List<MoodEntry> read() {
        Path path = Paths.get(filepath);
        String json = Files.readString(path);
        CsvMapper objCsvMapper=new CsvMapper();

        return objCsvMapper.readValue(json, new TypeReference<List<MoodEntry>>() {
   });
    }
}

