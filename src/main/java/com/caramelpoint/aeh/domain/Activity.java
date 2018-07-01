package com.caramelpoint.aeh.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Activity.
 */
@Entity
@Table(name = "activity")
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "activity")
    private Set<Company> activities = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("subActivities")
    private Activity activity;

    @OneToMany(mappedBy = "activity")
    private Set<Activity> subActivities = new HashSet<>();

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

    public Activity name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Activity description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Company> getActivities() {
        return activities;
    }

    public Activity activities(Set<Company> companies) {
        this.activities = companies;
        return this;
    }

    public Activity addActivity(Company company) {
        this.activities.add(company);
        company.setActivity(this);
        return this;
    }

    public Activity removeActivity(Company company) {
        this.activities.remove(company);
        company.setActivity(null);
        return this;
    }

    public void setActivities(Set<Company> companies) {
        this.activities = companies;
    }

    public Activity getActivity() {
        return activity;
    }

    public Activity activity(Activity activity) {
        this.activity = activity;
        return this;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Set<Activity> getSubActivities() {
        return subActivities;
    }

    public Activity subActivities(Set<Activity> activities) {
        this.subActivities = activities;
        return this;
    }

    public Activity addSubActivity(Activity activity) {
        this.subActivities.add(activity);
        activity.setActivity(this);
        return this;
    }

    public Activity removeSubActivity(Activity activity) {
        this.subActivities.remove(activity);
        activity.setActivity(null);
        return this;
    }

    public void setSubActivities(Set<Activity> activities) {
        this.subActivities = activities;
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
        Activity activity = (Activity) o;
        if (activity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), activity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Activity{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
