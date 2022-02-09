package sisviatura.springboot.dto;

import java.io.Serializable;

import sisviatura.springboot.model.Usuarios;

public class RelatorioUsuarios implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long id;
    private String login;
    private String telefone;
    private String nome;
    private String matricula;
    private String setor;
    private String roles;
    
    public RelatorioUsuarios() {
    	
    }
    
    public RelatorioUsuarios(Usuarios obj) {
    	this.id = obj.getId();
    	this.login = obj.getLogin();
    	this.telefone = obj.getTelefone();
    	this.nome = obj.getNome();
    	this.matricula = obj.getMatricula();
    	this.setor = obj.getSetor().getNome();
    	this.roles = obj.getRoles().getNome();
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getSetor() {
		return setor;
	}

	public void setSetor(String setor) {
		this.setor = setor;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
    
    
}
