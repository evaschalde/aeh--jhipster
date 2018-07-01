package com.caramelpoint.aeh.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.caramelpoint.aeh.domain.enumeration.Criticallity;

/**
 * A EventType.
 */
@Entity
@Table(name = "event_type")
public class EventType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "criticallity")
    private Criticallity criticallity;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public EventType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public EventType description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Criticallity getCriticallity() {
        return criticallity;
    }

    public EventType criticallity(Criticallity criticallity) {
        this.criticallity = criticallity;
        return this;
    }

    public void setCriticallity(Criticallity criticallity) {
        this.criticallity = criticallity;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EventType eventType = (EventType) o;
        if (eventType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eventType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EventType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", criticallity='" + getCriticallity() + "'" +
            "}";
    }
}
