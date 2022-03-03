package com.wp.system.utils;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSVConverter {
    public static String convertToCSV(String[] data) {
        return Stream.of(data)
                .map(CSVConverter::escapeSpecialCharacters)
                .collect(Collectors.joining(",")) + "\n";
    }

    public static String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }
}
