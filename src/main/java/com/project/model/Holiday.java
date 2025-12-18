package com.project.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "holidays")
@Data
@NoArgsConstructor
public class Holiday extends BaseEntity{
    @Id
    private String day;
    private String reason;
    @Enumerated(EnumType.STRING)
    private Type type;
    public enum Type {
        FESTIVAL, FEDERAL
    }
}
