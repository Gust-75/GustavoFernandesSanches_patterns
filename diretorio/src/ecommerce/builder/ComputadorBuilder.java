package ecommerce.builder;
public class ComputadorBuilder {
    String processador;
    double precoProcessador;
    String ram;
    double precoRam;
    String armazenamento;
    double precoArmazenamento;
    String placaVideo;
    double precoPlacaVideo;
    String sistemaOperacional;
    double precoSistemaOperacional;
    public ComputadorBuilder processador(String nome, double preco) {
        this.processador = nome;
        this.precoProcessador = preco;
        return this;
    }
    public ComputadorBuilder ram(String nome, double preco) {
        this.ram = nome;
        this.precoRam = preco;
        return this;
    }
    public ComputadorBuilder armazenamento(String nome, double preco) {
        this.armazenamento = nome;
        this.precoArmazenamento = preco;
        return this;
    }
    public ComputadorBuilder placaVideo(String nome, double preco) {
        this.placaVideo = nome;
        this.precoPlacaVideo = preco;
        return this;
    }
    public ComputadorBuilder sistemaOperacional(String nome, double preco) {
        this.sistemaOperacional = nome;
        this.precoSistemaOperacional = preco;
        return this;
    }
    public Computador build() {
        if (processador == null || ram == null || armazenamento == null) {
            throw new IllegalStateException(
                    "Processador, RAM e armazenamento são obrigatórios.");
        }
        return new Computador(this);
    }
}