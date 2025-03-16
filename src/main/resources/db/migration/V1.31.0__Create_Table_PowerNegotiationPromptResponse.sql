CREATE TABLE PowerNegotiationPromptResponse
(
  PowerNegotiationPromptResponseId    INT    NOT NULL    PRIMARY KEY
, ResponseId                          INT    NOT NULL
, FOREIGN KEY(ResponseId) REFERENCES Response(ResponseId)
);