CREATE TABLE serie
(
    id               SMALLINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    nome             VARCHAR(50) NOT NULL,
    data_cadastro    DATETIME NOT NULL,
    data_atualizacao DATETIME NULL,
    ativo            BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE turma
(
    id               SMALLINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    nome             VARCHAR(50) NOT NULL,
    serie_id         SMALLINT UNSIGNED NOT NULL,
    data_cadastro    DATETIME NOT NULL,
    data_atualizacao DATETIME NULL,
    ativo            BOOLEAN NOT NULL DEFAULT TRUE
);

ALTER TABLE turma ADD CONSTRAINT turma_serie_1 FOREIGN KEY (serie_id) REFERENCES serie (id);

CREATE TABLE kit_livro
(
    id                    SMALLINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    nome                  VARCHAR(50) NOT NULL,
    descricao             VARCHAR(255) NULL,
    valor                 DECIMAL(10, 2) NOT NULL,
    quantidade_disponivel SMALLINT UNSIGNED NOT NULL,
    data_cadastro         DATETIME NOT NULL,
    data_atualizacao      DATETIME NULL,
    ativo                 BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE livro
(
    id                    SMALLINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    identificador         VARCHAR(50) UNIQUE NOT NULL,
    nome                  VARCHAR(100) NOT NULL,
    uso_interno           BOOLEAN NOT NULL,
    valor                 DECIMAL(10, 2) NOT NULL,
    quantidade_disponivel SMALLINT UNSIGNED NOT NULL,
    serie_id              SMALLINT UNSIGNED NULL,
    data_cadastro         DATETIME NOT NULL,
    data_atualizacao      DATETIME NULL,
    ativo                 BOOLEAN NOT NULL DEFAULT TRUE
);

ALTER TABLE livro ADD CONSTRAINT livro_serie_1 FOREIGN KEY (serie_id) REFERENCES serie (id);

CREATE TABLE cliente
(
    id                SMALLINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    nome              VARCHAR(100) NOT NULL,
    cpf               VARCHAR(11) NOT NULL,
    telefone          VARCHAR(11) NOT NULL,
    email             VARCHAR(50) NOT NULL,
    cep               VARCHAR(8) NOT NULL,
    logradouro        VARCHAR(100) NOT NULL,
    bairro            VARCHAR(50) NOT NULL,
    cidade            VARCHAR(50) NOT NULL,
    estado            VARCHAR(50) NOT NULL,
    responsavel_aluno BOOLEAN NOT NULL,
    data_cadastro     DATETIME NOT NULL,
    data_atualizacao  DATETIME NULL,
    ativo             BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE aluno
(
    id                     SMALLINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    nome                   VARCHAR(100) NOT NULL,
    matricula              VARCHAR(50) NULL,
    rg                     VARCHAR(20) NULL,
    cpf                    VARCHAR(11) NULL,
    turma_id               SMALLINT UNSIGNED NOT NULL,
    cliente_id_responsavel SMALLINT UNSIGNED NOT NULL,
    data_cadastro          DATETIME NOT NULL,
    data_atualizacao       DATETIME NULL,
    ativo                  BOOLEAN NOT NULL DEFAULT TRUE
);

ALTER TABLE aluno ADD CONSTRAINT aluno_turma_1 FOREIGN KEY (turma_id) REFERENCES turma (id);
ALTER TABLE aluno ADD CONSTRAINT aluno_cliente_1 FOREIGN KEY (cliente_id_responsavel) REFERENCES cliente (id);

CREATE TABLE pedido
(
    id               SMALLINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    valor            DECIMAL(10, 2) NOT NULL,
    desconto         DECIMAL(10, 2) NULL DEFAULT 0.0,
    situacao_pedido  VARCHAR(50) NOT NULL,
    forma_pagamento  VARCHAR(50) NOT NULL,
    cliente_id       SMALLINT UNSIGNED NOT NULL,
    data_pedido      DATETIME NOT NULL,
    data_entrega     DATETIME NULL,
    data_atualizacao DATETIME NULL,
    ativo            BOOLEAN NOT NULL DEFAULT TRUE
);

ALTER TABLE pedido ADD CONSTRAINT pedido_cliente_1 FOREIGN KEY (cliente_id) REFERENCES cliente (id);

CREATE TABLE pedido_livro
(
    id                  SMALLINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    quantidade          INT               NOT NULL,
    quantidade_entregue INT UNSIGNED DEFAULT 0,
    valor_unitario      DECIMAL(10, 2)    NOT NULL,
    pedido_id           SMALLINT UNSIGNED NOT NULL,
    livro_id            SMALLINT UNSIGNED NOT NULL
);

ALTER TABLE pedido_livro ADD CONSTRAINT pedidoLivro_pedido_1 FOREIGN KEY (pedido_id) REFERENCES pedido (id);
ALTER TABLE pedido_livro ADD CONSTRAINT pedidoLivro_livro_1 FOREIGN KEY (livro_id) REFERENCES livro (id);

CREATE TABLE pedido_kit_livro
(
    id                  SMALLINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    quantidade          INT               NOT NULL,
    quantidade_entregue INT UNSIGNED DEFAULT 0,
    valor_unitario      DECIMAL(10, 2)    NOT NULL,
    pedido_id           SMALLINT UNSIGNED NOT NULL,
    kit_livro_id        SMALLINT UNSIGNED NOT NULL
);

ALTER TABLE pedido_kit_livro ADD CONSTRAINT pedidoKitLivro_pedido_1 FOREIGN KEY (pedido_id) REFERENCES pedido (id);
ALTER TABLE pedido_kit_livro ADD CONSTRAINT pedidoKitLivro_kitLivro_1 FOREIGN KEY (kit_livro_id) REFERENCES kit_livro (id);

CREATE TABLE lista_pendencia
(
    id               SMALLINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    data_cadastro    DATETIME NOT NULL,
    data_entrega     DATETIME NULL,
    data_atualizacao DATETIME NULL,
    situacao         VARCHAR(50) NOT NULL,
    pedido_id        SMALLINT UNSIGNED NOT NULL,
    ativo            BOOLEAN NOT NULL DEFAULT TRUE
);

ALTER TABLE lista_pendencia ADD CONSTRAINT listaPendencia_pedido_1 FOREIGN KEY (pedido_id) REFERENCES pedido (id);

CREATE TABLE lista_pendencia_livro
(
    id                  SMALLINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    quantidade          INT UNSIGNED      NOT NULL,
    quantidade_entregue INT UNSIGNED DEFAULT 0,
    lista_pendencia_id  SMALLINT UNSIGNED NOT NULL,
    livro_id            SMALLINT UNSIGNED NOT NULL
);

ALTER TABLE lista_pendencia_livro ADD CONSTRAINT listaPendenciaLivro_listaPendencia_1 FOREIGN KEY (lista_pendencia_id) REFERENCES lista_pendencia (id);
ALTER TABLE lista_pendencia_livro ADD CONSTRAINT listaPendenciaLivro_livro_1 FOREIGN KEY (livro_id) REFERENCES livro (id);

CREATE TABLE lista_pendencia_kit_livro
(
    id                  SMALLINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    quantidade          INT UNSIGNED      NOT NULL,
    quantidade_entregue INT UNSIGNED DEFAULT 0,
    lista_pendencia_id  SMALLINT UNSIGNED NOT NULL,
    kit_livro_id        SMALLINT UNSIGNED NOT NULL
);

ALTER TABLE lista_pendencia_kit_livro ADD CONSTRAINT listaPendenciaKitLivro_listaPendencia_1 FOREIGN KEY (lista_pendencia_id) REFERENCES lista_pendencia (id);
ALTER TABLE lista_pendencia_kit_livro ADD CONSTRAINT listaPendenciaKitLivro_kitLivro_1 FOREIGN KEY (kit_livro_id) REFERENCES kit_livro (id);
