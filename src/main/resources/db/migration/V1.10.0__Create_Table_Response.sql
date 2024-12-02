CREATE TABLE Response
(
  ResponseId        INT    NOT NULL     PRIMARY KEY
, ScriptLineId      INT    NOT NULL
, ResponseTypeId    INT    NOT NULL
, FOREIGN KEY(ScriptLineId) REFERENCES ScriptLine(ScriptLineId)
, FOREIGN KEY(ResponseTypeId) REFERENCES ResponseType(ResponseTypeId)
);