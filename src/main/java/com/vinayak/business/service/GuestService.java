package com.vinayak.business.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vinayak.data.model.Guest;
import com.vinayak.data.repository.GuestRepository;

@Service
public class GuestService {

	private GuestRepository guestRepository;

	public GuestService(GuestRepository guestRepository) {
		super();
		this.guestRepository = guestRepository;
	}

	public List<Guest> getAllGuest() {
		Iterable<Guest> iGuests = this.guestRepository.findAll();

		List<Guest> guests = new ArrayList<Guest>();
		if (null != iGuests) {
			iGuests.forEach(guest -> {
				guests.add(guest);

			});
		}

		return guests;
	}
}
