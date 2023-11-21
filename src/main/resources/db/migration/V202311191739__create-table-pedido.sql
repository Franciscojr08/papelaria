CREATE TABLE kit_livro
(
    id         SMALLINT UNSIGNED PRIMARY KEY auto_increment,
    nome       VARCHAR(50)       not null,
    descricao  VARCHAR(255)      null,
    valor      DECIMAL(10, 2)    not null,
    quantidade SMALLINT UNSIGNED not null,
    ativo      boolean           not null default true
);

CREATE TABLE livro
(
    id            SMALLINT UNSIGNED PRIMARY KEY auto_increment,
    identificador VARCHAR(50) UNIQUE not null,
    nome          VARCHAR(100)       not null,
    uso_interno   BOOLEAN            not null,
    valor         DECIMAL(10, 2)     not null,
    quantidade    SMALLINT UNSIGNED  not null,
    serie_id      SMALLINT UNSIGNED  null,
    ativo         boolean            not null default true
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

alter table pedido
    add constraint pdo_cle1 foreign key (cliente_id) references cliente (id);

CREATE TABLE pedido_livro
(
    id         SMALLINT UNSIGNED PRIMARY KEY auto_increment,
    quantidade int               not null,
    pedido_id  SMALLINT UNSIGNED not null,
    livro_id   SMALLINT UNSIGNED not null
);

alter table pedido_livro
    add constraint pdr_pdo1 foreign key (pedido_id) references pedido (id);
alter table pedido_livro
    add constraint pdr_lvo1 foreign key (livro_id) references livro (id);

CREATE TABLE pedido_kit_livro
(
    id          SMALLINT UNSIGNED PRIMARY KEY auto_increment,
    quantidade  int               not null,
    pedido_id   SMALLINT UNSIGNED not null,
    kitlivro_id SMALLINT UNSIGNED not null
);

alter table pedido_kit_livro
    add constraint pdv_pdo1 foreign key (pedido_id) references pedido (id);
alter table pedido_kit_livro
    add constraint pdv_lvo1 foreign key (kitlivro_id) references kit_livro (id);