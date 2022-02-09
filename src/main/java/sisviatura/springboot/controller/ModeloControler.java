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

import sisviatura.springboot.model.Modelo;
import sisviatura.springboot.model.Usuarios;
import sisviatura.springboot.repository.ModeloRepository;
import sisviatura.springboot.repository.UsuarioRepository;
import sisviatura.springboot.security.ImplementacaoUserDetailService;

@Controller
public class ModeloControler {
	
	@Autowired
	private ModeloRepository modeloRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	

	@GetMapping("**/Home/Modelo/Cadastro")
	public ModelAndView inicio() {
		ModelAndView modelAndView = new ModelAndView("/Home/Modelo/Cadastro");
		modelAndView.addObject("modelos", modeloRepository.findModelos());			
		modelAndView.addObject("modeloobj", new Modelo());
		return modelAndView ;
	}
	
	@PostMapping("**/Home/Modelo/SalvarModelo")
    public ModelAndView salvar(Modelo modelo, RedirectAttributes ra) {
		Modelo modelo2 = modeloRepository.findModeloByid(modelo.getId());			
		if (modelo2 == null) {	
			modelo.setDeletado(false);
			modeloRepository.save(modelo);
			ra.addFlashAttribute("msg", "Modelo Cadastrado com sucesso");						
			ModelAndView modelandView = new ModelAndView("redirect:/Home/Modelo/Cadastro");
			return modelandView;	
		}else {		
			modelo.setDeletado(false);
			modeloRepository.save(modelo);
			ra.addFlashAttribute("msg", "Modelo Atualizado com sucesso");						
			ModelAndView modelandView = new ModelAndView("redirect:/Home/Modelo/Cadastro");
			return modelandView;
		}					
	}
			
	@GetMapping("**/Home/Modelo/ListarModelo")
	public ModelAndView modelos() {
		ModelAndView modelandView = new ModelAndView("/Home/Modelo/ListarModelo");		
		modelandView.addObject("modelos", modeloRepository.findModelos());
		modelandView.addObject("modeloobj", new Modelo());
		return modelandView;
	}
	
	@GetMapping("**/Home/Modelo/EditarModelo/{idModelo}")
	public ModelAndView Editar(@PathVariable("idModelo") Long idModelo) {		
		Optional<Modelo> modelo = modeloRepository.findById(idModelo);		
		ModelAndView modelAndView = new ModelAndView("/Home/Modelo/Cadastro");
		modelAndView.addObject("modelos", modeloRepository.findModelos());		
		modelAndView.addObject("modeloobj", modelo.get());
		return modelAndView;
	}
	
	@GetMapping("**/Home/Modelo/RemoverModelo/{idModelo}")
	public ModelAndView Excluir(@PathVariable("idModelo") Long idModelo, RedirectAttributes ra) {
		Modelo modelo = modeloRepository.findModeloByid(idModelo);
		modelo.setDeletado(true);
		modeloRepository.save(modelo);
		ra.addFlashAttribute("msg", "Modelo Removido com sucesso");				
		ModelAndView modelAndView = new ModelAndView("redirect:/Home/Modelo/Cadastro");	
		return modelAndView;
	}
	 
}
