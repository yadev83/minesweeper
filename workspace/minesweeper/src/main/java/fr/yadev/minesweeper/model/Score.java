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
@Table(name="\"high_scores\"")
public class Score {
	@Id @Column
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false)
	private String username;
	
	@Column(nullable = false)
	private Long time;
}
