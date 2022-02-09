package sisviatura.springboot.security;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.annotation.ServletSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sisviatura.springboot.model.Usuarios;
import sisviatura.springboot.repository.UsuarioRepository;



@Service
@Transactional
public class ImplementacaoUserDetailService implements UserDetailsService{
	
	@Autowired
	private UsuarioRepository usuarioRepository2;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		
		Usuarios usuario = usuarioRepository2.findUserByLogin(username);
		
		
		
		if (usuario == null) {			
			throw new UsernameNotFoundException("Usuario não foi encontrado");
			
		}else if(usuario.getLogin().equals(username)) {
		
		Set<GrantedAuthority> perfil = new HashSet<GrantedAuthority>();
		perfil.add(new SimpleGrantedAuthority(usuario.getRoles().toString()));			
		return new User(usuario.getNome(), usuario.getSenha(), perfil);
	}else {

		return null;
	}

}
	
	
}