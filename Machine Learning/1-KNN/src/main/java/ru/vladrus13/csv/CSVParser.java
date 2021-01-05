package ru.vladrus13.csv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import ru.vladrus13.bean.Person;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Parser for .csv
 */
public class CSVParser {
    /**
     * Read all persons
     *
     * @return all persions
     * @throws IOException            if we have problem with read
     * @throws CsvValidationException table is invalid
     */
    public static ArrayList<Person> readAll() throws IOException, CsvValidationException {
        Reader reader = Files.newBufferedReader(Path.of("resources/phpMawTba.csv"));
        CSVReader csvReader = new CSVReader(reader);
        ArrayList<Person> persons = new ArrayList<>();
        @SuppressWarnings("UnusedAssignment")
        String[] line = csvReader.readNext();
        while ((line = csvReader.readNext()) != null) {
            persons.add(new Person(line));
        }
        reader.close();
        csvReader.close();
        return persons;
    }
}
