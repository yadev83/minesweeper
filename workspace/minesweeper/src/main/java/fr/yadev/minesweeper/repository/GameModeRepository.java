package fr.yadev.minesweeper.repository;

import fr.yadev.minesweeper.model.GameMode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameModeRepository extends JpaRepository<GameMode, Long>{

}
