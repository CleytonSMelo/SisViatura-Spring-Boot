package sisviatura.springboot.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sisviatura.springboot.model.Cor;
import sisviatura.springboot.repository.CorRepository;
import sisviatura.springboot.repository.ModeloRepository;
import sisviatura.springboot.repository.UsuarioRepository;
import sisviatura.springboot.security.ImplementacaoUserDetailService;

@Controller
public class CorControler {
	
	@Autowired
	private CorRepository corRepository;
	
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	

	@GetMapping("**/Home/Cor/Cadastro")
	public ModelAndView inicio() {
		ModelAndView modelAndView = new ModelAndView("/Home/Cor/Cadastro");
		modelAndView.addObject("cores", corRepository.findCores());
		modelAndView.addObject("corobj", new Cor());
		return modelAndView ;
	}
	
	@PostMapping("**/Home/Cor/SalvarCor")
    public ModelAndView salvar(Cor cor, RedirectAttributes ra) {
		Cor cor2 = corRepository.findCorByid(cor.getId());			
		if (cor2 == null) {	
			cor.setDeletado(false);
			corRepository.save(cor);
			ra.addFlashAttribute("msg", "Cor Cadastrado com sucesso");						
			ModelAndView modelandView = new ModelAndView("redirect:/Home/Cor/Cadastro");
			return modelandView;	
		}else {		
			cor.setDeletado(false);
			corRepository.save(cor);
			ra.addFlashAttribute("msg", "Cor Atualizado com sucesso");						
			ModelAndView modelandView = new ModelAndView("redirect:/Home/Cor/Cadastro");
			return modelandView;
		}					
	}
			
	@GetMapping("**/Home/Cor/ListarCor")
	public ModelAndView cores() {
		ModelAndView modelandView = new ModelAndView("/Home/Cor/ListarCor");		
		modelandView.addObject("cores", corRepository.findCores());
		modelandView.addObject("corobj", new Cor());
		return modelandView;
	}
	
	@GetMapping("**/Home/Cor/EditarCor/{idCor}")
	public ModelAndView Editar(@PathVariable("idCor") Long idCor) {		
		Optional<Cor> cor = corRepository.findById(idCor);		
		ModelAndView modelAndView = new ModelAndView("/Home/Cor/Cadastro");
		modelAndView.addObject("cores", corRepository.findCores());		
		modelAndView.addObject("corobj", cor.get());
		return modelAndView;
	}
	
	@GetMapping("**/Home/Cor/RemoverCor/{idCor}")
	public ModelAndView Excluir(@PathVariable("idCor") Long idCor, RedirectAttributes ra) {
		Cor cor = corRepository.findCorByid(idCor);
		cor.setDeletado(true);
		corRepository.save(cor);
		ra.addFlashAttribute("msg", "Cor Removido com sucesso");				
		ModelAndView modelAndView = new ModelAndView("redirect:/Home/Cor/Cadastro");	
		return modelAndView;
	}
	 
}