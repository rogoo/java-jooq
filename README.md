# java-jooq
Um projeto com jOOQ (Java Object Orientated Query).

Neste projeto estou utilizando o exemplo do próprio site do jOOQ, com a tabela Author, mas adicionei uma tabela com FK para fazer consultas mais divertidas.

## Criando Banco e Tabelas (docker)
Utilizei **docker** para criar o banco utilizando *Dockerfile* e um arquivo SQL com os.... tanananannnn... SQLs para criar a estrutura do banco.

Aqui o Dockerfile.
```
FROM mysql:9.2.0
LABEL maintainer="Rodrigo rosa (rogoo)"
COPY ddl_jooq.sql /docker-entrypoint-initdb.d
```

E aquiiiiiiii o arquivo SQL.
```
CREATE DATABASE `library`;

USE `library`;

CREATE TABLE `author` (
  `id` int AUTO_INCREMENT NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);

insert into author(`first_name`, `last_name`)values('Rodrigo', 'rosa Rogoo');
insert into author(`first_name`, `last_name`)values('Asdf', 'de coisa');
insert into author(`first_name`, `last_name`)values('Padawan', 'Rosa');
insert into author(`first_name`, `last_name`)values('Star', 'Warssss');

CREATE TABLE `post` (
  `id` int AUTO_INCREMENT NOT NULL,
  `assunto` varchar(255) DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `id_author` int NOT NULL,
  `time` datetime DEFAULT NULL,
  `time_disabled` datetime DEFAULT NULL,
  PRIMARY KEY (id),
  constraint `FK_POST_AUTHOR` FOREIGN KEY (id_author) REFERENCES author(id)
);

insert into post(`assunto`, `descricao`, `id_author`, `time`, `time_disabled`)values('Motocas', 'São muito legais de legais de legal e de legalzaozao', 1, now(), date_add(now(),interval 1 day));
insert into post(`assunto`, `descricao`, `id_author`, `time`)values('Peixe', 'Todos bem lindoes', 1, now());
insert into post(`assunto`, `descricao`, `id_author`, `time`)values('Carros sao legais ou nao?', 'Carros sao bem legais e vamos que vamos', 1, now());
insert into post(`assunto`, `descricao`, `id_author`, `time`, `time_disabled`)values('Nuvem', 'Formada pro agua e mais paranaues', 2, now(), date_add(now(),interval 2 day));
insert into post(`assunto`, `descricao`, `id_author`)values('Testando', 'Falando sobre testes e mais testes', 3);
```

## Geração do Código
Utilizei a geração via Maven, já apontando para a **main** e gerando o pacote ***br.rosa.rjooq.generated***. Favor dê uma olhadela no pom.xml.

## Configuração e Obtenção do Famigerado DSLContext
Primeiro precisa de um DSLContext, e neste projeto utilizei o construtor DSL.using(conexao, dialect), bem simplezão e fácil de configurar.

Existem vários construtores para ***DSL.using()***, e para projetos maiores em Spring ou Java EE, partiu usar javax.sql.DataSource e abaixo um exemplo.
```
public static final AtomicReference<JooqContext> BEAN = new AtomicReference<>();

@Resource(lookup = ConstanteJNDI.DATASOURCE)
protected DataSource ds;

private ThreadLocal<DSLContext> dslCtx = ThreadLocal.withInitial(() -> {
  Configuration config = new DefaultConfiguration();

  config.set(new DefaultExecuteListenerProvider(new JooqContextPrettyPrinter()));
  config.set(new DataSourceConnectionProvider(this.ds));
  config.set(SQLDialect. MYSQL);

  return DSL.using(config);
});

public DSLContext dsl() {
  return this. dslCtx.get();
}

public Configuration configuration() {
  return this. dslCtx.get().configuration();
}
```

jOOQ também "cafunciona" (=funciona) com R2DBC.

