package org.szasiii.github.infra.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Clinic {

    private Long id;
    private String name;
    private String region;


}
