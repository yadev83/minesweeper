package fr.yadev.minesweeper.form;

import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameModeForm {
	private Long id;
	
	@Size(min = 2, max = 20)
	private String title;
	
	private Long width;
	private Long height;

	private int nbMines;
}
