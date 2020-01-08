package fr.yadev.minesweeper.form;

import java.util.List;

import fr.yadev.minesweeper.model.GameMode;
import fr.yadev.minesweeper.model.Tile;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameForm {
	private Long id;

	private Long score;
	private boolean playing;
	
	private List<Tile> Tiles;
	private GameMode gamemode;
}
