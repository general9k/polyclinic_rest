package ru.rodionov.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "reception")
public class Reception {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "date_of_appointment", nullable = false)
    private OffsetDateTime dateOfAppointment;

    @CreatedDate
    @Column(name = "date_inspection")
    private OffsetDateTime dateInspection;

    @NotNull
    @Builder.Default
    @Column(name = "was_carried_out", nullable = false)
    private Boolean wasCarriedOut = Boolean.FALSE;

    @Column(name = "prescription")
    private String prescription;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_worker", referencedColumnName = "id", nullable = false)
    private User worker;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_patient", referencedColumnName = "id", nullable = false)
    private User patient;

    @ManyToMany
    @JoinTable(
            name = "reception_symptom",
            joinColumns = @JoinColumn(name = "reception_id"),
            inverseJoinColumns = @JoinColumn(name = "symptom_id")
    )
    private Set<Symptom> symptoms;

    @ManyToMany
    @JoinTable(
            name = "reception_diagnosis",
            joinColumns = @JoinColumn(name = "reception_id"),
            inverseJoinColumns = @JoinColumn(name = "diagnosis_id")
    )
    private Set<Diagnose> diagnoses;

    @ManyToMany
    @JoinTable(
            name = "reception_medicine",
            joinColumns = @JoinColumn(name = "reception_id"),
            inverseJoinColumns = @JoinColumn(name = "medicine_id")
    )
    private Set<Medicine> medicines;
}

