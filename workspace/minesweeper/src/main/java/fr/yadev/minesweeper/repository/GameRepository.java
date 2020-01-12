package fr.yadev.minesweeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.yadev.minesweeper.model.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

}
