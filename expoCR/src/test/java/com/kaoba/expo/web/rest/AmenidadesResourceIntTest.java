package com.kaoba.expo.web.rest;

import com.kaoba.expo.ExpoCrApp;

import com.kaoba.expo.domain.Amenidades;
import com.kaoba.expo.repository.AmenidadesRepository;
import com.kaoba.expo.service.AmenidadesService;
import com.kaoba.expo.service.dto.AmenidadesDTO;
import com.kaoba.expo.service.mapper.AmenidadesMapper;
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
 * Test class for the AmenidadesResource REST controller.
 *
 * @see AmenidadesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExpoCrApp.class)
public class AmenidadesResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private AmenidadesRepository amenidadesRepository;

    @Autowired
    private AmenidadesMapper amenidadesMapper;

    @Autowired
    private AmenidadesService amenidadesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAmenidadesMockMvc;

    private Amenidades amenidades;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AmenidadesResource amenidadesResource = new AmenidadesResource(amenidadesService);
        this.restAmenidadesMockMvc = MockMvcBuilders.standaloneSetup(amenidadesResource)
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
    public static Amenidades createEntity(EntityManager em) {
        Amenidades amenidades = new Amenidades()
            .nombre(DEFAULT_NOMBRE)
            .tipo(DEFAULT_TIPO)
            .descripcion(DEFAULT_DESCRIPCION);
        return amenidades;
    }

    @Before
    public void initTest() {
        amenidades = createEntity(em);
    }

    @Test
    @Transactional
    public void createAmenidades() throws Exception {
        int databaseSizeBeforeCreate = amenidadesRepository.findAll().size();

        // Create the Amenidades
        AmenidadesDTO amenidadesDTO = amenidadesMapper.toDto(amenidades);
        restAmenidadesMockMvc.perform(post("/api/amenidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amenidadesDTO)))
            .andExpect(status().isCreated());

        // Validate the Amenidades in the database
        List<Amenidades> amenidadesList = amenidadesRepository.findAll();
        assertThat(amenidadesList).hasSize(databaseSizeBeforeCreate + 1);
        Amenidades testAmenidades = amenidadesList.get(amenidadesList.size() - 1);
        assertThat(testAmenidades.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testAmenidades.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testAmenidades.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createAmenidadesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = amenidadesRepository.findAll().size();

        // Create the Amenidades with an existing ID
        amenidades.setId(1L);
        AmenidadesDTO amenidadesDTO = amenidadesMapper.toDto(amenidades);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAmenidadesMockMvc.perform(post("/api/amenidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amenidadesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Amenidades> amenidadesList = amenidadesRepository.findAll();
        assertThat(amenidadesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAmenidades() throws Exception {
        // Initialize the database
        amenidadesRepository.saveAndFlush(amenidades);

        // Get all the amenidadesList
        restAmenidadesMockMvc.perform(get("/api/amenidades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amenidades.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }

    @Test
    @Transactional
    public void getAmenidades() throws Exception {
        // Initialize the database
        amenidadesRepository.saveAndFlush(amenidades);

        // Get the amenidades
        restAmenidadesMockMvc.perform(get("/api/amenidades/{id}", amenidades.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(amenidades.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAmenidades() throws Exception {
        // Get the amenidades
        restAmenidadesMockMvc.perform(get("/api/amenidades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAmenidades() throws Exception {
        // Initialize the database
        amenidadesRepository.saveAndFlush(amenidades);
        int databaseSizeBeforeUpdate = amenidadesRepository.findAll().size();

        // Update the amenidades
        Amenidades updatedAmenidades = amenidadesRepository.findOne(amenidades.getId());
        updatedAmenidades
            .nombre(UPDATED_NOMBRE)
            .tipo(UPDATED_TIPO)
            .descripcion(UPDATED_DESCRIPCION);
        AmenidadesDTO amenidadesDTO = amenidadesMapper.toDto(updatedAmenidades);

        restAmenidadesMockMvc.perform(put("/api/amenidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amenidadesDTO)))
            .andExpect(status().isOk());

        // Validate the Amenidades in the database
        List<Amenidades> amenidadesList = amenidadesRepository.findAll();
        assertThat(amenidadesList).hasSize(databaseSizeBeforeUpdate);
        Amenidades testAmenidades = amenidadesList.get(amenidadesList.size() - 1);
        assertThat(testAmenidades.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testAmenidades.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testAmenidades.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingAmenidades() throws Exception {
        int databaseSizeBeforeUpdate = amenidadesRepository.findAll().size();

        // Create the Amenidades
        AmenidadesDTO amenidadesDTO = amenidadesMapper.toDto(amenidades);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAmenidadesMockMvc.perform(put("/api/amenidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amenidadesDTO)))
            .andExpect(status().isCreated());

        // Validate the Amenidades in the database
        List<Amenidades> amenidadesList = amenidadesRepository.findAll();
        assertThat(amenidadesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAmenidades() throws Exception {
        // Initialize the database
        amenidadesRepository.saveAndFlush(amenidades);
        int databaseSizeBeforeDelete = amenidadesRepository.findAll().size();

        // Get the amenidades
        restAmenidadesMockMvc.perform(delete("/api/amenidades/{id}", amenidades.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Amenidades> amenidadesList = amenidadesRepository.findAll();
        assertThat(amenidadesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Amenidades.class);
        Amenidades amenidades1 = new Amenidades();
        amenidades1.setId(1L);
        Amenidades amenidades2 = new Amenidades();
        amenidades2.setId(amenidades1.getId());
        assertThat(amenidades1).isEqualTo(amenidades2);
        amenidades2.setId(2L);
        assertThat(amenidades1).isNotEqualTo(amenidades2);
        amenidades1.setId(null);
        assertThat(amenidades1).isNotEqualTo(amenidades2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AmenidadesDTO.class);
        AmenidadesDTO amenidadesDTO1 = new AmenidadesDTO();
        amenidadesDTO1.setId(1L);
        AmenidadesDTO amenidadesDTO2 = new AmenidadesDTO();
        assertThat(amenidadesDTO1).isNotEqualTo(amenidadesDTO2);
        amenidadesDTO2.setId(amenidadesDTO1.getId());
        assertThat(amenidadesDTO1).isEqualTo(amenidadesDTO2);
        amenidadesDTO2.setId(2L);
        assertThat(amenidadesDTO1).isNotEqualTo(amenidadesDTO2);
        amenidadesDTO1.setId(null);
        assertThat(amenidadesDTO1).isNotEqualTo(amenidadesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(amenidadesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(amenidadesMapper.fromId(null)).isNull();
    }
}
