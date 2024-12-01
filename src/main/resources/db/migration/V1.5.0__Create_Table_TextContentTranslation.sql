CREATE TABLE TextContentTranslation
(
  TextContentTranslationId  INT        NOT NULL     PRIMARY KEY
, TextContentId             INT        NOT NULL
, LanguageCode              VARCHAR    NOT NULL
, Text                      TEXT       NOT NULL
, FOREIGN KEY(TextContentId) REFERENCES TextContent(TextContentId)
, FOREIGN KEY(LanguageCode) REFERENCES Language(LanguageCode)
);