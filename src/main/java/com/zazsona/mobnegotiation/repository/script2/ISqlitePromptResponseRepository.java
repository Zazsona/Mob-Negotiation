package com.zazsona.mobnegotiation.repository.script2;

import com.zazsona.mobnegotiation.model2.script.PromptResponseScriptNode;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ISqlitePromptResponseRepository extends AutoCloseable {
    Map<PromptResponseReference, PromptResponseScriptNode> getPromptResponses(PromptTypeSchema promptType, List<Integer> promptIds) throws IOException, SQLException;

    String getPreferredLanguageCode();

    void setPreferredLanguageCode(String preferredLanguageCode);
}
