package com.example.l2g.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.stereotype.Service;

@Service
public class FirebaseService {

    public FirebaseToken verifyToken(String idToken) {
        try {
            return FirebaseAuth.getInstance().verifyIdToken(idToken);
        } catch (FirebaseAuthException e) {
            throw new RuntimeException("Failed to verify Firebase token", e);
        }
    }

    public boolean validateUser(String idToken, String uid) {
        try {
            FirebaseToken decodedToken = verifyToken(idToken);
            return decodedToken.getUid().equals(uid);
        } catch (Exception e) {
            return false;
        }
    }
}