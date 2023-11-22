CREATE TABLE serie
(
    id   SMALLINT UNSIGNED PRIMARY KEY auto_increment,
    nome varchar(50) not null
);

CREATE TABLE turma
(
    id       SMALLINT UNSIGNED PRIMARY KEY auto_increment,
    nome     varchar(50)       not null,
    serie_id SMALLINT UNSIGNED not null
);

alter table turma add constraint turma_serie_1 foreign key (serie_id) references serie (id);

CREATE TABLE kit_livro
(
    id                    SMALLINT UNSIGNED PRIMARY KEY auto_increment,
    nome                  VARCHAR(50)       not null,
    descricao             VARCHAR(255)      null,
    valor                 DECIMAL(10, 2)    not null,
    quantidade_disponivel SMALLINT UNSIGNED not null,
    ativo                 boolean           not null default true
);

CREATE TABLE livro
(
    id                    SMALLINT UNSIGNED PRIMARY KEY auto_increment,
    identificador         VARCHAR(50) UNIQUE not null,
    nome                  VARCHAR(100)       not null,
    uso_interno           BOOLEAN            not null,
    valor                 DECIMAL(10, 2)     not null,
    quantidade_disponivel SMALLINT UNSIGNED  not null,
    serie_id              SMALLINT UNSIGNED  null,
    ativo                 boolean            not null default true
);

CREATE TABLE cliente
(
    id                SMALLINT UNSIGNED PRIMARY KEY auto_increment,
    nome              VARCHAR(100) not null,
    cpf               VARCHAR(11)  not null,
    telefone          VARCHAR(11)  not null,
    email             VARCHAR(50)  not null,
    cep               VARCHAR(8)   not null,
    logradouro        VARCHAR(100) not null,
    bairro            VARCHAR(50)  not null,
    cidade            VARCHAR(50)  not null,
    estado            VARCHAR(50)  not null,
    responsavel_aluno BOOLEAN      not null,
    ativo             boolean      not null default true
);

create table aluno
(
    id                     SMALLINT UNSIGNED PRIMARY KEY auto_increment,
    nome                   varchar(100)      not null,
    matricula              varchar(50)       null,
    rg                     varchar(20)       null,
    cpf                    VARCHAR(11)       null,
    turma_id               SMALLINT UNSIGNED not null,
    cliente_id_responsavel SMALLINT UNSIGNED not null,
    ativo                  boolean           not null default true
);

alter table aluno add constraint aluno_turma_1 foreign key (turma_id) references turma (id);
alter table aluno add constraint aluno_cliente_1 foreign key (cliente_id_responsavel) references cliente (id);

CREATE TABLE pedido
(
    id              SMALLINT UNSIGNED PRIMARY KEY auto_increment,
    data_pedido     DATE              not null,
    valor           DECIMAL(10, 2)    not null,
    desconto        DECIMAL(4, 2)     null     default 0.0,
    situacao_pedido varchar(50)       not null,
    forma_pagamento varchar(50)       not null,
    data_entrega    DATE              null,
    cliente_id      SMALLINT UNSIGNED not null,
    ativo           boolean           not null default true
);

alter table pedido add constraint pedido_cliente_1 foreign key (cliente_id) references cliente (id);

CREATE TABLE pedido_livro
(
    id             SMALLINT UNSIGNED PRIMARY KEY auto_increment,
    quantidade     int               not null,
    valor_unitario decimal(10, 2)    not null,
    pedido_id      SMALLINT UNSIGNED not null,
    livro_id       SMALLINT UNSIGNED not null
);

alter table pedido_livro add constraint pedidoLivro_pedido_1 foreign key (pedido_id) references pedido (id);
alter table pedido_livro add constraint pedidoLivro_livro_1 foreign key (livro_id) references livro (id);

CREATE TABLE pedido_kit_livro
(
    id             SMALLINT UNSIGNED PRIMARY KEY auto_increment,
    quantidade     int               not null,
    valor_unitario decimal(10, 2)    not null,
    pedido_id      SMALLINT UNSIGNED not null,
    kitlivro_id    SMALLINT UNSIGNED not null
);

alter table pedido_kit_livro add constraint pedidoLitLivro_pedido_1 foreign key (pedido_id) references pedido (id);
alter table pedido_kit_livro add constraint pedidoLitLivro_livro_1 foreign key (kitlivro_id) references kit_livro (id);