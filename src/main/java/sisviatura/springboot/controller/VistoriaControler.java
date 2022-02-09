package sisviatura.springboot.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bouncycastle.crypto.tls.MACAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sisviatura.springboot.enumerador.StatusManutencao;
import sisviatura.springboot.enumerador.StatusVistoria;
import sisviatura.springboot.model.Cor;
import sisviatura.springboot.model.Cota;
import sisviatura.springboot.model.Fotos;
import sisviatura.springboot.model.LogCota;
import sisviatura.springboot.model.LogManutencao;
import sisviatura.springboot.model.LogVistoria;
import sisviatura.springboot.model.Manutencao;
import sisviatura.springboot.model.Nota;
import sisviatura.springboot.model.Usuarios;
import sisviatura.springboot.model.Viatura;
import sisviatura.springboot.model.Vistoria;
import sisviatura.springboot.repository.CorRepository;
import sisviatura.springboot.repository.FotoRepository;
import sisviatura.springboot.repository.ManutencaoRepository;
import sisviatura.springboot.repository.ModeloRepository;
import sisviatura.springboot.repository.NotaRepository;
import sisviatura.springboot.repository.SetorRepository;
import sisviatura.springboot.repository.UsuarioRepository;
import sisviatura.springboot.repository.ViaturaRepository;
import sisviatura.springboot.repository.VistoriaRepository;
import sisviatura.springboot.repository.logManutencaoRepository;
import sisviatura.springboot.repository.logVistoriaRepository;
import sisviatura.springboot.security.ImplementacaoUserDetailService;

@Controller
public class VistoriaControler {
	
	@Autowired
	private SetorRepository setorRepository;
	
	@Autowired
	private VistoriaRepository vistoriaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ViaturaRepository viaturaRepository;
	
	@Autowired
	private logVistoriaRepository lgRepository;
	
	@Autowired
	private NotaRepository notaRepository;
	
	@Autowired
	private FotoRepository fotoRepository;


	@GetMapping("**/Home/Vistoria/Cadastro")
	public ModelAndView inicio() {
		ModelAndView modelAndView = new ModelAndView("/Home/Vistoria/Cadastro");
		modelAndView.addObject("vistoriaobj", new Vistoria());
		modelAndView.addObject("setores", setorRepository.findSetores());	
		modelAndView.addObject("solicitante", usuarioRepository.findUsuarios());
		modelAndView.addObject("viaturas", viaturaRepository.findViaturas());
		modelAndView.addObject("vistorias", vistoriaRepository.findVistorias());
		return modelAndView ;
	}
	
	private static String UPLOADED_FOLDER ="C:/fotos/";
	
