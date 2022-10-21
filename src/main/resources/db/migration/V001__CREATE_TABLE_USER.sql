create table user
(
    id          bigint       not null auto_increment,
    name        varchar(80)  not null,
    email       varchar(255) not null,
    password    varchar(255) not null,
    date_create datetime     not null,

    primary key (id)
) engine = InnoDB
  default charset = utf8;
