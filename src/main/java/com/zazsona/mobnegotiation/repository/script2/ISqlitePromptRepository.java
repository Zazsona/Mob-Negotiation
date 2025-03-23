package com.zazsona.mobnegotiation.repository.script2;

import com.zazsona.mobnegotiation.model2.NegotiationEntityType;
import com.zazsona.mobnegotiation.model2.PersonalityType;
import com.zazsona.mobnegotiation.model2.script.PromptScriptNode;

import javax.annotation.Nullable;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ISqlitePromptRepository extends AutoCloseable {
    String getPreferredLanguageCode();

    void setPreferredLanguageCode(String preferredLanguageCode);

    List<Integer> getPromptIds(PromptTypeSchema promptType, @Nullable List<NegotiationEntityType> entityTypes, @Nullable List<PersonalityType> personalityTypes) throws IOException, SQLException;

    Map<Integer, PromptScriptNode> getPrompts(PromptTypeSchema promptType, List<Integer> promptIds, boolean includeResponses) throws IOException, SQLException;
}
