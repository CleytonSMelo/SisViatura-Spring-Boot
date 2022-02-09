package sisviatura.springboot.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sisviatura.springboot.dto.RelatorioUsuarios;
import sisviatura.springboot.model.Setor;
import sisviatura.springboot.model.Usuarios;
import sisviatura.springboot.repository.SetorRepository;
import sisviatura.springboot.repository.UsuarioRepository;
import sisviatura.springboot.security.ImplementacaoUserDetailService;
import sisviatura.springboot.sisviaturaspringboot.ReportUtil;

@Controller
public class CadastroUserControler {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private SetorRepository setorRepository;
	
	@Autowired
	private ReportUtil report;
	
	
	@GetMapping("**/Home/Usuarios/Cadastro")
	public ModelAndView inicio() {
		ModelAndView modelAndView = new ModelAndView("/Home/Usuarios/Cadastro");		
		modelAndView.addObject("usuarioobj", new Usuarios());
		modelAndView.addObject("setores", setorRepository.findSetores());		
		return modelAndView ;
	}	
	
	@PostMapping("**/Home/Usuarios/SalvarUsuario")
    public ModelAndView salvar(Usuarios usuario, RedirectAttributes ra) {
		Usuarios user2 = usuarioRepository.findUserByid(usuario.getId());
		Usuarios user = usuarioRepository.findUserByLogin(usuario.getLogin());
		if (usuario.getId() == 0) {
			 if(user==null) {
				 
				    usuario.setDeletado(false);
					usuarioRepository.save(usuario);			
					ra.addFlashAttribute("msg", "Usuário Cadastrado com sucesso");			
					
					ModelAndView modelandView = new ModelAndView("redirect:/Home/Usuarios/ListarUser");
					return modelandView;
			 }else{
				 
				    ModelAndView modelandView = new ModelAndView("/Home/Usuarios/Cadastro");
					modelandView.addObject("msgErro", "Login já Cadastrado!!!");
					modelandView.addObject("usuarioobj", usuario);	
					modelandView.addObject("setores", setorRepository.findSetores());
		            return modelandView;				    
			 }
			 
		}else{
			
			usuario.setDeletado(false);			
			usuarioRepository.save(usuario);
			
			ra.addFlashAttribute("msg", "Usuário Atualizado com sucesso");
			ModelAndView modelandView = new ModelAndView("redirect:/Home/Usuarios/ListarUser");	
			return modelandView;
		}
	}
	
	@GetMapping("**/Home/Usuarios/ListarUser")
	public ModelAndView usuarios(@PageableDefault(size = 10) Pageable pageable, ModelAndView model) {
		ModelAndView modelandView = new ModelAndView("/Home/Usuarios/ListarUser");
		modelandView.addObject("usuarios", usuarioRepository.findUseByid(pageable));
		modelandView.addObject("usuarioobj", new Usuarios());
		return modelandView;
	}
	
	@GetMapping("**/usuariospag")
	public ModelAndView carregaPessoaPorPaginacao(@PageableDefault(size = 10) Pageable pageable, ModelAndView model, @RequestParam("nomepesquisar") String nomepesquisar) {
		Page<Usuarios> pageUsuario = usuarioRepository.findUseName(nomepesquisar, pageable);
		model.addObject("usuarios", pageUsuario);
		model.setViewName("/Home/Usuarios/ListarUser");	
		return model;
	}
	
	@GetMapping("**/Home/Usuarios/EditarUsuario/{idUsuario}")
	public ModelAndView Editar(@PathVariable("idUsuario") Long idUsuario) {		
		Optional<Usuarios> usuario2 = usuarioRepository.findById(idUsuario);		
		ModelAndView modelAndView = new ModelAndView("/Home/Usuarios/Cadastro");		
		modelAndView.addObject("usuarioobj", usuario2.get());
		modelAndView.addObject("setores", setorRepository.findSetores());
		return modelAndView;
	}
	
	@GetMapping("**/Home/Usuarios/RemoverUsuario/{idUsuario}")
	public ModelAndView Excluir(@PathVariable("idUsuario") Long idUsuario, RedirectAttributes ra) {
		Usuarios usuario = usuarioRepository.findUserByid(idUsuario);
		usuario.setDeletado(true);
		usuarioRepository.save(usuario);
		ra.addFlashAttribute("msg", "Usuário Removido com sucesso");
		
		ModelAndView modelAndView = new ModelAndView("redirect:/Home/Usuarios/ListarUser");		
		return modelAndView;
	}
	
	@GetMapping("**/Home/PesquisarUsuario")
	public ModelAndView pesquisar(@RequestParam("nomepesquisar") String nomepesquisar, @PageableDefault(size = 10) Pageable pageable) {
		ModelAndView modelAndView = new ModelAndView("/Home/Usuarios/ListarUser");
		modelAndView.addObject("usuarios", usuarioRepository.findUseName(nomepesquisar, pageable));
		modelAndView.addObject("nomepesquisar", nomepesquisar);
		modelAndView.addObject("usuarioobj", new Usuarios());
		return modelAndView;
	}	
	
	@GetMapping("**/Home/Usuarios/Relatorios")
	public ModelAndView relatorio() {
		ModelAndView modelAndView = new ModelAndView("/Home/Usuarios/Relatorios");				
		return modelAndView ;
	}	
	
	@PostMapping("**/imprimirRelatorioUser") 
	public void gerarRelatorioUsuario(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Usuarios> usuarios = usuarioRepository.findUsuarios();
		List<RelatorioUsuarios> useDto = usuarios.stream().map(obj -> new RelatorioUsuarios(obj)).collect(Collectors.toList());
		
		byte[] pdf = report.gerarRelatorio(useDto, "relatorioUsuarios", request.getServletContext());
		response.setContentLength(pdf.length);
		response.setContentType("application/octet-stream");
		String headerkey = "Content-Disposition";
		//String headerValue = String.format("attachment; filename=\"%s\"", "relatorio.pdf");
		String headerValue = String.format("inline; filename=\"%s\"", "relatorio_de_usuarios.pdf");		
		response.setHeader(headerkey, headerValue);
		response.getOutputStream().write(pdf);
	}
}
