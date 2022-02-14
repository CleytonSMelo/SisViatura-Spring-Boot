package sisviatura.springboot.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import sisviatura.springboot.model.Cor;
import sisviatura.springboot.model.Fotos;
import sisviatura.springboot.model.LogViatura;
import sisviatura.springboot.model.Manutencao;
import sisviatura.springboot.model.Usuarios;
import sisviatura.springboot.model.Viatura;
import sisviatura.springboot.model.Vistoria;
import sisviatura.springboot.repository.CorRepository;
import sisviatura.springboot.repository.FotoRepository;
import sisviatura.springboot.repository.ModeloRepository;
import sisviatura.springboot.repository.SetorRepository;
import sisviatura.springboot.repository.UsuarioRepository;
import sisviatura.springboot.repository.ViaturaRepository;
import sisviatura.springboot.repository.logViaturaRepository;
import sisviatura.springboot.security.ImplementacaoUserDetailService;

@Controller
public class ViaturaControler {
	
	@Autowired
	private ViaturaRepository viaturaRepository;
	
	@Autowired
	private CorRepository corRepository;
	
	@Autowired
	private ModeloRepository modeloRepository;
	
	@Autowired
	private SetorRepository setorRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private logViaturaRepository lgRepository;
	
	@Autowired
	private FotoRepository fotoRepository;
	

	@GetMapping("**/Home/Viatura/Cadastro")
	public ModelAndView inicio() {
		ModelAndView modelAndView = new ModelAndView("/Home/Viatura/Cadastro");
		modelAndView.addObject("cores", corRepository.findCores());
		modelAndView.addObject("modelos", modeloRepository.findModelos());
		modelAndView.addObject("responsavel", usuarioRepository.findUsuarios());
		modelAndView.addObject("setores", setorRepository.findSetores());
		modelAndView.addObject("viaturaobj", new Viatura());		
		return modelAndView ;
	}
	
	private static String UPLOADED_FOLDER ="C:/fotos/";
	
	@PostMapping("**/Home/Viatura/SalvarViatura")
    public ModelAndView salvar(Viatura viatura, RedirectAttributes ra,@RequestParam("foto") MultipartFile file) throws IOException {
		Viatura viatura2 = viaturaRepository.findViaturaByid(viatura.getId());	
		
		if (viatura2 == null) {	
			Viatura vtplaca = viaturaRepository.findViaturaByPlaca(viatura.getPlacaoficial());
			if (vtplaca != null) {
				ModelAndView modelandView = new ModelAndView("/Home/Viatura/Cadastro");
				modelandView.addObject("msgErro", "Viatura Já Cadastrada com esta Placa");
				modelandView.addObject("cores", corRepository.findCores());
				modelandView.addObject("modelos", modeloRepository.findModelos());
				modelandView.addObject("responsavel", usuarioRepository.findUsuarios());
				modelandView.addObject("setores", setorRepository.findSetores());
				modelandView.addObject("viaturaobj", viatura);
				return modelandView;
			}else {
				viatura.setDatacadastro(LocalDate.now());
				viatura.setPlacaoficial(viatura.getPlacaoficial().toUpperCase());
				viatura.setPlacareservada(viatura.getPlacareservada().toUpperCase());
				viatura.setDeletado(false);			
				gerarLog(viatura);
				Viatura v = viaturaRepository.save(viatura);
				
	            if (file.isEmpty()) {
					
				}else {
					byte[] bytes = file.getBytes();
					Path path = Paths.get(UPLOADED_FOLDER +""+v.getId()+""+file.getOriginalFilename());
					Files.write(path, bytes);
					Fotos fotos = new Fotos();
					fotos.setViatura(v);
					fotos.setCaminho(""+v.getId()+""+file.getOriginalFilename());
					fotoRepository.save(fotos);
				}
	            
				ra.addFlashAttribute("msg", "Viatura Cadastrada com sucesso");						
				ModelAndView modelandView = new ModelAndView("redirect:/Home/Viatura/ListarViaturas");
				return modelandView;
			}							
		}else {	
			viatura.setPlacaoficial(viatura.getPlacaoficial().toUpperCase());
			viatura.setPlacareservada(viatura.getPlacareservada().toUpperCase());			
			viatura.setDatacadastro(viatura2.getDatacadastro());
			viatura.setDeletado(viatura2.getDeletado());
			gerarLog(viatura);
			Viatura v = viaturaRepository.save(viatura);
			
            if (file.isEmpty()) {
				
			}else {
				byte[] bytes = file.getBytes();
				Path path = Paths.get(UPLOADED_FOLDER +""+v.getId()+""+file.getOriginalFilename());
				Files.write(path, bytes);
				Fotos fotos = new Fotos();
				fotos.setViatura(v);
				fotos.setCaminho(""+v.getId()+""+file.getOriginalFilename());
				fotoRepository.save(fotos);
			}
			ra.addFlashAttribute("msg", "Viatura Atualizada com sucesso");						
			ModelAndView modelandView = new ModelAndView("redirect:/Home/Viatura/ListarViaturas");
			return modelandView;
		}					
	}
			
