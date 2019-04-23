package org.szasiii.github.infra.solution;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.szasiii.github.infra.model.Disease;
import org.szasiii.github.infra.model.Prescription;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionDisease {
    private Prescription prescription;
    private Disease disease;
}