package com.openclassroomsProject.Mediscreenpatient.model;

import com.openclassroomsProject.Mediscreenpatient.constants.ValidationConstants;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Date;

/**
 * Patient entity
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull(message = ValidationConstants.NOT_NULL)
    private String family;
    @NotNull(message = ValidationConstants.NOT_NULL)
    private String given;
    @NotNull(message = ValidationConstants.NOT_NULL)
    private Date dob;
    @Size(min = 1, max = 1, message = ValidationConstants.WRONG_FORMAT)
    @NotNull(message = ValidationConstants.NOT_NULL)
    private String sex;
    private String address;
    @Pattern(regexp = ValidationConstants.REGEX_PHONE_NUMBER, message = ValidationConstants.WRONG_FORMAT)
    private String phone;

    public Patient() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getGiven() {
        return given;
    }

    public void setGiven(String given) {
        this.given = given;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", family='" + family + '\'' +
                ", given='" + given + '\'' +
                ", dob=" + dob +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}