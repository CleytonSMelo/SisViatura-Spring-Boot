package sisviatura.springboot.controller;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.expression.spel.ast.OpInc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import sisviatura.springboot.dto.TotalServicoPorData;
import sisviatura.springboot.model.Setor;
import sisviatura.springboot.model.Usuarios;
import sisviatura.springboot.repository.CotaRepository;
import sisviatura.springboot.repository.ManutencaoRepository;
import sisviatura.springboot.repository.SetorRepository;
import sisviatura.springboot.repository.UsuarioRepository;
import sisviatura.springboot.repository.VistoriaRepository;
import sisviatura.springboot.security.ImplementacaoUserDetailService;

@Controller
public class InicioControler {
	
	@Autowired
	private SetorRepository setorRepository; 
	
	@Autowired
	private UsuarioRepository usuarioRepository; 
	
	@Autowired
	private ManutencaoRepository manutencaoRepository;
	
	@Autowired
	private CotaRepository cotaRepository;
	
	@Autowired
	private VistoriaRepository vistoriaRepository;
	
	@RequestMapping(method = RequestMethod.GET , value = "**/Home/Dashboard/index")
	public ModelAndView cardsDashiboard() {
		ModelAndView mv = new ModelAndView("/Home/Dashboard/index");		
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
		Usuarios use = usuarioRepository.findUserByName(authentication.getName());
		//serviços
		mv.addObject("totalManutencaoSolicitadas", manutencaoRepository.contarManutencaoSolicitadas());
		mv.addObject("totalManutencaoConcluidas", manutencaoRepository.contarManutencaoConcluidas());
		mv.addObject("totalManutencaoAnual", manutencaoRepository.contarTotalManutencaoAnual());
		
		//cotas
		mv.addObject("totalCotasSolicitadas", cotaRepository.contarCotasSolicitados());
		mv.addObject("totalCotasAprovada", cotaRepository.contarCotasAprovadas());
		mv.addObject("totalCotasAnual", cotaRepository.contarTotalCotasAnual());

		
		//Vistorias
		mv.addObject("totalVistoriasSolicitadas", vistoriaRepository.contarVistoriasSolicitadas());
		mv.addObject("totalVistoriasConcluidas", vistoriaRepository.contarVistoriasConcluidas());
		mv.addObject("totalVistoriasAnual", vistoriaRepository.contarTotalVistoriasAnual());
		return mv;
		
	}
	
	@GetMapping(value="**/filtrarServicoPorAno/{data}", produces = "application/json")
	public ResponseEntity<Long> buscarServicoporAno(@PathVariable (value = "data")String data) {
		int v = Integer.parseInt(data);
		Long total = manutencaoRepository.contarTotalServicosSelectAnual(v);
		return new ResponseEntity<Long>(total, HttpStatus.OK);
	}
	
	@GetMapping(value="**/filtrarCotasPorAno/{data}", produces = "application/json")
	public ResponseEntity<Long> buscarCotasporAno(@PathVariable (value = "data")String data) {
		int v = Integer.parseInt(data);
		Long total = cotaRepository.contarTotalCotasSelectAnual(v);
		return new ResponseEntity<Long>(total, HttpStatus.OK);
	}
	
	@GetMapping(value="**/filtrarVistoriasPorAno/{data}", produces = "application/json")
	public ResponseEntity<Long> buscarVistoriasporAno(@PathVariable (value = "data")String data) {
		int v = Integer.parseInt(data);
		Long total = vistoriaRepository.contarTotalVistoriasSelectAnual(v);
		return new ResponseEntity<Long>(total, HttpStatus.OK);
	}
 
}
