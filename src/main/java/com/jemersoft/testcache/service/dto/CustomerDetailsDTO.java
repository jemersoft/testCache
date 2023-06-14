package com.jemersoft.testcache.service.dto;

import com.jemersoft.testcache.domain.enumeration.Gender;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.jemersoft.testcache.domain.CustomerDetails} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CustomerDetailsDTO implements Serializable {

    public CustomerDetailsDTO() {}

    private Long id;

    @NotNull
    private Gender gender;

    @NotNull
    private String phone;

    @NotNull
    private String addressLine1;

    private String addressLine2;

    @NotNull
    private String city;

    @NotNull
    private String country;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerDetailsDTO)) {
            return false;
        }

        CustomerDetailsDTO customerDetailsDTO = (CustomerDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, customerDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerDetailsDTO{" +
            "id=" + getId() +
            ", gender='" + getGender() + "'" +
            ", phone='" + getPhone() + "'" +
            ", addressLine1='" + getAddressLine1() + "'" +
            ", addressLine2='" + getAddressLine2() + "'" +
            ", city='" + getCity() + "'" +
            ", country='" + getCountry() + "'" +
            "}";
    }
}
