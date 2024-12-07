CREATE TABLE MoneyNegotiationPromptEntities
(
  MoneyNegotiationPromptId    INT        NOT NULL
, EntityTypeId                INT        NOT NULL
, FOREIGN KEY(MoneyNegotiationPromptId) REFERENCES MoneyNegotiationPrompt(MoneyNegotiationPromptId)
, FOREIGN KEY(EntityTypeId) REFERENCES EntityType(EntityTypeId)
);