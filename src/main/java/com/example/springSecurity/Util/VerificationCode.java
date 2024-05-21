package com.example.springSecurity.Util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class VerificationCode {

        public String verificationCode() {
            String nuevoCodigo = obtenerCodigoVerificacion(2, 5);
            return nuevoCodigo;
        }

        public String obtenerCodigoVerificacion(int alfLength, int numLength) {
            String alfabeto = generarCodigoVerificacion(alfLength, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            String numeros = generarCodigoVerificacion(numLength, "0123456789");
            return alfabeto + numeros;
        }

        public String generarCodigoVerificacion(int length, String cadena) {
            SecureRandom r = new SecureRandom();
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < length; i++) {
                int posicion = r.nextInt(cadena.length());
                char caracter = cadena.charAt(posicion);
                result.append(caracter);
            }
            return result.toString();
        }
}

