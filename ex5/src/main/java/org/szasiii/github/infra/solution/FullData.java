package org.szasiii.github.infra.solution;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.szasiii.github.infra.model.Doctor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FullData {
    private PrescriptionDisease prescriptionDisaese;
    private Doctor doctor;
}
