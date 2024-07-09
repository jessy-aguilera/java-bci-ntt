package cl.aguilera.projects.javabcintt.impl;

import cl.aguilera.projects.javabcintt.dto.PhoneDTO;
import cl.aguilera.projects.javabcintt.dto.UserRequestDTO;
import cl.aguilera.projects.javabcintt.dto.UserResponseDTO;
import cl.aguilera.projects.javabcintt.exception.UserException;
import cl.aguilera.projects.javabcintt.model.Phone;
import cl.aguilera.projects.javabcintt.model.User;
import cl.aguilera.projects.javabcintt.repository.PhoneRepository;
import cl.aguilera.projects.javabcintt.repository.UserRepository;
import cl.aguilera.projects.javabcintt.service.impl.LoginServiceImpl;
import cl.aguilera.projects.javabcintt.util.JwtUtil;
import cl.aguilera.projects.javabcintt.util.PasswordEncryption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class LoginServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PhoneRepository phoneRepository;

    @InjectMocks
    private LoginServiceImpl loginService;

    private UserRequestDTO userRequestDTO;

    private User user;

    private Phone phone;

    private PhoneDTO phoneDTO;

    private UserResponseDTO userResponseDTO;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setEmail("efpyi@example.com");
        userRequestDTO.setPassword("a2asfGfdfdf4");
        userRequestDTO.setName("Prueba");

        phoneDTO = new PhoneDTO();
        phoneDTO.setNumber("1234567890");
        phoneDTO.setCitycode("9");
        phoneDTO.setCountrycode("+56");
        userRequestDTO.setPhones(Collections.singletonList(phoneDTO));

        user = new User();
        user.setId("e1f136f0-b92d-4564-bd9d-25f12299a92e");
        user.setCreated(new Date());
        user.setModified(new Date());
        user.setLastLogin(new Date());
        user.setToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhc2RzZEBnbWFpbC5jb20iLCJleHAiOjE3MDE2MjkzNjJ9.k3JGost_2g6endbaV2C1F-k8QDLWdher5ZSf1eKbErE");
        user.setIsActive(Boolean.TRUE);
        user.setName("Prueba");
        user.setEmail("efpyi@example.com");
        user.setPassword("a2asfGfdfdf4");

        phone = new Phone();
        phone.setId("e1f");
        phone.setNumber("1234567890");
        phone.setCitycode("9");
        phone.setCountrycode("+56");
        phone.setUser(user);

        userResponseDTO = new UserResponseDTO();
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setId(user.getId());
        userResponseDTO.setCreated(user.getCreated());
        userResponseDTO.setLastLogin(user.getLastLogin());
        userResponseDTO.setIsActive(user.getIsActive());
        userResponseDTO.setName(user.getName());
        userResponseDTO.setToken(user.getToken());
        userResponseDTO.setPhones(Collections.singletonList(phoneDTO));
        userResponseDTO.setPassword(user.getPassword());
    }

    @Test
    void signUpOK() throws Exception {
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(modelMapper.map(eq(userRequestDTO), eq(User.class))).thenReturn(user);
        when(userRepository.save(any())).thenReturn(user);
        when(modelMapper.map(eq(user), eq(UserResponseDTO.class))).thenReturn(userResponseDTO);
        when(modelMapper.map(eq(phoneDTO), eq(Phone.class))).thenReturn(phone);
        when(phoneRepository.save(any())).thenReturn(phone);

        UserResponseDTO userResponseDTO = loginService.signUp(userRequestDTO);

        assertEquals(userResponseDTO.getEmail(), userRequestDTO.getEmail());
        assertEquals(userResponseDTO.getPassword(), userRequestDTO.getPassword());
        assertEquals(userResponseDTO.getId(), phone.getUser().getId());
        assertTrue(userResponseDTO.getIsActive());
    }

    @Test
    void signUpWithoutNameAndPhones() throws Exception {
        userRequestDTO.setName(null);
        userRequestDTO.setPhones(null);
        user.setName(null);
        userResponseDTO.setPhones(null);
        userResponseDTO.setName(null);
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(modelMapper.map(eq(userRequestDTO), eq(User.class))).thenReturn(user);
        when(userRepository.save(any())).thenReturn(user);
        when(modelMapper.map(eq(user), eq(UserResponseDTO.class))).thenReturn(userResponseDTO);

        UserResponseDTO userResponseDTO = loginService.signUp(userRequestDTO);

        assertEquals(userResponseDTO.getEmail(), userRequestDTO.getEmail());
        assertEquals(userResponseDTO.getPassword(), userRequestDTO.getPassword());
        assertNull(userResponseDTO.getName());
        assertNull(userResponseDTO.getPhones());
    }

    @Test
    void signUpWithoutPhones() throws Exception {
        userRequestDTO.setPhones(new ArrayList<>());
        userResponseDTO.setPhones(new ArrayList<>());
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(modelMapper.map(eq(userRequestDTO), eq(User.class))).thenReturn(user);
        when(userRepository.save(any())).thenReturn(user);
        when(modelMapper.map(eq(user), eq(UserResponseDTO.class))).thenReturn(userResponseDTO);

        UserResponseDTO userResponseDTO = loginService.signUp(userRequestDTO);

        assertEquals(userResponseDTO.getEmail(), userRequestDTO.getEmail());
        assertEquals(userResponseDTO.getPassword(), userRequestDTO.getPassword());
        assertTrue(userResponseDTO.getPhones().isEmpty());
    }

    @Test
    void signUpUserExists() {
        when(modelMapper.map(eq(userRequestDTO), eq(User.class))).thenReturn(user);
        when(userRepository.save(any())).thenThrow(new DataIntegrityViolationException("El correo ya está registrado"));

        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> loginService.signUp(userRequestDTO));
        assertEquals(exception.getMessage(), "El correo ya está registrado");
    }
}