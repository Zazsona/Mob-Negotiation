CREATE TABLE ResponsePluginDependency
(
  ResponsePluginDependencyId    INT      NOT NULL     PRIMARY KEY
, ResponseId                    INT      NOT NULL
, PluginNamespacedKey           TEXT     NOT NULL
, FOREIGN KEY(ResponseId) REFERENCES Response(ResponseId)
);