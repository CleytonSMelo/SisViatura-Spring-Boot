package sisviatura.springboot.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import sisviatura.springboot.enumerador.StatusCota;
import sisviatura.springboot.enumerador.TipoCombustivel;

/**
 * Created by Cleyton Santos on 01/01/2022.
 */
@Entity
public class Cota implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	
	@NumberFormat(pattern = "###0.###")
	private String kmviaturaatual;
	
	@JsonBackReference("cota-viatura")
	@ManyToOne(fetch = FetchType.EAGER)   
	private Viatura viatura;
	
	private String motivo;
	
	@NumberFormat(pattern = "###0.00")
	private Double quantidade;
	
	@NumberFormat(pattern = "###0.00")
	private Double percentualdotanque;
	 
	private Boolean deletado;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate datacadastro;
	
	@ManyToOne(fetch = FetchType.EAGER)
    private Setor setor;
	
    @ManyToOne(fetch = FetchType.EAGER)
	private Usuarios responsavel;
	
	private Boolean aprovado;
	
	@Enumerated(EnumType.STRING)
    private StatusCota statuscota;
	
	@JsonManagedReference("log-cota")
	@OneToMany(mappedBy = "cota", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<LogCota> logcota = new ArrayList<>();

	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}	
	
	public String getKmviaturaatual() {
		return kmviaturaatual;
	}

	public void setKmviaturaatual(String kmviaturaatual) {
		this.kmviaturaatual = kmviaturaatual;
	}

	public Viatura getViatura() {
		return viatura;
	}

	public void setViatura(Viatura viatura) {
		this.viatura = viatura;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}			

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	public Usuarios getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Usuarios responsavel) {
		this.responsavel = responsavel;
	}

	public Boolean getDeletado() {
		return deletado;
	}

	public void setDeletado(Boolean deletado) {
		this.deletado = deletado;
	}
	
	public LocalDate getDatacadastro() {
		return datacadastro;
	}

	public void setDatacadastro(LocalDate datacadastro) {
		this.datacadastro = datacadastro;
	}
	
	public Boolean getAprovado() {
		return aprovado;
	}

	public void setAprovado(Boolean aprovado) {
		this.aprovado = aprovado;
	}
	
	public StatusCota getStatuscota() {
		return statuscota;
	}

	public void setStatuscota(StatusCota statuscota) {
		this.statuscota = statuscota;
	}
	
	public List<LogCota> getLogcota() {
		return logcota;
	}

	public void setLogcota(List<LogCota> logcota) {
		this.logcota = logcota;
	}		

	public Double getPercentualdotanque() {
		return percentualdotanque;
	}

	public void setPercentualdotanque(Double percentualdotanque) {
		this.percentualdotanque = percentualdotanque;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		
		return super.clone();
	}
	
	
}
