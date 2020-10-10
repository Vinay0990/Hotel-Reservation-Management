package com.vinayak.webservices;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vinayak.data.model.Room;
import com.vinayak.data.repository.RoomRepository;

@RestController
@RequestMapping(value = "/api")
public class RoomController {

	@Autowired
	private RoomRepository repository;

	@RequestMapping(value = { "/rooms/{room_no}", "/rooms" }, method = RequestMethod.GET)
	List<Room> findAll(@PathVariable(value = "room_no", required = false) String roomNumber) {
		List<Room> rooms = new ArrayList<>();
		if (null == roomNumber) {
			Iterable<Room> results = this.repository.findAll();
			results.forEach(room -> {
				rooms.add(room);
			});
		} else {
			Room room = this.repository.findByNumber(roomNumber);
			if (null != room) {
				rooms.add(room);
			}
		}
		return rooms;
	}

}
