package com.example.studious;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Hash {
    private MessageDigest md;
    private String generatedHash;


    public void hashPassword(String password) {
        try {
            // Creating a MessageDigest Instance
            md = MessageDigest.getInstance("MD5");

            // Encode the password into an array of bytes using getBytes(), and update the digest
            md.update(password.getBytes());

            // Completes the hash computation by adding padding to the end
            byte[] bytes = md.digest();

            // Convert bytes in decimal format to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            // Assign hashed password in hex format
            this.generatedHash = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.toString());
        }  
    }


    public String getHashedPassword() {
        return this.generatedHash;
    }
}
