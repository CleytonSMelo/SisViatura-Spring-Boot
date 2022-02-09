package sisviatura.springboot.sisviaturaspringboot;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sisviatura.springboot.enumerador.Roles;
import sisviatura.springboot.model.Usuarios;
import sisviatura.springboot.repository.UsuarioRepository;

@Controller
public class IndexController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	
//	@RequestMapping("/Home/")
//	public String index() {
//		return "Home/Dashboard/index";
//	}
	
//	@RequestMapping("/Home/Dashboard/index")
//	public String inicio() {
//		
//		return "Home/Dashboard/index";
//		
//	}
	
	@RequestMapping("/Home/blank")
	public String branco() {
		
		return "Home/blank";
		
	}
	
//	@RequestMapping("**/Home/TelaChamados")
//	public String tela() {
//		
//		return "Home/TelaChamados";
//		
//	}
	
//	@RequestMapping("/Home/Usuarios/Cadastro")
//	public String cadastrouser() {
//		
//		return "Home/Usuarios/Cadastro";
//		
//	}
	
	@RequestMapping("**/Home/Permissao")
	public String inicio(RedirectAttributes ra) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
		Usuarios use = usuarioRepository.findUserByName(authentication.getName());	
		if (use.getRoles().equals(Roles.ROLE_ADMIN_GERAL) || use.getRoles().equals(Roles.ROLE_TEC)) {
			return "redirect:/Home/Dashboard/index";
		}else if(use.getRoles().equals(Roles.ROLE_ADMIN_UNIDADE)){
			return "redirect:/Home/Dashboard/indexCli";
		}else {
			return null;
		}
		//else {
			//ra.addFlashAttribute("error", "O Usuário Não Possui Permissão de Acesso. Entre em Contato com o Administrador Geral");
			//return "redirect:/Home/login";
		//}
		
		
		
	}
}
