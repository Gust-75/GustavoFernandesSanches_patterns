package ecommerce;
import ecommerce.adapter.LegacyLogger;
import ecommerce.adapter.LegacyLoggerAdapter;
import ecommerce.adapter.Logger;
import ecommerce.builder.Computador;
import ecommerce.builder.ComputadorBuilder;
import ecommerce.service.CheckoutService;
import ecommerce.strategy.Cart;
import ecommerce.strategy.FixedDiscount;
import ecommerce.strategy.NoDiscount;
import ecommerce.strategy.PercentageDiscount;
public class Main {
    public static void main(String[] args) {
        Logger logger = new LegacyLoggerAdapter(new LegacyLogger());
        CheckoutService checkout = new CheckoutService(logger);
        new Menu(checkout, () -> executarCenarios(checkout)).executar();
    }
    private static void executarCenarios(CheckoutService checkout) {
        System.out.println();
        // Cenário 1: computador básico, sem desconto
        Computador basico = new ComputadorBuilder()
                .processador("Intel Core i5", 1000.0)
                .ram("8GB", 250.0)
                .armazenamento("256GB SSD", 250.0)
                .build();
        Cart cart1 = new Cart(basico.getSubtotal());
        cart1.setDiscountStrategy(new NoDiscount());
        checkout.finalizarVenda(basico, cart1);
        System.out.println();
        // Cenário 2: gamer, troca de estratégia em tempo de execução
        Computador gamer = new ComputadorBuilder()
                .processador("Intel Core i9", 3000.0)
                .ram("32GB", 800.0)
                .armazenamento("1TB SSD", 600.0)
                .placaVideo("NVIDIA RTX 4070", 4000.0)
                .build();
        Cart cart2 = new Cart(gamer.getSubtotal());
        cart2.setDiscountStrategy(new PercentageDiscount(10));
        cart2.setDiscountStrategy(new FixedDiscount(500.0));
        checkout.finalizarVenda(gamer, cart2);
    }
}