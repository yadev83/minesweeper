package fr.yadev.minesweeper.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import fr.yadev.minesweeper.form.GameForm;
import fr.yadev.minesweeper.model.Game;
import fr.yadev.minesweeper.model.GameMode;
import fr.yadev.minesweeper.repository.GameModeRepository;
import fr.yadev.minesweeper.repository.GameRepository;

@Service
public class GameService {
	@Autowired
	private GameRepository games;
	
	@Autowired
	private GameModeRepository gamemodes;
	
	public String startGame() {
		List<Game> game_list = games.findAll();
		Long game_id = -1L;
		for(Game game : game_list) {
			if(game.isPlaying()) {
				game_id = game.getId();
				break;
			}
		}
		
		if(game_id == -1L) {
			return "redirect:/play/create";
		}else {
			return "redirect:/play/game/"+game_id;
		}
	}
	
	public void createGame(Model model) {
		GameForm form = new GameForm();
		model.addAttribute("game", form);
		model.addAttribute("gamemodes", gamemodes.findAll());
	}
	
	public Game initGame(GameMode gamemode) {
		Game game = new Game();
		game.setGamemode(gamemode);
		game.setPlaying(true);
		game.setScore(0L);
		
		games.save(game);
		
		return game;
	}
}
