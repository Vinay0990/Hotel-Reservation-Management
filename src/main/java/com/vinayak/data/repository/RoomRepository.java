package com.vinayak.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vinayak.data.model.Room;

@Repository 
public interface RoomRepository extends CrudRepository<Room, Long> {

	Room findByNumber(String number);
}
