package com.hackerrank.api.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackerrank.api.model.Event;
import com.hackerrank.api.repository.EventRepository;
import static org.hamcrest.Matchers.greaterThan;

import com.hackerrank.api.service.impl.DefaultEventService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:data.sql")
class EventControllerTest {
  ObjectMapper om = new ObjectMapper();
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private EventRepository repository;
  @Autowired
  private DefaultEventService defaultEventService;

  @Test
  public void testCreation() throws Exception {
    Event expectedRecord = Event.builder().name("Test Country").build();
    Event actualRecord = om.readValue(mockMvc.perform(post("/event")
            .contentType("application/json")
            .content(om.writeValueAsString(expectedRecord)))
            .andDo(print())
            .andExpect(jsonPath("$.id", greaterThan(0)))
            .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString(), Event.class);

    Assertions.assertEquals(expectedRecord.getName(), actualRecord.getName());
  }

  @Test
  public void testFindById() throws Exception{
    Event expectedRecord = Event.builder().name("Test Country").build();
    Event addRecord = addEvent(expectedRecord);

    String responseString = mockMvc.perform(get("/event/byId/"+addRecord.getId()))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();
    Event actualRecord = om.readValue(responseString, Event.class);


    Assertions.assertEquals(expectedRecord.getName(), actualRecord.getName());
  }

  @Test
  public void testInvalidIdThrowsException() throws Exception{
    mockMvc.perform(get("/event/byId/234"))
            .andDo(print())
            .andExpect(status().is4xxClientError())
            .andReturn().getResponse().getContentAsString();
  }
  @Test
  public void testTop3() throws Exception{
    Event expectedRecord = Event.builder().name("Test Country 1").cost(45).build();
    Event addRecord = addEvent(expectedRecord);
    Event expectedRecord2 = Event.builder().name("Test Country 2").cost(10).build();
    Event addRecord2 = addEvent(expectedRecord);
    Event expectedRecord3 = Event.builder().name("Test Country 3").cost(12).build();
    Event addRecord3 = addEvent(expectedRecord);
    Event expectedRecord4 = Event.builder().name("Test Country 4").cost(43).build();
    Event addRecord4 = addEvent(expectedRecord);
    List<Event> expectedres = new ArrayList<>();
    expectedres.add(addRecord);
    expectedres.add(addRecord4);
    expectedres.add(addRecord3);

    String responseString = mockMvc.perform(get("/event/top3?by=cost"))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();
    List<Event> actualRecord = om.readValue(responseString, new TypeReference<List<Event>>() {});
    Assertions.assertEquals(actualRecord.size(),3);
    for(int i =0;i<3;i++)
    {
      Assertions.assertEquals(actualRecord.get(0).getName(),expectedres.get(i).getName());

    }




  }

  private Event addEvent(Event expectedRecord) throws Exception {
    Event addRecord = om.readValue(mockMvc.perform(post("/event")
                    .contentType("application/json")
                    .content(om.writeValueAsString(expectedRecord)))
            .andDo(print())
            .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString(), Event.class);
    return addRecord;
  }

}
