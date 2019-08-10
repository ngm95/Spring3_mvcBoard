package com.javalec.mvc_board.command;

import java.util.ArrayList;

import org.springframework.ui.Model;

import com.javalec.mvc_board.dao.BDao;
import com.javalec.mvc_board.dto.BDto;

public class BListCommand implements BCommand {

	@Override
	public void execute(Model model) {
		BDao dao = new BDao();
		ArrayList<BDto> dtos = dao.list();
		model.addAttribute("list", dtos);
		
	}

}