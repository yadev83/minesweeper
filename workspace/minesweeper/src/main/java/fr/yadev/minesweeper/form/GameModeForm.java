package fr.yadev.minesweeper.form;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameModeForm {
	private Long id;
	
	@Size(min = 5)
	private String title;
	
	private Long width;
	private Long height;

	@DecimalMin("1")
	private int nbMines;
}
