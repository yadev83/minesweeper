package fr.yadev.minesweeper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fr.yadev.minesweeper.repository.ScoreRepository;

@Controller
public class IndexController {
	@Autowired
	private ScoreRepository scores;
	
	@GetMapping("/")
	public String welcome(Model model) {
		model.addAttribute("message", "Hello There");
		model.addAttribute("content_msg", "Ceci est un magnifique test !");
		
		model.addAttribute("scores", scores.findAll());
		
		return "index";
	}
}
