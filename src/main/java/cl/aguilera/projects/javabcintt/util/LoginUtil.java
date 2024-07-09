package cl.aguilera.projects.javabcintt.util;

import cl.aguilera.projects.javabcintt.model.User;

import java.util.Date;

public class LoginUtil {

    public static void setUserDate(User user) throws Exception {
        user.setCreated(new Date());
        user.setModified(new Date());
        user.setLastLogin(new Date());
        user.setToken(JwtUtil.generateToken(user));
        user.setIsActive(true);
        user.setPassword(PasswordEncryption.encrypt(user.getPassword()));
    }
}
