package com.example.springSecurity.Repositories.RepositoryUsers;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Data
public class VerificationCodeStorage {
    private final Map<String, String> storage = new HashMap<>();

    public void storeCode(String rut, String token) {
        System.out.println("Storing code. Rut: " + rut + ", Token: " + token); // Debugging output
        storage.put(rut, token);
    }

    public Map<String, String> getStorage() {
        return storage;
    }
}
