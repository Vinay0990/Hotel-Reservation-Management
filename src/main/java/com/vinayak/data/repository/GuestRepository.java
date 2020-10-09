package com.vinayak.data.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.vinayak.data.model.Guest;

@Repository
public interface GuestRepository extends PagingAndSortingRepository<Guest, Long> {

}
