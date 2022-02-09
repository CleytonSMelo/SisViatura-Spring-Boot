package sisviatura.springboot.dto;

import java.time.LocalDate;

public class TotalServicoPorData {
  
	
	
	private Double Ano;
	private Double Mes;
	private Long totalservico;
	
	public TotalServicoPorData() {
		
	}
	
    public TotalServicoPorData(Double Ano, Double Mes, Long totalservico ) {
		this.Ano = Ano;
		this.Mes = Mes;
		this.totalservico = totalservico;
	}
	
    
	public Double getAno() {
		return Ano;
	}

	public void setAno(Double ano) {
		Ano = ano;
	}

	public Double getMes() {
		return Mes;
	}

	public void setMes(Double mes) {
		Mes = mes;
	}

	public Long getTotalservico() {
		return totalservico;
	}
	public void setTotalservico(Long totalservico) {
		this.totalservico = totalservico;
	}
	
	
}
