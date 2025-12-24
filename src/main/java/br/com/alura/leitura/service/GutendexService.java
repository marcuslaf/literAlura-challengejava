package br.com.alura.leitura.service;

import br.com.alura.leitura.model.ApiResponse;
import br.com.alura.leitura.model.Book;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class GutendexService {

    private static final String BASE_URL = "https://gutendex.com/books";
    private final RestTemplate restTemplate = new RestTemplate();

    public List<Book> buscarLivrosPorTitulo(String titulo) {
        String url = BASE_URL + "?search=" + titulo.replace(" ", "%20");

        try {
            ApiResponse response = restTemplate.getForObject(url, ApiResponse.class);
            return response != null && response.getResults() != null ? response.getResults() : new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Erro ao buscar livros por título: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<Book> listarTodosLivros() {
        List<Book> todosLivros = new ArrayList<>();
        String url = BASE_URL;

        while (url != null) {
            try {
                ApiResponse response = restTemplate.getForObject(url, ApiResponse.class);
                if (response != null && response.getResults() != null) {
                    todosLivros.addAll(response.getResults());
                    url = response.getNext();
                } else {
                    break;
                }
            } catch (Exception e) {
                System.err.println("Erro ao buscar livros: " + e.getMessage());
                break;
            }
        }

        return todosLivros;
    }

    public List<Book> buscarLivrosPorIdioma(String idioma) {
        String url = BASE_URL + "?languages=" + idioma.toLowerCase();

        try {
            ApiResponse response = restTemplate.getForObject(url, ApiResponse.class);
            return response != null && response.getResults() != null ? response.getResults() : new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Erro ao buscar livros por idioma: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<Book> buscarLivrosPorAutoresVivos(Integer ano) {
        String url = BASE_URL + "?author_year_start=" + Math.max(ano - 100, -1000) +
                "&author_year_end=" + ano;

        try {
            ApiResponse response = restTemplate.getForObject(url, ApiResponse.class);
            return response != null && response.getResults() != null ? response.getResults() : new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Erro ao buscar livros por autores vivos: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}