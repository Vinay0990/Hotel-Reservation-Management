package com.vinayak.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vinayak.business.service.GuestService;
import com.vinayak.data.model.Guest;

@Controller
@RequestMapping(value = "/guests")
public class GuestController {

	@Autowired
	private GuestService guestService;

	@GetMapping
	public String getGuests(@RequestParam(value = "id", required = false) Long id, Model model) {
		List<Guest> guestList = this.guestService.getAllGuest();
		model.addAttribute("guests", guestList);
		return "guests";
	}


}
