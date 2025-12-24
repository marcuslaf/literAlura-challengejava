package br.com.alura.leitura.service;

import br.com.alura.leitura.model.Book;
import br.com.alura.leitura.model.LivroRegistrado;
import br.com.alura.leitura.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeituraService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private GutendexService gutendexService;

    public void registrarLivro(Book book) {
        if (livroRepository.findByGutenbergId(book.getId()).isEmpty()) {
            LivroRegistrado livro = new LivroRegistrado(book);
            livroRepository.save(livro);
            System.out.println("✅ Livro registrado com sucesso!");
        } else {
            System.out.println("ℹ️ Livro já está registrado.");
        }
    }

    public List<LivroRegistrado> listarLivrosRegistrados() {
        return livroRepository.findAll();
    }

    public List<String> listarAutoresRegistrados() {
        return livroRepository.findAutoresRegistrados();
    }

    public List<LivroRegistrado> buscarLivrosRegistradosPorTitulo(String titulo) {
        return livroRepository.findByTituloContainingIgnoreCase(titulo);
    }

    public List<LivroRegistrado> buscarLivrosRegistradosPorIdioma(String idioma) {
        return livroRepository.findByIdiomaIgnoreCase(idioma);
    }
}