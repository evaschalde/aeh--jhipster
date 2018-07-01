package com.caramelpoint.aeh.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A BranchOffice.
 */
@Entity
@Table(name = "branch_office")
public class BranchOffice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "street_address")
    private String streetAddress;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "city")
    private String city;

    @Column(name = "state_province")
    private String stateProvince;

    @Column(name = "phone")
    private String phone;

    @OneToMany(mappedBy = "branchOffice")
    private Set<ExternalResponsable> externalResponsables = new HashSet<>();

    @OneToMany(mappedBy = "branchOffice")
    private Set<Company> companies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public BranchOffice streetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
        return this;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public BranchOffice postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public BranchOffice city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public BranchOffice stateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
        return this;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public String getPhone() {
        return phone;
    }

    public BranchOffice phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<ExternalResponsable> getExternalResponsables() {
        return externalResponsables;
    }

    public BranchOffice externalResponsables(Set<ExternalResponsable> externalResponsables) {
        this.externalResponsables = externalResponsables;
        return this;
    }

    public BranchOffice addExternalResponsable(ExternalResponsable externalResponsable) {
        this.externalResponsables.add(externalResponsable);
        externalResponsable.setBranchOffice(this);
        return this;
    }

    public BranchOffice removeExternalResponsable(ExternalResponsable externalResponsable) {
        this.externalResponsables.remove(externalResponsable);
        externalResponsable.setBranchOffice(null);
        return this;
    }

    public void setExternalResponsables(Set<ExternalResponsable> externalResponsables) {
        this.externalResponsables = externalResponsables;
    }

    public Set<Company> getCompanies() {
        return companies;
    }

    public BranchOffice companies(Set<Company> companies) {
        this.companies = companies;
        return this;
    }

    public BranchOffice addCompany(Company company) {
        this.companies.add(company);
        company.setBranchOffice(this);
        return this;
    }

    public BranchOffice removeCompany(Company company) {
        this.companies.remove(company);
        company.setBranchOffice(null);
        return this;
    }

    public void setCompanies(Set<Company> companies) {
        this.companies = companies;
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
        BranchOffice branchOffice = (BranchOffice) o;
        if (branchOffice.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), branchOffice.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BranchOffice{" +
            "id=" + getId() +
            ", streetAddress='" + getStreetAddress() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", city='" + getCity() + "'" +
            ", stateProvince='" + getStateProvince() + "'" +
            ", phone='" + getPhone() + "'" +
            "}";
    }
}
