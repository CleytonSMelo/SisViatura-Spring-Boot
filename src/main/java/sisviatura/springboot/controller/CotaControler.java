package sisviatura.springboot.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sisviatura.springboot.enumerador.StatusCota;
import sisviatura.springboot.model.Cor;
import sisviatura.springboot.model.Cota;
import sisviatura.springboot.model.LogCota;
import sisviatura.springboot.model.LogViatura;
import sisviatura.springboot.model.Usuarios;
import sisviatura.springboot.model.Viatura;
import sisviatura.springboot.repository.CorRepository;
import sisviatura.springboot.repository.CotaRepository;
import sisviatura.springboot.repository.ModeloRepository;
import sisviatura.springboot.repository.SetorRepository;
import sisviatura.springboot.repository.UsuarioRepository;
import sisviatura.springboot.repository.ViaturaRepository;
import sisviatura.springboot.repository.logCotaRepository;
import sisviatura.springboot.security.ImplementacaoUserDetailService;

@Controller
public class CotaControler {
	
	@Autowired
	private CotaRepository cotaRepository;
		
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ViaturaRepository viaturaRepository;
	
	@Autowired
	private SetorRepository setorRepository;
	
	@Autowired
	private logCotaRepository lgRepository;

	@GetMapping("**/Home/Cota/Cadastro")
	public ModelAndView inicio() {
		ModelAndView modelAndView = new ModelAndView("/Home/Cota/Cadastro");
		modelAndView.addObject("cotas", cotaRepository.findCotas());
		modelAndView.addObject("viaturas", viaturaRepository.findViaturas());
		modelAndView.addObject("setores", setorRepository.findSetores());
		modelAndView.addObject("cotaobj", new Cota());
		return modelAndView ;
	}
	
	@PostMapping("**/Home/Cota/SalvarCota")
    public ModelAndView salvar(Cota cota, RedirectAttributes ra) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
		Usuarios use = usuarioRepository.findUserByName(authentication.getName());
		
