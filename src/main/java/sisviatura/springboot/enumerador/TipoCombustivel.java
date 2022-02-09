package sisviatura.springboot.enumerador;

public enum TipoCombustivel {
	GAS("Gás"),
	ALCOOL("Álcool"),
	GASOLINA("Gasolina"),
	DIESEL("Diesel"),
	ELETRICO("Elétrico"),
	FLEX("Flex -> Álcool e Gasolina");

	private String nome;
	
	private TipoCombustivel(String nome) {
		this.nome = nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
	
	@Override
	public String toString() {
		return this.name();
	}
}
