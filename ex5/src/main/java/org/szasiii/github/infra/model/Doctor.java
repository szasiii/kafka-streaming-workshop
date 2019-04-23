package org.szasiii.github.infra.model;

import lombok.Data;

@Data
public class Doctor {

    private Long id;
    private Long clinicId;
    private Long name;
    private String specialityCode;

}
