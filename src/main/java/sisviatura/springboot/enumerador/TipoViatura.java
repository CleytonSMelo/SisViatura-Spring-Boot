package sisviatura.springboot.enumerador;

public enum TipoViatura {
	LOCADO("Locado"),
	PROPIO("Própio"),
	CAUTELADO("Cautelado"),
	CEDIDO("Cedido");

	private String nome;
	
	private TipoViatura(String nome) {
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
