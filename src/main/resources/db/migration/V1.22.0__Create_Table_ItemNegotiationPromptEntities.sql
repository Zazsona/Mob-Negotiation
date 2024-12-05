CREATE TABLE ItemNegotiationPromptEntities
(
  ItemNegotiationPromptId   INT        NOT NULL
, EntityTypeId              INT        NOT NULL
, FOREIGN KEY(ItemNegotiationPromptId) REFERENCES ItemNegotiationPrompt(ItemNegotiationPromptId)
, FOREIGN KEY(EntityTypeId) REFERENCES EntityType(EntityTypeId)
);