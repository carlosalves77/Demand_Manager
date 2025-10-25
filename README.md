# DemandManager

Sistema de Gestão de Projetos e Demandas.

Este é um projeto de API RESTful desenvolvido com Java 17 e Spring Boot 3, focado no gerenciamento de projetos e demandas. A aplicação utiliza Spring Security com JWT para autenticação, Spring Data JPA para persistência de dados e está containerizada com Docker.

## Tecnologias Utilizadas

  * **Backend:**
      * Java 17
      * Spring Boot 3.5.6
      * Spring Web (REST API)
      * Spring Data JPA (Hibernate)
      * Spring Security (com autenticação JWT - `com.auth0:java-jwt`)
      * Spring Boot Validation
  * **Banco de Dados:**
      * PostgreSQL
  * **Documentação da API:**
      * Springdoc (Swagger/OpenAPI)
  * **Build/Dependências:**
      * Maven
  * **Ambiente/Deploy:**
      * Docker
      * Docker Compose

## Pré-requisitos

Para executar este projeto localmente, você precisará ter as seguintes ferramentas instaladas:

  * [Java 17 (JDK)](https://www.google.com/search?q=https://www.oracle.com/java/technologies/downloads/%23java17)
  * [Maven](https://maven.apache.org/download.cgi)
  * [Docker](https://www.docker.com/get-started)
  * [Docker Compose](https://docs.docker.com/compose/install/)

## Como Executar (Ambiente Docker)

A forma mais simples de subir a aplicação completa (API + Banco de Dados) é usando o Docker Compose.

### 1\. Clone o Repositório

```bash
git clone https://github.com/carlosalves77/Demand_Manager.git
cd DemandManager
```

### 2\. Crie uma variável de Ambiente

Este projeto precisa de variáveis de ambiente devido a dados sensíveis (usuários e senhas) no `docker-compose.yml`.

Crie variáveis de ambiente para os seguintes conteúdos:

```ini
# Credenciais do Banco de Dados Postgres
postgres_user=<usuariobanco>
postgres_password=<senhadobanco>
postgres_database=<nomedobanco>

# Credenciais que a aplicação Spring usará
spring_user=<usuario_spring>
spring_password=<senhadousuario>
```


### 3\. Suba os Containers

Execute o comando abaixo na raiz do projeto. Ele irá construir a imagem da aplicação (usando o `Dockerfile`) e iniciar os serviços da API e do banco de dados.

```bash
docker-compose up -d --build
```

### 4\. Acesse a Aplicação

Após os containers iniciarem, a aplicação estará disponível em:
`http://localhost:4000`

## Documentação da API (Swagger)

Com a aplicação rodando, você pode acessar a documentação interativa da API (Swagger UI) no seu navegador. O `pom.xml` inclui a dependência `springdoc-openapi`.

  * **Swagger UI:** [http://localhost:4000/swagger-ui.html](https://www.google.com/search?q=http://localhost:4000/swagger-ui.html)
  * **Definição OpenAPI (JSON):** [http://localhost:4000/v3/api-docs](https://www.google.com/search?q=http://localhost:4000/v3/api-docs)

    ```

A aplicação estará disponível em `http://localhost:4000`.
