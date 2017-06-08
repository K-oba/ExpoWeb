package com.kaoba.expo.web.rest;

import com.kaoba.expo.ExpoCrApp;

import com.kaoba.expo.domain.Charla;
import com.kaoba.expo.repository.CharlaRepository;
import com.kaoba.expo.service.CharlaService;
import com.kaoba.expo.service.dto.CharlaDTO;
import com.kaoba.expo.service.mapper.CharlaMapper;
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
 * Test class for the CharlaResource REST controller.
 *
 * @see CharlaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExpoCrApp.class)
public class CharlaResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_FECHA_INICIO = "AAAAAAAAAA";
    private static final String UPDATED_FECHA_INICIO = "BBBBBBBBBB";

    private static final String DEFAULT_FECHA_FIN = "AAAAAAAAAA";
    private static final String UPDATED_FECHA_FIN = "BBBBBBBBBB";

    @Autowired
    private CharlaRepository charlaRepository;

    @Autowired
    private CharlaMapper charlaMapper;

    @Autowired
    private CharlaService charlaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCharlaMockMvc;

    private Charla charla;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CharlaResource charlaResource = new CharlaResource(charlaService);
        this.restCharlaMockMvc = MockMvcBuilders.standaloneSetup(charlaResource)
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
    public static Charla createEntity(EntityManager em) {
        Charla charla = new Charla()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .fechaInicio(DEFAULT_FECHA_INICIO)
            .fechaFin(DEFAULT_FECHA_FIN);
        return charla;
    }

    @Before
    public void initTest() {
        charla = createEntity(em);
    }

    @Test
    @Transactional
    public void createCharla() throws Exception {
        int databaseSizeBeforeCreate = charlaRepository.findAll().size();

        // Create the Charla
        CharlaDTO charlaDTO = charlaMapper.toDto(charla);
        restCharlaMockMvc.perform(post("/api/charlas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(charlaDTO)))
            .andExpect(status().isCreated());

        // Validate the Charla in the database
        List<Charla> charlaList = charlaRepository.findAll();
        assertThat(charlaList).hasSize(databaseSizeBeforeCreate + 1);
        Charla testCharla = charlaList.get(charlaList.size() - 1);
        assertThat(testCharla.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testCharla.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testCharla.getFechaInicio()).isEqualTo(DEFAULT_FECHA_INICIO);
        assertThat(testCharla.getFechaFin()).isEqualTo(DEFAULT_FECHA_FIN);
    }

    @Test
    @Transactional
    public void createCharlaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = charlaRepository.findAll().size();

        // Create the Charla with an existing ID
        charla.setId(1L);
        CharlaDTO charlaDTO = charlaMapper.toDto(charla);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCharlaMockMvc.perform(post("/api/charlas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(charlaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Charla> charlaList = charlaRepository.findAll();
        assertThat(charlaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCharlas() throws Exception {
        // Initialize the database
        charlaRepository.saveAndFlush(charla);

        // Get all the charlaList
        restCharlaMockMvc.perform(get("/api/charlas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(charla.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].fechaInicio").value(hasItem(DEFAULT_FECHA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fechaFin").value(hasItem(DEFAULT_FECHA_FIN.toString())));
    }

    @Test
    @Transactional
    public void getCharla() throws Exception {
        // Initialize the database
        charlaRepository.saveAndFlush(charla);

        // Get the charla
        restCharlaMockMvc.perform(get("/api/charlas/{id}", charla.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(charla.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.fechaInicio").value(DEFAULT_FECHA_INICIO.toString()))
            .andExpect(jsonPath("$.fechaFin").value(DEFAULT_FECHA_FIN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCharla() throws Exception {
        // Get the charla
        restCharlaMockMvc.perform(get("/api/charlas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCharla() throws Exception {
        // Initialize the database
        charlaRepository.saveAndFlush(charla);
        int databaseSizeBeforeUpdate = charlaRepository.findAll().size();

        // Update the charla
        Charla updatedCharla = charlaRepository.findOne(charla.getId());
        updatedCharla
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN);
        CharlaDTO charlaDTO = charlaMapper.toDto(updatedCharla);

        restCharlaMockMvc.perform(put("/api/charlas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(charlaDTO)))
            .andExpect(status().isOk());

        // Validate the Charla in the database
        List<Charla> charlaList = charlaRepository.findAll();
        assertThat(charlaList).hasSize(databaseSizeBeforeUpdate);
        Charla testCharla = charlaList.get(charlaList.size() - 1);
        assertThat(testCharla.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testCharla.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testCharla.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testCharla.getFechaFin()).isEqualTo(UPDATED_FECHA_FIN);
    }

    @Test
    @Transactional
    public void updateNonExistingCharla() throws Exception {
        int databaseSizeBeforeUpdate = charlaRepository.findAll().size();

        // Create the Charla
        CharlaDTO charlaDTO = charlaMapper.toDto(charla);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCharlaMockMvc.perform(put("/api/charlas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(charlaDTO)))
            .andExpect(status().isCreated());

        // Validate the Charla in the database
        List<Charla> charlaList = charlaRepository.findAll();
        assertThat(charlaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCharla() throws Exception {
        // Initialize the database
        charlaRepository.saveAndFlush(charla);
        int databaseSizeBeforeDelete = charlaRepository.findAll().size();

        // Get the charla
        restCharlaMockMvc.perform(delete("/api/charlas/{id}", charla.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Charla> charlaList = charlaRepository.findAll();
        assertThat(charlaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Charla.class);
        Charla charla1 = new Charla();
        charla1.setId(1L);
        Charla charla2 = new Charla();
        charla2.setId(charla1.getId());
        assertThat(charla1).isEqualTo(charla2);
        charla2.setId(2L);
        assertThat(charla1).isNotEqualTo(charla2);
        charla1.setId(null);
        assertThat(charla1).isNotEqualTo(charla2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CharlaDTO.class);
        CharlaDTO charlaDTO1 = new CharlaDTO();
        charlaDTO1.setId(1L);
        CharlaDTO charlaDTO2 = new CharlaDTO();
        assertThat(charlaDTO1).isNotEqualTo(charlaDTO2);
        charlaDTO2.setId(charlaDTO1.getId());
        assertThat(charlaDTO1).isEqualTo(charlaDTO2);
        charlaDTO2.setId(2L);
        assertThat(charlaDTO1).isNotEqualTo(charlaDTO2);
        charlaDTO1.setId(null);
        assertThat(charlaDTO1).isNotEqualTo(charlaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(charlaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(charlaMapper.fromId(null)).isNull();
    }
}
