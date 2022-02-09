package sisviatura.springboot.enumerador;

public enum StatusCota {
	APROVADA("Aprovada"),
	RECUSADA("Recusada"),
	EM_ANALISE("Em Análise");

	private String nome;
	
	private StatusCota(String nome) {
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
