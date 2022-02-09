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
import sisviatura.springboot.enumerador.StatusVistoria;
import sisviatura.springboot.enumerador.TipoCombustivel;

/**
 * Created by Cleyton Santos on 01/01/2022.
 */

@Entity
public class Vistoria implements Serializable{
	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	
	@NumberFormat(pattern = "###0.###")
	private String kmatual;
	
	@ManyToOne(fetch = FetchType.EAGER)   
	private Viatura viatura;
	
	private String motivo;
	
	private String codVistoria;
	 
	private Boolean deletado;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate datacadastro;	
	
	@ManyToOne(fetch = FetchType.EAGER)
    private Setor unidadeOrigem;
	
	@ManyToOne(fetch = FetchType.EAGER)
    private Setor unidadeDestino;
	
    @ManyToOne(fetch = FetchType.EAGER)
	private Usuarios responsavel;
	   
	private String solicitante;		
    		
	@Enumerated(EnumType.STRING)
    private StatusVistoria statusvistoria;

	@JsonManagedReference("nota-vistoria")
	@OneToMany(mappedBy = "vistoria", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Nota> notas;
	
	@JsonManagedReference("log-vistoria")
	@OneToMany(mappedBy = "vistoria", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<LogVistoria> logvistoria = new ArrayList<>();
	
	@OneToMany(mappedBy = "vistoria", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Fotos> fotos = new ArrayList<>();

	
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
		
	public String getKmatual() {
		return kmatual;
	}

	public void setKmatual(String kmatual) {
		this.kmatual = kmatual;
	}

	public String getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}

	public List<Nota> getNotas() {
		return notas;
	}

	public void setNotas(List<Nota> notas) {
		this.notas = notas;
	}				

	public String getCodVistoria() {
		return codVistoria;
	}

	public void setCodVistoria(String codVistoria) {
		this.codVistoria = codVistoria;
	}

	public LocalDate getDatacadastro() {
		return datacadastro;
	}

	public void setDatacadastro(LocalDate datacadastro) {
		this.datacadastro = datacadastro;
	}

	public Setor getUnidadeOrigem() {
		return unidadeOrigem;
	}

	public void setUnidadeOrigem(Setor unidadeOrigem) {
		this.unidadeOrigem = unidadeOrigem;
	}

	public Setor getUnidadeDestino() {
		return unidadeDestino;
	}

	public void setUnidadeDestino(Setor unidadeDestino) {
		this.unidadeDestino = unidadeDestino;
	}

	public List<LogVistoria> getLogvistoria() {
		return logvistoria;
	}

	public void setLogvistoria(List<LogVistoria> logvistoria) {
		this.logvistoria = logvistoria;
	}
	
	public List<Fotos> getFotos() {
		return fotos;
	}

	public void setFotos(List<Fotos> fotos) {
		this.fotos = fotos;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	
	public StatusVistoria getStatusvistoria() {
		return statusvistoria;
	}

	public void setStatusvistoria(StatusVistoria statusvistoria) {
		this.statusvistoria = statusvistoria;
	}
}
