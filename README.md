# Sistema CRUD de Livros

Este sistema é uma aplicação CRUD (Create, Read, Update, Delete) que permite o gerenciamento de livros, com funcionalidades como:
- Cadastro de novos livros;
- Consulta de livros com paginação;
- Atualização de informações de livros;
- Exclusão de livros do sistema.

### Tecnologias Utilizadas
- Java 11+
- Spring Boot
- Spring Data JPA
- Banco de dados relacional (ex.: H2, MySQL, PostgreSQL)
- Maven para gerenciamento de dependências

### Pré-requisitos

Antes de iniciar, certifique-se de ter instalado:
- [Java 11+](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Maven](https://maven.apache.org/)
- Um banco de dados relacional, como H2, MySQL ou PostgreSQL

### Configuração do Projeto

1. Clone o repositório:
    ```bash
    git clone https://github.com/seu-usuario/seu-repositorio.git
    cd seu-repositorio
    ```

2. Compile o projeto usando Maven:
    ```bash
    mvn clean install
    ```

3. Execute a aplicação:
    ```bash
    mvn spring-boot:run
    ```

4. O sistema estará disponível em: `http://localhost:8080`

### Endpoints da API

#### 1. Listar todos os livros
- **GET** `/api/books`
- Parâmetros:
  - `page` (opcional): número da página (default: 0)
  - `size` (opcional): quantidade de livros por página (default: 10)
- Resposta: Uma lista paginada de livros.

#### Exemplo de Requisição:
```bash
GET http://localhost:8080/api/books?page=0&size=10
```

#### 2. Consultar livro por ID
- **GET** `/api/books/{id}`
- Parâmetros:
  - `id`: ID do livro
- Resposta: Detalhes do livro.

#### Exemplo de Requisição:
```bash
GET http://localhost:8080/api/books/1
```

#### 3. Criar um novo livro
- **POST** `/api/books`
- Corpo da Requisição: 
```json
{
  "title": "Livro Exemplo",
  "author": "Autor Exemplo",
  "genre": "Ficção",
  "publisher": "Editora Exemplo"
}
```
- Resposta: O livro criado.

### Execução de Testes

Para rodar os testes unitários da aplicação:

```bash
mvn test
```

Os testes garantirão o correto funcionamento dos serviços e das rotas da aplicação.

### Arquitetura do Sistema

O sistema é dividido nas seguintes camadas:

- **Controller**: Responsável por receber as requisições HTTP e direcioná-las para os serviços correspondentes.
- **Service**: Contém a lógica de negócios e manipula as interações com o repositório.
- **Repository**: Interface que interage com o banco de dados usando JPA.

Principais classes:
- `BookController`: Controla as requisições da API.
- `BookService`: Contém as regras de negócio para manipulação dos livros.
- `BookRepository`: Interface para realizar as operações CRUD no banco de dados.

### Modelo de Dados

#### Entidade `Book`
- `id`: Identificador único do livro.
- `title`: Título do livro (obrigatório).
- `author`: Autor do livro (obrigatório).
- `genre`: Gênero do livro (opcional).
- `publisher`: Editora do livro (opcional).

### Contribuição

Sinta-se à vontade para abrir issues ou pull requests no repositório. Todo tipo de contribuição é bem-vinda!

