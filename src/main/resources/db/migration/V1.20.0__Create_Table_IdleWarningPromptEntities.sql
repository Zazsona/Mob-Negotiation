CREATE TABLE IdleWarningPromptEntities
(
  IdleWarningPromptId   INT        NOT NULL
, EntityTypeId          INT        NOT NULL
, FOREIGN KEY(IdleWarningPromptId) REFERENCES IdleWarningPrompt(IdleWarningPromptId)
, FOREIGN KEY(EntityTypeId) REFERENCES EntityType(EntityTypeId)
);