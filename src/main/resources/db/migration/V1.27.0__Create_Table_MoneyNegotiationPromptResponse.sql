CREATE TABLE MoneyNegotiationPromptResponse
(
  MoneyNegotiationPromptResponseId   INT        NOT NULL     PRIMARY KEY
, ResponseId                         INT        NOT NULL
, FOREIGN KEY(ResponseId) REFERENCES Response(ResponseId)
);