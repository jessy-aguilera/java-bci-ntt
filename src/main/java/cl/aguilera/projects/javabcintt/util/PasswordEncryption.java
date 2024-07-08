package cl.aguilera.projects.javabcintt.util;

import at.favre.lib.crypto.bcrypt.BCrypt;
import cl.aguilera.projects.javabcintt.exception.PasswordEncryptionException;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncryption {

    public String encrypt(String strToEncrypt) throws Exception {
        try {
            return BCrypt.withDefaults().hashToString(12, strToEncrypt.toCharArray());
        } catch (Exception e) {
            throw new PasswordEncryptionException("Error al encriptar: " + e);
        }
    }
}
