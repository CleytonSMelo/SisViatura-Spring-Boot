package sisviatura.springboot.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.dialect.springdata.SpringDataDialect;

import sisviatura.springboot.model.Usuarios;





@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {				
		
		 http
         .authorizeRequests()        
         .antMatchers( "/meucss/**","/img/**","/vendor/**","/js/**","/plugins/**").permitAll()   
         .antMatchers("/Home/Dashboard/index").hasAnyRole("ADMIN_GERAL","TEC")
         .anyRequest().authenticated()
         .and()
         .formLogin().permitAll()
         .loginPage("/Home/login")
         .defaultSuccessUrl("/Home/Permissao",true)
         .permitAll()
         .failureUrl("/redirectErroLogin")
         .and()
         .logout().logoutSuccessUrl("/Home/login")
         .permitAll()
         .logoutRequestMatcher(new AntPathRequestMatcher("/Home/logout"))
         .and().csrf().disable();
		
	}
    
	
		
	    // o codigo a baixo coneca ao banco e autentica com o AD
		@Bean
		public AuthenticationProvider activeDirectoryLdapAuthenticationProvider(){
		    ActiveDirectoryLdapAuthenticationProvider provider = new ActiveDirectoryLdapAuthenticationProvider("cleyton.local", "ldap://10.0.0.55:389/");
		    provider.setConvertSubErrorCodesToExceptions(true);
		    provider.setUseAuthenticationRequestCredentials(true);
		    provider.setUserDetailsContextMapper(userDetailsContextMapper());
		    return provider;
		}

		@Bean
		public UserDetailsContextMapper userDetailsContextMapper() {
		     return new CustomUserMapper();
		}
		
		@Bean
		public SpringDataDialect springDataDialect() {
		        return new SpringDataDialect();
	    }
	
}