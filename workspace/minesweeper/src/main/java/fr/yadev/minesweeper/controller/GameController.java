package fr.yadev.minesweeper.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.yadev.minesweeper.form.GameForm;
import fr.yadev.minesweeper.model.Game;
import fr.yadev.minesweeper.services.GameService;
import fr.yadev.minesweeper.services.MinesweeperGameService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Controller
@RequestMapping("/play")
public class GameController {
	@Autowired
	private GameService service;
	
	@Autowired 
	private MinesweeperGameService ms_game;
	
	@GetMapping("/start")
	public String start(Model model) {	
		return service.startGame();
	}
	
	@GetMapping("/create")
	public String create(Model model) {
		service.createGame(model);
		return "create_game";
	}
	
	@GetMapping("/game/{id}")
	public String play(@PathVariable(required = true) Long id, Model model) {
		service.game(id, model);
		return "game";
	}
	
	@PostMapping("/create")
	public String createForm(@Valid @ModelAttribute("game") GameForm form, BindingResult result, Model model) {
		if(result.hasErrors()) {
			model.addAttribute("game", form);
			return "create_game";
		}
		
		Game game = service.initGame(form.getGamemode());
		
		return "redirect:/play/game/"+game.getId();
	}
}