	@PostMapping("**/Home/Vistoria/SalvarVistoria")
    public ModelAndView salvar(Vistoria vistoria, RedirectAttributes ra, @RequestParam("foto") MultipartFile file) throws IOException {
		Vistoria vistoria2 = vistoriaRepository.findVistoriaByid(vistoria.getId());
		Viatura vt = viaturaRepository.findViaturaByid(vistoria.getViatura().getId());
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
		Usuarios use = usuarioRepository.findUserByName(authentication.getName());
		if (vistoria2 == null) {
			
			Long qt = vistoriaRepository.totalvistorias();
			vistoria.setDeletado(false);			
			vistoria.setStatusvistoria(StatusVistoria.EM_ANALISE);
			vistoria.setCodVistoria("V"+LocalDate.now().getYear()+qt);
			vistoria.setUnidadeOrigem(vt.getLotacao());
			vistoria.setDatacadastro(LocalDate.now());
			vistoria.setSolicitante(use.getNome());
			
			gerarLog(vistoria);
			Vistoria v = vistoriaRepository.save(vistoria);
			
			if (file.isEmpty()) {
				
			}else {
				byte[] bytes = file.getBytes();
				Path path = Paths.get(UPLOADED_FOLDER +""+v.getId()+""+file.getOriginalFilename());
				Files.write(path, bytes);
				Fotos fotos = new Fotos();
				fotos.setVistoria(v);
				fotos.setCaminho(""+v.getId()+""+file.getOriginalFilename());
				fotoRepository.save(fotos);
			}
			
			ra.addFlashAttribute("msg", "Vistoria Cadastrada com sucesso");						
			ModelAndView modelandView = new ModelAndView("redirect:/Home/Vistoria/Cadastro");
			return modelandView;	
		}else {	
			vistoria.setStatusvistoria(vistoria2.getStatusvistoria());
			vistoria.setCodVistoria(vistoria2.getCodVistoria());
			vistoria.setUnidadeOrigem(vt.getLotacao());
			vistoria.setDatacadastro(vistoria2.getDatacadastro());
			vistoria.setSolicitante(vistoria2.getSolicitante());
			vistoria.setDeletado(false);
			gerarLog(vistoria);
			Vistoria v = vistoriaRepository.save(vistoria);
			
            if (file.isEmpty()) {
				
			}else {
				byte[] bytes = file.getBytes();
				Path path = Paths.get(UPLOADED_FOLDER +""+v.getId()+""+file.getOriginalFilename());
				Files.write(path, bytes);
				Fotos fotos = new Fotos();
				fotos.setVistoria(v);
				fotos.setCaminho(""+v.getId()+""+file.getOriginalFilename());
				fotoRepository.save(fotos);
			}
			ra.addFlashAttribute("msg", "Vistoria Atualizada com sucesso");						
			ModelAndView modelandView = new ModelAndView("redirect:/Home/Vistoria/Cadastro");
			return modelandView;
		}					
	}
			
	@GetMapping("**/Home/Vistoria/ListarVistoria")
	public ModelAndView vistorias() {
	//public ModelAndView setores(@PageableDefault(size = 10) Pageable pageable, ModelAndView model) {
		ModelAndView modelandView = new ModelAndView("/Home/Vistoria/ListarVistoria");		
		//modelandView.addObject("cores", corRepository.findCores());
		//modelandView.addObject("corobj", new Cor());
		return modelandView;
	}
	
	@GetMapping("**/Home/Vistoria/EditarVistoria/{idVistoria}")
	public ModelAndView Editar(@PathVariable("idVistoria") Long idVistoria) {		
		Optional<Vistoria> vistoria = vistoriaRepository.findById(idVistoria);		
		ModelAndView modelAndView = new ModelAndView("/Home/Vistoria/Cadastro");
		modelAndView.addObject("vistorias", vistoriaRepository.findVistorias());	
		modelAndView.addObject("setores", setorRepository.findSetores());
		modelAndView.addObject("vistoriaobj", vistoria.get());
		modelAndView.addObject("solicitante", usuarioRepository.findUsuarios());
		modelAndView.addObject("viaturas", viaturaRepository.findViaturas());
		return modelAndView;
	}
	
	@GetMapping("**/Home/Vistoria/RemoverVistoria/{idVistoria}")
	public ModelAndView Excluir(@PathVariable("idVistoria") Long idVistoria, RedirectAttributes ra) {
		Vistoria vistoria = vistoriaRepository.findVistoriaByid(idVistoria);
		vistoria.setDeletado(true);
		vistoriaRepository.save(vistoria);
		ra.addFlashAttribute("msg", "Vistoria Removida com sucesso");				
		ModelAndView modelAndView = new ModelAndView("redirect:/Home/Vistoria/Cadastro");	
		return modelAndView;
	}
	
	@GetMapping("**/Home/Vistoria/ConfirmarVistoria/{idVistoria}")
	public ModelAndView Confirmar(@PathVariable("idVistoria") Long idVistoria, RedirectAttributes ra) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
	    Usuarios use = usuarioRepository.findUserByName(authentication.getName());
		
