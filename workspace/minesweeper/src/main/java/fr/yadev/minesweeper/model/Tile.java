package fr.yadev.minesweeper.model;

import java.util.List;

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
}
