# 📚 Sistema de Leitura - Gutendex API

Sistema Spring Boot que consome a API Gutendex para gerenciar livros do Project Gutenberg com PostgreSQL.

## 🚀 Funcionalidades

* Buscar livros por título
* Listar livros e autores cadastrados
* Filtrar por idioma e período de vida dos autores
* API REST completa
* Console interativo

## 📋 Tecnologias

* Java 25 + Spring Boot 4.0.1
* PostgreSQL
* Gutendex API
* Maven

## 🔧 Instalação
```bash
# Clone o repositório
git clone https://github.com/marcuslaf/literAlura-challengejava
cd leitura

# Configure o PostgreSQL
psql -U postgres
CREATE DATABASE leituradb;
\q

# Configure a aplicação
# Edite application.properties com suas credenciais do PostgreSQL

# Execute
mvn spring-boot:run
```

## 📖 Como Usar

### Console Interativo

Aparecerá automaticamente ao executar:
```
=== MENU PRINCIPAL ===
1 - Buscar livro por título
2 - Listar livros registrados
3 - Listar autores registrados
4 - Listar autores vivos em determinado ano
5 - Listar livros em um determinado idioma
0 - Sair
```

### API REST
```
GET /api/           - Status da aplicação
GET /api/livros     - Lista todos os livros
GET /api/autores    - Lista todos os autores
GET /api/health     - Health check
POST /api/livros/cadastrar-teste - Cadastra livros de teste
```

### Exemplos de uso:
```bash
# Ver livros cadastrados
curl http://localhost:8080/api/livros

# Cadastrar livros de teste
curl -X POST http://localhost:8080/api/livros/cadastrar-teste

# Buscar livros no banco
curl "http://localhost:8080/api/livros/buscar?titulo=Sherlock"
```

## 🗃️ Banco de Dados

**Tabela:** `livros_registrados`

* id, gutenberg_id, titulo, autor, idioma, downloads

## 🐛 Comandos Úteis
```bash
# Ver livros no PostgreSQL
psql -U postgres -d leituradb -c "SELECT * FROM livros_registrados LIMIT 10;"

# Compilar
mvn clean install

# Executar
mvn spring-boot:run
```

## 📞 Suporte

### Erros comuns:

* **Database does not exist:** Crie o banco `leituradb`
* **Tabela não existe:** A aplicação criará automaticamente
* **Porta em uso:** Mude para 8081 em application.properties

---

⭐ Se este projeto foi útil, considere dar uma estrela!
