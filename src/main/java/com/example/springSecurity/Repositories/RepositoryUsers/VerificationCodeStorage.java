package com.example.springSecurity.Repositories.RepositoryUsers;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class VerificationCodeStorage {
    private final Map<String, String> storage = new HashMap<>();

    public void storeCode(String token, String code) {
        storage.put(token, code);
    }

    public String retrieveCode(String token) {
        return storage.get(token);
    }
}
