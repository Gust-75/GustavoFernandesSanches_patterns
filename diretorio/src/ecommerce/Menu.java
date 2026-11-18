package ecommerce;
import ecommerce.builder.Computador;
import ecommerce.builder.ComputadorBuilder;
import ecommerce.service.CheckoutService;
import ecommerce.strategy.Cart;
import ecommerce.strategy.DiscountStrategy;
import ecommerce.strategy.FixedDiscount;
import ecommerce.strategy.NoDiscount;
import ecommerce.strategy.PercentageDiscount;
import java.util.Scanner;
public class Menu {
    private final Scanner scanner = new Scanner(System.in);
    private final CheckoutService checkout;
    private final Runnable demo;

    public Menu(CheckoutService checkout, Runnable demo) {
        this.checkout = checkout;
        this.demo = demo;
    }

    public void executar() {
        while (true) {
            System.out.println("\n===== LOJA DE COMPUTADORES =====");
            System.out.println("1 - Montar computador personalizado");
            System.out.println("2 - Executar cenários de demonstração");
            System.out.println("0 - Sair");
            int opcao = lerOpcao("Escolha: ", 0, 2);
            switch (opcao) {
                case 1 -> montarEVender();
                case 2 -> demo.run();
                default -> {
                    System.out.println("Até logo!");
                    return;
                }
            }
        }
    }

    private void montarEVender() {
        System.out.println("\n--- Montagem do computador ---");
        System.out.println("(Peças obrigatórias: processador, RAM e armazenamento)");
        ComputadorBuilder builder = new ComputadorBuilder();
        String nome = lerTextoObrigatorio("Processador - nome: ");
        builder.processador(nome, lerPreco("Processador - preço (R$): "));
        nome = lerTextoObrigatorio("RAM - nome: ");
        builder.ram(nome, lerPreco("RAM - preço (R$): "));
        nome = lerTextoObrigatorio("Armazenamento - nome: ");
        builder.armazenamento(nome, lerPreco("Armazenamento - preço (R$): "));
        nome = lerTextoOpcional("Placa de vídeo - nome (ENTER para pular): ");
        if (!nome.isEmpty()) {
            builder.placaVideo(nome, lerPreco("Placa de vídeo - preço (R$): "));
        }
        nome = lerTextoOpcional("Sistema operacional - nome (ENTER para pular): ");
        if (!nome.isEmpty()) {
            builder.sistemaOperacional(nome, lerPreco("Sistema operacional - preço (R$): "));
        }
        Computador computador = builder.build();
        Cart cart = new Cart(computador.getSubtotal());
        System.out.println("\nSubtotal das peças: R$ " + computador.getSubtotal());
        escolherDesconto(cart);
        // Permite trocar a estratégia em tempo de execução antes de fechar
        while (true) {
            System.out.println("\nTotal atual: R$ " + cart.total());
            System.out.println("1 - Finalizar venda");
            System.out.println("2 - Trocar desconto");
            if (lerOpcao("Escolha: ", 1, 2) == 1) {
                break;
            }
            escolherDesconto(cart);
        }
        System.out.println();
        checkout.finalizarVenda(computador, cart);
    }

    private void escolherDesconto(Cart cart) {
        System.out.println("\nTipo de desconto:");
        System.out.println("1 - Sem desconto");
        System.out.println("2 - Percentual (%)");
        System.out.println("3 - Valor fixo (R$)");
        int tipo = lerOpcao("Escolha: ", 1, 3);
        DiscountStrategy estrategia;
        switch (tipo) {
            case 2 -> estrategia = new PercentageDiscount(lerPreco("Percentual (ex: 10): "));
            case 3 -> estrategia = new FixedDiscount(lerPreco("Valor do desconto (R$): "));
            default -> estrategia = new NoDiscount();
        }
        cart.setDiscountStrategy(estrategia);
    }

    // ---------- Leitura segura de entradas ----------
    private String lerTextoOpcional(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private String lerTextoObrigatorio(String prompt) {
        while (true) {
            String texto = lerTextoOpcional(prompt);
            if (!texto.isEmpty()) {
                return texto;
            }
            System.out.println("Campo obrigatório.");
        }
    }

    private double lerPreco(String prompt) {
        while (true) {
            String texto = lerTextoOpcional(prompt).replace(',', '.');
            try {
                double valor = Double.parseDouble(texto);
                if (valor >= 0) {
                    return valor;
                }
                System.out.println("O valor não pode ser negativo.");
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Use números, ex: 1500 ou 1500,50");
            }
        }
    }

    private int lerOpcao(String prompt, int min, int max) {
        while (true) {
            String texto = lerTextoOpcional(prompt);
            try {
                int valor = Integer.parseInt(texto);
                if (valor >= min && valor <= max) {
                    return valor;
                }
            } catch (NumberFormatException ignored) {
                // cai na mensagem abaixo
            }
            System.out.println("Opção inválida. Digite um número de " + min + " a " + max + ".");
        }
    }
}