		Cota cota2 = cotaRepository.findCotaByid(cota.getId());			
		if (cota2 == null) {
			cota.setDatacadastro(LocalDate.now());			
			cota.setResponsavel(use);			
			cota.setStatuscota(StatusCota.EM_ANALISE);
			cota.setDeletado(false);
			gerarLog(cota);
			cotaRepository.save(cota);
			ra.addFlashAttribute("msg", "Cota Cadastrada com sucesso");						
			ModelAndView modelandView = new ModelAndView("redirect:/Home/Cota/Cadastro");
			return modelandView;	
		}else {	
			cota.setDatacadastro(cota2.getDatacadastro());
			cota.setResponsavel(cota2.getResponsavel());
			cota.setStatuscota(cota2.getStatuscota());
			cota.setAprovado(cota2.getAprovado());
			cota.setSetor(cota2.getSetor());
			cota.setDeletado(false);
			gerarLog(cota);
			cotaRepository.save(cota);
			ra.addFlashAttribute("msg", "Cota Atualizada com sucesso");						
			ModelAndView modelandView = new ModelAndView("redirect:/Home/Cota/Cadastro");
			return modelandView;
		}					
	}
			
	@GetMapping("**/Home/Cota/ListarCota")
	public ModelAndView cotas() {
		ModelAndView modelandView = new ModelAndView("/Home/Cota/ListarCota");		
		modelandView.addObject("cotas", cotaRepository.findCotas());
		modelandView.addObject("cotaobj", new Cota());
		return modelandView;
	}
	
	@GetMapping("**/Home/Cota/EditarCota/{idCota}")
	public ModelAndView Editar(@PathVariable("idCota") Long idCota) {		
		Optional<Cota> cota = cotaRepository.findById(idCota);		
		ModelAndView modelAndView = new ModelAndView("/Home/Cota/Cadastro");
		modelAndView.addObject("cotas", cotaRepository.findCotas());
		modelAndView.addObject("viaturas", viaturaRepository.findViaturas());
		modelAndView.addObject("setores", setorRepository.findSetores());
		modelAndView.addObject("cotaobj", cota.get());
		return modelAndView;
	}
	
	@GetMapping("**/Home/Cota/RemoverCota/{idCota}")
	public ModelAndView Excluir(@PathVariable("idCota") Long idCota, RedirectAttributes ra) {
		Cota cota = cotaRepository.findCotaByid(idCota);
		cota.setDeletado(true);
		cotaRepository.save(cota);
		ra.addFlashAttribute("msg", "Cota Removida com sucesso");			
		ModelAndView modelAndView = new ModelAndView("redirect:/Home/Cota/Cadastro");	
		return modelAndView;
	}		
	
	@GetMapping("**/Home/Cota/AprovarCota/{idCota}")
	public ModelAndView AprovarCota(@PathVariable("idCota") Long idCota, RedirectAttributes ra) {
		Cota cota = cotaRepository.findCotaByid(idCota);	
		
		cota.setStatuscota(StatusCota.APROVADA);
		cota.setAprovado(true);
		gerarLog(cota);
		cotaRepository.save(cota);
		ra.addFlashAttribute("msg", "Cota Aprovada com Sucesso");		
		ModelAndView modelAndView = new ModelAndView("redirect:/Home/Cota/Cadastro");	
		return modelAndView;
	}
	
	@GetMapping("**/Home/Cota/RecusarCota/{idCota}")
	public ModelAndView RecusarCota(@PathVariable("idCota") Long idCota, RedirectAttributes ra) {
		Cota cota = cotaRepository.findCotaByid(idCota);
	
		cota.setStatuscota(StatusCota.RECUSADA);
		cota.setAprovado(false);
		gerarLog(cota);
		cotaRepository.save(cota);
		ra.addFlashAttribute("msg", "Cota Aprovada com Sucesso");				
		ModelAndView modelAndView = new ModelAndView("redirect:/Home/Cota/Cadastro");	
		return modelAndView;
	}
	
	@GetMapping(value="**/Cota/buscarCotaPorId/{idCota}", produces = "application/json")
	public ResponseEntity<Cota> buscarporId(@PathVariable (value = "idCota")Long idCota) {	
	    Cota cota = cotaRepository.findCotaByid(idCota);
		return new ResponseEntity<Cota>(cota, HttpStatus.OK);
	}
	
	@RequestMapping(value = "**/Home/Cota/LogCota/{idCota}", method = RequestMethod.GET)
	public ModelAndView logCota(@PathVariable("idCota") Long idCota) {		
	    List<LogCota> lg = lgRepository.findLogByData(idCota);		
		ModelAndView modelAndView = new ModelAndView("/Home/Cota/ListarLog");		
		modelAndView.addObject("logsobj", lg);		
		return modelAndView;
	}
	 
	public void gerarLog(Cota cota) {
	      LocalDateTime horario = LocalDateTime.now().plusSeconds(1l);
	      if(cota.getId() == null) {
	    	  cota.getLogcota().add(gerarLogCadastroCota(cota));
	      } else {
	          Cota cotaAntigo = cotaRepository.findCotaByid(cota.getId());
	          String comparaAlienacao = compararCotas(cotaAntigo, cota);
	          if (!comparaAlienacao.equals("")) {
	        	  cota.getLogcota().add(gerarLogAtualizarCota(cota, horario, comparaAlienacao));
	          }
	      }
	 }
	
	public LogCota gerarLogCadastroCota(Cota cota){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
		Usuarios use = usuarioRepository.findUserByName(authentication.getName());
		LogCota logCota = new LogCota();
		
		logCota.setLog("O Usuário "+use.getNome()+" Cadastrou a Cota para a viatura de placa: " + cota.getViatura().getPlacaoficial() +".");	
        logCota.setUsuario(use); 
        logCota.setDataAlteracao(LocalDateTime.now());
        logCota.setCota(cota);
        return logCota;
    }
	
	private LogCota gerarLogAtualizarCota(Cota idCota, LocalDateTime horario, String comparaCota) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
		Usuarios use = usuarioRepository.findUserByName(authentication.getName());
        LogCota logCota = new LogCota();
        
        Cota c = cotaRepository.findCotaByid(idCota.getId());        
        String LogString = "O Usuário "+use.getNome()+" alterou esta Cota " + "\n" + comparaCota;
        logCota.setLog(LogString);
        logCota.setUsuario(use);        
        logCota.setDataAlteracao(horario);
        logCota.setCota(idCota);
        return logCota;
    }
	
	public String compararCotas(Cota cotaAntigo, Cota cotaNova) {
	      String retorno = "";

	      if (!cotaAntigo.getKmviaturaatual().equals(cotaNova.getKmviaturaatual())){
	          retorno += "Km Atual modificado, Antigo: '" + cotaAntigo.getKmviaturaatual() + "'.";
	      }
	      if (!cotaAntigo.getMotivo().equals(cotaNova.getMotivo())) {
	          retorno += " Motivo modificado! Antigo: '" + cotaAntigo.getMotivo() + "'.";
	      }
	      if (!cotaAntigo.getQuantidade().equals(cotaNova.getQuantidade())){
	          retorno += "Quantidade modificado, Antigo: '" + cotaAntigo.getQuantidade() + "'.";
	      }
	      if (!cotaAntigo.getSetor().equals(cotaNova.getSetor())){
	          retorno += " Setor modificado, Antigo: '" + cotaAntigo.getSetor() + "'.";
	      }
	      if (!cotaAntigo.getStatuscota().equals(cotaNova.getStatuscota())){
	          retorno += "Status modificado, Antigo: '" + cotaAntigo.getStatuscota() + "'.";
	      }
	      if (!cotaAntigo.getViatura().equals(cotaNova.getViatura())){
	          retorno += "Viatura modificada, Antigo: Viatura de Placa '" + cotaAntigo.getViatura().getPlacaoficial() +" Modelo: "+cotaAntigo.getViatura().getModelo()+ "'.";
	      }
	      
	      return retorno;
	    }
}