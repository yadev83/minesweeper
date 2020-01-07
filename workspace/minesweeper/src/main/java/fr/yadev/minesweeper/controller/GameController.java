package fr.yadev.minesweeper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.yadev.minesweeper.repository.GameRepository;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Controller
@RequestMapping("/GameController")
public class GameController {
	@Autowired
	private GameRepository games;
}
