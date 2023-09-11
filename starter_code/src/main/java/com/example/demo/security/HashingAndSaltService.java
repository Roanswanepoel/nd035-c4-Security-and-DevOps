package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Service
public class HashingAndSaltService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public HashingAndSaltService() {
    }

    public byte[] createSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public String get_SecurePassword(String passwordToHash, byte[] salt) {
        String SaltPassword = passwordToHash + salt;
        return bCryptPasswordEncoder.encode(SaltPassword);
    }
}
