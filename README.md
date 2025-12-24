# 📚 Projeto Literatura – Catálogo de Livros

Este projeto é uma **aplicação de console** desenvolvida em **Java com Spring Boot** que consome a API pública **Gutendex** para catalogar informações sobre **livros e autores** do Projeto Gutenberg, persistindo esses dados em um banco de dados relacional **PostgreSQL**.

---

## 🚀 Funcionalidades

O sistema oferece as seguintes funcionalidades ao usuário:

- **Buscar livro por título**  
  Consulta a API Gutendex e retorna o primeiro livro encontrado.

- **Listar livros registrados**  
  Exibe todos os livros salvos no banco de dados.

- **Listar autores registrados**  
  Mostra todos os autores cujos livros foram buscados.

- **Listar autores vivos em determinado ano**  
  Filtra autores que estavam vivos com base nas datas de nascimento e falecimento.

- **Estatísticas por idioma**  
  Conta e lista quantos livros existem cadastrados em um idioma específico (ex.: `en`, `pt`, `es`).

---

## 🛠 Tecnologias Utilizadas

- **Java 24 (LTS)**
- **Spring Boot 4.0.1**
- Spring Web
- **Spring Data JPA** – Persistência de dados
- **Maven** – Gerenciamento de dependências
- **PostgreSQL** – Banco de dados relacional
- **Jackson** – Desserialização de dados JSON
- **Java HTTP Client** – Requisições HTTP para a API Gutendex
--

## 📄 Pré-requisitos

Antes de executar o projeto, certifique-se de ter instalado:

- **Java Development Kit (JDK)** versão **17 ou superior**
- **Maven** (opcional, pois IDEs como o IntelliJ IDEA já possuem integração)
- **PostgreSQL** instalado e em execução
---

## ⚙️ Configuração e Instalação

### 1️⃣ Clonar o repositório

Abra o terminal e execute os comandos abaixo:

Bash
git clone https://github.com/AinzOoalGamer/literatura.git
cd literatura

## 2️⃣ Configurar o Banco de Dados
O projeto requer um banco de dados PostgreSQL configurado. Acesse o seu gerenciador (pgAdmin ou SQL Shell) e crie o banco:

SQL

CREATE DATABASE literatura_db;

## 3️⃣ Configurar a Aplicação
Localize o arquivo de configuração em: src/main/resources/application.properties.

Edite as credenciais de acordo com o seu ambiente local:

Properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literatura_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

## 4️⃣ Executar o Projeto
Pelo IntelliJ IDEA
Abra a classe LiteraturaApplication.java.

Clique no botão Run (ícone de play verde) ao lado do método main.

Via Terminal (Maven)
Bash
mvn spring-boot:run

## 📸 Como Usar
Após iniciar a aplicação, um menu interativo será exibido diretamente no console:

Plaintext

*** LITERATURA - CATÁLOGO DE LIVROS ***

**1 - Buscar livro pelo título**
--
**2 - Listar livros registrados**
--
**3 - Listar autores registrados**
--
**4 - Listar autores vivos em um determinado ano**
--
**5 - Contar livros por idioma**
--
**0 - Sair**
Instrução: Digite o número da opção desejada e siga as orientações que
aparecerão no console.
---

## 🔎 Exemplo de Funcionamento

Ao buscar um livro, informe o nome completo ou parte dele (ex.: "Sherlock Holmes"). 
O fluxo de processamento do sistema segue estas etapas:

Consulta: O sistema acessa a API Gutendex.

Persistência: Os dados retornados são salvos automaticamente no seu banco de dados local.

Exibição: As informações detalhadas do livro e do autor são formatadas e exibidas no terminal.

---

## 🤝 Autor

Desenvolvido como parte do Bootcamp Java da Alura.

Ricardo - https://github.com/AinzOoalGamer.com

---

## 📝 Licença
Este projeto está licenciado sob a Licença MIT. Veja o arquivo LICENSE para mais detalhes.
