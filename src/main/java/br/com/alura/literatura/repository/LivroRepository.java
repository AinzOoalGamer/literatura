package br.com.alura.literatura.repository;

import br.com.alura.literatura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    List<Livro> findByIdioma(String idioma);
    Optional<Livro> findByTitulo(String titulo);

    // Opcional: Top 10 mais baixados
    List<Livro> findTop10ByOrderByNumeroDownloadsDesc();
}