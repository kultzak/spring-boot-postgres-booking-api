## REST API para cadastro de hospedes e checkins com Spring Boot, PostgreSQL, JPA, Hibernate.

## Para rodar localmente

**1. Clonar o repositório**

```bash
git clone https://github.com/kultzak/spring-boot-postgres-booking-api.git
```

**2. Configurar o PostgreSQL**

Criar um banco de dados chamado `hotel_ooking`. Abrir
`src/main/resources/application.properties` e modificar o usuário e senha para o
utilizado pelo banco em sua instalação do PostgreSQL.

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

**3. Utilização da API**

### Inserir hóspede:
```bash
POST http://localhost:8080/api/hospedes/register
Exemplo de input:     
{
    "name": "Guadalupe",
    "document": "000002",
    "phone": "999 818181"
}
```

### Atualizar hóspede:
```bash
PUT http://localhost:8080/api/hospedes/{hospede id}
Exemplo de input:     
{
    "name": "Guadalupe Josefina",
    "document": "000002",
    "phone": "999 818181"
}

```

### Excluir hóspede:
```bash
DELETE http://localhost:8080/api/hospedes/{hospede id}
```

### Pesquisar hóspede:
```bash
# retorna a lista completa de hóspedes paginada:
GET http://localhost:8080/api/hospedes/

# pesquisa por nome, identidade ou telefone: 
http://localhost:8080/api/hospedes/q/?name=fulano
http://localhost:8080/api/hospedes/q/?document=00001
http://localhost:8080/api/hospedes/q/?phone=789 111
```