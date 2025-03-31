package com.zazsona.mobnegotiation.repository.script2;

import com.zazsona.mobnegotiation.model2.NegotiationEntityType;

import javax.annotation.Nullable;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ISqliteDialogueTreePromptRepository extends ISqlitePromptRepository {
    List<Integer> getDialogueTreePromptIds(PromptTypeSchema promptType, @Nullable List<NegotiationEntityType> entityTypes, boolean requireCanBeInitialPrompt) throws IOException, SQLException;
}
