package sisviatura.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginControler {

	@GetMapping("**/redirectErroLogin")
	public ModelAndView login(RedirectAttributes ra) {
		ra.addFlashAttribute("error", "Login ou Senha Invalida!");
		ModelAndView mv = new ModelAndView("redirect:/Home/login");
		return mv;
	}
	
}
