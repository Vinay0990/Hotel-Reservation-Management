package com.vinayak.business.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vinayak.business.domain.RoomReservation;
import com.vinayak.data.model.Guest;
import com.vinayak.data.model.Reservation;
import com.vinayak.data.model.Room;
import com.vinayak.data.repository.GuestRepository;
import com.vinayak.data.repository.ReservationRepository;
import com.vinayak.data.repository.RoomRepository;

@Service
public class ReservationService {
	private RoomRepository roomRepository;
	private GuestRepository guestRepository;
	private ReservationRepository reservationRepository;

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	public ReservationService(RoomRepository roomRepository, GuestRepository guestRepository,
			ReservationRepository reservationRepository) {
		super();
		this.roomRepository = roomRepository;
		this.guestRepository = guestRepository;
		this.reservationRepository = reservationRepository;
	}

	public List<RoomReservation> getRoomReservationByDate(String dateString) {
		Date date = getDatefromString(dateString);
		Iterable<Room> rooms = roomRepository.findAll();
		Map<Long, RoomReservation> roomReservationMap = new HashMap<Long, RoomReservation>();

		rooms.forEach(room -> {
			RoomReservation roomReservation = new RoomReservation();
			roomReservation.setRoomId(room.getId());
			roomReservation.setRoomName(room.getName());
			roomReservation.setRoomNumber(room.getNumber());

			roomReservationMap.put(room.getId(), roomReservation);
		});

		Iterable<Reservation> reservations = this.reservationRepository.findByDate(new java.sql.Date(date.getTime()));

		if (null != reservations) {
			reservations.forEach(reservation -> {
				Optional<Guest> guest = this.guestRepository.findById(reservation.getGuestId());
				if (null != guest) {
					RoomReservation roomReservation = roomReservationMap.get(reservation.getId());
					roomReservation.setDate(date);
					roomReservation.setFirstName(guest.get().getFirstName());
					roomReservation.setLastName(guest.get().getLastName());
					roomReservation.setGuestId(guest.get().getId());
				}
				
			});
		}

		List<RoomReservation> roomReservations = new ArrayList<RoomReservation>();
		for (long roomId : roomReservationMap.keySet()) {
			roomReservations.add(roomReservationMap.get(roomId));
		}
		return roomReservations;
	}

	private Date getDatefromString(String dateString) {
		Date date;
		if (null != dateString) {
			try {
				date = DATE_FORMAT.parse(dateString);
			} catch (ParseException pe) {
				date = new Date();
			}
		} else {
			date = new Date();
		}
		return date;
	}

}
