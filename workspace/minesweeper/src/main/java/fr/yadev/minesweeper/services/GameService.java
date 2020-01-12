package fr.yadev.minesweeper.services;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import fr.yadev.minesweeper.form.GameForm;
import fr.yadev.minesweeper.model.Game;
import fr.yadev.minesweeper.model.GameMode;
import fr.yadev.minesweeper.model.Score;
import fr.yadev.minesweeper.model.Tile;
import fr.yadev.minesweeper.repository.GameModeRepository;
import fr.yadev.minesweeper.repository.GameRepository;
import fr.yadev.minesweeper.repository.ScoreRepository;
import fr.yadev.minesweeper.repository.TileRepository;

@Service
public class GameService {
	@Autowired
	private GameRepository games;
	
	@Autowired
	private GameModeRepository gamemodes;
	
	@Autowired
	private ScoreRepository scores;
	
	@Autowired
	private TileRepository tiles;
	
	@Autowired
	private TileService tile_service;
	
	public List<GameMode> getGamemodes(){
		return gamemodes.findAll();
	}
	
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
	
	public void stopGame() {
		for(Game game : games.findAll()) {
			if(game.isPlaying())
				game.setPlaying(false);
		}
		games.saveAll(games.findAll());
	}
	
	public void game(Long id, Model model) {
		List<Tile> tiles = getGame(id).getTiles();
		Collections.sort(tiles, tile_service.compareByPosX);
		Collections.sort(tiles, tile_service.compareByPosY);
		
		int nbFlag = 0;
		for(Tile tile : tiles) {
			if(tile.getState() == -1) {
				++nbFlag;
			}
		}
		model.addAttribute("nbFlag", nbFlag);
		
		model.addAttribute("name", games.findById(id).get().getPlayer_name());
		model.addAttribute("gamemode", games.findById(id).get().getGamemode());
		model.addAttribute("tiles", tiles);
		model.addAttribute("mode", games.findById(id).get().getMode());
		model.addAttribute("gameover", isGameOver(games.findById(id).get()));
	}
	
	public Game initGame(GameMode gamemode, String playerName) {
		Game game = new Game();
		game.setGamemode(gamemode);
		game.setPlaying(true);
		game.setScore(0L);
		game.setMode(1);
		game.setPlayer_name(playerName);
		game.setStartTime(Instant.now());
		
		List<Tile> tiles = new ArrayList<>();
		
		for(Long i = 0L; i < gamemode.getWidth(); ++i) {
			for(Long j = 0L; j < gamemode.getHeight(); ++j) {
				tiles.add(tile_service.createTile(i, j));
			}
		}
		
		tile_service.mineField(tiles, gamemode.getNbMines());
		
		game.setTiles(tiles);
		
		games.save(game);
		
		return game;
	}

	public int isGameOver(Game game) {
		List<Tile> tiles = game.getTiles();
		int covered = 0;
		for(Tile tile : tiles) {
			if(tile.getState() == -3) {
				return -1;
			}else if(tile.getState() == -2 || tile.getState() == -1) {
				++covered;
			}
		}

		if(covered == game.getGamemode().getNbMines()) {
			Score score = new Score();
			score.setUsername(game.getPlayer_name());
			score.setTime(Instant.now().getEpochSecond()-game.getStartTime().getEpochSecond());
			score.setGamemode(game.getGamemode());
			scores.save(score);
			return 1;
		}
		
		return 0;
	}
	
	public Game getGame(Long id) {
		return games.getOne(id);
	}
	
	public void swapMode(Game game) {
		Game update = games.getOne(game.getId());
		update.setMode(update.getMode() * (-1));
		games.save(update);
	}
	
