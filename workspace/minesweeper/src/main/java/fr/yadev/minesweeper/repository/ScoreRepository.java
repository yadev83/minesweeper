package fr.yadev.minesweeper.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.yadev.minesweeper.model.Score;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long>{

}
