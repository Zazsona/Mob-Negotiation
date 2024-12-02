CREATE TABLE ResponsePermissionDependency
(
  ResponsePermissionDependencyId    INT      NOT NULL     PRIMARY KEY
, ResponseId                        INT      NOT NULL
, PermissionKey                     TEXT     NOT NULL
, FOREIGN KEY(ResponseId) REFERENCES Response(ResponseId)
);