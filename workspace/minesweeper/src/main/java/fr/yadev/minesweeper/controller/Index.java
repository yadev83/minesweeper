package fr.yadev.minesweeper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Index {
	@GetMapping("/")
	public String welcome(Model model) {
		model.addAttribute("message", "Hello There");
		model.addAttribute("content_msg", "Ceci est un magnifique test !");
		
		return "index";
	}
}
