package cl.aguilera.projects.javabcintt.service;

import cl.aguilera.projects.javabcintt.dto.UserRequestDTO;
import cl.aguilera.projects.javabcintt.dto.UserResponseDTO;

public interface LoginService {

    UserResponseDTO signUp(UserRequestDTO userDTO) throws Exception;
}
