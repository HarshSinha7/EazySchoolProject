package com.project.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Profile {
    @NotBlank(message = "Feild can't be empty")
    private String name;
    @NotBlank(message = "Feild can't be empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digit")
    private String mobileNumber;
    @NotBlank(message = "Feild can't be empty")
    @Email(message = "Not a valid email")
    private String email;
    @NotBlank(message="Address1 must not be blank")
    @Size(min=5, message="Address1 must be at least 5 characters long")
    private String address1;

    private String address2;

    @NotBlank(message="City must not be blank")
    @Size(min=5, message="City must be at least 5 characters long")
    private String city;

    @NotBlank(message="State must not be blank")
    @Size(min=5, message="State must be at least 5 characters long")
    private String state;

    @NotBlank(message="Zip Code must not be blank")
    @Pattern(regexp="(^$|[0-9]{5})",message = "Zip Code must be 5 digits")
    private String zipCode;
}
