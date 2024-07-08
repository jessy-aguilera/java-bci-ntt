package cl.aguilera.projects.javabcintt.dto;

import cl.aguilera.projects.javabcintt.util.validation.Email;
import cl.aguilera.projects.javabcintt.util.validation.Password;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserRequestDTO {
    private String name;
    @Email(message = "email debe contener un formato valido")
    private String email;
    @Password(message = "password debe contener un formato valido")
    private String password;
    private List<PhoneDTO> phones;
}
