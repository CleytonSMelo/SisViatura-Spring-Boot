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

import sisviatura.springboot.enumerador.StatusCota;
import sisviatura.springboot.enumerador.StatusManutencao;
import sisviatura.springboot.model.Arquivo;
import sisviatura.springboot.model.Cor;
import sisviatura.springboot.model.Cota;
import sisviatura.springboot.model.Fotos;
import sisviatura.springboot.model.LogCota;
import sisviatura.springboot.model.LogManutencao;
import sisviatura.springboot.model.Manutencao;
import sisviatura.springboot.model.Nota;
import sisviatura.springboot.model.Usuarios;
import sisviatura.springboot.model.Viatura;
import sisviatura.springboot.model.Vistoria;
import sisviatura.springboot.repository.ArquivoRepository;
import sisviatura.springboot.repository.CorRepository;
import sisviatura.springboot.repository.ManutencaoRepository;
import sisviatura.springboot.repository.ModeloRepository;
import sisviatura.springboot.repository.NotaRepository;
import sisviatura.springboot.repository.SetorRepository;
import sisviatura.springboot.repository.UsuarioRepository;
import sisviatura.springboot.repository.ViaturaRepository;
import sisviatura.springboot.repository.logManutencaoRepository;
import sisviatura.springboot.security.ImplementacaoUserDetailService;

@Controller
public class ManutencaoControler {
	
	@Autowired
	private SetorRepository setorRepository;
	
	@Autowired
	private ManutencaoRepository manutencaoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ViaturaRepository viaturaRepository;
	
	@Autowired
	private logManutencaoRepository lgRepository;
	
	@Autowired
	private NotaRepository notaRepository;
	
	@Autowired
	private ArquivoRepository arqRepository;

	@GetMapping("**/Home/Manutencao/Cadastro")
	public ModelAndView inicio() {
		ModelAndView modelAndView = new ModelAndView("/Home/Manutencao/Cadastro");
		modelAndView.addObject("manutencaoobj", new Manutencao());
		modelAndView.addObject("setores", setorRepository.findSetores());	
		modelAndView.addObject("solicitante", usuarioRepository.findUsuarios());
		modelAndView.addObject("viaturas", viaturaRepository.findViaturas());
		modelAndView.addObject("manutencoes", manutencaoRepository.findManutencoes());
		return modelAndView ;
	}
	
	@PostMapping("**/Home/Manutencao/SalvarManutencao")
    public ModelAndView salvar(Manutencao manutencao, RedirectAttributes ra) {
		Manutencao manut2 = manutencaoRepository.findManutencaoByid(manutencao.getId());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
		Usuarios use = usuarioRepository.findUserByName(authentication.getName());
		if (manut2 == null) {
			
			Long qt = manutencaoRepository.totalmanutencoes();
			manutencao.setDeletado(false);
			if (manutencao.getCustoManutencao() == null) {
				manutencao.setCustoManutencao(0.0);
			}else {
				manutencao.setCustoManutencao(manutencao.getCustoManutencao());
			}
			
			manutencao.setCodManutencao("S"+LocalDate.now().getYear()+qt);
			manutencao.setDataabertura(LocalDate.now());
			manutencao.setSolicitante(use.getNome());
			manutencao.setStatusmanutencao(StatusManutencao.EM_ANALISE);
			gerarLog(manutencao);
			manutencaoRepository.save(manutencao);
			ra.addFlashAttribute("msg", "Serviço Cadastrado com sucesso");						
			ModelAndView modelandView = new ModelAndView("redirect:/Home/Manutencao/Cadastro");
			return modelandView;	
		}else {	
			if (manutencao.getStatusmanutencao() == null) {
				manutencao.setStatusmanutencao(manut2.getStatusmanutencao());
			}else {
				manutencao.setStatusmanutencao(manutencao.getStatusmanutencao());
			}
			
			manutencao.setSolicitante(manut2.getSolicitante());
			manutencao.setDataabertura(manut2.getDataabertura());
			manutencao.setCodManutencao(manut2.getCodManutencao());
			manutencao.setDeletado(false);
			gerarLog(manutencao);
			manutencaoRepository.save(manutencao);
			ra.addFlashAttribute("msg", "Serviço Atualizado com sucesso");						
			ModelAndView modelandView = new ModelAndView("redirect:/Home/Manutencao/Cadastro");
			return modelandView;
		}					
	}
			
	@GetMapping("**/Home/Manutencao/ListarManutencao")
	public ModelAndView manutencoes() {
		ModelAndView modelandView = new ModelAndView("/Home/Manutencao/ListarManutencao");		
		return modelandView;
	}
	
