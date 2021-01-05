package ru.vladrus13.csv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import ru.vladrus13.bean.Bean;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class CSVParser {
    public static ArrayList<Bean> readAll(Path path) throws IOException, CsvValidationException {
        Reader reader = Files.newBufferedReader(path);
        CSVReader csvReader = new CSVReader(reader);
        ArrayList<Bean> persons = new ArrayList<>();
        @SuppressWarnings("UnusedAssignment")
        String[] line = csvReader.readNext();
        while ((line = csvReader.readNext()) != null) {
            persons.add(new Bean(line));
        }
        reader.close();
        csvReader.close();
        return persons;
    }
}
