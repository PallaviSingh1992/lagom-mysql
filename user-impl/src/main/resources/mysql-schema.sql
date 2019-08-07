create database mae_users_mgt;

DROP TABLE IF EXISTS mae_users_mgt.journal;

CREATE TABLE IF NOT EXISTS mae_users_mgt.journal (
  ordering SERIAL,
  persistence_id VARCHAR(255) NOT NULL,
  sequence_number BIGINT NOT NULL,
  deleted BOOLEAN DEFAULT FALSE,
  tags VARCHAR(255) DEFAULT NULL,
  message BLOB NOT NULL,
  PRIMARY KEY(persistence_id, sequence_number)
);

DROP TABLE IF EXISTS mae_users_mgt.snapshot;

CREATE TABLE IF NOT EXISTS mae_users_mgt.snapshot (
  persistence_id VARCHAR(255) NOT NULL,
  sequence_number BIGINT NOT NULL,
  created BIGINT NOT NULL,
  snapshot BLOB NOT NULL,
  PRIMARY KEY (persistence_id, sequence_number)
);

DROP TABLE IF EXISTS mae_users_mgt.users;

create table mae_users_mgt.users(
   id VARCHAR(100) NOT NULL,
   name VARCHAR(100) NOT NULL,
   mobile_1 VARCHAR(40) NOT NULL,
   company VARCHAR(40) NOT NULL,
   pin_no VARCHAR(40) NOT NULL,
   vat_no VARCHAR(40) NOT NULL,
   PRIMARY KEY (mobile_1)
);

