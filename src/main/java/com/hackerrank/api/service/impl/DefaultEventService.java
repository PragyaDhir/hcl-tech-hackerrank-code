package com.hackerrank.api.service.impl;

import com.hackerrank.api.exception.BadRequestException;
import com.hackerrank.api.exception.ElementNotFoundException;
import com.hackerrank.api.model.Event;
import com.hackerrank.api.repository.EventRepository;
import com.hackerrank.api.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultEventService implements EventService {
  private final EventRepository eventRepository;

  @Autowired
  DefaultEventService(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  @Override
  public List<Event> getAllEvent() {
    return eventRepository.findAll();
  }


  @Override
  public Event createNewEvent(Event event) {
    if (event.getId() != null) {
      throw new BadRequestException("The ID must not be provided when creating a new Event");
    }

    return eventRepository.save(event);
  }

  @Override
  public Event getEventById(Long id) {
    Optional<Event> eventOptional=
            eventRepository.findById(id);
    if(eventOptional.isPresent())
      return eventOptional.get();
    throw  new ElementNotFoundException("Id doesn't exist");
  }

  @Override
  public List<Event> top3By(String by) {

    switch (by) {
      case "cost": {
        Pageable pageable = PageRequest.of(0, 3,Sort.by(Sort.Order.desc(by)));
        return eventRepository.findAll(pageable).getContent();
      }
      case "duration": {
        Pageable pageable = PageRequest.of(0, 3,Sort.by(Sort.Order.desc(by)));
        return eventRepository.findAll(pageable).getContent();
      }
      default:
        throw new BadRequestException(" by is invalid");
    }
  }

  @Override
  public Integer totalBy(String by) {
    int flag =0;
    if(by.equals("cost"))
      flag =1;
    else if (!by.equals("duration")) {
      throw new BadRequestException("by is invalid");

    }

    List<Event> events = eventRepository.findAll();
    int sum =0;

    for(Event event : events)
    {
      if(flag == 0)
        sum = sum + event.getCost();
      else sum = sum +event.getDuration();
    }
    return sum;
  }
}
