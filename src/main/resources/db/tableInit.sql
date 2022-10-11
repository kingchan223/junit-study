drop table if exists book;
create table book (
                      id bigint not null auto_increment,
                      author varchar(20) not null,
                      title varchar(50) not null,
                      primary key (id)
);