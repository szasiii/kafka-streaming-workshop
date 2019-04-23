package org.szasiii.github.infra.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    private Long id;
    private Long clinicId;
    private String name;
    private String specialityCode;

}
