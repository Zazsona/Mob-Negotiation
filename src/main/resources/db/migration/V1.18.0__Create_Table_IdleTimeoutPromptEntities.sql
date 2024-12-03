CREATE TABLE IdleTimeoutPromptEntities
(
  IdleTimeoutPromptId   INT        NOT NULL
, EntityTypeId          INT        NOT NULL
, FOREIGN KEY(IdleTimeoutPromptId) REFERENCES IdleTimeoutPrompt(IdleTimeoutPromptId)
, FOREIGN KEY(EntityTypeId) REFERENCES EntityType(EntityTypeId)
);