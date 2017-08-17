package com.kaoba.expo.web.rest;

import com.kaoba.expo.ExpoCrApp;

import com.kaoba.expo.domain.HistorialUsuariosExpo;
import com.kaoba.expo.repository.HistorialUsuariosExpoRepository;
import com.kaoba.expo.service.HistorialUsuariosExpoService;
import com.kaoba.expo.service.dto.HistorialUsuariosExpoDTO;
import com.kaoba.expo.service.mapper.HistorialUsuariosExpoMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the HistorialUsuariosExpoResource REST controller.
 *
 * @see HistorialUsuariosExpoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExpoCrApp.class)
public class HistorialUsuariosExpoResourceIntTest {

    private static final Integer DEFAULT_ID_EXPO = 1;
    private static final Integer UPDATED_ID_EXPO = 2;

    private static final String DEFAULT_DEVICE_ID = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_STAND_ID = 1;
    private static final Integer UPDATED_STAND_ID = 2;

    private static final Integer DEFAULT_SUBCATEGORY_ID = 1;
    private static final Integer UPDATED_SUBCATEGORY_ID = 2;

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private HistorialUsuariosExpoRepository historialUsuariosExpoRepository;

    @Autowired
    private HistorialUsuariosExpoMapper historialUsuariosExpoMapper;

    @Autowired
    private HistorialUsuariosExpoService historialUsuariosExpoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHistorialUsuariosExpoMockMvc;

    private HistorialUsuariosExpo historialUsuariosExpo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HistorialUsuariosExpoResource historialUsuariosExpoResource = new HistorialUsuariosExpoResource(historialUsuariosExpoService);
        this.restHistorialUsuariosExpoMockMvc = MockMvcBuilders.standaloneSetup(historialUsuariosExpoResource)
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
    public static HistorialUsuariosExpo createEntity(EntityManager em) {
        HistorialUsuariosExpo historialUsuariosExpo = new HistorialUsuariosExpo()
            .idExpo(DEFAULT_ID_EXPO)
            .deviceId(DEFAULT_DEVICE_ID)
            .standId(DEFAULT_STAND_ID)
            .subcategoryId(DEFAULT_SUBCATEGORY_ID)
            .fecha(DEFAULT_FECHA);
        return historialUsuariosExpo;
    }

    @Before
    public void initTest() {
        historialUsuariosExpo = createEntity(em);
    }

