package com.caramelpoint.aeh.web.rest;

import com.caramelpoint.aeh.AehApp;

import com.caramelpoint.aeh.domain.Event;
import com.caramelpoint.aeh.repository.EventRepository;
import com.caramelpoint.aeh.service.EventService;
import com.caramelpoint.aeh.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.caramelpoint.aeh.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.caramelpoint.aeh.domain.enumeration.Criticallity;
import com.caramelpoint.aeh.domain.enumeration.State;
/**
 * Test class for the EventResource REST controller.
 *
 * @see EventResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AehApp.class)
public class EventResourceIntTest {

    private static final String DEFAULT_OBSERVATIONS = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVATIONS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE_PROGRAMED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE_PROGRAMED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FINISH_DATE_PROGRAMED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FINISH_DATE_PROGRAMED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_START_DATE_VALIDITY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE_VALIDITY = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FINISH_DATE_VALIDITY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FINISH_DATE_VALIDITY = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_START_DATE_COMPLETED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE_COMPLETED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FINISH_DATE_COMPLETED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FINISH_DATE_COMPLETED = LocalDate.now(ZoneId.systemDefault());

    private static final Criticallity DEFAULT_CRITICALLITY = Criticallity.CRITICAL;
    private static final Criticallity UPDATED_CRITICALLITY = Criticallity.HIGH;

    private static final State DEFAULT_SATTE = State.PROGRAMED;
    private static final State UPDATED_SATTE = State.FINISHED;

    @Autowired
    private EventRepository eventRepository;

    

    @Autowired
    private EventService eventService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEventMockMvc;

    private Event event;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EventResource eventResource = new EventResource(eventService);
        this.restEventMockMvc = MockMvcBuilders.standaloneSetup(eventResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Event createEntity(EntityManager em) {
        Event event = new Event()
            .observations(DEFAULT_OBSERVATIONS)
            .startDateProgramed(DEFAULT_START_DATE_PROGRAMED)
            .finishDateProgramed(DEFAULT_FINISH_DATE_PROGRAMED)
            .startDateValidity(DEFAULT_START_DATE_VALIDITY)
            .finishDateValidity(DEFAULT_FINISH_DATE_VALIDITY)
            .startDateCompleted(DEFAULT_START_DATE_COMPLETED)
            .finishDateCompleted(DEFAULT_FINISH_DATE_COMPLETED)
            .criticallity(DEFAULT_CRITICALLITY)
            .satte(DEFAULT_SATTE);
        return event;
    }

    @Before
    public void initTest() {
        event = createEntity(em);
    }

    @Test
    @Transactional
    public void createEvent() throws Exception {
        int databaseSizeBeforeCreate = eventRepository.findAll().size();

        // Create the Event
        restEventMockMvc.perform(post("/api/events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(event)))
            .andExpect(status().isCreated());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeCreate + 1);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getObservations()).isEqualTo(DEFAULT_OBSERVATIONS);
        assertThat(testEvent.getStartDateProgramed()).isEqualTo(DEFAULT_START_DATE_PROGRAMED);
        assertThat(testEvent.getFinishDateProgramed()).isEqualTo(DEFAULT_FINISH_DATE_PROGRAMED);
        assertThat(testEvent.getStartDateValidity()).isEqualTo(DEFAULT_START_DATE_VALIDITY);
        assertThat(testEvent.getFinishDateValidity()).isEqualTo(DEFAULT_FINISH_DATE_VALIDITY);
        assertThat(testEvent.getStartDateCompleted()).isEqualTo(DEFAULT_START_DATE_COMPLETED);
        assertThat(testEvent.getFinishDateCompleted()).isEqualTo(DEFAULT_FINISH_DATE_COMPLETED);
        assertThat(testEvent.getCriticallity()).isEqualTo(DEFAULT_CRITICALLITY);
        assertThat(testEvent.getSatte()).isEqualTo(DEFAULT_SATTE);
    }

    @Test
    @Transactional
    public void createEventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eventRepository.findAll().size();

        // Create the Event with an existing ID
        event.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventMockMvc.perform(post("/api/events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(event)))
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEvents() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList
        restEventMockMvc.perform(get("/api/events?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(event.getId().intValue())))
            .andExpect(jsonPath("$.[*].observations").value(hasItem(DEFAULT_OBSERVATIONS.toString())))
            .andExpect(jsonPath("$.[*].startDateProgramed").value(hasItem(DEFAULT_START_DATE_PROGRAMED.toString())))
            .andExpect(jsonPath("$.[*].finishDateProgramed").value(hasItem(DEFAULT_FINISH_DATE_PROGRAMED.toString())))
            .andExpect(jsonPath("$.[*].startDateValidity").value(hasItem(DEFAULT_START_DATE_VALIDITY.toString())))
            .andExpect(jsonPath("$.[*].finishDateValidity").value(hasItem(DEFAULT_FINISH_DATE_VALIDITY.toString())))
            .andExpect(jsonPath("$.[*].startDateCompleted").value(hasItem(DEFAULT_START_DATE_COMPLETED.toString())))
            .andExpect(jsonPath("$.[*].finishDateCompleted").value(hasItem(DEFAULT_FINISH_DATE_COMPLETED.toString())))
            .andExpect(jsonPath("$.[*].criticallity").value(hasItem(DEFAULT_CRITICALLITY.toString())))
            .andExpect(jsonPath("$.[*].satte").value(hasItem(DEFAULT_SATTE.toString())));
    }
    

    @Test
    @Transactional
    public void getEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get the event
        restEventMockMvc.perform(get("/api/events/{id}", event.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(event.getId().intValue()))
            .andExpect(jsonPath("$.observations").value(DEFAULT_OBSERVATIONS.toString()))
            .andExpect(jsonPath("$.startDateProgramed").value(DEFAULT_START_DATE_PROGRAMED.toString()))
            .andExpect(jsonPath("$.finishDateProgramed").value(DEFAULT_FINISH_DATE_PROGRAMED.toString()))
            .andExpect(jsonPath("$.startDateValidity").value(DEFAULT_START_DATE_VALIDITY.toString()))
            .andExpect(jsonPath("$.finishDateValidity").value(DEFAULT_FINISH_DATE_VALIDITY.toString()))
            .andExpect(jsonPath("$.startDateCompleted").value(DEFAULT_START_DATE_COMPLETED.toString()))
            .andExpect(jsonPath("$.finishDateCompleted").value(DEFAULT_FINISH_DATE_COMPLETED.toString()))
            .andExpect(jsonPath("$.criticallity").value(DEFAULT_CRITICALLITY.toString()))
            .andExpect(jsonPath("$.satte").value(DEFAULT_SATTE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingEvent() throws Exception {
        // Get the event
        restEventMockMvc.perform(get("/api/events/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEvent() throws Exception {
        // Initialize the database
        eventService.save(event);

        int databaseSizeBeforeUpdate = eventRepository.findAll().size();

        // Update the event
        Event updatedEvent = eventRepository.findById(event.getId()).get();
        // Disconnect from session so that the updates on updatedEvent are not directly saved in db
        em.detach(updatedEvent);
        updatedEvent
            .observations(UPDATED_OBSERVATIONS)
            .startDateProgramed(UPDATED_START_DATE_PROGRAMED)
            .finishDateProgramed(UPDATED_FINISH_DATE_PROGRAMED)
            .startDateValidity(UPDATED_START_DATE_VALIDITY)
            .finishDateValidity(UPDATED_FINISH_DATE_VALIDITY)
            .startDateCompleted(UPDATED_START_DATE_COMPLETED)
            .finishDateCompleted(UPDATED_FINISH_DATE_COMPLETED)
            .criticallity(UPDATED_CRITICALLITY)
            .satte(UPDATED_SATTE);

        restEventMockMvc.perform(put("/api/events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEvent)))
            .andExpect(status().isOk());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getObservations()).isEqualTo(UPDATED_OBSERVATIONS);
        assertThat(testEvent.getStartDateProgramed()).isEqualTo(UPDATED_START_DATE_PROGRAMED);
        assertThat(testEvent.getFinishDateProgramed()).isEqualTo(UPDATED_FINISH_DATE_PROGRAMED);
        assertThat(testEvent.getStartDateValidity()).isEqualTo(UPDATED_START_DATE_VALIDITY);
        assertThat(testEvent.getFinishDateValidity()).isEqualTo(UPDATED_FINISH_DATE_VALIDITY);
        assertThat(testEvent.getStartDateCompleted()).isEqualTo(UPDATED_START_DATE_COMPLETED);
        assertThat(testEvent.getFinishDateCompleted()).isEqualTo(UPDATED_FINISH_DATE_COMPLETED);
        assertThat(testEvent.getCriticallity()).isEqualTo(UPDATED_CRITICALLITY);
        assertThat(testEvent.getSatte()).isEqualTo(UPDATED_SATTE);
    }

    @Test
    @Transactional
    public void updateNonExistingEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();

        // Create the Event

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEventMockMvc.perform(put("/api/events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(event)))
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEvent() throws Exception {
        // Initialize the database
        eventService.save(event);

        int databaseSizeBeforeDelete = eventRepository.findAll().size();

        // Get the event
        restEventMockMvc.perform(delete("/api/events/{id}", event.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Event.class);
        Event event1 = new Event();
        event1.setId(1L);
        Event event2 = new Event();
        event2.setId(event1.getId());
        assertThat(event1).isEqualTo(event2);
        event2.setId(2L);
        assertThat(event1).isNotEqualTo(event2);
        event1.setId(null);
        assertThat(event1).isNotEqualTo(event2);
    }
}
