CREATE TABLE HoldUpResponse
(
  HoldUpResponseId   INT        NOT NULL     PRIMARY KEY
, ResponseId         INT        NOT NULL
, FOREIGN KEY(ResponseId) REFERENCES Response(ResponseId)
);