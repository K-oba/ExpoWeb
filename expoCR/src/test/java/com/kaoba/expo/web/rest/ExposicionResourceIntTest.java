package com.kaoba.expo.web.rest;

import com.kaoba.expo.ExpoCrApp;

import com.kaoba.expo.domain.Exposicion;
import com.kaoba.expo.repository.ExposicionRepository;
import com.kaoba.expo.service.ExposicionService;
import com.kaoba.expo.service.dto.ExposicionDTO;
import com.kaoba.expo.service.mapper.ExposicionMapper;
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
 * Test class for the ExposicionResource REST controller.
 *
 * @see ExposicionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExpoCrApp.class)
public class ExposicionResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ESTADO_EXPO = false;
    private static final Boolean UPDATED_ESTADO_EXPO = true;

    private static final String DEFAULT_FECHA_INICIO = "AAAAAAAAAA";
    private static final String UPDATED_FECHA_INICIO = "BBBBBBBBBB";

    private static final String DEFAULT_FECHA_FIN = "AAAAAAAAAA";
    private static final String UPDATED_FECHA_FIN = "BBBBBBBBBB";

    private static final String DEFAULT_COORDENADAS = "AAAAAAAAAA";
    private static final String UPDATED_COORDENADAS = "BBBBBBBBBB";

    @Autowired
    private ExposicionRepository exposicionRepository;

    @Autowired
    private ExposicionMapper exposicionMapper;

    @Autowired
    private ExposicionService exposicionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restExposicionMockMvc;

    private Exposicion exposicion;

