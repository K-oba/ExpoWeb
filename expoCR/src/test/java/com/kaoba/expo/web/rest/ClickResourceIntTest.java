package com.kaoba.expo.web.rest;

import com.kaoba.expo.ExpoCrApp;

import com.kaoba.expo.domain.Click;
import com.kaoba.expo.repository.ClickRepository;
import com.kaoba.expo.service.ClickService;
import com.kaoba.expo.service.dto.ClickDTO;
import com.kaoba.expo.service.mapper.ClickMapper;
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
 * Test class for the ClickResource REST controller.
 *
 * @see ClickResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExpoCrApp.class)
public class ClickResourceIntTest {

    private static final String DEFAULT_BEACON_ID = "AAAAAAAAAA";
    private static final String UPDATED_BEACON_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_CLIENT_ID = 1;
    private static final Integer UPDATED_CLIENT_ID = 2;

    private static final String DEFAULT_COORDENADAS = "AAAAAAAAAA";
    private static final String UPDATED_COORDENADAS = "BBBBBBBBBB";

    private static final String DEFAULT_FECHA_HORA = "AAAAAAAAAA";
    private static final String UPDATED_FECHA_HORA = "BBBBBBBBBB";

    @Autowired
    private ClickRepository clickRepository;

    @Autowired
    private ClickMapper clickMapper;

    @Autowired
    private ClickService clickService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClickMockMvc;

    private Click click;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClickResource clickResource = new ClickResource(clickService);
        this.restClickMockMvc = MockMvcBuilders.standaloneSetup(clickResource)
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
    public static Click createEntity(EntityManager em) {
        Click click = new Click()
            .beaconId(DEFAULT_BEACON_ID)
            .clientId(DEFAULT_CLIENT_ID)
            .coordenadas(DEFAULT_COORDENADAS)
            .fechaHora(DEFAULT_FECHA_HORA);
        return click;
    }

    @Before
    public void initTest() {
        click = createEntity(em);
    }

    @Test
    @Transactional
    public void createClick() throws Exception {
        int databaseSizeBeforeCreate = clickRepository.findAll().size();

        // Create the Click
        ClickDTO clickDTO = clickMapper.toDto(click);
        restClickMockMvc.perform(post("/api/clicks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clickDTO)))
            .andExpect(status().isCreated());

        // Validate the Click in the database
        List<Click> clickList = clickRepository.findAll();
        assertThat(clickList).hasSize(databaseSizeBeforeCreate + 1);
        Click testClick = clickList.get(clickList.size() - 1);
        assertThat(testClick.getBeaconId()).isEqualTo(DEFAULT_BEACON_ID);
        assertThat(testClick.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testClick.getCoordenadas()).isEqualTo(DEFAULT_COORDENADAS);
        assertThat(testClick.getFechaHora()).isEqualTo(DEFAULT_FECHA_HORA);
    }

    @Test
    @Transactional
    public void createClickWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clickRepository.findAll().size();

        // Create the Click with an existing ID
        click.setId(1L);
        ClickDTO clickDTO = clickMapper.toDto(click);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClickMockMvc.perform(post("/api/clicks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clickDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Click> clickList = clickRepository.findAll();
        assertThat(clickList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClicks() throws Exception {
        // Initialize the database
        clickRepository.saveAndFlush(click);

        // Get all the clickList
        restClickMockMvc.perform(get("/api/clicks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(click.getId().intValue())))
            .andExpect(jsonPath("$.[*].beaconId").value(hasItem(DEFAULT_BEACON_ID.toString())))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID)))
            .andExpect(jsonPath("$.[*].coordenadas").value(hasItem(DEFAULT_COORDENADAS.toString())))
            .andExpect(jsonPath("$.[*].fechaHora").value(hasItem(DEFAULT_FECHA_HORA.toString())));
    }

    @Test
    @Transactional
    public void getClick() throws Exception {
        // Initialize the database
        clickRepository.saveAndFlush(click);

        // Get the click
        restClickMockMvc.perform(get("/api/clicks/{id}", click.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(click.getId().intValue()))
            .andExpect(jsonPath("$.beaconId").value(DEFAULT_BEACON_ID.toString()))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID))
            .andExpect(jsonPath("$.coordenadas").value(DEFAULT_COORDENADAS.toString()))
            .andExpect(jsonPath("$.fechaHora").value(DEFAULT_FECHA_HORA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClick() throws Exception {
        // Get the click
        restClickMockMvc.perform(get("/api/clicks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClick() throws Exception {
        // Initialize the database
        clickRepository.saveAndFlush(click);
        int databaseSizeBeforeUpdate = clickRepository.findAll().size();

        // Update the click
        Click updatedClick = clickRepository.findOne(click.getId());
        updatedClick
            .beaconId(UPDATED_BEACON_ID)
            .clientId(UPDATED_CLIENT_ID)
            .coordenadas(UPDATED_COORDENADAS)
            .fechaHora(UPDATED_FECHA_HORA);
        ClickDTO clickDTO = clickMapper.toDto(updatedClick);

        restClickMockMvc.perform(put("/api/clicks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clickDTO)))
            .andExpect(status().isOk());

        // Validate the Click in the database
        List<Click> clickList = clickRepository.findAll();
        assertThat(clickList).hasSize(databaseSizeBeforeUpdate);
        Click testClick = clickList.get(clickList.size() - 1);
        assertThat(testClick.getBeaconId()).isEqualTo(UPDATED_BEACON_ID);
        assertThat(testClick.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testClick.getCoordenadas()).isEqualTo(UPDATED_COORDENADAS);
        assertThat(testClick.getFechaHora()).isEqualTo(UPDATED_FECHA_HORA);
    }

    @Test
    @Transactional
    public void updateNonExistingClick() throws Exception {
        int databaseSizeBeforeUpdate = clickRepository.findAll().size();

        // Create the Click
        ClickDTO clickDTO = clickMapper.toDto(click);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClickMockMvc.perform(put("/api/clicks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clickDTO)))
            .andExpect(status().isCreated());

        // Validate the Click in the database
        List<Click> clickList = clickRepository.findAll();
        assertThat(clickList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClick() throws Exception {
        // Initialize the database
        clickRepository.saveAndFlush(click);
        int databaseSizeBeforeDelete = clickRepository.findAll().size();

        // Get the click
        restClickMockMvc.perform(delete("/api/clicks/{id}", click.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Click> clickList = clickRepository.findAll();
        assertThat(clickList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Click.class);
        Click click1 = new Click();
        click1.setId(1L);
        Click click2 = new Click();
        click2.setId(click1.getId());
        assertThat(click1).isEqualTo(click2);
        click2.setId(2L);
        assertThat(click1).isNotEqualTo(click2);
        click1.setId(null);
        assertThat(click1).isNotEqualTo(click2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClickDTO.class);
        ClickDTO clickDTO1 = new ClickDTO();
        clickDTO1.setId(1L);
        ClickDTO clickDTO2 = new ClickDTO();
        assertThat(clickDTO1).isNotEqualTo(clickDTO2);
        clickDTO2.setId(clickDTO1.getId());
        assertThat(clickDTO1).isEqualTo(clickDTO2);
        clickDTO2.setId(2L);
        assertThat(clickDTO1).isNotEqualTo(clickDTO2);
        clickDTO1.setId(null);
        assertThat(clickDTO1).isNotEqualTo(clickDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(clickMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(clickMapper.fromId(null)).isNull();
    }
}
