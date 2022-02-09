package sisviatura.springboot.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sisviatura.springboot.model.Setor;
import sisviatura.springboot.model.Usuarios;
import sisviatura.springboot.repository.SetorRepository;
import sisviatura.springboot.repository.UsuarioRepository;
import sisviatura.springboot.security.ImplementacaoUserDetailService;

@Controller
public class SetorControler {
	
	
	@Autowired
	private SetorRepository setorRepository; 
	   
	@RequestMapping(method = RequestMethod.GET , value = "**/Home/Setor/Cadastro")
	public ModelAndView inicio() {
		ModelAndView modelAndView = new ModelAndView("/Home/Setor/Cadastro");		
		modelAndView.addObject("setorobj", new Setor());
		return modelAndView ;
	}
	
	@RequestMapping(method = RequestMethod.POST , value = "**/Home/Setor/SalvarSetor")
    public ModelAndView salvar(Setor setor, RedirectAttributes ra) {
		Setor setor2 = setorRepository.findSetorByid(setor.getId());
			
		if (setor2 == null) {
			setor.setDeletado(false);
			setor.setCriarservico(false);
			setorRepository.save(setor);
			ra.addFlashAttribute("msg", "Setor Cadastrado com sucesso");
						
			ModelAndView modelandView = new ModelAndView("redirect:/Home/Setor/ListarSetor");
			return modelandView;	
		}else {
			setor.setCriarservico(setor2.getCriarservico());
			setor.setDeletado(setor2.getDeletado());
			setorRepository.save(setor);
			ra.addFlashAttribute("msg", "Setor Atualizado com sucesso");
						
			ModelAndView modelandView = new ModelAndView("redirect:/Home/Setor/ListarSetor");
			return modelandView;
		}					
	}
	
	@RequestMapping(method = RequestMethod.GET , value = "**/Home/Setor/ListarSetor")
	public ModelAndView setores(@PageableDefault(size = 10) Pageable pageable, ModelAndView model) {
		ModelAndView modelandView = new ModelAndView("/Home/Setor/ListarSetor");
		modelandView.addObject("setores", setorRepository.findSetoByid(pageable));
		modelandView.addObject("setorobj", new Setor());
		return modelandView;
	}
	
	@GetMapping("**/setorespag")
	public ModelAndView carregaSetorPorPaginacao(@PageableDefault(size = 10) Pageable pageable, ModelAndView model, @RequestParam("nomepesquisar") String nomepesquisar) {
		Page<Setor> pageSetor = setorRepository.findSetoName(nomepesquisar, pageable);
		model.addObject("setores", pageSetor);
		model.setViewName("/Home/Setor/ListarSetor");	
		return model;
	}
	
	
	@GetMapping("**/Home/Setor/EditarSetor/{idSetor}")
	public ModelAndView Editar(@PathVariable("idSetor") Long idSetor) {
		
		Optional<Setor> setor = setorRepository.findById(idSetor);
		
		ModelAndView modelAndView = new ModelAndView("/Home/Setor/Cadastro");		
		modelAndView.addObject("setorobj", setor.get());
		return modelAndView;
	}
	
	@GetMapping("**/Home/Setor/RemoverSetor/{idSetor}")
	public ModelAndView Excluir(@PathVariable("idSetor") Long idSetor, RedirectAttributes ra) {
		Setor setor = setorRepository.findSetorByid(idSetor);
		setor.setDeletado(true);
		setorRepository.save(setor);
		ra.addFlashAttribute("msg", "Setor Removido com sucesso");
		
		ModelAndView modelAndView = new ModelAndView("redirect:/Home/Setor/ListarSetor");		
		return modelAndView;
	}
	
	@GetMapping("**/Home/Setor/CriarServicoSetor/{idSetor}")
	public ModelAndView CriarServico(@PathVariable("idSetor") Long idSetor, RedirectAttributes ra) {
		Setor setor = setorRepository.findSetorByid(idSetor);
		setor.setCriarservico(true);
		setorRepository.save(setor);
		ra.addFlashAttribute("msg", "Permissão de Criar Srviço por Setor Concedida com Sucesso ");
		
		ModelAndView modelAndView = new ModelAndView("redirect:/Home/Setor/ListarSetor");		
		return modelAndView;
	}
	
	@GetMapping("**/Home/Setor/RemCriarServicoSetor/{idSetor}")
	public ModelAndView remCriarServico(@PathVariable("idSetor") Long idSetor, RedirectAttributes ra) {
		Setor setor = setorRepository.findSetorByid(idSetor);
		setor.setCriarservico(false);
		setorRepository.save(setor);
		ra.addFlashAttribute("msg", "Permissão de Criar Srviço por Setor Revogada com Sucesso ");
		
		ModelAndView modelAndView = new ModelAndView("redirect:/Home/Setor/ListarSetor");		
		return modelAndView;
	}
	
	@GetMapping("**/Home/PesquisarSetor")
	public ModelAndView pesquisar(@RequestParam("nomepesquisar") String nomepesquisar, @PageableDefault(size = 10) Pageable pageable) {
		ModelAndView modelAndView = new ModelAndView("/Home/Setor/ListarSetor");
		modelAndView.addObject("setores", setorRepository.findSetoName(nomepesquisar, pageable));
		modelAndView.addObject("nomepesquisar", nomepesquisar);
		modelAndView.addObject("setorobj", new Setor());
		return modelAndView;
	}
}
