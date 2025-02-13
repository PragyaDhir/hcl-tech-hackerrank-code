package com.hackerrank.api.controller;

import com.hackerrank.api.model.Event;
import com.hackerrank.api.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {
  private final EventService eventService;

  @Autowired
  public EventController(EventService eventService) {
    this.eventService = eventService;
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Event> getAllEvent() {
    return eventService.getAllEvent();
  }

  @GetMapping(value = "/byId/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Event getByIdEvent(@PathVariable Long id) {
    return eventService.getEventById(id);
  }

  @GetMapping(value = "/top3")
  @ResponseStatus(HttpStatus.OK)
  public List<Event> getTop3EventBy(@RequestParam String by) {
    return eventService.top3By(by);
  }

  @GetMapping(value = "/total")
  @ResponseStatus(HttpStatus.OK)
  public Integer getTotalBy(@RequestParam String by) {
    return eventService.totalBy(by);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Event createEvent(@RequestBody Event event) {
    return eventService.createNewEvent(event);
  }
}
