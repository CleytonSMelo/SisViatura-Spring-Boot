package sisviatura.springboot.model;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Cleyton Santos on 01/01/2022.
 */

@Entity
public class Nota implements Serializable  {

    
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name = "datacriacao")
    private LocalDateTime dataCriacao;

    @Column(columnDefinition = "text")
    private String descricao;      
    
    @JsonBackReference("nota-manutencao")
    @JoinColumn(name="manutencao_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Manutencao manutencao;
    
    @JsonBackReference("nota-vistoria")
    @JoinColumn(name="vistoria_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vistoria vistoria;

    @ManyToOne
    private Usuarios tecnico;


   

    public Long getId() {
        return null;
    }

  
    public void setId(Long id) {

    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Usuarios getTecnico() {
        return tecnico;
    }

    public void setTecnico(Usuarios tecnico) {
        this.tecnico = tecnico;
    }

	public Manutencao getManutencao() {
		return manutencao;
	}

	public void setManutencao(Manutencao manutencao) {
		this.manutencao = manutencao;
	}

	public Vistoria getVistoria() {
		return vistoria;
	}

	public void setVistoria(Vistoria vistoria) {
		this.vistoria = vistoria;
	}
    
    
}
