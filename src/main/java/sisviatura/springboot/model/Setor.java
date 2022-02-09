package sisviatura.springboot.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Cleyton on 01/01/2022.
 */
@Entity
public class Setor implements Serializable{

   
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean deletado;
    private String nome;
    private String senha;
    private String telefone;
    private String endereco;
    private String informacao;

    private Boolean criarservico;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getInformacao() {
        return informacao;
    }

    public void setInformacao(String informacao) {
        this.informacao = informacao;
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

    public String getEndereco() { 
    	return endereco; 
    }

    public void setEndereco(String endereco) { 
    	this.endereco = endereco; 
    }

    public Boolean getCriarservico() {
		return criarservico;
	}

	public void setCriarservico(Boolean criarservico) {
		this.criarservico = criarservico;
	}
    
}
