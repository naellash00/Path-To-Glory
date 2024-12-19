package com.example.PathOfGlory.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Achievement { // Naelah
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "achievement title cannot be empty")
    @Size(min = 5, message = "title cannot be less than 5 letters")
    @Column(columnDefinition = "varchar(50) not null")
    private String title;

    @NotEmpty(message = "achievement description cannot be empty")
    @Size(min = 30, message = "title cannot be less than 30 letters")
    @Column(columnDefinition = "varchar(200) not null")
    private String description;

    @ManyToOne
    @JsonIgnore
    private Athlete athlete;
}
