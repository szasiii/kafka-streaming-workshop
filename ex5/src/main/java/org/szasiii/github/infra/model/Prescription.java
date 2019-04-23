package org.szasiii.github.infra.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Prescription {

    private Long prescriptionId;
    private Long doctorId;
    private Long diseaseId;
    private LocalDate date;
}
