package fr.yadev.minesweeper.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.yadev.minesweeper.repository.GameRepository;

@Service
public class GameService {
	@Autowired
	private GameRepository games;
}
