package sisviatura.springboot.enumerador;

import java.util.Arrays;

public enum Roles {
   ROLE_ADMIN_GERAL("Administrador Geral"),
   ROLE_TEC("Técnico"),
   ROLE_ADMIN_UNIDADE("Administrador Unidade");
	
	private String nome;
	
	private Roles(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public String toString() {
		
		return this.name();
	}
	
	public static Roles[] getSortedValue(){
		Roles[] values = Roles.values();
        Arrays.sort(values,(s1,s2)->s1.getNome().toString().compareTo(s2.getNome().toString()));
        return values;
    }
}
