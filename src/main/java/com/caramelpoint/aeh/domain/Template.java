package com.caramelpoint.aeh.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Template.
 */
@Entity
@Table(name = "template")
public class Template implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "form")
    private byte[] form;

    @Column(name = "form_content_type")
    private String formContentType;

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

    public Template name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getForm() {
        return form;
    }

    public Template form(byte[] form) {
        this.form = form;
        return this;
    }

    public void setForm(byte[] form) {
        this.form = form;
    }

    public String getFormContentType() {
        return formContentType;
    }

    public Template formContentType(String formContentType) {
        this.formContentType = formContentType;
        return this;
    }

    public void setFormContentType(String formContentType) {
        this.formContentType = formContentType;
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
        Template template = (Template) o;
        if (template.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), template.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Template{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", form='" + getForm() + "'" +
            ", formContentType='" + getFormContentType() + "'" +
            "}";
    }
}
