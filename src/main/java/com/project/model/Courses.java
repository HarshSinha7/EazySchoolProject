package com.project.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.id.IncrementGenerator;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class Courses extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int courseId;
    private String name;
    private String fees;
    @ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Person> persons=new ArrayList<>();
}
