package org.szasiii.github.infra.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prescription {

    private Long id;
    private Long doctorid;
    private Long diseaseid;
    private String medicinename;
}
