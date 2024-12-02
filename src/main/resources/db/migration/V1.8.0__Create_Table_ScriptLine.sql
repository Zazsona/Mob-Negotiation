CREATE TABLE ScriptLine
(
  ScriptLineId      INT     NOT NULL     PRIMARY KEY
, ScriptLineTypeId  INT     NOT NULL
, ScriptLineToneId  INT     NOT NULL
, TextContentId     INT     NOT NULL
, FOREIGN KEY(ScriptLineTypeId) REFERENCES ScriptLineType(ScriptLineTypeId)
, FOREIGN KEY(ScriptLineToneId) REFERENCES ScriptLineTone(ScriptLineToneId)
, FOREIGN KEY(TextContentId)    REFERENCES TextContent(TextContentId)
);