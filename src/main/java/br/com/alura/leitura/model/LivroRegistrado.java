package br.com.alura.leitura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "livros_registrados")
public class LivroRegistrado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Integer gutenbergId;

    private String titulo;
    private String autor;
    private String idioma;
    private Integer downloads;

    public LivroRegistrado() {}

    public LivroRegistrado(Book book) {
        this.gutenbergId = book.getId();
        this.titulo = book.getTitle();
        this.autor = book.getAuthors() != null && !book.getAuthors().isEmpty() ?
                book.getAuthors().get(0).getName() : "Desconhecido";
        this.idioma = book.getLanguages() != null && !book.getLanguages().isEmpty() ?
                book.getLanguages().get(0) : "Desconhecido";
        this.downloads = book.getDownload_count() != null ? book.getDownload_count() : 0;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getGutenbergId() { return gutenbergId; }
    public void setGutenbergId(Integer gutenbergId) { this.gutenbergId = gutenbergId; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }

    public Integer getDownloads() { return downloads; }
    public void setDownloads(Integer downloads) { this.downloads = downloads; }

    @Override
    public String toString() {
        return String.format("ID: %d | Título: %s | Autor: %s | Idioma: %s | Downloads: %d",
                gutenbergId, titulo, autor, idioma, downloads);
    }
}