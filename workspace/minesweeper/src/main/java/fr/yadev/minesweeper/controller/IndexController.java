package fr.yadev.minesweeper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fr.yadev.minesweeper.repository.GameModeRepository;
import fr.yadev.minesweeper.repository.ScoreRepository;
import fr.yadev.minesweeper.services.ScoreService;

@Controller
public class IndexController {
	@Autowired
	private ScoreService scoreServ;
	
	@Autowired
	private GameModeRepository gamemodes;
	
	@GetMapping("/")
	public String welcome(Model model) {
		model.addAttribute("message", "Minesweeper - Accueil");
		model.addAttribute("content_msg", "Bienvenue sur la page d'accueil du Minesweeper.");
		
		model.addAttribute("gamemodes", scoreServ.getRelevantGameModes());
		model.addAttribute("scores", scoreServ.getHighScores(5));
		
		return "index";
	}
}
