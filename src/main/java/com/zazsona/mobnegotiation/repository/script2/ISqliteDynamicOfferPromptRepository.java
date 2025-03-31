package com.zazsona.mobnegotiation.repository.script2;

import com.zazsona.mobnegotiation.model2.NegotiationEntityType;
import com.zazsona.mobnegotiation.model2.PersonalityType;

import javax.annotation.Nullable;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ISqliteDynamicOfferPromptRepository extends ISqlitePromptRepository {
    List<Integer> getDynamicOfferPromptIds(PromptTypeSchema promptType, @Nullable List<NegotiationEntityType> entityTypes, @Nullable List<PersonalityType> personalityTypes, boolean requireCanBeInitialOffer, boolean requireCanBeRevisedOffer, boolean requireCanBeRepeatOffer, boolean requireCanBeRejection, boolean requireCanBeAcceptance) throws IOException, SQLException;
}
