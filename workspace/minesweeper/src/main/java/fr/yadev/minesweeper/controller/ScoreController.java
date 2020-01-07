package fr.yadev.minesweeper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.yadev.minesweeper.repository.ScoreRepository;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Controller
@RequestMapping("/ScoreController")
public class ScoreController {
	@Autowired
	private ScoreRepository scores;
}
