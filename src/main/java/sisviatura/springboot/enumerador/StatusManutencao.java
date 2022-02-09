package sisviatura.springboot.enumerador;

public enum StatusManutencao {
	CONCLUIDO("Concluido"),
	EM_EXECUCAO("Em Execução"),
	EM_ANALISE("Em Análise");

	private String nome;
	
	private StatusManutencao(String nome) {
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
