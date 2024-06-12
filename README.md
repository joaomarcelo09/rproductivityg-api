# TodoList Project

## Descrição

Este projeto é uma aplicação de lista de tarefas (TodoList) desenvolvida em Java utilizando Spring Boot, JPA, Maven e MySQL. A aplicação permite que os usuários criem, deletem e listem tarefas, compartilhem tarefas para visualização apenas e apliquem filtros nas listas de tarefas.

## Funcionalidades

- **Criar, Deletar e Listar Tarefas**
  - Um usuário pode criar novas tarefas.
  - Um usuário pode deletar tarefas existentes.
  - Um usuário pode listar todas as suas tarefas.

- **Compartilhamento de Tarefas**
  - Um usuário pode compartilhar uma tarefa com outro usuário, permitindo apenas a visualização.
  - Apenas o criador da tarefa tem permissão para modificá-la.

- **Filtragem e Ordenação de Tarefas**
  - As tarefas podem ser filtradas por:
    - Dia
    - Semana
    - Todas as tarefas sem filtro
  - As tarefas são ordenadas da que vai expirar primeiro para a que vai expirar por último.

- **Detalhes do Card de Tarefa**
  - Cada card de tarefa exibe:
    - Nome da tarefa
    - Descrição da tarefa
    - Tempo restante para a expiração da tarefa

- **Prioridades das Tarefas**
  - As tarefas possuem diferentes níveis de prioridade que são visualmente representados por cores:
    - Prioridade 1: Verde
    - Prioridade 2: Laranja
    - Prioridade 3: Vermelho

## Estrutura do Projeto

1. **Modelos de Dados**
    - Usuário
    - Tarefa
        - Nome
        - Descrição
        - Data de Criação
        - Data de Expiração
        - Prioridade (1, 2, 3)
        - Criador
        - Visualizadores

2. **Interface do Usuário**
    - Formulário para criação de tarefas.
    - Lista de tarefas com filtros e ordenação.
    - Cards de tarefas exibindo nome, descrição e tempo restante.

## Tecnologias Utilizadas

- **Backend**
  - Framework: [Spring Boot](https://spring.io/projects/spring-boot)
  - ORM: [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
  - Banco de Dados: [MySQL](https://www.mysql.com/)
  - Build: [Maven](https://maven.apache.org/)

## Instalação e Configuração

1. **Clone o repositório:**
   ```sh
   git clone https://github.com/seu-usuario/todolist.git
   cd todolist

2. **Configuração do Banco de Dados:**
   - Certifique-se de que o Docker está instalado e em execução.
   - Inicie o contêiner do MySQL com Docker Compose:
     ```sh
     docker-compose up
     ```

3. **Construção e Execução do Backend:**
   - Compile o projeto com Maven.
     ```sh
     mvn clean install
     ```
   - Inicie o servidor do Spring Boot.
     ```sh
     mvn spring-boot:run
     ```

4. **Acessar a aplicação:**
   - Abra o navegador e acesse `http://localhost:8080` para usar a aplicação.

## Licença

Este projeto está licenciado sob a [MIT License](LICENSE).

---

Feito com ❤️ por [João Gomes](https://github.com/joaomarcelo09)

