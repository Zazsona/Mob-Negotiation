CREATE TABLE ResponsePluginDependency
(
  ResponsePluginDependencyId    INT      NOT NULL     PRIMARY KEY
, ResponseId                    INT      NOT NULL
, PluginKey                     TEXT     NOT NULL
, FOREIGN KEY(ResponseId) REFERENCES Response(ResponseId)
);