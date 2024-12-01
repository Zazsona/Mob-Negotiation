CREATE TABLE TextContent
(
  TextContentId    INT        NOT NULL     PRIMARY KEY
, BaseLanguageCode VARCHAR    NOT NULL
, BaseText         TEXT       NOT NULL
, FOREIGN KEY(BaseLanguageCode) REFERENCES Language(LanguageCode)
);