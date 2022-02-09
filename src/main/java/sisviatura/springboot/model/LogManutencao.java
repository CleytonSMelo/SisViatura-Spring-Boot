package sisviatura.springboot.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "logmanutencao")
public class LogManutencao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "text")
    private String log;

    @Column(name = "dataalteracao")
    private LocalDateTime dataAlteracao;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="usuario_id")
    private Usuarios usuario;

    @JsonBackReference("log-manutencao")
    @ManyToOne(fetch = FetchType.EAGER)   
    private Manutencao manutencao;

   

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public LocalDateTime getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(LocalDateTime dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }    

	public Usuarios getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}

	public Manutencao getManutencao() {
		return manutencao;
	}

	public void setManutencao(Manutencao manutencao) {
		this.manutencao = manutencao;
	}

	

    

}