	@GetMapping("**/Home/Manutencao/EditarManutencao/{idManutencao}")
	public ModelAndView Editar(@PathVariable("idManutencao") Long idManutencao) {		
		Optional<Manutencao> manutencao = manutencaoRepository.findById(idManutencao);		
		ModelAndView modelAndView = new ModelAndView("/Home/Manutencao/Cadastro");
		modelAndView.addObject("manutencoes", manutencaoRepository.findManutencoes());	
		modelAndView.addObject("setores", setorRepository.findSetores());
		modelAndView.addObject("manutencaoobj", manutencao.get());
		modelAndView.addObject("solicitante", usuarioRepository.findUsuarios());
		modelAndView.addObject("viaturas", viaturaRepository.findViaturas());
		return modelAndView;
	}
	
	@GetMapping("**/Home/Manutencao/RemoverManutencao/{idManutencao}")
	public ModelAndView Excluir(@PathVariable("idManutencao") Long idManutencao, RedirectAttributes ra) {
		Manutencao manutencao = manutencaoRepository.findManutencaoByid(idManutencao);
		manutencao.setDeletado(true);
		manutencaoRepository.save(manutencao);
		ra.addFlashAttribute("msg", "Manutenção Removida com sucesso");		
		ModelAndView modelAndView = new ModelAndView("redirect:/Home/Manutencao/Cadastro");	
		return modelAndView;
	}
	
	@GetMapping(value="**/Manutencao/buscarManutencaoPorId/{idManutencao}", produces = "application/json")
	public ResponseEntity<Manutencao> buscarporId(@PathVariable (value = "idManutencao")Long idManutencao) {		
	    Manutencao manut= manutencaoRepository.findManutencaoByid(idManutencao);
		return new ResponseEntity<Manutencao>(manut, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST , value = "**/Home/Nota/SalvarNotaManutencao")
    public ModelAndView addNotasManut(Nota nota, RedirectAttributes ra) {
		  
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
		Usuarios use = usuarioRepository.findUserByName(authentication.getName());
			nota.setDataCriacao(LocalDateTime.now());		
			nota.setTecnico(use);
			notaRepository.save(nota);			
			ra.addFlashAttribute("msg", "Nota Cadastrada com sucesso");						
			ModelAndView modelandView = new ModelAndView("redirect:/Home/Manutencao/Cadastro");			
			return modelandView;		
	}
	
	private static String UPLOADED_ARQ ="C:/fotos/arquivos/";
	@PostMapping("**/Home/Manutencao/UploadArquivo")
	public String uploadArquivo(Long idManutencao, RedirectAttributes ra, @RequestParam("arquivo") MultipartFile file) throws IOException {
		
		Manutencao m = manutencaoRepository.findManutencaoByid(idManutencao);				
        
		if (file.isEmpty()) {
			
		}else {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_ARQ +""+idManutencao+""+file.getOriginalFilename());
			Files.write(path, bytes);
			Arquivo arquivos = new Arquivo();
			arquivos.setManutencao(m);
			arquivos.setCaminho(""+m.getId()+""+file.getOriginalFilename());
			arqRepository.save(arquivos);
		}
		ra.addFlashAttribute("msg", "Arquivo Cadastrada com sucesso");											
		return  "redirect:/Home/Manutencao/Cadastro";
	}
	
    private static final String EXTERNAL_FILE_PATH = "C:/fotos/arquivos/";
	
