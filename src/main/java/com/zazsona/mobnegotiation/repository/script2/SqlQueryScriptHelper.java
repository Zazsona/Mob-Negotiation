package com.zazsona.mobnegotiation.repository.script2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class SqlQueryScriptHelper {

    public SqlQueryScriptHelper() { }

    public String prepareQueryFromFile(String queryScriptPath, Map<String, String> queryParameters) throws IOException {
        Path path = Paths.get(queryScriptPath);
        String sqlQuery = String.join("", Files.readAllLines(path));
        for (Map.Entry<String, String> parameter : queryParameters.entrySet()) {
            sqlQuery = sqlQuery.replace(parameter.getKey(), parameter.getValue());
        }
        return sqlQuery;
    }
}
