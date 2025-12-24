package br.com.alura.literatura.service;

import br.com.alura.literatura.dto.DadosAutor;
import br.com.alura.literatura.dto.DadosLivro;
import br.com.alura.literatura.dto.DadosResultado;
import br.com.alura.literatura.model.Autor;
import br.com.alura.literatura.model.Livro;
import br.com.alura.literatura.repository.AutorRepository;
import br.com.alura.literatura.repository.LivroRepository;
import com.fasterxml.jackson.databind.ObjectMapper; // Import corrigido aqui

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

public class ConsumoApi {

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    private final LivroRepository livroRepositorio;
    private final AutorRepository autorRepositorio;

    public ConsumoApi(LivroRepository livroRepositorio, AutorRepository autorRepositorio) {
        this.livroRepositorio = livroRepositorio;
        this.autorRepositorio = autorRepositorio;
    }

    public void buscarLivroPorTitulo(String titulo) {
        String endereco = "https://gutendex.com/books/?search=" + titulo.replace(" ", "%20");

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endereco))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String json = response.body();

                // Correção: ObjectMapper resolve o JSON agora
                DadosResultado dados = mapper.readValue(json, DadosResultado.class);

                if (dados.results() != null && !dados.results().isEmpty()) {
                    // Pega o primeiro resultado
                    DadosLivro dadosLivro = dados.results().get(0);

                    // Pega o primeiro autor da lista
                    DadosAutor dadosAutor = dadosLivro.authors().get(0);

                    // Salvar ou buscar Autor (agora usando o construtor criado na etapa 2)
                    Autor autor = autorRepositorio.findByNomeIgnoreCase(dadosAutor.nome())
                            .orElseGet(() -> autorRepositorio.save(new Autor(dadosAutor)));

                    // Salvar Livro
                    Livro livro = new Livro(dadosLivro, autor);

                    // Verifica duplicatas usando o método criado na etapa 1
                    Optional<Livro> livroExistente = livroRepositorio.findByTitulo(livro.getTitulo());
                    if (livroExistente.isEmpty()) {
                        livroRepositorio.save(livro);
                        System.out.println("\nLivro salvo com sucesso!");
                        System.out.println(livro);
                    } else {
                        System.out.println("\nO livro já consta na base de dados.");
                        System.out.println(livroExistente.get());
                    }

                } else {
                    System.out.println("Nenhum livro encontrado para o título: " + titulo);
                }
            } else {
                System.out.println("Erro na conexão com a API: " + response.statusCode());
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro ao buscar dados da API", e);
        }
    }
}