package com.kaoba.expo.web.rest;

import com.kaoba.expo.ExpoCrApp;

import com.kaoba.expo.domain.Distrito;
import com.kaoba.expo.repository.DistritoRepository;
import com.kaoba.expo.service.DistritoService;
import com.kaoba.expo.service.dto.DistritoDTO;
import com.kaoba.expo.service.mapper.DistritoMapper;
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
 * Test class for the DistritoResource REST controller.
 *
 * @see DistritoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExpoCrApp.class)
public class DistritoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private DistritoRepository distritoRepository;

    @Autowired
    private DistritoMapper distritoMapper;

    @Autowired
    private DistritoService distritoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDistritoMockMvc;

    private Distrito distrito;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DistritoResource distritoResource = new DistritoResource(distritoService);
        this.restDistritoMockMvc = MockMvcBuilders.standaloneSetup(distritoResource)
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
    public static Distrito createEntity(EntityManager em) {
        Distrito distrito = new Distrito()
            .nombre(DEFAULT_NOMBRE);
        return distrito;
    }

    @Before
    public void initTest() {
        distrito = createEntity(em);
    }

    @Test
    @Transactional
    public void createDistrito() throws Exception {
        int databaseSizeBeforeCreate = distritoRepository.findAll().size();

        // Create the Distrito
        DistritoDTO distritoDTO = distritoMapper.toDto(distrito);
        restDistritoMockMvc.perform(post("/api/distritos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(distritoDTO)))
            .andExpect(status().isCreated());

        // Validate the Distrito in the database
        List<Distrito> distritoList = distritoRepository.findAll();
        assertThat(distritoList).hasSize(databaseSizeBeforeCreate + 1);
        Distrito testDistrito = distritoList.get(distritoList.size() - 1);
        assertThat(testDistrito.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createDistritoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = distritoRepository.findAll().size();

        // Create the Distrito with an existing ID
        distrito.setId(1L);
        DistritoDTO distritoDTO = distritoMapper.toDto(distrito);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDistritoMockMvc.perform(post("/api/distritos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(distritoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Distrito> distritoList = distritoRepository.findAll();
        assertThat(distritoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDistritos() throws Exception {
        // Initialize the database
        distritoRepository.saveAndFlush(distrito);

        // Get all the distritoList
        restDistritoMockMvc.perform(get("/api/distritos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(distrito.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }

    @Test
    @Transactional
    public void getDistrito() throws Exception {
        // Initialize the database
        distritoRepository.saveAndFlush(distrito);

        // Get the distrito
        restDistritoMockMvc.perform(get("/api/distritos/{id}", distrito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(distrito.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDistrito() throws Exception {
        // Get the distrito
        restDistritoMockMvc.perform(get("/api/distritos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDistrito() throws Exception {
        // Initialize the database
        distritoRepository.saveAndFlush(distrito);
        int databaseSizeBeforeUpdate = distritoRepository.findAll().size();

        // Update the distrito
        Distrito updatedDistrito = distritoRepository.findOne(distrito.getId());
        updatedDistrito
            .nombre(UPDATED_NOMBRE);
        DistritoDTO distritoDTO = distritoMapper.toDto(updatedDistrito);

        restDistritoMockMvc.perform(put("/api/distritos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(distritoDTO)))
            .andExpect(status().isOk());

        // Validate the Distrito in the database
        List<Distrito> distritoList = distritoRepository.findAll();
        assertThat(distritoList).hasSize(databaseSizeBeforeUpdate);
        Distrito testDistrito = distritoList.get(distritoList.size() - 1);
        assertThat(testDistrito.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingDistrito() throws Exception {
        int databaseSizeBeforeUpdate = distritoRepository.findAll().size();

        // Create the Distrito
        DistritoDTO distritoDTO = distritoMapper.toDto(distrito);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDistritoMockMvc.perform(put("/api/distritos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(distritoDTO)))
            .andExpect(status().isCreated());

        // Validate the Distrito in the database
        List<Distrito> distritoList = distritoRepository.findAll();
        assertThat(distritoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDistrito() throws Exception {
        // Initialize the database
        distritoRepository.saveAndFlush(distrito);
        int databaseSizeBeforeDelete = distritoRepository.findAll().size();

        // Get the distrito
        restDistritoMockMvc.perform(delete("/api/distritos/{id}", distrito.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Distrito> distritoList = distritoRepository.findAll();
        assertThat(distritoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Distrito.class);
        Distrito distrito1 = new Distrito();
        distrito1.setId(1L);
        Distrito distrito2 = new Distrito();
        distrito2.setId(distrito1.getId());
        assertThat(distrito1).isEqualTo(distrito2);
        distrito2.setId(2L);
        assertThat(distrito1).isNotEqualTo(distrito2);
        distrito1.setId(null);
        assertThat(distrito1).isNotEqualTo(distrito2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DistritoDTO.class);
        DistritoDTO distritoDTO1 = new DistritoDTO();
        distritoDTO1.setId(1L);
        DistritoDTO distritoDTO2 = new DistritoDTO();
        assertThat(distritoDTO1).isNotEqualTo(distritoDTO2);
        distritoDTO2.setId(distritoDTO1.getId());
        assertThat(distritoDTO1).isEqualTo(distritoDTO2);
        distritoDTO2.setId(2L);
        assertThat(distritoDTO1).isNotEqualTo(distritoDTO2);
        distritoDTO1.setId(null);
        assertThat(distritoDTO1).isNotEqualTo(distritoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(distritoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(distritoMapper.fromId(null)).isNull();
    }
}
