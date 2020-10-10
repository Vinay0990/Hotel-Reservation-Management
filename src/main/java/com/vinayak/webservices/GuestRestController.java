package com.vinayak.webservices;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vinayak.data.model.Guest;
import com.vinayak.data.repository.GuestRepository;

@RestController
@RequestMapping(value = "/api")
public class GuestRestController {

	@Autowired
	private GuestRepository repository;

	@RequestMapping(value = { "/guests/{guest_id}", "/guests" }, method = RequestMethod.GET)
	List<Guest> findAll(@PathVariable(value = "guest_id", required = false) Long guestID) {
		List<Guest> guests = new ArrayList<>();
		if (guestID == null) {
			Iterable<Guest> results = this.repository.findAll();
			results.forEach(guest -> {
				guests.add(guest);
			});
		} else {
			Optional<Guest> guest = this.repository.findById(guestID);
			if (null != guest) {
				guests.add(guest.get());
			}
		}
		return guests;
	}
}
