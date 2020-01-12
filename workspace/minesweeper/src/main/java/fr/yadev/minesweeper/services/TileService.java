package fr.yadev.minesweeper.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.yadev.minesweeper.model.Tile;
import fr.yadev.minesweeper.repository.TileRepository;

@Service
public class TileService {
	@Autowired
	private TileRepository tiles;
	
	public Comparator<Tile> compareByPosX = new Comparator<Tile>() {
	    @Override
	    public int compare(Tile t1, Tile t2) {
	        return t1.getPos_x().compareTo(t2.getPos_x());
	    }
	};
	
	public Comparator<Tile> compareByPosY = new Comparator<Tile>() {
	    @Override
	    public int compare(Tile t1, Tile t2) {
	        return t1.getPos_y().compareTo(t2.getPos_y());
	    }
	};
	
	public Tile createTile(Long pos_x, Long pos_y) {
		Tile tile = new Tile();
		tile.setPos_x(pos_x);
		tile.setPos_y(pos_y);
		tile.setMined(false);
		tile.setState(-2);
		
		tiles.save(tile);
		
		return tile;
	}
	
	public List<Tile> mineField(List<Tile> tileset, int nb_mines){
		ArrayList<Integer> pioche = new ArrayList<>();
		for(int i = 0; i < tileset.size(); ++i) {
			pioche.add(i);
		}
		
		Random rng = new Random();
		System.out.println(nb_mines);
		for(int i = 0; i < nb_mines; ++i) {
			int tmp = rng.nextInt(pioche.size());
			System.out.println("Nb" + tmp + "chosen through 0 to " + nb_mines);
			tileset.get(pioche.get(tmp)).setMined(true);
			pioche.remove(tmp);
		}
		
		tiles.saveAll(tileset);
		
		return tileset;
	}
	

}
