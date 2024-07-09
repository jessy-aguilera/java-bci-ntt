package cl.aguilera.projects.javabcintt.controller;

import cl.aguilera.projects.javabcintt.dto.PhoneDTO;
import cl.aguilera.projects.javabcintt.dto.UserRequestDTO;
import cl.aguilera.projects.javabcintt.dto.UserResponseDTO;
import cl.aguilera.projects.javabcintt.exception.UserException;
import cl.aguilera.projects.javabcintt.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class LoginControllerTest {

    @Mock
    private LoginService loginService;

    @InjectMocks
    private LoginController loginController;

    private UserRequestDTO userRequestDTO;

    private UserResponseDTO userResponseDTO;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setEmail("efpyi@example.com");
        userRequestDTO.setPassword("a2asfGfdfdf4");
        userRequestDTO.setName("Prueba");

        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.setNumber("1234567890");
        phoneDTO.setCitycode("9");
        phoneDTO.setCountrycode("+56");
        userRequestDTO.setPhones(Collections.singletonList(phoneDTO));

        userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId("e1f136f0-b92d-4564-bd9d-25f12299a92e");
        userResponseDTO.setCreated(new Date());
        userResponseDTO.setModified(new Date());
        userResponseDTO.setLastLogin(new Date());
        userResponseDTO.setToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhc2RzZEBnbWFpbC5jb20iLCJleHAiOjE3MDE2MjkzNjJ9.k3JGost_2g6endbaV2C1F-k8QDLWdher5ZSf1eKbErE");
        userResponseDTO.setIsActive(Boolean.TRUE);
        userResponseDTO.setName("Prueba");
        userResponseDTO.setEmail("efpyi@example.com");
        userResponseDTO.setPassword("a2asfGfdfdf4");
        userResponseDTO.setPhones(Collections.singletonList(phoneDTO));
    }

    @Test
    void signUp() throws Exception {
        when(loginService.signUp(any())).thenReturn(userResponseDTO);

        ResponseEntity<UserResponseDTO> response = loginController.signUp(userRequestDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userResponseDTO.getEmail(), response.getBody().getEmail());
    }

    @Test
    void signUpUserExists() throws Exception {
        when(loginService.signUp(any())).thenThrow(new UserException("El correo ya está registrado"));

        UserException exception = assertThrows(UserException.class, () -> loginController.signUp(userRequestDTO));
        assertEquals(exception.getMessage(), "El correo ya está registrado");
    }
}