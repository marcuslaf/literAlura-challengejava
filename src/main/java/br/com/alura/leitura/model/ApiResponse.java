package br.com.alura.leitura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse {
    private Integer count;
    private String next;
    private String previous;
    private List<Book> results;

    public ApiResponse() {}

    public Integer getCount() { return count; }
    public String getNext() { return next; }
    public String getPrevious() { return previous; }
    public List<Book> getResults() { return results; }

    public void setCount(Integer count) { this.count = count; }
    public void setNext(String next) { this.next = next; }
    public void setPrevious(String previous) { this.previous = previous; }
    public void setResults(List<Book> results) { this.results = results; }
}