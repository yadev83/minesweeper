package fr.yadev.minesweeper.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="\"game\"")
public class Game {
	@Id @Column
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false)
	private Long score;
	
	@OneToMany
	private List<Tile> Tiles;
	
	@ManyToOne
	private GameMode gamemode;
}
