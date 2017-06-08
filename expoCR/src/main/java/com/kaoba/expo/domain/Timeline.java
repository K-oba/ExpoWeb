package com.kaoba.expo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Timeline.
 */
@Entity
@Table(name = "timeline")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Timeline implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Exposicion exposicion;

    @OneToMany(mappedBy = "timeline")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Post> posts = new HashSet<>();

    @ManyToMany(mappedBy = "timelines")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Usuario> usuarios = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Exposicion getExposicion() {
        return exposicion;
    }

    public Timeline exposicion(Exposicion exposicion) {
        this.exposicion = exposicion;
        return this;
    }

    public void setExposicion(Exposicion exposicion) {
        this.exposicion = exposicion;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public Timeline posts(Set<Post> posts) {
        this.posts = posts;
        return this;
    }

    public Timeline addPost(Post post) {
        this.posts.add(post);
        post.setTimeline(this);
        return this;
    }

    public Timeline removePost(Post post) {
        this.posts.remove(post);
        post.setTimeline(null);
        return this;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public Timeline usuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
        return this;
    }

    public Timeline addUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
        usuario.getTimelines().add(this);
        return this;
    }

    public Timeline removeUsuario(Usuario usuario) {
        this.usuarios.remove(usuario);
        usuario.getTimelines().remove(this);
        return this;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Timeline timeline = (Timeline) o;
        if (timeline.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), timeline.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Timeline{" +
            "id=" + getId() +
            "}";
    }
}
