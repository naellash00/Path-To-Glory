// Osama Alghamdi
package com.example.PathOfGlory.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EventHeldRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // status should be accepted - rejected - requested
    @Column(columnDefinition = "varchar(10)")
    private String status = "Pending";

    @ManyToOne
    @JsonIgnore
    private Arena arena;

    @ManyToOne
    @JsonIgnore
    private Sponsor sponsor;

    @OneToOne
    @MapsId
    @JsonIgnore
    private Event event;
}