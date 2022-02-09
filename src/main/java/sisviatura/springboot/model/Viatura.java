package sisviatura.springboot.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import sisviatura.springboot.enumerador.NaturezaViatura;
import sisviatura.springboot.enumerador.TipoCombustivel;
import sisviatura.springboot.enumerador.TipoViatura;

/**
 * Created by Cleyton Santos on 01/01/2022.
 */

@Entity
public class Viatura implements Serializable{

   
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean deletado;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Modelo modelo;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Cor cor;
    
    private String ano;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate datacadastro;
    
    @NumberFormat(pattern = "###0.00")
    private Double cotaSemanal;
    
    @NumberFormat(pattern = "###0.00")
    private Double cotaExtra;
    
    @NumberFormat(pattern = "###0.00")
    private Double valorInvestido;
    
    @NumberFormat(pattern = "###0.00")
    private Double valorMercado;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Usuarios responsavel;
    
    private String placaoficial;
    private String placareservada;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Setor Lotacao;
    
    @NumberFormat(pattern = "###0.00")
    private Double kmLitro;
    
    @NumberFormat(pattern = "###0.00")
    private Double kmInicial;
    
    @Enumerated(EnumType.STRING)
    private TipoCombustivel tipocombustivel;
    
    @Enumerated(EnumType.STRING)
    private TipoViatura tipoviatura;
    
    @Enumerated(EnumType.STRING)
    private NaturezaViatura naturezaviatura;
    
    @NumberFormat(pattern = "###0.00")
    private Double capacidadetanque;
    
    @JsonManagedReference("log-viatura")
    @OneToMany(mappedBy = "viatura", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<LogViatura> logviatura = new ArrayList<>();
    
    @JsonManagedReference("cota-viatura")
    @OneToMany(mappedBy = "viatura", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Cota> cotas = new ArrayList<>();
    
    //@JsonManagedReference("log-viatura")
    @OneToMany(mappedBy = "viatura", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Fotos> fotos = new ArrayList<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }  
    
    public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public Cor getCor() {
		return cor;
	}

	public void setCor(Cor cor) {
		this.cor = cor;
	}	

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public LocalDate getDatacadastro() {
		return datacadastro;
	}

	public void setDatacadastro(LocalDate datacadastro) {
		this.datacadastro = datacadastro;
	}

	public Double getCotaSemanal() {
		return cotaSemanal;
	}

	public void setCotaSemanal(Double cotaSemanal) {
		this.cotaSemanal = cotaSemanal;
	}

	public Double getCotaExtra() {
		return cotaExtra;
	}

	public void setCotaExtra(Double cotaExtra) {
		this.cotaExtra = cotaExtra;
	}

	public Double getValorInvestido() {
		return valorInvestido;
	}

	public void setValorInvestido(Double valorInvestido) {
		this.valorInvestido = valorInvestido;
	}

	public Double getValorMercado() {
		return valorMercado;
	}

	public void setValorMercado(Double valorMercado) {
		this.valorMercado = valorMercado;
	}

	public Usuarios getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Usuarios responsavel) {
		this.responsavel = responsavel;
	}

	public String getPlacaoficial() {
		return placaoficial;
	}

	public void setPlacaoficial(String placaoficial) {
		this.placaoficial = placaoficial;
	}

	public String getPlacareservada() {
		return placareservada;
	}

	public void setPlacareservada(String placareservada) {
		this.placareservada = placareservada;
	}

	public Setor getLotacao() {
		return Lotacao;
	}

	public void setLotacao(Setor lotacao) {
		Lotacao = lotacao;
	}

	public Double getKmLitro() {
		return kmLitro;
	}

	public void setKmLitro(Double kmLitro) {
		this.kmLitro = kmLitro;
	}

	public Double getKmInicial() {
		return kmInicial;
	}

	public void setKmInicial(Double kmInicial) {
		this.kmInicial = kmInicial;
	}

	public TipoCombustivel getTipocombustivel() {
		return tipocombustivel;
	}

	public void setTipocombustivel(TipoCombustivel tipocombustivel) {
		this.tipocombustivel = tipocombustivel;
	}

	public TipoViatura getTipoviatura() {
		return tipoviatura;
	}

	public void setTipoviatura(TipoViatura tipoviatura) {
		this.tipoviatura = tipoviatura;
	}

	public NaturezaViatura getNaturezaviatura() {
		return naturezaviatura;
	}

	public void setNaturezaviatura(NaturezaViatura naturezaviatura) {
		this.naturezaviatura = naturezaviatura;
	}

	public void setDeletado(Boolean deletado) {
		this.deletado = deletado;
	}

	public boolean isDeletado() {
        return deletado;
    }
    
    public boolean getDeletado() {
    	return deletado;
    }
    
    public void setDeletado(boolean deletado) {
        this.deletado = deletado;
    }       
    
	public List<LogViatura> getLogviatura() {
		return logviatura;
	}

	public void setLogviatura(List<LogViatura> logviatura) {
		this.logviatura = logviatura;
	}
	
	public List<Cota> getCotas() {
		return cotas;
	}

	public void setCotas(List<Cota> cotas) {
		this.cotas = cotas;
	}

	public List<Fotos> getFotos() {
		return fotos;
	}

	public void setFotos(List<Fotos> fotos) {
		this.fotos = fotos;
	}
	
	public Double getCapacidadetanque() {
		return capacidadetanque;
	}

	public void setCapacidadetanque(Double capacidadetanque) {
		this.capacidadetanque = capacidadetanque;
	}

}
