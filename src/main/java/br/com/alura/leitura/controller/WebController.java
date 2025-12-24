package br.com.alura.leitura.controller;

import br.com.alura.leitura.model.Book;
import br.com.alura.leitura.model.LivroRegistrado;
import br.com.alura.leitura.service.GutendexService;
import br.com.alura.leitura.service.LeituraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class WebController {

    @Autowired
    private LeituraService leituraService;

    @Autowired
    private GutendexService gutendexService;

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> home() {
        try {
            List<LivroRegistrado> livros = leituraService.listarLivrosRegistrados();

            Map<String, Object> response = new HashMap<>();
            response.put("status", "sucesso");
            response.put("totalLivros", livros.size());
            response.put("mensagem", "API Gutendex integrada com sucesso!");
            response.put("banco", "PostgreSQL");
            response.put("data", java.time.LocalDateTime.now());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("status", "erro");
            error.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/livros")
    public ResponseEntity<Map<String, Object>> listarLivros() {
        try {
            List<LivroRegistrado> livros = leituraService.listarLivrosRegistrados();

            Map<String, Object> response = new HashMap<>();
            response.put("status", "sucesso");
            response.put("quantidade", livros.size());
            response.put("livros", livros);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("status", "erro");
            error.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> info() {
        Map<String, Object> response = new HashMap<>();
        response.put("mensagem", "API Gutendex integrada com sucesso!");
        response.put("status", "Conectado");
        response.put("banco", "PostgreSQL");
        response.put("endpoints", List.of(
                "/api/ - Página inicial",
                "/api/livros - Lista de livros",
                "/api/info - Informações da API"
        ));
        response.put("data", java.time.LocalDateTime.now());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> health = new HashMap<>();
        try {
            long count = leituraService.listarLivrosRegistrados().size();
            health.put("status", "UP");
            health.put("database", "Connected");
            health.put("totalBooks", count);
            health.put("timestamp", java.time.LocalDateTime.now());
            return ResponseEntity.ok(health);
        } catch (Exception e) {
            health.put("status", "DOWN");
            health.put("database", "Error: " + e.getMessage());
            health.put("timestamp", java.time.LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(health);
        }
    }

    @GetMapping("/autores")
    public ResponseEntity<Map<String, Object>> listarAutores() {
        try {
            List<String> autores = leituraService.listarAutoresRegistrados();

            Map<String, Object> response = new HashMap<>();
            response.put("status", "sucesso");
            response.put("quantidade", autores.size());
            response.put("autores", autores);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("status", "erro");
            error.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/livros/buscar")
    public ResponseEntity<Map<String, Object>> buscarLivrosPorTitulo(
            @RequestParam String titulo) {
        try {
            List<LivroRegistrado> livros = leituraService.buscarLivrosRegistradosPorTitulo(titulo);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "sucesso");
            response.put("busca", titulo);
            response.put("quantidade", livros.size());
            response.put("livros", livros);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("status", "erro");
            error.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PostMapping("/livros/cadastrar-teste")
    public ResponseEntity<Map<String, Object>> cadastrarLivrosTeste() {
        try {
            String[] titulos = {
                    "Sherlock Holmes",
                    "Pride and Prejudice",
                    "Romeo and Juliet",
                    "Dom Quixote",
                    "Great Expectations"
            };

            int cadastrados = 0;
            for (String titulo : titulos) {
                try {
                    List<Book> livros = gutendexService.buscarLivrosPorTitulo(titulo);
                    if (!livros.isEmpty()) {
                        leituraService.registrarLivro(livros.get(0));
                        cadastrados++;
                    }
                } catch (Exception e) {
                    System.err.println("Erro ao cadastrar " + titulo + ": " + e.getMessage());
                }
            }

            Map<String, Object> response = new HashMap<>();
            response.put("status", "sucesso");
            response.put("mensagem", cadastrados + " livros de teste cadastrados com sucesso!");
            response.put("cadastrados", cadastrados);
            response.put("tentados", titulos.length);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("status", "erro");
            error.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}