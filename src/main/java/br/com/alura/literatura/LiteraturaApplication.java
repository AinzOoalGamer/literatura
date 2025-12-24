package br.com.alura.literatura;

import br.com.alura.literatura.model.Autor;
import br.com.alura.literatura.model.Livro;
import br.com.alura.literatura.repository.AutorRepository;
import br.com.alura.literatura.repository.LivroRepository;
import br.com.alura.literatura.service.ConsumoApi;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class LiteraturaApplication implements CommandLineRunner {

    private final LivroRepository livroRepositorio;
    private final AutorRepository autorRepositorio;
    private final ConsumoApi consumoApi;

    // Injeção de dependência via construtor
    public LiteraturaApplication(LivroRepository livroRepositorio, AutorRepository autorRepositorio) {
        this.livroRepositorio = livroRepositorio;
        this.autorRepositorio = autorRepositorio;
        this.consumoApi = new ConsumoApi(livroRepositorio, autorRepositorio);
    }

    public static void main(String[] args) {
        SpringApplication.run(LiteraturaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner leitura = new Scanner(System.in);
        var opcao = -1;

        while (opcao != 0) {
            var menu = """
                    \n
                    *** LITERATURA - CATÁLOGO DE LIVROS ***
                    
                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Contar livros por idioma
                    
                    0 - Sair
                    """;

            System.out.println(menu);
            try {
                opcao = leitura.nextInt();
                leitura.nextLine(); // Consumir a quebra de linha

                switch (opcao) {
                    case 1:
                        buscarLivroPeloTitulo(leitura);
                        break;
                    case 2:
                        listarLivrosRegistrados();
                        break;
                    case 3:
                        listarAutoresRegistrados();
                        break;
                    case 4:
                        listarAutoresVivosEmAno(leitura);
                        break;
                    case 5:
                        contarLivrosPorIdioma(leitura);
                        break;
                    case 0:
                        System.out.println("Saindo da aplicação...");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("Erro ao processar opção: " + e.getMessage());
                leitura.nextLine(); // Limpar buffer em caso de erro de input
            }
        }
        leitura.close();
    }

    private void buscarLivroPeloTitulo(Scanner leitura) {
        System.out.println("Digite o nome do livro para busca: ");
        var nomeLivro = leitura.nextLine();
        consumoApi.buscarLivroPorTitulo(nomeLivro);
    }

    private void listarLivrosRegistrados() {
        List<Livro> livros = livroRepositorio.findAll();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro registrado yet.");
        } else {
            livros.forEach(System.out::println);
        }
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = autorRepositorio.findAll();
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor registrado yet.");
        } else {
            autores.forEach(a -> System.out.println("Nome: " + a.getNome() +
                    " | Nascimento: " + a.getAnoNascimento() +
                    " | Falecimento: " + a.getAnoFalecimento()));
        }
    }

    private void listarAutoresVivosEmAno(Scanner leitura) {
        System.out.println("Digite o ano para verificar autores vivos: ");
        try {
            int ano = leitura.nextInt();
            // Passamos o ano duas vezes para a derived query: <= anoNascimento AND >= anoFalecimento OR NULL
            List<Autor> autores = autorRepositorio.findByAnoNascimentoLessThanEqualAndAnoFalecimentoGreaterThanEqualOrAnoFalecimentoIsNull(ano, ano);

            if (autores.isEmpty()) {
                System.out.println("Nenhum autor encontrado vivo no ano " + ano);
            } else {
                System.out.println("Autores vivos em " + ano + ":");
                autores.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Entrada inválida para o ano.");
            leitura.nextLine();
        }
    }

    private void contarLivrosPorIdioma(Scanner leitura) {
        System.out.println("Digite o idioma para buscar (ex: en, pt, es, fr): ");
        String idioma = leitura.nextLine().trim().toLowerCase();

        List<Livro> livros = livroRepositorio.findByIdioma(idioma);
        System.out.println("Quantidade de livros em '" + idioma + "': " + livros.size());

        if (!livros.isEmpty()) {
            livros.forEach(l -> System.out.println("- " + l.getTitulo()));
        }
    }
}