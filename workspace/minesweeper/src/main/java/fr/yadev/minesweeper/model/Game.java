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
@Table(name="\"game\"")
public class Game {
	@Id @Column
	@GeneratedValue
	private Long id;
}
