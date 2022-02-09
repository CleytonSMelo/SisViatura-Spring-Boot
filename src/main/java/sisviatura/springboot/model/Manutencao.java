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
import sisviatura.springboot.enumerador.StatusManutencao;
import sisviatura.springboot.enumerador.TipoCombustivel;

/**
 * Created by Cleyton Santos on 01/01/2022.
 */
@Entity
public class Manutencao implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	
	@NumberFormat(pattern = "###0.###")
	private String kmatual;
	
	@ManyToOne(fetch = FetchType.EAGER)   
	private Viatura viatura;
	
	private String problema;
	
	private String codManutencao;
	 
	private Boolean deletado;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataabertura;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate datafechamento;
	
	@ManyToOne(fetch = FetchType.EAGER)
    private Setor setor;
	
    @ManyToOne(fetch = FetchType.EAGER)
	private Usuarios responsavel;
	   
	private String solicitante;
    
	@NumberFormat(pattern = "###0.00")
	private Double custoManutencao;
		
	private Boolean aprovado;
	
	private Byte arquivo;
	
	@Enumerated(EnumType.STRING)
    private StatusManutencao statusmanutencao;
	
	@JsonManagedReference("nota-manutencao")
	@OneToMany(mappedBy = "manutencao", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Nota> notas;
	
	@JsonManagedReference("log-manutencao")
	@OneToMany(mappedBy = "manutencao", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<LogManutencao> logmanutencao = new ArrayList<>();

	@OneToMany(mappedBy = "manutencao", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Arquivo> arquivos = new ArrayList<>();
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}			

	public Viatura getViatura() {
		return viatura;
	}

	public void setViatura(Viatura viatura) {
		this.viatura = viatura;
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
		
	public Boolean getAprovado() {
		return aprovado;
	}

	public void setAprovado(Boolean aprovado) {
		this.aprovado = aprovado;
	}
	
	public String getKmatual() {
		return kmatual;
	}

	public void setKmatual(String kmatual) {
		this.kmatual = kmatual;
	}

	public String getProblema() {
		return problema;
	}

	public void setProblema(String problema) {
		this.problema = problema;
	}

	public LocalDate getDataabertura() {
		return dataabertura;
	}

	public void setDataabertura(LocalDate dataabertura) {
		this.dataabertura = dataabertura;
	}

	public LocalDate getDatafechamento() {
		return datafechamento;
	}

	public void setDatafechamento(LocalDate datafechamento) {
		this.datafechamento = datafechamento;
	}

	public String getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}

	public Double getCustoManutencao() {
		return custoManutencao;
	}

	public void setCustoManutencao(Double custoManutencao) {
		this.custoManutencao = custoManutencao;
	}

	public Byte getArquivo() {
		return arquivo;
	}

	public void setArquivo(Byte arquivo) {
		this.arquivo = arquivo;
	}

	
	public StatusManutencao getStatusmanutencao() {
		return statusmanutencao;
	}

	public void setStatusmanutencao(StatusManutencao statusmanutencao) {
		this.statusmanutencao = statusmanutencao;
	}

	public List<Nota> getNotas() {
		return notas;
	}

	public void setNotas(List<Nota> notas) {
		this.notas = notas;
	}			

	public List<LogManutencao> getLogmanutencao() {
		return logmanutencao;
	}

	public void setLogmanutencao(List<LogManutencao> logmanutencao) {
		this.logmanutencao = logmanutencao;
	}

	
	public List<Arquivo> getArquivos() {
		return arquivos;
	}

	public void setArquivos(List<Arquivo> arquivos) {
		this.arquivos = arquivos;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		
		return super.clone();
	}

	public String getCodManutencao() {
		return codManutencao;
	}

	public void setCodManutencao(String codManutencao) {
		this.codManutencao = codManutencao;
	}
	
	
}
