![Java](https://img.shields.io/badge/Java-21-blue?logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.4-green?logo=spring&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-4.0.0-red?logo=apache-maven&logoColor=white)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)


Um sistema robusto e escalável para monitoramento contínuo do status e performance de serviços web (APIs e Sites). Construído com Java e Spring Boot, este projeto oferece uma base sólida para garantir a confiabilidade dos seus serviços e dependências externas.


---


## 📋 Tabela de Conteúdos


* [Visão Geral](#-visão-geral)

* [Principais Funcionalidades](#-principais-funcionalidades)

* [Arquitetura do Sistema](#-arquitetura-do-sistema)

* [Tecnologias Utilizadas](#-tecnologias-utilizadas)

* [Roadmap e Visão de Futuro](#-roadmap-e-visão-de-futuro)

* [Começando](#-começando)

    * [Pré-requisitos](#pré-requisitos)

    * [Configuração](#configuração)

    * [Execução](#execução)

* [Uso da API](#-uso-da-api)

* [Como Contribuir](#-como-contribuir)

* [Licença](#-licença)

* [Contato](#-contato)


---


## 📝 Visão Geral


Em um ecossistema de microsserviços e integrações, a falha de um único serviço pode impactar todo o sistema. Este projeto nasceu da necessidade de ter uma ferramenta simples, porém poderosa, para verificar ativamente a saúde e o tempo de resposta de endpoints HTTP, registrar seu histórico e expor esses dados de forma clara e intuitiva.


---


## ✨ Principais Funcionalidades


* **Monitoramento Contínuo**: Realiza checagens de status e tempo de resposta em intervalos de tempo fixos.

* **Persistência de Histórico**: Salva cada verificação em um banco de dados MySQL, incluindo status code, timestamp e tempo de resposta.

* **Configuração Externa**: As URLs a serem monitoradas são facilmente configuradas em um arquivo `application.properties`.

* **API RESTful**: Expõe os dados coletados através de um endpoint `GET` de fácil consumo.

* **Arquitetura Limpa**: Organizado em camadas (Controller, Service, Data) para facilitar a manutenção e a expansão.


---


## 🏗️ Arquitetura do Sistema


A aplicação é projetada seguindo os princípios da arquitetura em camadas para garantir a separação de responsabilidades e a testabilidade do código.


---


## 🛠️ Tecnologias Utilizadas


* **Backend**: Java 21, Spring Boot 3.5.4

* **Persistência**: Spring Data JPA, MySQL (via XAMPP)

* **API**: Spring Web (RESTful)

* **Build & Dependências**: Apache Maven

* **Utilitários**: Lombok


---


## 🗺️ Roadmap e Visão de Futuro


Este projeto foi construído com a escalabilidade em mente. As fases abaixo descrevem o caminho de evolução do sistema.


### Fase 1: Fundação (Concluída ✅)

- [x] API REST para consulta de status.

- [x] Arquitetura em 3 camadas (Controller, Service, Repository).

- [x] Lógica de verificação de API com `HttpClient`.


### Fase 2: Aplicação Robusta (Estado Atual ✔️)

- [x] **Banco de Dados Persistente**: Migração para **MySQL (XAMPP)**.

- [x] **Agendamento de Tarefas**: Checagens periódicas e contínuas com **Spring Scheduler (`@Scheduled`)**.

- [x] **Configuração Externa**: URLs de monitoramento gerenciadas via `application.properties`.

- [x] **Métrica de Performance**: Medição e persistência do tempo de resposta de cada checagem.

- [ ] **Sistema de Notificações**: Integrar com serviços de E-mail ou Slack para enviar alertas de falha.


### Fase 3: Portal de Status Visual (Próximos Passos)

- [ ] **Dashboard de "Flashcards" (Frontend)**: Construir um portal com **React**, **Vue** ou **Angular** com um layout de cartões para cada serviço, permitindo aos usuários:

    - **Filtrar por Tipo e Nome**: Navegar entre abas de "APIs" e "Sites" e usar uma barra de busca para encontrar um serviço específico.

    - **Visualizar Status por Cores**:

        - **Verde**: Serviço online e com resposta rápida.

        - **Amarelo**: Serviço online, mas com lentidão.

        - **Vermelho**: Serviço fora do ar ou com erro (`4xx`/`5xx`).

    - **Gerenciar Serviços via Interface**: Adicionar, editar e remover APIs/Sites a serem monitorados através de formulários no site.

- [ ] **Autenticação de Usuários**: Implementar **Spring Security** para que cada usuário gerencie sua própria lista de serviços.



---


## 🚀 Começando


Siga estas instruções para obter uma cópia do projeto e executá-lo em sua máquina local.


### Pré-requisitos


* **Java Development Kit (JDK)** - Versão 21 ou superior

* **Apache Maven** - Versão 3.6 ou superior

* **Git** para clonar o repositório

* **XAMPP** - Com o módulo **MySQL** iniciado.


### Configuração


1.  **Crie o Banco de Dados:**

    * Abra o **phpMyAdmin** do seu XAMPP.

    * Crie um novo banco de dados chamado `api_monitor_db`.


2.  **Configure a Conexão:**

    * No projeto, navegue até `src/main/resources/` e abra o arquivo `application.properties`.

    * Garanta que ele contenha as seguintes propriedades (ajuste o usuário e a senha se o seu XAMPP não usar o padrão `root` com senha vazia):


    ```properties

    # Configuração do Banco de Dados MySQL (XAMPP)

    spring.datasource.url=jdbc:mysql://localhost:3306/api_monitor_db?serverTimezone=UTC

    spring.datasource.username=root

    spring.datasource.password=

    

    # Configuração do Hibernate (JPA)

    spring.jpa.hibernate.ddl-auto=update

    spring.jpa.show-sql=true

    

    # Lista de APIs para o monitoramento (separadas por vírgula)

    monitor.urls=[https://www.google.com],[https://api.github.com],[https://httpstat.us/503]

    ```


### Execução


1.  **Clone o repositório**

    ```sh

    git clone [https://github.com/SEU_USUARIO/SEU_REPOSITORIO.git](https://github.com/SEU_USUARIO/SEU_REPOSITORIO.git)

    ```


2.  **Navegue até o diretório do projeto**

    ```sh

    cd SEU_REPOSITORIO

    ```


3.  **Execute a aplicação com o Maven Wrapper**

    * No Linux/macOS:

        ```sh

        ./mvnw spring-boot:run

        ```

    * No Windows:

        ```sh

        mvnw.cmd spring-boot:run

        ```


4.  A aplicação estará rodando em `http://localhost:8080`.


---


## 🔌 Uso da API


A API expõe os dados de monitoramento de forma simples.


### Listar Todos os Status


Retorna uma lista de todos os resultados de checagem salvos no banco de dados.


* **URL**: `/api/status`

* **Método**: `GET`

* **Exemplo de Resposta (JSON)**:

    ```json

    [

      {

        "id": 1,

        "url": "[https://www.google.com](https://www.google.com)",

        "statusCode": 200,

        "timestamp": "2025-08-20T12:02:10.123456",

        "responseTimeMillis": 150

      },

      {

        "id": 2,

        "url": "[https://api.github.com](https://api.github.com)",

        "statusCode": 503,

        "timestamp": "2025-08-20T12:02:11.789123",

        "responseTimeMillis": 1234

      }

    ]

    ```


---


## 🤝 Como Contribuir


Contribuições são o que tornam a comunidade de código aberto um lugar incrível para aprender, inspirar e criar. Qualquer contribuição que você fizer será **muito apreciada**.


1.  Faça um **Fork** do projeto

2.  Crie uma **Branch** para sua feature (`git checkout -b feature/AmazingFeature`)

3.  Faça o **Commit** de suas alterações (`git commit -m 'Add some AmazingFeature'`)

4.  Faça o **Push** para a Branch (`git push origin feature/AmazingFeature`)

5.  Abra um **Pull Request**


---


## 📄 Licença


Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.


---


## 📫 Contato


Gustavo Freitas - [LinkedIn](https://www.linkedin.com/in/gustavofirmino/) - gustavofirmino501124@gmail.com


Link do Projeto: [https://github.com/gustavoFreitass/api-monitor](https://github.com/gustavoFreitass/api-monitor) 
