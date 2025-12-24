package br.com.alura.literatura.model;

import br.com.alura.literatura.dto.DadosLivro;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "livros")
@JsonIgnoreProperties(ignoreUnknown = true) // Ignora campos do JSON que não existem na classe
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String idioma; // Simplificação: apenas o primeiro idioma
    private Integer numeroDownloads;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    // Construtores, Getters e Setters
    public Livro() {}

    public Livro(DadosLivro dados, Autor autor) {
        this.titulo = dados.titulo();
        this.idioma = dados.idiomas().get(0); // Pega o primeiro idioma conforme desafio
        this.numeroDownloads = dados.numeroDownloads();
        this.autor = autor;
    }

    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getIdioma() { return idioma; }
    public Integer getNumeroDownloads() { return numeroDownloads; }
    public Autor getAutor() { return autor; }

    public void setId(Long id) { this.id = id; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setIdioma(String idioma) { this.idioma = idioma; }
    public void setNumeroDownloads(Integer numeroDownloads) { this.numeroDownloads = numeroDownloads; }
    public void setAutor(Autor autor) { this.autor = autor; }

    @Override
    public String toString() {
        return "----- LIVRO -----\n" +
                "Título: " + titulo + "\n" +
                "Autor: " + (autor != null ? autor.getNome() : "Desconhecido") + "\n" +
                "Idioma: " + idioma + "\n" +
                "Downloads: " + numeroDownloads + "\n";
    }
}
