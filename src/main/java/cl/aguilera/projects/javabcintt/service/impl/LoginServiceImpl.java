package cl.aguilera.projects.javabcintt.service.impl;

import cl.aguilera.projects.javabcintt.dto.PhoneDTO;
import cl.aguilera.projects.javabcintt.dto.UserRequestDTO;
import cl.aguilera.projects.javabcintt.dto.UserResponseDTO;
import cl.aguilera.projects.javabcintt.exception.UserException;
import cl.aguilera.projects.javabcintt.model.Phone;
import cl.aguilera.projects.javabcintt.model.User;
import cl.aguilera.projects.javabcintt.repository.PhoneRepository;
import cl.aguilera.projects.javabcintt.repository.UserRepository;
import cl.aguilera.projects.javabcintt.service.LoginService;
import cl.aguilera.projects.javabcintt.util.JwtUtil;
import cl.aguilera.projects.javabcintt.util.PasswordEncryption;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    private UserRepository userRepository;
    private JwtUtil jwtUtil;
    private PasswordEncryption passwordEncryption;
    private ModelMapper modelMapper;
    private PhoneRepository phoneRepository;

    public LoginServiceImpl(UserRepository userRepository, JwtUtil jwtUtil,
                            PasswordEncryption passwordEncryption, ModelMapper modelMapper,
                            PhoneRepository phoneRepository) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncryption = passwordEncryption;
        this.modelMapper = modelMapper;
        this.phoneRepository = phoneRepository;
    }

    @Override
    public UserResponseDTO signUp(UserRequestDTO user) throws Exception {
        this.validateUser(user.getEmail());

        User userEntity = modelMapper.map(user, User.class);
        userEntity.setCreated(new Date());
        userEntity.setModified(new Date());
        userEntity.setLastLogin(new Date());
        userEntity.setToken(jwtUtil.generateToken(userEntity));
        userEntity.setIsActive(true);
        userEntity.setPassword(passwordEncryption.encrypt(userEntity.getPassword()));

        UserResponseDTO userResponseDTO = modelMapper.map(userRepository.save(userEntity), UserResponseDTO.class);
        userResponseDTO.setPhones(
                savePhones(userEntity, user.getPhones()));

        return userResponseDTO;
    }

    private List<PhoneDTO> savePhones(User user, List<PhoneDTO> phonesDTO) {
        if (phonesDTO != null && !phonesDTO.isEmpty()) {
            phonesDTO.forEach(p -> {
                Phone phone = modelMapper.map(p, Phone.class);
                phone.setUser(user);
                phoneRepository.save(phone);
            });
        }
        return phonesDTO;
    }

    private void validateUser(String email) throws UserException {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            throw new UserException("El correo ya está registrado");
        }
    }
}