	@GetMapping("**/Home/Viatura/ListarViaturas")
	public ModelAndView viaturas() {
		ModelAndView modelandView = new ModelAndView("/Home/Viatura/ListarViaturas");		
		modelandView.addObject("viaturas", viaturaRepository.findViaturas());
		return modelandView;
	}
	
	@GetMapping("**/Home/Viatura/EditarViatura/{idViatura}")
	public ModelAndView Editar(@PathVariable("idViatura") Long idViatura) {		
		Optional<Viatura> viatura = viaturaRepository.findById(idViatura);		
		ModelAndView modelAndView = new ModelAndView("/Home/Viatura/Cadastro");
		modelAndView.addObject("cores", corRepository.findCores());
		modelAndView.addObject("modelos", modeloRepository.findModelos());
		modelAndView.addObject("responsavel", usuarioRepository.findUsuarios());
		modelAndView.addObject("setores", setorRepository.findSetores());
		modelAndView.addObject("viaturaobj", viatura.get());
		return modelAndView;
	}
	
	@GetMapping("**/Home/Viatura/RemoverViatura/{idViatura}")
	public ModelAndView Excluir(@PathVariable("idViatura") Long idViatura, RedirectAttributes ra) {
		Viatura viatura = viaturaRepository.findViaturaByid(idViatura);
		viatura.setDeletado(true);
		viaturaRepository.save(viatura);
		ra.addFlashAttribute("msg", "Viatura Removida com sucesso");			
		ModelAndView modelAndView = new ModelAndView("redirect:/Home/Viatura/ListarViaturas");	
		return modelAndView;
	}
	
