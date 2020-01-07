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
@Table(name="\"game_modes\"")
public class GameMode {
	@Id @Column
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private Long width;
	
	@Column(nullable = false)
	private Long height;
	
	@Column(nullable = false)
	private int nbMines;
}
