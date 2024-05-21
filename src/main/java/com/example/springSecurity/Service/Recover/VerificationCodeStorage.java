package com.example.springSecurity.Service.Recover;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class VerificationCodeStorage {
    private final ConcurrentHashMap<String, String> codeStorage = new ConcurrentHashMap<>();

    // Almacena el código asociado con el token
    public void storeCode(String token, String code) {
        codeStorage.put(token, code);
    }

    // Recupera el código almacenado por token
    public String retrieveCode(String token) {
        return codeStorage.get(token);
    }

    // Elimina el código almacenado por token (opcional, para limpieza)
    public void removeCode(String token) {
        codeStorage.remove(token);
    }
}
