package fr.yadev.minesweeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.yadev.minesweeper.model.Tile;

@Repository
public interface TileRepository extends JpaRepository<Tile, Long>{

}
