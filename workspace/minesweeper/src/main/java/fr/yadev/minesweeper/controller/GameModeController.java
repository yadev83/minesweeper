package fr.yadev.minesweeper.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.yadev.minesweeper.form.GameModeForm;
import fr.yadev.minesweeper.model.GameMode;
import fr.yadev.minesweeper.repository.GameModeRepository;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Controller
@RequestMapping("/gamemode")
public class GameModeController {
	@Autowired
	private GameModeRepository gamemodes;
	
	@GetMapping("/list")
	public String list(Model model) {
		model.addAttribute("gamemodes", gamemodes.findAll());
		
		return "list_gamemode";
	}
	
	@GetMapping({"/add", "edit/{id}"})
	public String add(@PathVariable(required = false) Long id, Model model) {
		GameModeForm form = new GameModeForm();
		model.addAttribute("gamemode", form);
		
		if(id != null) {
			GameMode gm = gamemodes.findById(id).get();
			
			form.setId(gm.getId());
			form.setTitle(gm.getTitle());
			form.setHeight(gm.getHeight());
			form.setWidth(gm.getWidth());
			form.setNbMines(gm.getNbMines());
		}
		
		return "add_gamemode";
	}
	
	@PostMapping("/add")
	public String addForm(@Valid @ModelAttribute("gamemode") GameModeForm form, BindingResult result, Model model) {
		if(result.hasErrors()) {
			model.addAttribute("gamemode", form);
			return "add_gamemode";
		}
		
		GameMode gm = new GameMode();
		
		if(form.getId() != null) {
			gm = gamemodes.findById(form.getId()).get();
		}
		
		gm.setTitle(form.getTitle());
		gm.setHeight(form.getHeight());
		gm.setWidth(form.getWidth());
		gm.setNbMines(form.getNbMines());
		
		gamemodes.save(gm);
		
		return "redirect:/gamemode/list";
	}
}
