package com.shyrkov;

import au.com.bytecode.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;

public class ResultToCsvWriter {

    public static void writeResult(String path, boolean boolRes, int length) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(path, true), ';');
        String[] stringsToWrite;
        if (length != 0)
            stringsToWrite = new String[]{String.valueOf(boolRes), String.valueOf(length)};
        else
            stringsToWrite = new String[]{String.valueOf(boolRes), ""};
        writer.writeNext(stringsToWrite);
        writer.close();
    }
}
