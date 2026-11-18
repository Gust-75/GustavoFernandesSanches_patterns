package ecommerce.service;
import ecommerce.adapter.Logger;
import ecommerce.builder.Computador;
import ecommerce.strategy.Cart;
/** Cliente desacoplado: conhece apenas a interface Logger. */
public class CheckoutService {
    private final Logger logger;

    public CheckoutService(Logger logger) {
        this.logger = logger;
    }

    public void finalizarVenda(Computador computador, Cart cart) {
        double total = cart.total();
        System.out.println("=== Resumo do Pedido ===");
        System.out.println(computador);
        System.out.println("Total com desconto: R$ " + total);
        System.out.println("========================");

        logger.info("Venda aprovada! Valor cobrado: R$ " + total);
    }
}