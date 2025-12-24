package br.com.alura.leitura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Author {
    private String name;
    private Integer birth_year;
    private Integer death_year;

    public Author() {}

    public String getName() { return name; }
    public Integer getBirth_year() { return birth_year; }
    public Integer getDeath_year() { return death_year; }

    public void setName(String name) { this.name = name; }
    public void setBirth_year(Integer birth_year) { this.birth_year = birth_year; }
    public void setDeath_year(Integer death_year) { this.death_year = death_year; }

    @Override
    public String toString() {
        return String.format("Nome: %s | Nascimento: %s | Falecimento: %s",
                name,
                birth_year != null ? birth_year.toString() : "Desconhecido",
                death_year != null ? death_year.toString() : "Desconhecido");
    }
}