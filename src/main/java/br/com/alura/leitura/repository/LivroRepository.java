package br.com.alura.leitura.repository;

import br.com.alura.leitura.model.LivroRegistrado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<LivroRegistrado, Long> {
    Optional<LivroRegistrado> findByGutenbergId(Integer gutenbergId);

    @Query("SELECT l FROM LivroRegistrado l WHERE LOWER(l.titulo) LIKE LOWER(CONCAT('%', :titulo, '%'))")
    List<LivroRegistrado> findByTituloContainingIgnoreCase(@Param("titulo") String titulo);

    @Query("SELECT l FROM LivroRegistrado l WHERE LOWER(l.idioma) = LOWER(:idioma)")
    List<LivroRegistrado> findByIdiomaIgnoreCase(@Param("idioma") String idioma);

    @Query("SELECT DISTINCT l.autor FROM LivroRegistrado l ORDER BY l.autor")
    List<String> findAutoresRegistrados();
}