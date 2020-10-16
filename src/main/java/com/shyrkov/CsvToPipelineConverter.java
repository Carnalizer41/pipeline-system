package com.shyrkov;

import au.com.bytecode.opencsv.CSVReader;
import com.shyrkov.model.Pipeline;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvToPipelineConverter {

    private CSVReader csvReader;

    public CsvToPipelineConverter(String csvPath) throws IOException {
        csvReader = new CSVReader(new FileReader(csvPath), ';');
    }

    public List<Pipeline> convert() throws IOException{
        List<Pipeline> pipelines = new ArrayList<>();
        String[] line;

        while((line = csvReader.readNext()) != null){
            Pipeline pipeline = Pipeline.parseFromStringArray(line);
            pipelines.add(pipeline);
        }
        csvReader.close();
        return pipelines;
    }
}
