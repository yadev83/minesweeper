package fr.yadev.minesweeper.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import fr.yadev.minesweeper.repository.GameModeRepository;
import fr.yadev.minesweeper.form.GameModeForm;
import fr.yadev.minesweeper.model.GameMode;

@Service
public class GameModeService {
	@Autowired
	private GameModeRepository gamemodes;
	
	public List<GameMode> getGameModes(){
		return gamemodes.findAll();
	}
	
	public GameMode editGameMode(Long id, String title, Long width, Long height, int nbMines) {
		GameMode gm = new GameMode();
		if(id != null) {
			gm = gamemodes.findById(id).get();;
		}
		
		gm.setTitle(title);
		gm.setHeight(height);
		gm.setWidth(width);
		gm.setNbMines(nbMines);
		
		gamemodes.save(gm);
		
		return gm;
	}
	
	public GameModeForm getGameModeForm(Long id) {
		GameModeForm form = new GameModeForm();
		GameMode gm = gamemodes.findById(id).get();
		
		form.setId(gm.getId());
		form.setTitle(gm.getTitle());
		form.setHeight(gm.getHeight());
		form.setWidth(gm.getWidth());
		form.setNbMines(gm.getNbMines());
		
		return form;
	}
	
	public void deleteGameMode(Long id) {
		gamemodes.deleteById(id);
	}
}
