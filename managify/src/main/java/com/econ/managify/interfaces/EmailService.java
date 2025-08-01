package com.econ.managify.interfaces;


import com.econ.managify.exceptions.EmailException;

public interface EmailService {
    void sendEmailWithToken(String userEmail, String link) throws EmailException;
}
