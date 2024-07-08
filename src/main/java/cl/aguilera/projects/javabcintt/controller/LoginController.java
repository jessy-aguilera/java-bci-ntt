package cl.aguilera.projects.javabcintt.controller;

import cl.aguilera.projects.javabcintt.dto.UserRequestDTO;
import cl.aguilera.projects.javabcintt.dto.UserResponseDTO;
import cl.aguilera.projects.javabcintt.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
@Slf4j
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserResponseDTO> signUp(@Valid @RequestBody UserRequestDTO user) throws Exception {
        log.info("signUp: " + user);

        return new ResponseEntity<>(loginService.signUp(user), HttpStatus.CREATED);
    }
}
