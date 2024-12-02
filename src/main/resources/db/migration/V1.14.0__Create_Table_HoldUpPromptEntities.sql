CREATE TABLE HoldUpPromptEntities
(
  HoldUpPromptId   INT        NOT NULL
, EntityTypeId     INT        NOT NULL
, FOREIGN KEY(HoldUpPromptId) REFERENCES HoldUpPrompt(HoldUpPromptId)
, FOREIGN KEY(EntityTypeId) REFERENCES EntityType(EntityTypeId)
);