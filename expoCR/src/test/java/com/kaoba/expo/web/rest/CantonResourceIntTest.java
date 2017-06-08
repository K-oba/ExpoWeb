package com.kaoba.expo.web.rest;

import com.kaoba.expo.ExpoCrApp;

import com.kaoba.expo.domain.Canton;
import com.kaoba.expo.repository.CantonRepository;
import com.kaoba.expo.service.CantonService;
import com.kaoba.expo.service.dto.CantonDTO;
import com.kaoba.expo.service.mapper.CantonMapper;
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
 * Test class for the CantonResource REST controller.
 *
 * @see CantonResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExpoCrApp.class)
public class CantonResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private CantonRepository cantonRepository;

    @Autowired
    private CantonMapper cantonMapper;

    @Autowired
    private CantonService cantonService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCantonMockMvc;

    private Canton canton;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CantonResource cantonResource = new CantonResource(cantonService);
        this.restCantonMockMvc = MockMvcBuilders.standaloneSetup(cantonResource)
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
    public static Canton createEntity(EntityManager em) {
        Canton canton = new Canton()
            .nombre(DEFAULT_NOMBRE);
        return canton;
    }

    @Before
    public void initTest() {
        canton = createEntity(em);
    }

    @Test
    @Transactional
    public void createCanton() throws Exception {
        int databaseSizeBeforeCreate = cantonRepository.findAll().size();

        // Create the Canton
        CantonDTO cantonDTO = cantonMapper.toDto(canton);
        restCantonMockMvc.perform(post("/api/cantons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cantonDTO)))
            .andExpect(status().isCreated());

        // Validate the Canton in the database
        List<Canton> cantonList = cantonRepository.findAll();
        assertThat(cantonList).hasSize(databaseSizeBeforeCreate + 1);
        Canton testCanton = cantonList.get(cantonList.size() - 1);
        assertThat(testCanton.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createCantonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cantonRepository.findAll().size();

        // Create the Canton with an existing ID
        canton.setId(1L);
        CantonDTO cantonDTO = cantonMapper.toDto(canton);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCantonMockMvc.perform(post("/api/cantons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cantonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Canton> cantonList = cantonRepository.findAll();
        assertThat(cantonList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCantons() throws Exception {
        // Initialize the database
        cantonRepository.saveAndFlush(canton);

        // Get all the cantonList
        restCantonMockMvc.perform(get("/api/cantons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(canton.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }

    @Test
    @Transactional
    public void getCanton() throws Exception {
        // Initialize the database
        cantonRepository.saveAndFlush(canton);

        // Get the canton
        restCantonMockMvc.perform(get("/api/cantons/{id}", canton.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(canton.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCanton() throws Exception {
        // Get the canton
        restCantonMockMvc.perform(get("/api/cantons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCanton() throws Exception {
        // Initialize the database
        cantonRepository.saveAndFlush(canton);
        int databaseSizeBeforeUpdate = cantonRepository.findAll().size();

        // Update the canton
        Canton updatedCanton = cantonRepository.findOne(canton.getId());
        updatedCanton
            .nombre(UPDATED_NOMBRE);
        CantonDTO cantonDTO = cantonMapper.toDto(updatedCanton);

        restCantonMockMvc.perform(put("/api/cantons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cantonDTO)))
            .andExpect(status().isOk());

        // Validate the Canton in the database
        List<Canton> cantonList = cantonRepository.findAll();
        assertThat(cantonList).hasSize(databaseSizeBeforeUpdate);
        Canton testCanton = cantonList.get(cantonList.size() - 1);
        assertThat(testCanton.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingCanton() throws Exception {
        int databaseSizeBeforeUpdate = cantonRepository.findAll().size();

        // Create the Canton
        CantonDTO cantonDTO = cantonMapper.toDto(canton);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCantonMockMvc.perform(put("/api/cantons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cantonDTO)))
            .andExpect(status().isCreated());

        // Validate the Canton in the database
        List<Canton> cantonList = cantonRepository.findAll();
        assertThat(cantonList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCanton() throws Exception {
        // Initialize the database
        cantonRepository.saveAndFlush(canton);
        int databaseSizeBeforeDelete = cantonRepository.findAll().size();

        // Get the canton
        restCantonMockMvc.perform(delete("/api/cantons/{id}", canton.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Canton> cantonList = cantonRepository.findAll();
        assertThat(cantonList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Canton.class);
        Canton canton1 = new Canton();
        canton1.setId(1L);
        Canton canton2 = new Canton();
        canton2.setId(canton1.getId());
        assertThat(canton1).isEqualTo(canton2);
        canton2.setId(2L);
        assertThat(canton1).isNotEqualTo(canton2);
        canton1.setId(null);
        assertThat(canton1).isNotEqualTo(canton2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CantonDTO.class);
        CantonDTO cantonDTO1 = new CantonDTO();
        cantonDTO1.setId(1L);
        CantonDTO cantonDTO2 = new CantonDTO();
        assertThat(cantonDTO1).isNotEqualTo(cantonDTO2);
        cantonDTO2.setId(cantonDTO1.getId());
        assertThat(cantonDTO1).isEqualTo(cantonDTO2);
        cantonDTO2.setId(2L);
        assertThat(cantonDTO1).isNotEqualTo(cantonDTO2);
        cantonDTO1.setId(null);
        assertThat(cantonDTO1).isNotEqualTo(cantonDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cantonMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cantonMapper.fromId(null)).isNull();
    }
}