	private static String UPLOADED_ARQ ="C:/fotos/viatura/";
	@PostMapping("**/Home/Viatura/UploadFoto")
	public String uploadFoto(Long idViatura, RedirectAttributes ra, @RequestParam("foto") MultipartFile file) throws IOException {
		
		Viatura v = viaturaRepository.findViaturaByid(idViatura);				
        
		if (file.isEmpty()) {
			
		}else {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_ARQ +""+idViatura+""+file.getOriginalFilename());
			Files.write(path, bytes);
			Fotos fotos = new Fotos();
			fotos.setViatura(v);
			fotos.setCaminho(""+v.getId()+""+file.getOriginalFilename());
			fotoRepository.save(fotos);
		}
		ra.addFlashAttribute("msg", "Foto Cadastrada com sucesso");											
		return  "redirect:/Home/Viatura/ListarViaturas";
	}
	
	@RequestMapping(value = "**/Home/Viatura/DetalhesViatura/{idViatura}", method = RequestMethod.GET)
	public ModelAndView detalhesViatura(@PathVariable("idViatura") Long idViatura) {
		
		Optional<Viatura> viatura = viaturaRepository.findById(idViatura);		
		ModelAndView modelAndView = new ModelAndView("/Home/Viatura/Detalhe");		
		modelAndView.addObject("viaturaobj", viatura.get());		
		return modelAndView;
	}
	
	private static final String EXTERNAL_FILE_PATH = "C:/fotos/viatura/";
	
	@RequestMapping("**/Home/Viatura/FotoDownload/{fileName}")
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
	
	@RequestMapping("**/Home/Viatura/RemoverFoto/{fotoid}")
	public String removerFotos(HttpServletRequest request, HttpServletResponse response, @PathVariable("fotoid") Long fotoid) {
		Fotos foto = fotoRepository.findFotosByid(fotoid);
		Viatura v = viaturaRepository.findViaturaByid(foto.getViatura().getId());
		String nome = "C:/fotos/viatura/"+foto.getCaminho();  
		File f = new File(nome);  
		f.delete();
		
		fotoRepository.delete(foto);
		return "redirect:/Home/Viatura/DetalhesViatura/"+v.getId();
	}
	
	@RequestMapping(value = "**/Home/Viatura/LogViatura/{idViatura}", method = RequestMethod.GET)
	public ModelAndView logViatura(@PathVariable("idViatura") Long idViatura) {		
	    List<LogViatura> lg = lgRepository.findLogByData(idViatura);		
		ModelAndView modelAndView = new ModelAndView("/Home/Viatura/ListarLog");		
		modelAndView.addObject("logsobj", lg);		
		return modelAndView;
	}
	
	public void gerarLog(Viatura viatura) {
	      LocalDateTime horario = LocalDateTime.now().plusSeconds(1l);
	      if(viatura.getId() == null) {
	    	  viatura.getLogviatura().add(gerarLogCadastroViatura(viatura));
	      } else {
	          Viatura viaturaAntigo = viaturaRepository.findViaturaByid(viatura.getId());
	          String comparaAlienacao = compararViaturas(viaturaAntigo, viatura);
	          if (!comparaAlienacao.equals("")) {
	        	  viatura.getLogviatura().add(gerarLogAtualizarViatura(viatura, horario, comparaAlienacao));
	          }
	      }
	    }
		
		public LogViatura gerarLogCadastroViatura(Viatura viatura){
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
			Usuarios use = usuarioRepository.findUserByName(authentication.getName());
			LogViatura logViatura = new LogViatura();
			
			logViatura.setLog("O Usuário "+use.getNome()+" Cadastrou a Viatura de Placa: " + viatura.getPlacaoficial() +".");
			
	        logViatura.setUsuario(use); 
	        logViatura.setDataAlteracao(LocalDateTime.now());
	        logViatura.setViatura(viatura);
	        return logViatura;
	    }
		
		private LogViatura gerarLogAtualizarViatura(Viatura idViatura, LocalDateTime horario, String comparaViatura) {
	    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
			Usuarios use = usuarioRepository.findUserByName(authentication.getName());
	        LogViatura logViatura = new LogViatura();
	        
	        Viatura v = viaturaRepository.findViaturaByid(idViatura.getId());        
	        String LogString = "O Usuário "+use.getNome()+" alterou esta Viatura " + "\n" + comparaViatura;
	        logViatura.setLog(LogString);
	        logViatura.setUsuario(use);        
	        logViatura.setDataAlteracao(horario);
	        logViatura.setViatura(idViatura);
	        return logViatura;
	    }
		
		public String compararViaturas(Viatura viaturaAntigo, Viatura viaturaNova) {
	      String retorno = "";

	      if (!viaturaAntigo.getAno().equals(viaturaNova.getAno())){
	          retorno += "Ano modificado, Antigo: '" + viaturaAntigo.getAno() + "'.";
	      }
	      if (!viaturaAntigo.getCor().equals(viaturaNova.getCor())) {
	          retorno += " Cor modificado! Antigo: '" + viaturaAntigo.getCor() + "'.";
	      }
	      if (!viaturaAntigo.getCotaExtra().equals(viaturaNova.getCotaExtra())){
	          retorno += "Cota Extra modificado, Antigo: '" + viaturaAntigo.getCotaExtra() + "'.";
	      }
	      if (!viaturaAntigo.getCotaSemanal().equals(viaturaNova.getCotaSemanal())){
	          retorno += "Cota Mensal modificado, Antigo: '" + viaturaAntigo.getCotaSemanal() + "'.";
	      }
	      if (!viaturaAntigo.getKmInicial().equals(viaturaNova.getKmInicial())){
	          retorno += "Km Inicial modificado, Antigo: '" + viaturaAntigo.getKmInicial() + "'.";
	      }
	      if (!viaturaAntigo.getKmLitro().equals(viaturaNova.getKmLitro())){
	          retorno += "Km/L modificado, Antigo: '" + viaturaAntigo.getKmLitro() + "'.";
	      }
	      if (!viaturaAntigo.getLotacao().equals(viaturaNova.getLotacao())){
	          retorno += "Lotação modificada, Antigo: '" + viaturaAntigo.getLotacao() + "'.";
	      }
	      if (!viaturaAntigo.getModelo().equals(viaturaNova.getModelo())){
	          retorno += "Modelo modificado, Antigo: '" + viaturaAntigo.getModelo() + "'.";
	      }
	      if (!viaturaAntigo.getNaturezaviatura().equals(viaturaNova.getNaturezaviatura())){
	          retorno += "Natureza Viatura modificada, Antigo: '" + viaturaAntigo.getNaturezaviatura() + "'.";
	      }
	      if (!viaturaAntigo.getPlacaoficial().equals(viaturaNova.getPlacaoficial())){
	          retorno += "Placa Oficial modificada, Antigo: '" + viaturaAntigo.getPlacaoficial() + "'.";
	      }
	      if (!viaturaAntigo.getPlacareservada().equals(viaturaNova.getPlacareservada())){
	          retorno += "Placa Reservada modificado, Antigo: '" + viaturaAntigo.getPlacareservada() + "'.";
	      }
	      if (!viaturaAntigo.getResponsavel().equals(viaturaNova.getResponsavel())){
	          retorno += "Responsavel modificado, Antigo: '" + viaturaAntigo.getResponsavel() + "'.";
	      }
	      if (!viaturaAntigo.getTipocombustivel().equals(viaturaNova.getTipocombustivel())){
	          retorno += "Ano modificado, Antigo: '" + viaturaAntigo.getTipocombustivel() + "'.";
	      }
	      if (!viaturaAntigo.getTipoviatura().equals(viaturaNova.getTipoviatura())){
	          retorno += "Tipo Viatura modificada, Antigo: '" + viaturaAntigo.getTipoviatura() + "'.";
	      }
	      if (!viaturaAntigo.getValorInvestido().equals(viaturaNova.getValorInvestido())){
	          retorno += "Valor Investido modificado, Antigo: '" + viaturaAntigo.getValorInvestido() + "'.";
	      }
	      if (!viaturaAntigo.getValorMercado().equals(viaturaNova.getValorMercado())){
	          retorno += "Valor de Mercado modificado, Antigo: '" + viaturaAntigo.getValorMercado() + "'.";
	      }
	      return retorno;
	    }
	 
}