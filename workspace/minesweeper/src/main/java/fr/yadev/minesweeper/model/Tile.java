package fr.yadev.minesweeper.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="\"tiles\"")
public class Tile {
	@Id @Column
	@GeneratedValue
	private Long id;
	
	@Column
	private Long pos_x;
	
	@Column
	private Long pos_y;
	
	@Column
	private int state;
	//State can be : -2 (covered) | -1 (flagged) | 0 - 8 ( discovered + nb of close mines)	
	
	@Column
	private boolean mined;
}
