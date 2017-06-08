package com.kaoba.expo.web.rest;

import com.kaoba.expo.ExpoCrApp;

import com.kaoba.expo.domain.Stand;
import com.kaoba.expo.repository.StandRepository;
import com.kaoba.expo.service.StandService;
import com.kaoba.expo.service.dto.StandDTO;
import com.kaoba.expo.service.mapper.StandMapper;
import com.kaoba.expo.web.rest.errors.ExceptionTranslator;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the StandResource REST controller.
 *
 * @see StandResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExpoCrApp.class)
public class StandResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    @Autowired
    private StandRepository standRepository;

    @Autowired
    private StandMapper standMapper;

    @Autowired
    private StandService standService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStandMockMvc;

    private Stand stand;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StandResource standResource = new StandResource(standService);
        this.restStandMockMvc = MockMvcBuilders.standaloneSetup(standResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stand createEntity(EntityManager em) {
        Stand stand = new Stand()
            .nombre(DEFAULT_NOMBRE)
            .tipo(DEFAULT_TIPO);
        return stand;
    }

    @Before
    public void initTest() {
        stand = createEntity(em);
    }

    @Test
    @Transactional
    public void createStand() throws Exception {
        int databaseSizeBeforeCreate = standRepository.findAll().size();

        // Create the Stand
        StandDTO standDTO = standMapper.toDto(stand);
        restStandMockMvc.perform(post("/api/stands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(standDTO)))
            .andExpect(status().isCreated());

        // Validate the Stand in the database
        List<Stand> standList = standRepository.findAll();
        assertThat(standList).hasSize(databaseSizeBeforeCreate + 1);
        Stand testStand = standList.get(standList.size() - 1);
        assertThat(testStand.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testStand.getTipo()).isEqualTo(DEFAULT_TIPO);
    }

    @Test
    @Transactional
    public void createStandWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = standRepository.findAll().size();

        // Create the Stand with an existing ID
        stand.setId(1L);
        StandDTO standDTO = standMapper.toDto(stand);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStandMockMvc.perform(post("/api/stands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(standDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Stand> standList = standRepository.findAll();
        assertThat(standList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllStands() throws Exception {
        // Initialize the database
        standRepository.saveAndFlush(stand);

        // Get all the standList
        restStandMockMvc.perform(get("/api/stands?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stand.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())));
    }

    @Test
    @Transactional
    public void getStand() throws Exception {
        // Initialize the database
        standRepository.saveAndFlush(stand);

        // Get the stand
        restStandMockMvc.perform(get("/api/stands/{id}", stand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stand.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStand() throws Exception {
        // Get the stand
        restStandMockMvc.perform(get("/api/stands/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStand() throws Exception {
        // Initialize the database
        standRepository.saveAndFlush(stand);
        int databaseSizeBeforeUpdate = standRepository.findAll().size();

        // Update the stand
        Stand updatedStand = standRepository.findOne(stand.getId());
        updatedStand
            .nombre(UPDATED_NOMBRE)
            .tipo(UPDATED_TIPO);
        StandDTO standDTO = standMapper.toDto(updatedStand);

        restStandMockMvc.perform(put("/api/stands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(standDTO)))
            .andExpect(status().isOk());

        // Validate the Stand in the database
        List<Stand> standList = standRepository.findAll();
        assertThat(standList).hasSize(databaseSizeBeforeUpdate);
        Stand testStand = standList.get(standList.size() - 1);
        assertThat(testStand.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testStand.getTipo()).isEqualTo(UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void updateNonExistingStand() throws Exception {
        int databaseSizeBeforeUpdate = standRepository.findAll().size();

        // Create the Stand
        StandDTO standDTO = standMapper.toDto(stand);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStandMockMvc.perform(put("/api/stands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(standDTO)))
            .andExpect(status().isCreated());

        // Validate the Stand in the database
        List<Stand> standList = standRepository.findAll();
        assertThat(standList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteStand() throws Exception {
        // Initialize the database
        standRepository.saveAndFlush(stand);
        int databaseSizeBeforeDelete = standRepository.findAll().size();

        // Get the stand
        restStandMockMvc.perform(delete("/api/stands/{id}", stand.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Stand> standList = standRepository.findAll();
        assertThat(standList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Stand.class);
        Stand stand1 = new Stand();
        stand1.setId(1L);
        Stand stand2 = new Stand();
        stand2.setId(stand1.getId());
        assertThat(stand1).isEqualTo(stand2);
        stand2.setId(2L);
        assertThat(stand1).isNotEqualTo(stand2);
        stand1.setId(null);
        assertThat(stand1).isNotEqualTo(stand2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StandDTO.class);
        StandDTO standDTO1 = new StandDTO();
        standDTO1.setId(1L);
        StandDTO standDTO2 = new StandDTO();
        assertThat(standDTO1).isNotEqualTo(standDTO2);
        standDTO2.setId(standDTO1.getId());
        assertThat(standDTO1).isEqualTo(standDTO2);
        standDTO2.setId(2L);
        assertThat(standDTO1).isNotEqualTo(standDTO2);
        standDTO1.setId(null);
        assertThat(standDTO1).isNotEqualTo(standDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(standMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(standMapper.fromId(null)).isNull();
    }
}
