package com.example.PathOfGlory.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.*;


import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Check(constraints = "age >= 18")
public class Coach { // Naelah
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "full name cannot be empty")
    @Size(min = 5, message = "full name cannot be less than 5 letters")
    @Column(columnDefinition = "varchar(30) not null")
    private String fullName;

    @NotEmpty(message = "username cannot be empty")
    @Column(columnDefinition = "varchar(15) not null unique")
    private String username;

    @NotEmpty(message = "phone number cannot be empty")
    @Size(min = 10, max = 10, message = "phone number must be 10 digits")
    @Pattern(regexp = "^05\\d+$", message = "phone number must start with '05'")
    @Column(columnDefinition = "varchar(15) not null unique")
    private String phoneNumber;

    @NotEmpty(message = "email cannot be empty")
    @Email(message = "enter a valid email")
    @Column(columnDefinition = "varchar(30) not null unique")
    private String email;

    @NotNull(message = "age cannot be empty")
    @Min(value = 18, message = "age cannot be less than 18")
    @Column(columnDefinition = "int not null")
    private Integer age;

    @NotEmpty(message = "city cannot be empty")
    @Column(columnDefinition = "varchar(15) not null")
    private String city;


    @NotEmpty(message = "sport discipline cannot be empty")
    @Column(columnDefinition = "varchar(30) not null")
    private String sportDiscipline;

    @NotEmpty(message = "license cannot be empty")
    @Column(columnDefinition = "varchar(30) not null unique") // check correct length
    private String license;

    @Future(message = "end date must be in the future")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate licenseEndDate;

    @Column(columnDefinition = "varchar(10)")
    private String isActivated;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "coach")
    private Set<BookCoach> bookings;
}
