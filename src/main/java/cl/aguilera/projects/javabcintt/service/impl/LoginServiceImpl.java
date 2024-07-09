package cl.aguilera.projects.javabcintt.service.impl;

import cl.aguilera.projects.javabcintt.dto.PhoneDTO;
import cl.aguilera.projects.javabcintt.dto.UserRequestDTO;
import cl.aguilera.projects.javabcintt.dto.UserResponseDTO;
import cl.aguilera.projects.javabcintt.model.Phone;
import cl.aguilera.projects.javabcintt.model.User;
import cl.aguilera.projects.javabcintt.repository.PhoneRepository;
import cl.aguilera.projects.javabcintt.repository.UserRepository;
import cl.aguilera.projects.javabcintt.service.LoginService;
import cl.aguilera.projects.javabcintt.util.LoginUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private PhoneRepository phoneRepository;

    public LoginServiceImpl(UserRepository userRepository,
                            ModelMapper modelMapper,
                            PhoneRepository phoneRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.phoneRepository = phoneRepository;
    }

    @Override
    public UserResponseDTO signUp(UserRequestDTO user) throws Exception {

        User userEntity = modelMapper.map(user, User.class);
        LoginUtil.setUserDate(userEntity);

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
}
