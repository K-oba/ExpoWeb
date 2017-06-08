package com.kaoba.expo.web.rest;

import com.kaoba.expo.ExpoCrApp;

import com.kaoba.expo.domain.Brouchure;
import com.kaoba.expo.repository.BrouchureRepository;
import com.kaoba.expo.service.BrouchureService;
import com.kaoba.expo.service.dto.BrouchureDTO;
import com.kaoba.expo.service.mapper.BrouchureMapper;
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
 * Test class for the BrouchureResource REST controller.
 *
 * @see BrouchureResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExpoCrApp.class)
public class BrouchureResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_URLIMAGEN = "AAAAAAAAAA";
    private static final String UPDATED_URLIMAGEN = "BBBBBBBBBB";

    @Autowired
    private BrouchureRepository brouchureRepository;

    @Autowired
    private BrouchureMapper brouchureMapper;

    @Autowired
    private BrouchureService brouchureService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBrouchureMockMvc;

    private Brouchure brouchure;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BrouchureResource brouchureResource = new BrouchureResource(brouchureService);
        this.restBrouchureMockMvc = MockMvcBuilders.standaloneSetup(brouchureResource)
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
    public static Brouchure createEntity(EntityManager em) {
        Brouchure brouchure = new Brouchure()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .urlimagen(DEFAULT_URLIMAGEN);
        return brouchure;
    }

    @Before
    public void initTest() {
        brouchure = createEntity(em);
    }

    @Test
    @Transactional
    public void createBrouchure() throws Exception {
        int databaseSizeBeforeCreate = brouchureRepository.findAll().size();

        // Create the Brouchure
        BrouchureDTO brouchureDTO = brouchureMapper.toDto(brouchure);
        restBrouchureMockMvc.perform(post("/api/brouchures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brouchureDTO)))
            .andExpect(status().isCreated());

        // Validate the Brouchure in the database
        List<Brouchure> brouchureList = brouchureRepository.findAll();
        assertThat(brouchureList).hasSize(databaseSizeBeforeCreate + 1);
        Brouchure testBrouchure = brouchureList.get(brouchureList.size() - 1);
        assertThat(testBrouchure.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testBrouchure.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testBrouchure.getUrlimagen()).isEqualTo(DEFAULT_URLIMAGEN);
    }

    @Test
    @Transactional
    public void createBrouchureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = brouchureRepository.findAll().size();

        // Create the Brouchure with an existing ID
        brouchure.setId(1L);
        BrouchureDTO brouchureDTO = brouchureMapper.toDto(brouchure);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBrouchureMockMvc.perform(post("/api/brouchures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brouchureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Brouchure> brouchureList = brouchureRepository.findAll();
        assertThat(brouchureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBrouchures() throws Exception {
        // Initialize the database
        brouchureRepository.saveAndFlush(brouchure);

        // Get all the brouchureList
        restBrouchureMockMvc.perform(get("/api/brouchures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(brouchure.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].urlimagen").value(hasItem(DEFAULT_URLIMAGEN.toString())));
    }

    @Test
    @Transactional
    public void getBrouchure() throws Exception {
        // Initialize the database
        brouchureRepository.saveAndFlush(brouchure);

        // Get the brouchure
        restBrouchureMockMvc.perform(get("/api/brouchures/{id}", brouchure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(brouchure.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.urlimagen").value(DEFAULT_URLIMAGEN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBrouchure() throws Exception {
        // Get the brouchure
        restBrouchureMockMvc.perform(get("/api/brouchures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBrouchure() throws Exception {
        // Initialize the database
        brouchureRepository.saveAndFlush(brouchure);
        int databaseSizeBeforeUpdate = brouchureRepository.findAll().size();

        // Update the brouchure
        Brouchure updatedBrouchure = brouchureRepository.findOne(brouchure.getId());
        updatedBrouchure
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .urlimagen(UPDATED_URLIMAGEN);
        BrouchureDTO brouchureDTO = brouchureMapper.toDto(updatedBrouchure);

        restBrouchureMockMvc.perform(put("/api/brouchures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brouchureDTO)))
            .andExpect(status().isOk());

        // Validate the Brouchure in the database
        List<Brouchure> brouchureList = brouchureRepository.findAll();
        assertThat(brouchureList).hasSize(databaseSizeBeforeUpdate);
        Brouchure testBrouchure = brouchureList.get(brouchureList.size() - 1);
        assertThat(testBrouchure.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testBrouchure.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testBrouchure.getUrlimagen()).isEqualTo(UPDATED_URLIMAGEN);
    }

    @Test
    @Transactional
    public void updateNonExistingBrouchure() throws Exception {
        int databaseSizeBeforeUpdate = brouchureRepository.findAll().size();

        // Create the Brouchure
        BrouchureDTO brouchureDTO = brouchureMapper.toDto(brouchure);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBrouchureMockMvc.perform(put("/api/brouchures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brouchureDTO)))
            .andExpect(status().isCreated());

        // Validate the Brouchure in the database
        List<Brouchure> brouchureList = brouchureRepository.findAll();
        assertThat(brouchureList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBrouchure() throws Exception {
        // Initialize the database
        brouchureRepository.saveAndFlush(brouchure);
        int databaseSizeBeforeDelete = brouchureRepository.findAll().size();

        // Get the brouchure
        restBrouchureMockMvc.perform(delete("/api/brouchures/{id}", brouchure.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Brouchure> brouchureList = brouchureRepository.findAll();
        assertThat(brouchureList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Brouchure.class);
        Brouchure brouchure1 = new Brouchure();
        brouchure1.setId(1L);
        Brouchure brouchure2 = new Brouchure();
        brouchure2.setId(brouchure1.getId());
        assertThat(brouchure1).isEqualTo(brouchure2);
        brouchure2.setId(2L);
        assertThat(brouchure1).isNotEqualTo(brouchure2);
        brouchure1.setId(null);
        assertThat(brouchure1).isNotEqualTo(brouchure2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BrouchureDTO.class);
        BrouchureDTO brouchureDTO1 = new BrouchureDTO();
        brouchureDTO1.setId(1L);
        BrouchureDTO brouchureDTO2 = new BrouchureDTO();
        assertThat(brouchureDTO1).isNotEqualTo(brouchureDTO2);
        brouchureDTO2.setId(brouchureDTO1.getId());
        assertThat(brouchureDTO1).isEqualTo(brouchureDTO2);
        brouchureDTO2.setId(2L);
        assertThat(brouchureDTO1).isNotEqualTo(brouchureDTO2);
        brouchureDTO1.setId(null);
        assertThat(brouchureDTO1).isNotEqualTo(brouchureDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(brouchureMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(brouchureMapper.fromId(null)).isNull();
    }
}
