create database camel;
create table negociacao (id BIGINT NOT NULL AUTO_INCREMENT, preco DECIMAL(5,2), quantidade MEDIUMINT, data DATETIME, primary key (id));
