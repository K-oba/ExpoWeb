package com.kaoba.expo.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Post.
 */
@Entity
@Table(name = "post")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "imagen")
    private String imagen;

    @Column(name = "mensaje")
    private String mensaje;

    @ManyToOne
    private Timeline timeline;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImagen() {
        return imagen;
    }

    public Post imagen(String imagen) {
        this.imagen = imagen;
        return this;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Post mensaje(String mensaje) {
        this.mensaje = mensaje;
        return this;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public Post timeline(Timeline timeline) {
        this.timeline = timeline;
        return this;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        if (post.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), post.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Post{" +
            "id=" + getId() +
            ", imagen='" + getImagen() + "'" +
            ", mensaje='" + getMensaje() + "'" +
            "}";
    }
}