//    @Before
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        ExposicionResource exposicionResource = new ExposicionResource(exposicionService);
//        this.restExposicionMockMvc = MockMvcBuilders.standaloneSetup(exposicionResource)
//            .setCustomArgumentResolvers(pageableArgumentResolver)
//            .setControllerAdvice(exceptionTranslator)
//            .setMessageConverters(jacksonMessageConverter).build();
//    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Exposicion createEntity(EntityManager em) {
        Exposicion exposicion = new Exposicion()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .estadoExpo(DEFAULT_ESTADO_EXPO)
            .fechaInicio(DEFAULT_FECHA_INICIO)
            .fechaFin(DEFAULT_FECHA_FIN)
            .coordenadas(DEFAULT_COORDENADAS);
        return exposicion;
    }

    @Before
    public void initTest() {
        exposicion = createEntity(em);
    }

    @Test
    @Transactional
    public void createExposicion() throws Exception {
        int databaseSizeBeforeCreate = exposicionRepository.findAll().size();

        // Create the Exposicion
        ExposicionDTO exposicionDTO = exposicionMapper.toDto(exposicion);
        restExposicionMockMvc.perform(post("/api/exposicions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exposicionDTO)))
            .andExpect(status().isCreated());

        // Validate the Exposicion in the database
        List<Exposicion> exposicionList = exposicionRepository.findAll();
        assertThat(exposicionList).hasSize(databaseSizeBeforeCreate + 1);
        Exposicion testExposicion = exposicionList.get(exposicionList.size() - 1);
        assertThat(testExposicion.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testExposicion.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testExposicion.isEstadoExpo()).isEqualTo(DEFAULT_ESTADO_EXPO);
        assertThat(testExposicion.getFechaInicio()).isEqualTo(DEFAULT_FECHA_INICIO);
        assertThat(testExposicion.getFechaFin()).isEqualTo(DEFAULT_FECHA_FIN);
        assertThat(testExposicion.getCoordenadas()).isEqualTo(DEFAULT_COORDENADAS);
    }

    @Test
    @Transactional
    public void createExposicionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = exposicionRepository.findAll().size();

        // Create the Exposicion with an existing ID
        exposicion.setId(1L);
        ExposicionDTO exposicionDTO = exposicionMapper.toDto(exposicion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExposicionMockMvc.perform(post("/api/exposicions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exposicionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Exposicion> exposicionList = exposicionRepository.findAll();
        assertThat(exposicionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllExposicions() throws Exception {
        // Initialize the database
        exposicionRepository.saveAndFlush(exposicion);

        // Get all the exposicionList
        restExposicionMockMvc.perform(get("/api/exposicions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exposicion.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].estadoExpo").value(hasItem(DEFAULT_ESTADO_EXPO.booleanValue())))
            .andExpect(jsonPath("$.[*].fechaInicio").value(hasItem(DEFAULT_FECHA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fechaFin").value(hasItem(DEFAULT_FECHA_FIN.toString())))
            .andExpect(jsonPath("$.[*].coordenadas").value(hasItem(DEFAULT_COORDENADAS.toString())));
    }

    @Test
    @Transactional
    public void getExposicion() throws Exception {
        // Initialize the database
        exposicionRepository.saveAndFlush(exposicion);

        // Get the exposicion
        restExposicionMockMvc.perform(get("/api/exposicions/{id}", exposicion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(exposicion.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.estadoExpo").value(DEFAULT_ESTADO_EXPO.booleanValue()))
            .andExpect(jsonPath("$.fechaInicio").value(DEFAULT_FECHA_INICIO.toString()))
            .andExpect(jsonPath("$.fechaFin").value(DEFAULT_FECHA_FIN.toString()))
            .andExpect(jsonPath("$.coordenadas").value(DEFAULT_COORDENADAS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingExposicion() throws Exception {
        // Get the exposicion
        restExposicionMockMvc.perform(get("/api/exposicions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExposicion() throws Exception {
        // Initialize the database
        exposicionRepository.saveAndFlush(exposicion);
        int databaseSizeBeforeUpdate = exposicionRepository.findAll().size();

        // Update the exposicion
        Exposicion updatedExposicion = exposicionRepository.findOne(exposicion.getId());
        updatedExposicion
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .estadoExpo(UPDATED_ESTADO_EXPO)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .coordenadas(UPDATED_COORDENADAS);
        ExposicionDTO exposicionDTO = exposicionMapper.toDto(updatedExposicion);

        restExposicionMockMvc.perform(put("/api/exposicions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exposicionDTO)))
            .andExpect(status().isOk());

        // Validate the Exposicion in the database
        List<Exposicion> exposicionList = exposicionRepository.findAll();
        assertThat(exposicionList).hasSize(databaseSizeBeforeUpdate);
        Exposicion testExposicion = exposicionList.get(exposicionList.size() - 1);
        assertThat(testExposicion.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testExposicion.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testExposicion.isEstadoExpo()).isEqualTo(UPDATED_ESTADO_EXPO);
        assertThat(testExposicion.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testExposicion.getFechaFin()).isEqualTo(UPDATED_FECHA_FIN);
        assertThat(testExposicion.getCoordenadas()).isEqualTo(UPDATED_COORDENADAS);
    }

    @Test
    @Transactional
    public void updateNonExistingExposicion() throws Exception {
        int databaseSizeBeforeUpdate = exposicionRepository.findAll().size();

        // Create the Exposicion
        ExposicionDTO exposicionDTO = exposicionMapper.toDto(exposicion);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restExposicionMockMvc.perform(put("/api/exposicions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exposicionDTO)))
            .andExpect(status().isCreated());

        // Validate the Exposicion in the database
        List<Exposicion> exposicionList = exposicionRepository.findAll();
        assertThat(exposicionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteExposicion() throws Exception {
        // Initialize the database
        exposicionRepository.saveAndFlush(exposicion);
        int databaseSizeBeforeDelete = exposicionRepository.findAll().size();

        // Get the exposicion
        restExposicionMockMvc.perform(delete("/api/exposicions/{id}", exposicion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Exposicion> exposicionList = exposicionRepository.findAll();
        assertThat(exposicionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Exposicion.class);
        Exposicion exposicion1 = new Exposicion();
        exposicion1.setId(1L);
        Exposicion exposicion2 = new Exposicion();
        exposicion2.setId(exposicion1.getId());
        assertThat(exposicion1).isEqualTo(exposicion2);
        exposicion2.setId(2L);
        assertThat(exposicion1).isNotEqualTo(exposicion2);
        exposicion1.setId(null);
        assertThat(exposicion1).isNotEqualTo(exposicion2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExposicionDTO.class);
        ExposicionDTO exposicionDTO1 = new ExposicionDTO();
        exposicionDTO1.setId(1L);
        ExposicionDTO exposicionDTO2 = new ExposicionDTO();
        assertThat(exposicionDTO1).isNotEqualTo(exposicionDTO2);
        exposicionDTO2.setId(exposicionDTO1.getId());
        assertThat(exposicionDTO1).isEqualTo(exposicionDTO2);
        exposicionDTO2.setId(2L);
        assertThat(exposicionDTO1).isNotEqualTo(exposicionDTO2);
        exposicionDTO1.setId(null);
        assertThat(exposicionDTO1).isNotEqualTo(exposicionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(exposicionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(exposicionMapper.fromId(null)).isNull();
    }
}
