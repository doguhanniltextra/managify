package com.econ.managify.interfaces;

import com.econ.managify.exceptions.InvatitionException;
import com.econ.managify.models.Invitation;

import java.util.UUID;

public interface InvitationService {

    public void sendInvitation(String email, UUID projectId) throws InvatitionException;
    public Invitation acceptInvitation(String token, Long userId) throws InvatitionException;
    public String getTokenByUserMail(String userEmail);
    void deleteToken(String token);
}
