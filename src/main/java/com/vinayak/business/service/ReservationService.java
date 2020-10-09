package com.vinayak.business.service;

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
	
	@Autowired
	public ReservationService(RoomRepository roomRepository, GuestRepository guestRepository,
			ReservationRepository reservationRepository) {
		super();
		this.roomRepository = roomRepository;
		this.guestRepository = guestRepository;
		this.reservationRepository = reservationRepository;
	}
	
	public List<RoomReservation> getRoomReservationByDate(Date date){
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
		
		if(null != reservations) {
			reservations.forEach(reservation -> {
				Optional<Guest> guest = this.guestRepository.findById(reservation.getGuestId());
				if(null != guest) {
					RoomReservation roomReservation = roomReservationMap.get(reservation.getId());
					roomReservation.setDate(date);
					roomReservation.setFirstName(guest.get().getFirstName());
					roomReservation.setLastName(guest.get().getLastName());
					roomReservation.setGuestId(guest.get().getId());
				}
			});
		}
		
		
		List<RoomReservation> roomReservations = new ArrayList<RoomReservation>();
		for(long roomId:roomReservationMap.keySet()) {
			roomReservations.add(roomReservationMap.get(roomId));
		}
		return roomReservations;
	}

	
}
