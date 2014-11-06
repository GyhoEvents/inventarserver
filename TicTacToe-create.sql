create table users (
  id                        integer auto_increment not null,
  username                  varchar(255),
  password                  varchar(255),
  mail                      varchar(255),
  groupid                   integer,
  last_update               datetime not null,
  constraint pk_users primary key (id))
;



