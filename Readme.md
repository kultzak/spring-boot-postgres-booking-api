## REST API para cadastro de hospedes e checkins com Spring Boot, PostgreSQL, JPA, Hibernate.

## Para rodar localmente

**1. Clonar o repositório**

```bash
git clone https://github.com/callicoder/spring-boot-postgresql-jpa-hibernate-rest-api-demo.git
```

**2. Configurar o PostgreSQL**

Criar um banco de dados chamado `hotel_ooking`. Abrir `src/main/resources/application.properties` e modificar o usuário 
e senha para o utilizado pelo banco em sua instalação do PostgreSQL.

**3. Rodar a API**

Digitar o seguinte comando na raiz do projeto:

```bash
mvn spring-boot:run
```

ou o comando seguinte para empacotar a aplicação como JAR e rodar.

```bash
mvn clean package
java -jar target/postgres-demo-0.0.1-SNAPSHOT.jar
```