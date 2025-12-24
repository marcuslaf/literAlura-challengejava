package br.com.alura.leitura.controller;

import br.com.alura.leitura.model.Book;
import br.com.alura.leitura.model.LivroRegistrado;
import br.com.alura.leitura.service.GutendexService;
import br.com.alura.leitura.service.LeituraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Controller
public class LeituraController {

    @Autowired
    private GutendexService gutendexService;

    @Autowired
    private LeituraService leituraService;

    private final Scanner scanner = new Scanner(System.in);

    public void iniciarAplicacao() {
        System.out.println("\nBEM-VINDO AO SISTEMA DE LEITURA COM GUTENDEX API");

        int opcao;
        do {
            exibirMenu();
            try {
                opcao = scanner.nextInt();
                scanner.nextLine(); // Limpar buffer

                switch (opcao) {
                    case 1:
                        buscarLivroPorTitulo();
                        break;
                    case 2:
                        listarLivrosRegistrados();
                        break;
                    case 3:
                        listarAutoresRegistrados();
                        break;
                    case 4:
                        listarAutoresVivosPorAno();
                        break;
                    case 5:
                        listarLivrosPorIdioma();
                        break;
                    case 0:
                        System.out.println("Obrigado por usar o sistema! Até logo!");
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Por favor, digite um número.");
                scanner.nextLine();
                opcao = -1;
            }
        } while (opcao != 0);
    }

    private void exibirMenu() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1 - Buscar livro por título");
        System.out.println("2 - Listar livros registrados");
        System.out.println("3 - Listar autores registrados");
        System.out.println("4 - Listar autores vivos em um determinado ano");
        System.out.println("5 - Listar livros em um determinado idioma");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private void buscarLivroPorTitulo() {
        System.out.print("\nDigite o título do livro: ");
        String titulo = scanner.nextLine();

        if (titulo.trim().isEmpty()) {
            System.out.println("Título não pode estar vazio!");
            return;
        }

        List<Book> livros = gutendexService.buscarLivrosPorTitulo(titulo);

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado com esse título.");
            return;
        }

        System.out.println("\nLivros encontrados:");
        for (int i = 0; i < livros.size(); i++) {
            System.out.println((i + 1) + ". " + livros.get(i).toString());
        }

        System.out.print("\nDeseja registrar algum livro? (s/n): ");
        String resposta = scanner.nextLine().toLowerCase();

        if (resposta.equals("s")) {
            try {
                System.out.print("Digite o número do livro: ");
                int numero = scanner.nextInt();
                scanner.nextLine();

                if (numero > 0 && numero <= livros.size()) {
                    leituraService.registrarLivro(livros.get(numero - 1));
                } else {
                    System.out.println("Número inválido.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Digite um número válido.");
                scanner.nextLine();
            }
        }
    }

    private void listarLivrosRegistrados() {
        List<LivroRegistrado> livros = leituraService.listarLivrosRegistrados();

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro registrado.");
            return;
        }

        System.out.println("\nLivros Registrados:");
        livros.forEach(System.out::println);
    }

    private void listarAutoresRegistrados() {
        List<String> autores = leituraService.listarAutoresRegistrados();

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor registrado.");
            return;
        }

        System.out.println("\nAutores Registrados:");
        autores.forEach(System.out::println);
    }

    private void listarAutoresVivosPorAno() {
        try {
            System.out.print("\nDigite o ano: ");
            int ano = scanner.nextInt();
            scanner.nextLine();

            if (ano < -3000 || ano > 2025) {
                System.out.println("Ano inválido! Digite um ano entre -3000 e 2025.");
                return;
            }

            List<Book> livros = gutendexService.buscarLivrosPorAutoresVivos(ano);

            if (livros.isEmpty()) {
                System.out.println("Nenhum livro encontrado com autores vivos nesse ano.");
                return;
            }

            System.out.println("\nLivros com autores vivos em " + ano + ":");
            livros.stream()
                    .flatMap(livro -> livro.getAuthors().stream()) // CORRIGIDO: getAuthors()
                    .distinct()
                    .forEach(System.out::println);

        } catch (InputMismatchException e) {
            System.out.println("Ano inválido! Digite um número válido.");
            scanner.nextLine();
        }
    }

    private void listarLivrosPorIdioma() {
        System.out.println("\nIdiomas disponíveis: en, pt, es, fr, de, it");
        System.out.print("Digite o código do idioma: ");
        String idioma = scanner.nextLine().toLowerCase();

        // Validar código do idioma
        if (!idioma.matches("^(en|pt|es|fr|de|it)$")) {
            System.out.println("Código de idioma inválido! Use: en, pt, es, fr, de, ou it");
            return;
        }

        List<Book> livrosApi = gutendexService.buscarLivrosPorIdioma(idioma);
        List<LivroRegistrado> livrosRegistrados = leituraService.buscarLivrosRegistradosPorIdioma(idioma);

        System.out.println("\nLivros encontrados na API:");
        if (livrosApi.isEmpty()) {
            System.out.println("Nenhum livro encontrado nesse idioma na API.");
        } else {
            livrosApi.stream().limit(10).forEach(System.out::println);
        }

        System.out.println("\nLivros registrados no sistema:");
        if (livrosRegistrados.isEmpty()) {
            System.out.println("Nenhum livro registrado nesse idioma.");
        } else {
            livrosRegistrados.forEach(System.out::println);
        }
    }
}