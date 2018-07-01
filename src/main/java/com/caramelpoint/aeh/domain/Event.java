package com.caramelpoint.aeh.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.caramelpoint.aeh.domain.enumeration.Criticallity;

import com.caramelpoint.aeh.domain.enumeration.State;

/**
 * A Event.
 */
@Entity
@Table(name = "event")
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "observations")
    private String observations;

    @Column(name = "start_date_programed")
    private LocalDate startDateProgramed;

    @Column(name = "finish_date_programed")
    private LocalDate finishDateProgramed;

    @Column(name = "start_date_validity")
    private LocalDate startDateValidity;

    @Column(name = "finish_date_validity")
    private LocalDate finishDateValidity;

    @Column(name = "start_date_completed")
    private LocalDate startDateCompleted;

    @Column(name = "finish_date_completed")
    private LocalDate finishDateCompleted;

    @Enumerated(EnumType.STRING)
    @Column(name = "criticallity")
    private Criticallity criticallity;

    @Enumerated(EnumType.STRING)
    @Column(name = "satte")
    private State satte;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Responsable responsable;

    @ManyToOne
    @JsonIgnoreProperties("")
    private BranchOffice branchOffice;

    @ManyToOne
    @JsonIgnoreProperties("")
    private EventType eventType;

    @ManyToOne
    @JsonIgnoreProperties("")
    private ExternalResponsable externalResposable;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObservations() {
        return observations;
    }

    public Event observations(String observations) {
        this.observations = observations;
        return this;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public LocalDate getStartDateProgramed() {
        return startDateProgramed;
    }

    public Event startDateProgramed(LocalDate startDateProgramed) {
        this.startDateProgramed = startDateProgramed;
        return this;
    }

    public void setStartDateProgramed(LocalDate startDateProgramed) {
        this.startDateProgramed = startDateProgramed;
    }

    public LocalDate getFinishDateProgramed() {
        return finishDateProgramed;
    }

    public Event finishDateProgramed(LocalDate finishDateProgramed) {
        this.finishDateProgramed = finishDateProgramed;
        return this;
    }

    public void setFinishDateProgramed(LocalDate finishDateProgramed) {
        this.finishDateProgramed = finishDateProgramed;
    }

    public LocalDate getStartDateValidity() {
        return startDateValidity;
    }

    public Event startDateValidity(LocalDate startDateValidity) {
        this.startDateValidity = startDateValidity;
        return this;
    }

    public void setStartDateValidity(LocalDate startDateValidity) {
        this.startDateValidity = startDateValidity;
    }

    public LocalDate getFinishDateValidity() {
        return finishDateValidity;
    }

    public Event finishDateValidity(LocalDate finishDateValidity) {
        this.finishDateValidity = finishDateValidity;
        return this;
    }

    public void setFinishDateValidity(LocalDate finishDateValidity) {
        this.finishDateValidity = finishDateValidity;
    }

    public LocalDate getStartDateCompleted() {
        return startDateCompleted;
    }

    public Event startDateCompleted(LocalDate startDateCompleted) {
        this.startDateCompleted = startDateCompleted;
        return this;
    }

    public void setStartDateCompleted(LocalDate startDateCompleted) {
        this.startDateCompleted = startDateCompleted;
    }

    public LocalDate getFinishDateCompleted() {
        return finishDateCompleted;
    }

    public Event finishDateCompleted(LocalDate finishDateCompleted) {
        this.finishDateCompleted = finishDateCompleted;
        return this;
    }

    public void setFinishDateCompleted(LocalDate finishDateCompleted) {
        this.finishDateCompleted = finishDateCompleted;
    }

    public Criticallity getCriticallity() {
        return criticallity;
    }

    public Event criticallity(Criticallity criticallity) {
        this.criticallity = criticallity;
        return this;
    }

    public void setCriticallity(Criticallity criticallity) {
        this.criticallity = criticallity;
    }

    public State getSatte() {
        return satte;
    }

    public Event satte(State satte) {
        this.satte = satte;
        return this;
    }

    public void setSatte(State satte) {
        this.satte = satte;
    }

    public Responsable getResponsable() {
        return responsable;
    }

    public Event responsable(Responsable responsable) {
        this.responsable = responsable;
        return this;
    }

    public void setResponsable(Responsable responsable) {
        this.responsable = responsable;
    }

    public BranchOffice getBranchOffice() {
        return branchOffice;
    }

    public Event branchOffice(BranchOffice branchOffice) {
        this.branchOffice = branchOffice;
        return this;
    }

    public void setBranchOffice(BranchOffice branchOffice) {
        this.branchOffice = branchOffice;
    }

    public EventType getEventType() {
        return eventType;
    }

    public Event eventType(EventType eventType) {
        this.eventType = eventType;
        return this;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public ExternalResponsable getExternalResposable() {
        return externalResposable;
    }

    public Event externalResposable(ExternalResponsable externalResponsable) {
        this.externalResposable = externalResponsable;
        return this;
    }

    public void setExternalResposable(ExternalResponsable externalResponsable) {
        this.externalResposable = externalResponsable;
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
        Event event = (Event) o;
        if (event.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), event.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Event{" +
            "id=" + getId() +
            ", observations='" + getObservations() + "'" +
            ", startDateProgramed='" + getStartDateProgramed() + "'" +
            ", finishDateProgramed='" + getFinishDateProgramed() + "'" +
            ", startDateValidity='" + getStartDateValidity() + "'" +
            ", finishDateValidity='" + getFinishDateValidity() + "'" +
            ", startDateCompleted='" + getStartDateCompleted() + "'" +
            ", finishDateCompleted='" + getFinishDateCompleted() + "'" +
            ", criticallity='" + getCriticallity() + "'" +
            ", satte='" + getSatte() + "'" +
            "}";
    }
}
