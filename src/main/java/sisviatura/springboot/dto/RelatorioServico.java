package sisviatura.springboot.dto;

import sisviatura.springboot.model.Usuarios;

public class RelatorioServico {

    private String setorServico;
    private Integer numServicos;
    private Integer totalRegistro;
    private String dtDe;
    private String dtAte;
    private String dataFechamento;
    private String tecnicoResponsavel;
    private String titulo;   
    private String dataEmissao;
    private String userEmissor;
    private String imagem;

    public RelatorioServico(String setorServico, Integer numServicos, Integer totalRegistro, String dtDe, String dtAte, String img, String dataemissao, Usuarios useremissor) {
        this.setorServico = setorServico != null ? setorServico : "";
        this.dtDe = dtDe != null ? dtDe : "";
        this.dtAte = dtAte != null ? dtAte : "";
        this.numServicos = numServicos;
        this.totalRegistro = totalRegistro;
        
        this.dataEmissao = dataemissao;
        this.userEmissor = useremissor.getNome();
        this.imagem = img;
    }

    public RelatorioServico(String setorServico, Integer totalRegistro, String dataFechamento, String tecnicoResponsavel, String titulo, String dataemissao, Usuarios useremissor, String dtDe, String dtAte,String img) {
        this.setorServico = setorServico != null ? setorServico : "";
        this.totalRegistro = totalRegistro != null ? totalRegistro : 0;
        this.dataFechamento = dataFechamento != null ? dataFechamento : "";
        this.tecnicoResponsavel = tecnicoResponsavel != null ? tecnicoResponsavel : "";
        this.titulo = titulo != null ? titulo : "";
        this.dtDe = dtDe != null ? dtDe : "";
        this.dtAte = dtAte != null ? dtAte : "";
        
        this.dataEmissao = dataemissao;
        this.userEmissor = useremissor.getNome();
        this.imagem = img;
    }


    public String getSetorServico() {
        return setorServico;
    }

    public void setSetorServico(String setorServico) {
        this.setorServico = setorServico;
    }

    public Integer getNumServicos() {
        return numServicos;
    }

    public void setNumServicos(Integer numServicos) {
        this.numServicos = numServicos;
    }

    public Integer getTotalRegistro() {
        return totalRegistro;
    }

    public void setTotalRegistro(Integer totalRegistro) {
        this.totalRegistro = totalRegistro;
    }

    public String getDtDe() {
        return dtDe;
    }

    public void setDtDe(String dtDe) {
        this.dtDe = dtDe;
    }

    public String getDtAte() {
        return dtAte;
    }

    public void setDtAte(String dtAte) {
        this.dtAte = dtAte;
    }

    public String getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(String dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public String getTecnicoResponsavel() {
        return tecnicoResponsavel;
    }

    public void setTecnicoResponsavel(String tecnicoResponsavel) {
        this.tecnicoResponsavel = tecnicoResponsavel;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

	public String getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(String dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public String getUserEmissor() {
		return userEmissor;
	}

	public void setUserEmissor(String userEmissor) {
		this.userEmissor = userEmissor;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
    
    
}
