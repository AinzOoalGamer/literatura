package br.com.alura.literatura.repository;

import br.com.alura.literatura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNomeIgnoreCase(String nome);

    // Derived Query para buscar autores vivos em determinado ano
    // Busca onde o ano de nascimento é menor ou igual ao ano informado
    // E (o ano de falecimento é maior ou igual ao ano informado OU é nulo)
    List<Autor> findByAnoNascimentoLessThanEqualAndAnoFalecimentoGreaterThanEqualOrAnoFalecimentoIsNull(Integer ano, Integer ano2);
}