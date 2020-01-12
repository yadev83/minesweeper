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
import fr.yadev.minesweeper.model.Tile;
import fr.yadev.minesweeper.services.GameService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Controller
@RequestMapping("/play")
public class GameController {
	@Autowired
	private GameService service;
	
	@GetMapping("/start")
	public String start(Model model) {	
		return service.startGame();
	}
	
	@GetMapping("/create")
	public String create(Model model) {
		service.createGame(model);
		return "create_game";
	}
	
	@GetMapping("/reset")
	public String reset(Model model) {
		service.stopGame();
		return "redirect:/play/create";
	}
	
	@GetMapping("/game/{id}")
	public String play(@PathVariable(required = true) Long id, Model model) {
		service.game(id, model);
		return "game";
	}
	
	@GetMapping("/game/{id}/{mode}/{posx}/{posy}")
	public String handleTile(@PathVariable(required = true) Long id, @PathVariable(required=true) Long mode, @PathVariable(required = true) Long posx, @PathVariable(required = true) Long posy, Model model) {
		Tile tile = service.getTile(id, posx, posy);
		Game game = service.getGame(id);
		System.out.println(model.getAttribute("nbFlag"));
		if(mode == 1)
			service.processTile(game, tile, model);
		else if(mode == -1)
			service.flagTile(game, tile, model);
		return "redirect:/play/game/"+id;
	}
	
	@GetMapping("/swap/{id}")
	public String changeMode(@PathVariable(required=true) Long id, Model model) {
		service.swapMode(service.getGame(id));
		return "redirect:/play/game/"+id;
	}
	
	@PostMapping("/create")
	public String createForm(@Valid @ModelAttribute("game") GameForm form, BindingResult result, Model model) {
		if(result.hasErrors() || form.getGamemode() == null) {
			model.addAttribute("game", form);
			model.addAttribute("gamemodes", service.getGamemodes());
			return "create_game";
		}
		
		Game game = service.initGame(form.getGamemode(), form.getPlayer_name());
		
		return "redirect:/play/game/"+game.getId();
	}
}
