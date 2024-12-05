CREATE TABLE ItemNegotiationResponse
(
  ItemNegotiationResponseId   INT        NOT NULL     PRIMARY KEY
, ResponseId                  INT        NOT NULL
, FOREIGN KEY(ResponseId) REFERENCES Response(ResponseId)
);