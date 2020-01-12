package fr.yadev.minesweeper.form;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
	
	@Size(min = 2, max = 20)
	private String player_name;
	
	private List<Tile> Tiles;
	
	@NotNull
	private GameMode gamemode;
}
