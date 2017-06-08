package com.kaoba.expo.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.kaoba.expo.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.kaoba.expo.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.kaoba.expo.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.kaoba.expo.domain.PersistentToken.class.getName(), jcacheConfiguration);
            cm.createCache(com.kaoba.expo.domain.User.class.getName() + ".persistentTokens", jcacheConfiguration);
            cm.createCache(com.kaoba.expo.domain.SocialUserConnection.class.getName(), jcacheConfiguration);
            cm.createCache(com.kaoba.expo.domain.Usuario.class.getName(), jcacheConfiguration);
            cm.createCache(com.kaoba.expo.domain.Rol.class.getName(), jcacheConfiguration);
            cm.createCache(com.kaoba.expo.domain.Brouchure.class.getName(), jcacheConfiguration);
            cm.createCache(com.kaoba.expo.domain.Provincia.class.getName(), jcacheConfiguration);
            cm.createCache(com.kaoba.expo.domain.Canton.class.getName(), jcacheConfiguration);
            cm.createCache(com.kaoba.expo.domain.Rol.class.getName() + ".usuarios", jcacheConfiguration);
            cm.createCache(com.kaoba.expo.domain.Provincia.class.getName() + ".cantons", jcacheConfiguration);
            cm.createCache(com.kaoba.expo.domain.Canton.class.getName() + ".distritos", jcacheConfiguration);
            cm.createCache(com.kaoba.expo.domain.Distrito.class.getName(), jcacheConfiguration);
            cm.createCache(com.kaoba.expo.domain.Distrito.class.getName() + ".exposicions", jcacheConfiguration);
            cm.createCache(com.kaoba.expo.domain.Amenidades.class.getName(), jcacheConfiguration);
            cm.createCache(com.kaoba.expo.domain.Exposicion.class.getName(), jcacheConfiguration);
            cm.createCache(com.kaoba.expo.domain.Categoria.class.getName(), jcacheConfiguration);
            cm.createCache(com.kaoba.expo.domain.Categoria.class.getName() + ".exposicions", jcacheConfiguration);
            cm.createCache(com.kaoba.expo.domain.Categoria.class.getName() + ".subCategorias", jcacheConfiguration);
            cm.createCache(com.kaoba.expo.domain.SubCategoria.class.getName(), jcacheConfiguration);
            cm.createCache(com.kaoba.expo.domain.Charla.class.getName(), jcacheConfiguration);
            cm.createCache(com.kaoba.expo.domain.Exposicion.class.getName() + ".charlas", jcacheConfiguration);
            cm.createCache(com.kaoba.expo.domain.Exposicion.class.getName() + ".amenidades", jcacheConfiguration);
            cm.createCache(com.kaoba.expo.domain.Amenidades.class.getName() + ".exposicions", jcacheConfiguration);
            cm.createCache(com.kaoba.expo.domain.Exposicion.class.getName() + ".beacons", jcacheConfiguration);
            cm.createCache(com.kaoba.expo.domain.Exposicion.class.getName() + ".timelines", jcacheConfiguration);
            cm.createCache(com.kaoba.expo.domain.Timeline.class.getName(), jcacheConfiguration);
            cm.createCache(com.kaoba.expo.domain.Timeline.class.getName() + ".posts", jcacheConfiguration);
            cm.createCache(com.kaoba.expo.domain.Timeline.class.getName() + ".usuarios", jcacheConfiguration);
            cm.createCache(com.kaoba.expo.domain.Post.class.getName(), jcacheConfiguration);
            cm.createCache(com.kaoba.expo.domain.Usuario.class.getName() + ".timelines", jcacheConfiguration);
            cm.createCache(com.kaoba.expo.domain.Pregunta.class.getName(), jcacheConfiguration);
            cm.createCache(com.kaoba.expo.domain.Pregunta.class.getName() + ".charlas", jcacheConfiguration);
            cm.createCache(com.kaoba.expo.domain.Charla.class.getName() + ".preguntas", jcacheConfiguration);
            cm.createCache(com.kaoba.expo.domain.Usuario.class.getName() + ".preguntas", jcacheConfiguration);
            cm.createCache(com.kaoba.expo.domain.Stand.class.getName(), jcacheConfiguration);
            cm.createCache(com.kaoba.expo.domain.Beacon.class.getName(), jcacheConfiguration);
            cm.createCache(com.kaoba.expo.domain.Click.class.getName(), jcacheConfiguration);
            cm.createCache(com.kaoba.expo.domain.Exposicion.class.getName() + ".preguntas", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
