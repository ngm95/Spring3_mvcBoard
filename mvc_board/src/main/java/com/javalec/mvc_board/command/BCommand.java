package com.javalec.mvc_board.command;

import org.springframework.ui.Model;

public interface BCommand {
	
	void execute(Model model);
	
}