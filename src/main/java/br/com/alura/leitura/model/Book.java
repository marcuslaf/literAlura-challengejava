package br.com.alura.leitura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {
    private Integer id;
    private String title;
    private List<Author> authors;
    private List<String> languages;
    private Integer download_count;

    public Book() {}

    public Integer getId() { return id; }
    public String getTitle() { return title; }
    public List<Author> getAuthors() { return authors; }
    public List<String> getLanguages() { return languages; }
    public Integer getDownload_count() { return download_count; }

    public void setId(Integer id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthors(List<Author> authors) { this.authors = authors; }
    public void setLanguages(List<String> languages) { this.languages = languages; }
    public void setDownload_count(Integer download_count) { this.download_count = download_count; }

    @Override
    public String toString() {
        String authorNames = authors != null && !authors.isEmpty() ?
                authors.stream().map(Author::getName).reduce((a, b) -> a + ", " + b).orElse("Desconhecido") :
                "Desconhecido";
        return String.format("Título: %s | Autor: %s | Idioma: %s | Downloads: %d",
                title, authorNames,
                languages != null && !languages.isEmpty() ? languages.get(0) : "Desconhecido",
                download_count != null ? download_count : 0);
    }
}