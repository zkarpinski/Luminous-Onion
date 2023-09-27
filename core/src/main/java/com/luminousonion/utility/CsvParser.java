package com.luminousonion.utility;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class CsvParser {
    private CsvParser() {
        throw new IllegalStateException("Utility class");
    }
    private static final CsvMapper csvMapper = new CsvMapper();

    public static <T> List<T> read(Class<T> c, InputStream stream) throws IOException {
        CsvSchema schema = csvMapper.schemaFor(c).withHeader().withColumnReordering(true);
        ObjectReader reader = csvMapper.readerFor(c).with(schema);
        return reader.<T>readValues(stream).readAll();
    }
}