		Vistoria vistoria = vistoriaRepository.findVistoriaByid(idVistoria);
		vistoria.setStatusvistoria(StatusVistoria.CONCLUIDO);
		vistoria.setResponsavel(use);
		vistoriaRepository.save(vistoria);
		ra.addFlashAttribute("msg", "Vistoria Concluida com sucesso");				
		ModelAndView modelAndView = new ModelAndView("redirect:/Home/Vistoria/Cadastro");	
		return modelAndView;
	}
	
	@GetMapping(value="**/Vistoria/buscarVistoriaPorId/{idVistoria}", produces = "application/json")
	public ResponseEntity<Vistoria> buscarporId(@PathVariable (value = "idVistoria")Long idVistoria) {
		
		Vistoria vistoria= vistoriaRepository.findVistoriaByid(idVistoria);
		return new ResponseEntity<Vistoria>(vistoria, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST , value = "**/Home/Nota/SalvarNotaVistoria")
    public ModelAndView addNotasVistoria(Nota nota, RedirectAttributes ra) {
		  
		    Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
		    Usuarios use = usuarioRepository.findUserByName(authentication.getName());
			nota.setDataCriacao(LocalDateTime.now());		
			nota.setTecnico(use);
			
			notaRepository.save(nota);			
			ra.addFlashAttribute("msg", "Nota Cadastrada com sucesso");						
			ModelAndView modelandView = new ModelAndView("redirect:/Home/Vistoria/Cadastro");			
			return modelandView;		
	}
	
	private static String UPLOADED_ARQ ="C:/fotos/vistoria/";
	@PostMapping("**/Home/Vistoria/UploadFoto")
	public String uploadFoto(Long idVistoria, RedirectAttributes ra, @RequestParam("foto") MultipartFile file) throws IOException {
		
		Vistoria v = vistoriaRepository.findVistoriaByid(idVistoria);				
        
		if (file.isEmpty()) {
			
		}else {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_ARQ +""+idVistoria+""+file.getOriginalFilename());
			Files.write(path, bytes);
			Fotos fotos = new Fotos();
			fotos.setVistoria(v);
			fotos.setCaminho(""+v.getId()+""+file.getOriginalFilename());
			fotoRepository.save(fotos);
		}
		ra.addFlashAttribute("msg", "Foto Cadastrada com sucesso");											
		return  "redirect:/Home/Vistoria/Cadastro";
	}
	
	@RequestMapping(value = "**/Home/Vistoria/DetalhesVistoria/{idVistoria}", method = RequestMethod.GET)
	public ModelAndView detalhesVistoria(@PathVariable("idVistoria") Long idVistoria) {
		
		Optional<Vistoria> vistoria = vistoriaRepository.findById(idVistoria);		
		ModelAndView modelAndView = new ModelAndView("/Home/Vistoria/Detalhe");		
		modelAndView.addObject("vistoriaobj", vistoria.get());		
		return modelAndView;
	}
	
    private static final String EXTERNAL_FILE_PATH = "C:/fotos/vistoria/";
	
	@RequestMapping("**/Home/Vistoria/FotoDownload/{fileName}")
	public void downloadFoto(HttpServletRequest request, HttpServletResponse response, @PathVariable("fileName") String fileName) throws IOException{
		File file = new File(EXTERNAL_FILE_PATH + fileName);
		if (file.exists()) {
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			
			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", String.format("inline; filename=\""+file.getName()+"\""));
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		}
	}
	
	@RequestMapping(value = "**/Home/Vistoria/LogVistoria/{idVistoria}", method = RequestMethod.GET)
	public ModelAndView logVistoria(@PathVariable("idVistoria") Long idVistoria) {		
	    List<LogVistoria> lg = lgRepository.findLogByData(idVistoria);		
		ModelAndView modelAndView = new ModelAndView("/Home/Vistoria/ListarLog");		
		modelAndView.addObject("logsobj", lg);		
		return modelAndView;
	}
	
	public void gerarLog(Vistoria vistoria) {
	      LocalDateTime horario = LocalDateTime.now().plusSeconds(1l);
	      if(vistoria.getId() == null) {
	    	  vistoria.getLogvistoria().add(gerarLogCadastroVistoria(vistoria));
	      } else {
	    	  Vistoria vistoriaAntigo = vistoriaRepository.findVistoriaByid(vistoria.getId());
	          String comparaVistoria = compararVistorias(vistoriaAntigo, vistoria);
	          if (!comparaVistoria.equals("")) {
	        	  vistoria.getLogvistoria().add(gerarLogAtualizarVistoria(vistoria, horario, comparaVistoria));
	          }
	      }
	 }
	
	public LogVistoria gerarLogCadastroVistoria(Vistoria vistoria){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
		Usuarios use = usuarioRepository.findUserByName(authentication.getName());
		LogVistoria logVistoria = new LogVistoria();
		
		logVistoria.setLog("O Usuário "+use.getNome()+" Cadastrou a Vistoria de Codigo: '"+vistoria.getCodVistoria()+"', para a viatura de placa: " + vistoria.getViatura().getPlacaoficial() +".");	
        logVistoria.setUsuario(use); 
        logVistoria.setDataAlteracao(LocalDateTime.now());
        logVistoria.setVistoria(vistoria);
        return logVistoria;
    }
	
	private LogVistoria gerarLogAtualizarVistoria(Vistoria idVistoria, LocalDateTime horario, String comparaVistoria) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
		Usuarios use = usuarioRepository.findUserByName(authentication.getName());
        LogVistoria logVistoria = new LogVistoria();
        
        Vistoria v = vistoriaRepository.findVistoriaByid(idVistoria.getId());        
        String LogString = "O Usuário "+use.getNome()+" alterou esta Vistoria " + "\n" + comparaVistoria;
        logVistoria.setLog(LogString);
        logVistoria.setUsuario(use);        
        logVistoria.setDataAlteracao(horario);
        logVistoria.setVistoria(idVistoria);
        return logVistoria;
    }
	
	public String compararVistorias(Vistoria vistoriaAntigo, Vistoria vistoriaNova) {
	      String retorno = "";

	      if (!vistoriaAntigo.getKmatual().equals(vistoriaNova.getKmatual())){
	          retorno += "Km Atual modificado, Antigo: '" + vistoriaAntigo.getKmatual() + "'.";
	      }
	      if (!vistoriaAntigo.getMotivo().equals(vistoriaNova.getMotivo())) {
	          retorno += " Motivo modificado! Antigo: '" + vistoriaAntigo.getMotivo() + "'.";
	      }
	      if (!vistoriaAntigo.getSolicitante().equals(vistoriaNova.getSolicitante())){
	          retorno += "Solicitante modificado, Antigo: '" + vistoriaAntigo.getSolicitante() + "'.";
	      }
	      if (!vistoriaAntigo.getUnidadeDestino().equals(vistoriaNova.getUnidadeDestino())){
	          retorno += " Unidade de Destino modificado, Antigo: '" + vistoriaAntigo.getUnidadeDestino() + "'.";
	      }
	      if (!vistoriaAntigo.getUnidadeOrigem().equals(vistoriaNova.getUnidadeOrigem())){
	          retorno += " Unidade de Origem modificado, Antigo: '" + vistoriaAntigo.getUnidadeOrigem() + "'.";
	      }
	      if (!vistoriaAntigo.getViatura().equals(vistoriaNova.getViatura())){	          
	          retorno += "Viatura modificada, Antigo: Viatura de Placa '" + vistoriaAntigo.getViatura().getPlacaoficial() +" Modelo: "+vistoriaAntigo.getViatura().getModelo()+ "'.";
	      }	     
	      
	      return retorno;
	 }
	 
}