    @Test
    @Transactional
    public void createHistorialUsuariosExpo() throws Exception {
        int databaseSizeBeforeCreate = historialUsuariosExpoRepository.findAll().size();

        // Create the HistorialUsuariosExpo
        HistorialUsuariosExpoDTO historialUsuariosExpoDTO = historialUsuariosExpoMapper.toDto(historialUsuariosExpo);
        restHistorialUsuariosExpoMockMvc.perform(post("/api/historial-usuarios-expos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historialUsuariosExpoDTO)))
            .andExpect(status().isCreated());

        // Validate the HistorialUsuariosExpo in the database
        List<HistorialUsuariosExpo> historialUsuariosExpoList = historialUsuariosExpoRepository.findAll();
        assertThat(historialUsuariosExpoList).hasSize(databaseSizeBeforeCreate + 1);
        HistorialUsuariosExpo testHistorialUsuariosExpo = historialUsuariosExpoList.get(historialUsuariosExpoList.size() - 1);
        assertThat(testHistorialUsuariosExpo.getIdExpo()).isEqualTo(DEFAULT_ID_EXPO);
        assertThat(testHistorialUsuariosExpo.getDeviceId()).isEqualTo(DEFAULT_DEVICE_ID);
        assertThat(testHistorialUsuariosExpo.getStandId()).isEqualTo(DEFAULT_STAND_ID);
        assertThat(testHistorialUsuariosExpo.getSubcategoryId()).isEqualTo(DEFAULT_SUBCATEGORY_ID);
        assertThat(testHistorialUsuariosExpo.getFecha()).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    @Transactional
    public void createHistorialUsuariosExpoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = historialUsuariosExpoRepository.findAll().size();

        // Create the HistorialUsuariosExpo with an existing ID
        historialUsuariosExpo.setId(1L);
        HistorialUsuariosExpoDTO historialUsuariosExpoDTO = historialUsuariosExpoMapper.toDto(historialUsuariosExpo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHistorialUsuariosExpoMockMvc.perform(post("/api/historial-usuarios-expos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historialUsuariosExpoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<HistorialUsuariosExpo> historialUsuariosExpoList = historialUsuariosExpoRepository.findAll();
        assertThat(historialUsuariosExpoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHistorialUsuariosExpos() throws Exception {
        // Initialize the database
        historialUsuariosExpoRepository.saveAndFlush(historialUsuariosExpo);

        // Get all the historialUsuariosExpoList
        restHistorialUsuariosExpoMockMvc.perform(get("/api/historial-usuarios-expos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(historialUsuariosExpo.getId().intValue())))
            .andExpect(jsonPath("$.[*].idExpo").value(hasItem(DEFAULT_ID_EXPO)))
            .andExpect(jsonPath("$.[*].deviceId").value(hasItem(DEFAULT_DEVICE_ID.toString())))
            .andExpect(jsonPath("$.[*].standId").value(hasItem(DEFAULT_STAND_ID)))
            .andExpect(jsonPath("$.[*].subcategoryId").value(hasItem(DEFAULT_SUBCATEGORY_ID)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }

    @Test
    @Transactional
    public void getHistorialUsuariosExpo() throws Exception {
        // Initialize the database
        historialUsuariosExpoRepository.saveAndFlush(historialUsuariosExpo);

        // Get the historialUsuariosExpo
        restHistorialUsuariosExpoMockMvc.perform(get("/api/historial-usuarios-expos/{id}", historialUsuariosExpo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(historialUsuariosExpo.getId().intValue()))
            .andExpect(jsonPath("$.idExpo").value(DEFAULT_ID_EXPO))
            .andExpect(jsonPath("$.deviceId").value(DEFAULT_DEVICE_ID.toString()))
            .andExpect(jsonPath("$.standId").value(DEFAULT_STAND_ID))
            .andExpect(jsonPath("$.subcategoryId").value(DEFAULT_SUBCATEGORY_ID))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHistorialUsuariosExpo() throws Exception {
        // Get the historialUsuariosExpo
        restHistorialUsuariosExpoMockMvc.perform(get("/api/historial-usuarios-expos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHistorialUsuariosExpo() throws Exception {
        // Initialize the database
        historialUsuariosExpoRepository.saveAndFlush(historialUsuariosExpo);
        int databaseSizeBeforeUpdate = historialUsuariosExpoRepository.findAll().size();

        // Update the historialUsuariosExpo
        HistorialUsuariosExpo updatedHistorialUsuariosExpo = historialUsuariosExpoRepository.findOne(historialUsuariosExpo.getId());
        updatedHistorialUsuariosExpo
            .idExpo(UPDATED_ID_EXPO)
            .deviceId(UPDATED_DEVICE_ID)
            .standId(UPDATED_STAND_ID)
            .subcategoryId(UPDATED_SUBCATEGORY_ID)
            .fecha(UPDATED_FECHA);
        HistorialUsuariosExpoDTO historialUsuariosExpoDTO = historialUsuariosExpoMapper.toDto(updatedHistorialUsuariosExpo);

        restHistorialUsuariosExpoMockMvc.perform(put("/api/historial-usuarios-expos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historialUsuariosExpoDTO)))
            .andExpect(status().isOk());

        // Validate the HistorialUsuariosExpo in the database
        List<HistorialUsuariosExpo> historialUsuariosExpoList = historialUsuariosExpoRepository.findAll();
        assertThat(historialUsuariosExpoList).hasSize(databaseSizeBeforeUpdate);
        HistorialUsuariosExpo testHistorialUsuariosExpo = historialUsuariosExpoList.get(historialUsuariosExpoList.size() - 1);
        assertThat(testHistorialUsuariosExpo.getIdExpo()).isEqualTo(UPDATED_ID_EXPO);
        assertThat(testHistorialUsuariosExpo.getDeviceId()).isEqualTo(UPDATED_DEVICE_ID);
        assertThat(testHistorialUsuariosExpo.getStandId()).isEqualTo(UPDATED_STAND_ID);
        assertThat(testHistorialUsuariosExpo.getSubcategoryId()).isEqualTo(UPDATED_SUBCATEGORY_ID);
        assertThat(testHistorialUsuariosExpo.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void updateNonExistingHistorialUsuariosExpo() throws Exception {
        int databaseSizeBeforeUpdate = historialUsuariosExpoRepository.findAll().size();

        // Create the HistorialUsuariosExpo
        HistorialUsuariosExpoDTO historialUsuariosExpoDTO = historialUsuariosExpoMapper.toDto(historialUsuariosExpo);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHistorialUsuariosExpoMockMvc.perform(put("/api/historial-usuarios-expos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historialUsuariosExpoDTO)))
            .andExpect(status().isCreated());

        // Validate the HistorialUsuariosExpo in the database
        List<HistorialUsuariosExpo> historialUsuariosExpoList = historialUsuariosExpoRepository.findAll();
        assertThat(historialUsuariosExpoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHistorialUsuariosExpo() throws Exception {
        // Initialize the database
        historialUsuariosExpoRepository.saveAndFlush(historialUsuariosExpo);
        int databaseSizeBeforeDelete = historialUsuariosExpoRepository.findAll().size();

        // Get the historialUsuariosExpo
        restHistorialUsuariosExpoMockMvc.perform(delete("/api/historial-usuarios-expos/{id}", historialUsuariosExpo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<HistorialUsuariosExpo> historialUsuariosExpoList = historialUsuariosExpoRepository.findAll();
        assertThat(historialUsuariosExpoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HistorialUsuariosExpo.class);
        HistorialUsuariosExpo historialUsuariosExpo1 = new HistorialUsuariosExpo();
        historialUsuariosExpo1.setId(1L);
        HistorialUsuariosExpo historialUsuariosExpo2 = new HistorialUsuariosExpo();
        historialUsuariosExpo2.setId(historialUsuariosExpo1.getId());
        assertThat(historialUsuariosExpo1).isEqualTo(historialUsuariosExpo2);
        historialUsuariosExpo2.setId(2L);
        assertThat(historialUsuariosExpo1).isNotEqualTo(historialUsuariosExpo2);
        historialUsuariosExpo1.setId(null);
        assertThat(historialUsuariosExpo1).isNotEqualTo(historialUsuariosExpo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HistorialUsuariosExpoDTO.class);
        HistorialUsuariosExpoDTO historialUsuariosExpoDTO1 = new HistorialUsuariosExpoDTO();
        historialUsuariosExpoDTO1.setId(1L);
        HistorialUsuariosExpoDTO historialUsuariosExpoDTO2 = new HistorialUsuariosExpoDTO();
        assertThat(historialUsuariosExpoDTO1).isNotEqualTo(historialUsuariosExpoDTO2);
        historialUsuariosExpoDTO2.setId(historialUsuariosExpoDTO1.getId());
        assertThat(historialUsuariosExpoDTO1).isEqualTo(historialUsuariosExpoDTO2);
        historialUsuariosExpoDTO2.setId(2L);
        assertThat(historialUsuariosExpoDTO1).isNotEqualTo(historialUsuariosExpoDTO2);
        historialUsuariosExpoDTO1.setId(null);
        assertThat(historialUsuariosExpoDTO1).isNotEqualTo(historialUsuariosExpoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(historialUsuariosExpoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(historialUsuariosExpoMapper.fromId(null)).isNull();
    }
}
