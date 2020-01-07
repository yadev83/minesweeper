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
import fr.yadev.minesweeper.services.GameModeService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Controller
@RequestMapping("/gamemode")
public class GameModeController {
	@Autowired
	private GameModeService service;
	
	@GetMapping("/list")
	public String list(Model model) {
		model.addAttribute("gamemodes", service.getGameModes());
		
		return "list_gamemode";
	}
	
	@GetMapping({"/add", "edit/{id}"})
	public String add(@PathVariable(required = false) Long id, Model model) {
		GameModeForm form;
		if(id != null) {
			form = service.getGameModeForm(id);
		}else {
			form = new GameModeForm();
		}
		model.addAttribute("gamemode", form);
		
		return "add_gamemode";
	}
	
	@GetMapping("/remove/{id}")
	public String remove(@PathVariable(required = true) Long id, Model model) {
		service.deleteGameMode(id);
		return "redirect:/gamemode/list";
	}
	
	@GetMapping("/remove")
	public String removeError() {
		//If no id is provided to the /remove page, redirects to the list instead
		return "redirect:/gamemode/list";
	}
	
	@PostMapping("/add")
	public String addForm(@Valid @ModelAttribute("gamemode") GameModeForm form, BindingResult result, Model model) {
		if(result.hasErrors()) {
			model.addAttribute("gamemode", form);
			return "add_gamemode";
		}
		
		service.editGameMode(form.getId(), form.getTitle(), form.getWidth(), form.getHeight(), form.getNbMines());
		
		return "redirect:/gamemode/list";
	}
}
