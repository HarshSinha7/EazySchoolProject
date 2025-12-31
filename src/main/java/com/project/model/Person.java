package com.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Person extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int personId;
    @NotBlank(message = "Feild can't be empty")
    private String name;
    @NotBlank(message = "Feild can't be empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digit")
    @Column(name = "mobile_number")
    private String mobileNum;
    @NotBlank(message = "Feild can't be empty")
    @Email(message = "Not a valid email")
    private String email;
    @NotBlank(message = "Feild can't be empty")
    @Email(message = "Not a valid email")
    @Transient
    private String confirmEmail;
    @NotBlank(message = "Feild can't be empty")
    @Size(min = 5,message = "Password atleast have 5 characters")
    @Column(name = "pwd")
    private String password;
    @NotBlank(message = "Feild can't be empty")
    @Size(min = 5,message = "Password atleast have 5 characters")
    @Transient
    private String confirmPassword;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Address.class)
    @JoinColumn(name = "address_id", referencedColumnName = "addressId", nullable = true)
    private Address address;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, targetEntity = Roles.class)
    @JoinColumn(name = "role_id", referencedColumnName = "roleId", nullable = false)
    private Roles roles;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "class_id", referencedColumnName = "classId", nullable = true)
    private Classes classes;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "courses", joinColumns = {
            @JoinColumn(name = "person_id", referencedColumnName = "personId")},
            inverseJoinColumns = {@JoinColumn(name = "course_id", referencedColumnName = "courseId")})
    List<Courses> courses=new ArrayList<>();
}