	public Tile getTile(Long game_id, Long posx, Long posy) {
		Collections.sort(getGame(game_id).getTiles(), tile_service.compareByPosX);
		Collections.sort(getGame(game_id).getTiles(), tile_service.compareByPosY);
		
		Object[] array = games.getOne(game_id).getTiles().toArray();
		for(int i = 0; i < games.getOne(game_id).getTiles().size(); ++i) {
			if(posx == ((Tile) array[i]).getPos_x() && posy == ((Tile) array[i]).getPos_y()) {
				System.out.println("Index : " + i + " | " + ((Tile) array[i]).getPos_x() + " " + ((Tile) array[i]).getPos_y());
				if(((Tile) array[i]).isMined())
					System.out.println("MINE");
				return (Tile) array[i];
			}
		}
		
		/*for(Tile tile : games.getOne(game_id).getTiles()) {
			//System.out.println(tile.getPos_x() + " " + tile.getPos_y());
			if(posx == tile.getPos_x() && posy == tile.getPos_y()) {
				//System.out.println("yes");
				return tile;
			}
		}*/
		
		return null;
	}
	
	public void flagTile(Game game, Tile tile, Model model) {
		Tile update = tiles.getOne(tile.getId());
		
		int nbFlag = 0;
		for(Tile t : game.getTiles()) {
			if(t.getState() == -1) {
				++nbFlag;
			}
		}
		
		System.out.println(nbFlag);
		
		if(nbFlag != game.getGamemode().getNbMines() && update.getState() == -2) {
			update.setState(-1);
			tiles.save(update);
		}else if(update.getState() == -1){
			update.setState(-2);
			tiles.save(update);
		}
	}
	
	public void processTile(Game game, Tile tile, Model model) {
		Tile update = tiles.getOne(tile.getId());
		Long posx = update.getPos_x();
		Long posy = update.getPos_y();
		//System.out.println("Updated tile with id " + update.getId() + " at pos " + update.getPos_x() + ", " + update.getPos_y());

		if(update.getState() == -2) {
			if(update.isMined()) {
				update.setState(-3);
				tiles.save(update);
			}else {
				int mineNb = getNumberOfCloseMines(game, update, model);
				update.setState(mineNb);
				tiles.save(update);
				if(mineNb == 0) {
					propagation(game, getTile(game.getId(), posx-1, posy), model);
					propagation(game, getTile(game.getId(), posx+1, posy), model);
					propagation(game, getTile(game.getId(), posx, posy-1), model);
					propagation(game, getTile(game.getId(), posx, posy+1), model);
				}	
			}
		}
	}
	
	private void propagation(Game game, Tile tile, Model model) {
		if(tile != null) {
			if(!tile.isMined()) {
				processTile(game, tile, model);
			}
		}	
	}
	
	private int getNumberOfCloseMines(Game game, Tile tileToProcess, Model model) {
		int nbMines = 0;
		Tile tile = tiles.getOne(tileToProcess.getId());
		Long posx = tile.getPos_x();
		Long posy = tile.getPos_y();
		
		if(posy-1 >= 0) {
			if(getTile(game.getId(), posx, posy-1).isMined())
				++nbMines;
		}
		
		if(posy+1 < game.getGamemode().getHeight()) {
			if(getTile(game.getId(), posx, posy+1).isMined())
				++nbMines;
		}
		
		if(posx-1 >= 0) {
			if(getTile(game.getId(), posx-1, posy).isMined())
				++nbMines;
			if(posy-1 >= 0) {
				if(getTile(game.getId(), posx-1, posy-1).isMined())
					++nbMines;
				
			}
			if(posy+1 < game.getGamemode().getHeight()) {
				if(getTile(game.getId(), posx-1, posy+1).isMined())
					++nbMines;
			}
		}
		
		if(posx+1 < game.getGamemode().getWidth()) {
			if(getTile(game.getId(), posx+1, posy).isMined())
				++nbMines;
			if(posy-1 >= 0) {
				if(getTile(game.getId(), posx+1, posy-1).isMined())
					++nbMines;
				
			}
			if(posy+1 < game.getGamemode().getHeight()) {
				if(getTile(game.getId(), posx+1, posy+1).isMined())
					++nbMines;
			}
		}
		
		return nbMines;
	}
}
