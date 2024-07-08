package cl.aguilera.projects.javabcintt.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PhoneDTO {
    private String number;
    private String citycode;
    private String countrycode;
}