	@RequestMapping("**/Home/Manutencao/ArquivoDownload/{fileName}")
	public void downloadArquivo(HttpServletRequest request, HttpServletResponse response, @PathVariable("fileName") String fileName) throws IOException{
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
	
	@RequestMapping(value = "**/Home/Manutencao/LogManutencao/{idManutencao}", method = RequestMethod.GET)
	public ModelAndView logManutencao(@PathVariable("idManutencao") Long idManutencao) {		
	    List<LogManutencao> lg = lgRepository.findLogByData(idManutencao);		
		ModelAndView modelAndView = new ModelAndView("/Home/Manutencao/ListarLog");		
		modelAndView.addObject("logsobj", lg);		
		return modelAndView;
	}
	
	@RequestMapping(value = "**/Home/Manutencao/DetalhesManutencao/{idManutencao}", method = RequestMethod.GET)
	public ModelAndView detalhesManutencao(@PathVariable("idManutencao") Long idManutencao) {
		
		Optional<Manutencao> manutencao = manutencaoRepository.findById(idManutencao);		
		ModelAndView modelAndView = new ModelAndView("/Home/Manutencao/Detalhe");		
		modelAndView.addObject("manutencaoobj", manutencao.get());		
		return modelAndView;
	}
	
	@GetMapping("**/Home/Manutencao/ConcluirManutencao/{idManutencao}")
	public ModelAndView AprovarCota(@PathVariable("idManutencao") Long idManutencao, RedirectAttributes ra) {
		Manutencao manut = manutencaoRepository.findManutencaoByid(idManutencao);
		//cota.setDeletado(true);	
		manut.setDatafechamento(LocalDate.now());
		manut.setStatusmanutencao(StatusManutencao.CONCLUIDO);
		manut.setAprovado(true);
		gerarLog(manut);
		manutencaoRepository.save(manut);
		ra.addFlashAttribute("msg", "Serviço Concluido com Sucesso");		
		ModelAndView modelAndView = new ModelAndView("redirect:/Home/Manutencao/Cadastro");	
		return modelAndView;
	}
	
	public void gerarLog(Manutencao manutencao) {
	      LocalDateTime horario = LocalDateTime.now().plusSeconds(1l);
	      if(manutencao.getId() == null) {
	    	  manutencao.getLogmanutencao().add(gerarLogCadastroManutencao(manutencao));
	      } else {
	          Manutencao manutencaoAntigo = manutencaoRepository.findManutencaoByid(manutencao.getId());
	          String comparaManutencao = compararManutencoes(manutencaoAntigo, manutencao);
	          if (!comparaManutencao.equals("")) {
	        	  manutencao.getLogmanutencao().add(gerarLogAtualizarManutencao(manutencao, horario, comparaManutencao));
	          }
	      }
	 }
	
	public LogManutencao gerarLogCadastroManutencao(Manutencao manutencao){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
		Usuarios use = usuarioRepository.findUserByName(authentication.getName());
		LogManutencao logManutencao = new LogManutencao();
		
		logManutencao.setLog("O Usuário "+use.getNome()+" Cadastrou a Manutenção de Codigo: '"+manutencao.getCodManutencao()+"', para a viatura de placa: " + manutencao.getViatura().getPlacaoficial() +".");	
        logManutencao.setUsuario(use); 
        logManutencao.setDataAlteracao(LocalDateTime.now());
        logManutencao.setManutencao(manutencao);
        return logManutencao;
    }
	
	private LogManutencao gerarLogAtualizarManutencao(Manutencao idManutencao, LocalDateTime horario, String comparaManutencao) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
		Usuarios use = usuarioRepository.findUserByName(authentication.getName());
        LogManutencao logManutencao = new LogManutencao();
        
        Manutencao m = manutencaoRepository.findManutencaoByid(idManutencao.getId());        
        String LogString = "O Usuário "+use.getNome()+" alterou esta Manutenção " + "\n" + comparaManutencao;
        logManutencao.setLog(LogString);
        logManutencao.setUsuario(use);        
        logManutencao.setDataAlteracao(horario);
        logManutencao.setManutencao(idManutencao);
        return logManutencao;
    }
	
	public String compararManutencoes(Manutencao manutencaoAntigo, Manutencao manutencaoNova) {
	      String retorno = "";

	      if (!manutencaoAntigo.getCustoManutencao().equals(manutencaoNova.getCustoManutencao())){
	          retorno += "Km Atual modificado, Antigo: '" + manutencaoAntigo.getCustoManutencao() + "'.";
	      }
	      if (!manutencaoAntigo.getKmatual().equals(manutencaoNova.getKmatual())) {
	          retorno += " Motivo modificado! Antigo: '" + manutencaoAntigo.getKmatual() + "'.";
	      }
	      if (!manutencaoAntigo.getStatusmanutencao().equals(manutencaoNova.getStatusmanutencao())){
	          retorno += "Quantidade modificado, Antigo: '" + manutencaoAntigo.getStatusmanutencao() + "'.";
	      }
	      if (!manutencaoAntigo.getSetor().equals(manutencaoNova.getSetor())){
	          retorno += " Setor modificado, Antigo: '" + manutencaoAntigo.getSetor() + "'.";
	      }
	      if (!manutencaoAntigo.getViatura().equals(manutencaoNova.getViatura())){	          
	          retorno += "Viatura modificada, Antigo: Viatura de Placa '" + manutencaoAntigo.getViatura().getPlacaoficial() +" Modelo: "+manutencaoAntigo.getViatura().getModelo()+ "'.";
	      }	     
	      
	      return retorno;
	 }
	 
}