package sisviatura.springboot.enumerador;

public enum NaturezaViatura {
	CARACTERIZADO("Carcterizado"),
	DESCARCTERIZADO("Descaracterizado");

	private String nome;
	
	private NaturezaViatura(String nome) {
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
