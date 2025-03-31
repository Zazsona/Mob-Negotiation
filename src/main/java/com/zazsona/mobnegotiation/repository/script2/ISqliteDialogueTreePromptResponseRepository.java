package com.zazsona.mobnegotiation.repository.script2;

import com.zazsona.mobnegotiation.model2.script.PromptResponseScriptNode;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ISqliteDialogueTreePromptResponseRepository extends ISqlitePromptResponseRepository {
    Map<PromptResponseReference, PromptResponseScriptNode> getPromptResponses(PromptTypeSchema promptType, List<Integer> promptIds) throws IOException, SQLException;
}
