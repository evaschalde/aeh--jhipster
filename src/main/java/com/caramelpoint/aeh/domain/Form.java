package com.caramelpoint.aeh.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Form.
 */
@Entity
@Table(name = "form")
public class Form implements Serializable {

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

    @ManyToOne
    @JsonIgnoreProperties("")
    private Event event;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Responsable responsable;

    @ManyToOne
    @JsonIgnoreProperties("")
    private ExternalResponsable externalResponsable;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Template template;

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

    public Form name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getForm() {
        return form;
    }

    public Form form(byte[] form) {
        this.form = form;
        return this;
    }

    public void setForm(byte[] form) {
        this.form = form;
    }

    public String getFormContentType() {
        return formContentType;
    }

    public Form formContentType(String formContentType) {
        this.formContentType = formContentType;
        return this;
    }

    public void setFormContentType(String formContentType) {
        this.formContentType = formContentType;
    }

    public Event getEvent() {
        return event;
    }

    public Form event(Event event) {
        this.event = event;
        return this;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Responsable getResponsable() {
        return responsable;
    }

    public Form responsable(Responsable responsable) {
        this.responsable = responsable;
        return this;
    }

    public void setResponsable(Responsable responsable) {
        this.responsable = responsable;
    }

    public ExternalResponsable getExternalResponsable() {
        return externalResponsable;
    }

    public Form externalResponsable(ExternalResponsable externalResponsable) {
        this.externalResponsable = externalResponsable;
        return this;
    }

    public void setExternalResponsable(ExternalResponsable externalResponsable) {
        this.externalResponsable = externalResponsable;
    }

    public Template getTemplate() {
        return template;
    }

    public Form template(Template template) {
        this.template = template;
        return this;
    }

    public void setTemplate(Template template) {
        this.template = template;
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
        Form form = (Form) o;
        if (form.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), form.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Form{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", form='" + getForm() + "'" +
            ", formContentType='" + getFormContentType() + "'" +
            "}";
    }
}
