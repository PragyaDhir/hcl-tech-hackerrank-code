package com.hackerrank.api.repository;

import com.hackerrank.api.model.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT SUM(e.cost) FROM Event e")
    Integer sumCost();

    @Query("SELECT SUM(e.duration) FROM Event e")
    Integer sumDuration();

    @Query("SELECT e FROM Event e ORDER BY e.cost DESC")
    List<Event> findTop3ByOrderByCostDesc(Pageable pageable);

    @Query("SELECT e FROM Event e ORDER BY e.duration DESC")
    List<Event> findTop3ByOrderByDurationDesc(Pageable pageable);
}
