package ecommerce.builder;
public class Computador {
    private final String processador;
    private final double precoProcessador;
    private final String ram;
    private final double precoRam;
    private final String armazenamento;
    private final double precoArmazenamento;
    private final String placaVideo;          // opcional
    private final double precoPlacaVideo;
    private final String sistemaOperacional;  // opcional
    private final double precoSistemaOperacional;
    Computador(ComputadorBuilder b) {
        this.processador = b.processador;
        this.precoProcessador = b.precoProcessador;
        this.ram = b.ram;
        this.precoRam = b.precoRam;
        this.armazenamento = b.armazenamento;
        this.precoArmazenamento = b.precoArmazenamento;
        this.placaVideo = b.placaVideo;
        this.precoPlacaVideo = b.precoPlacaVideo;
        this.sistemaOperacional = b.sistemaOperacional;
        this.precoSistemaOperacional = b.precoSistemaOperacional;
    }
    public double getSubtotal() {
        return precoProcessador + precoRam + precoArmazenamento
                + precoPlacaVideo + precoSistemaOperacional;
    }
    private String linha(String nome, double preco) {
        return nome == null ? "N/A" : nome + " (R$ " + preco + ")";
    }
    @Override
    public String toString() {
        return "Processador: " + linha(processador, precoProcessador) + "\n"
                + "RAM: " + linha(ram, precoRam) + "\n"
                + "Armazenamento: " + linha(armazenamento, precoArmazenamento) + "\n"
                + "Placa de vídeo: " + linha(placaVideo, precoPlacaVideo) + "\n"
                + "Sistema operacional: " + linha(sistemaOperacional, precoSistemaOperacional) + "\n"
                + "Subtotal das peças: R$ " + getSubtotal();
    }
}