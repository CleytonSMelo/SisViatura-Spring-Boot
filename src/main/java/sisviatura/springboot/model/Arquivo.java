package sisviatura.springboot.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Created by Cleyton Santos on 01/01/2022.
 */

@Entity
public class Arquivo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	private String caminho;
	
	@ManyToOne(fetch = FetchType.EAGER) 
	private Manutencao manutencao;
		
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}	

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}	

	public Manutencao getManutencao() {
		return manutencao;
	}

	public void setManutencao(Manutencao manutencao) {
		this.manutencao = manutencao;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		
		return super.clone();
	}
	
	
}
