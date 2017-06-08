package com.kaoba.expo.web.rest;

import com.kaoba.expo.ExpoCrApp;

import com.kaoba.expo.domain.SubCategoria;
import com.kaoba.expo.repository.SubCategoriaRepository;
import com.kaoba.expo.service.SubCategoriaService;
import com.kaoba.expo.service.dto.SubCategoriaDTO;
import com.kaoba.expo.service.mapper.SubCategoriaMapper;
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
 * Test class for the SubCategoriaResource REST controller.
 *
 * @see SubCategoriaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExpoCrApp.class)
public class SubCategoriaResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    @Autowired
    private SubCategoriaRepository subCategoriaRepository;

    @Autowired
    private SubCategoriaMapper subCategoriaMapper;

    @Autowired
    private SubCategoriaService subCategoriaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSubCategoriaMockMvc;

    private SubCategoria subCategoria;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SubCategoriaResource subCategoriaResource = new SubCategoriaResource(subCategoriaService);
        this.restSubCategoriaMockMvc = MockMvcBuilders.standaloneSetup(subCategoriaResource)
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
    public static SubCategoria createEntity(EntityManager em) {
        SubCategoria subCategoria = new SubCategoria()
            .nombre(DEFAULT_NOMBRE)
            .tipo(DEFAULT_TIPO);
        return subCategoria;
    }

    @Before
    public void initTest() {
        subCategoria = createEntity(em);
    }

    @Test
    @Transactional
    public void createSubCategoria() throws Exception {
        int databaseSizeBeforeCreate = subCategoriaRepository.findAll().size();

        // Create the SubCategoria
        SubCategoriaDTO subCategoriaDTO = subCategoriaMapper.toDto(subCategoria);
        restSubCategoriaMockMvc.perform(post("/api/sub-categorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subCategoriaDTO)))
            .andExpect(status().isCreated());

        // Validate the SubCategoria in the database
        List<SubCategoria> subCategoriaList = subCategoriaRepository.findAll();
        assertThat(subCategoriaList).hasSize(databaseSizeBeforeCreate + 1);
        SubCategoria testSubCategoria = subCategoriaList.get(subCategoriaList.size() - 1);
        assertThat(testSubCategoria.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testSubCategoria.getTipo()).isEqualTo(DEFAULT_TIPO);
    }

    @Test
    @Transactional
    public void createSubCategoriaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subCategoriaRepository.findAll().size();

        // Create the SubCategoria with an existing ID
        subCategoria.setId(1L);
        SubCategoriaDTO subCategoriaDTO = subCategoriaMapper.toDto(subCategoria);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubCategoriaMockMvc.perform(post("/api/sub-categorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subCategoriaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SubCategoria> subCategoriaList = subCategoriaRepository.findAll();
        assertThat(subCategoriaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSubCategorias() throws Exception {
        // Initialize the database
        subCategoriaRepository.saveAndFlush(subCategoria);

        // Get all the subCategoriaList
        restSubCategoriaMockMvc.perform(get("/api/sub-categorias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subCategoria.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())));
    }

    @Test
    @Transactional
    public void getSubCategoria() throws Exception {
        // Initialize the database
        subCategoriaRepository.saveAndFlush(subCategoria);

        // Get the subCategoria
        restSubCategoriaMockMvc.perform(get("/api/sub-categorias/{id}", subCategoria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(subCategoria.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSubCategoria() throws Exception {
        // Get the subCategoria
        restSubCategoriaMockMvc.perform(get("/api/sub-categorias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubCategoria() throws Exception {
        // Initialize the database
        subCategoriaRepository.saveAndFlush(subCategoria);
        int databaseSizeBeforeUpdate = subCategoriaRepository.findAll().size();

        // Update the subCategoria
        SubCategoria updatedSubCategoria = subCategoriaRepository.findOne(subCategoria.getId());
        updatedSubCategoria
            .nombre(UPDATED_NOMBRE)
            .tipo(UPDATED_TIPO);
        SubCategoriaDTO subCategoriaDTO = subCategoriaMapper.toDto(updatedSubCategoria);

        restSubCategoriaMockMvc.perform(put("/api/sub-categorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subCategoriaDTO)))
            .andExpect(status().isOk());

        // Validate the SubCategoria in the database
        List<SubCategoria> subCategoriaList = subCategoriaRepository.findAll();
        assertThat(subCategoriaList).hasSize(databaseSizeBeforeUpdate);
        SubCategoria testSubCategoria = subCategoriaList.get(subCategoriaList.size() - 1);
        assertThat(testSubCategoria.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testSubCategoria.getTipo()).isEqualTo(UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void updateNonExistingSubCategoria() throws Exception {
        int databaseSizeBeforeUpdate = subCategoriaRepository.findAll().size();

        // Create the SubCategoria
        SubCategoriaDTO subCategoriaDTO = subCategoriaMapper.toDto(subCategoria);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSubCategoriaMockMvc.perform(put("/api/sub-categorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subCategoriaDTO)))
            .andExpect(status().isCreated());

        // Validate the SubCategoria in the database
        List<SubCategoria> subCategoriaList = subCategoriaRepository.findAll();
        assertThat(subCategoriaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSubCategoria() throws Exception {
        // Initialize the database
        subCategoriaRepository.saveAndFlush(subCategoria);
        int databaseSizeBeforeDelete = subCategoriaRepository.findAll().size();

        // Get the subCategoria
        restSubCategoriaMockMvc.perform(delete("/api/sub-categorias/{id}", subCategoria.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SubCategoria> subCategoriaList = subCategoriaRepository.findAll();
        assertThat(subCategoriaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubCategoria.class);
        SubCategoria subCategoria1 = new SubCategoria();
        subCategoria1.setId(1L);
        SubCategoria subCategoria2 = new SubCategoria();
        subCategoria2.setId(subCategoria1.getId());
        assertThat(subCategoria1).isEqualTo(subCategoria2);
        subCategoria2.setId(2L);
        assertThat(subCategoria1).isNotEqualTo(subCategoria2);
        subCategoria1.setId(null);
        assertThat(subCategoria1).isNotEqualTo(subCategoria2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubCategoriaDTO.class);
        SubCategoriaDTO subCategoriaDTO1 = new SubCategoriaDTO();
        subCategoriaDTO1.setId(1L);
        SubCategoriaDTO subCategoriaDTO2 = new SubCategoriaDTO();
        assertThat(subCategoriaDTO1).isNotEqualTo(subCategoriaDTO2);
        subCategoriaDTO2.setId(subCategoriaDTO1.getId());
        assertThat(subCategoriaDTO1).isEqualTo(subCategoriaDTO2);
        subCategoriaDTO2.setId(2L);
        assertThat(subCategoriaDTO1).isNotEqualTo(subCategoriaDTO2);
        subCategoriaDTO1.setId(null);
        assertThat(subCategoriaDTO1).isNotEqualTo(subCategoriaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(subCategoriaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(subCategoriaMapper.fromId(null)).isNull();
    }
}
