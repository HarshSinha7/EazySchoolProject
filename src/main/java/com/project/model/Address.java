package com.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Address extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addressId;
    @NotBlank(message = "Feild can't be blank")
    @Size(min = 5, message = "Address should be atleast 5 character long")
    private String address1;
    private String address2;
    @NotBlank( message = "Feild can't be blank")
    @Size(min = 5, message = "City should be atleast 5 character long")
    private String city;
    @NotBlank(message = "Feild can't be blank")
    @Size(min = 5, message = "State should be atleast 5 character long")
    private String state;
    @NotBlank(message = "Feild can't be blank")
    @Pattern(regexp="(^$|[0-9]{5})", message = "Invalid zip code")
    private String zipCode;

}
