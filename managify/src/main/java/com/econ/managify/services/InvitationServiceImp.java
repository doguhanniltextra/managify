package com.econ.managify.services;

import com.econ.managify.exceptions.InvatitionException;
import com.econ.managify.interfaces.EmailService;
import com.econ.managify.interfaces.InvitationService;
import com.econ.managify.models.Invitation;
import com.econ.managify.repositories.InvitationRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvitationServiceImp implements InvitationService {

    private final InvitationRepository invitationRepository;
    private final EmailService emailService;

    public InvitationServiceImp(InvitationRepository invitationRepository, EmailService emailService) {
        this.invitationRepository = invitationRepository;
        this.emailService = emailService;
    }

    @Override
    public void sendInvitation(String email, Long projectId) throws InvatitionException {
        String invitationToken = UUID.randomUUID().toString();
        Invitation invitation = new Invitation();
        invitation.setEmail(email);
        invitation.setProjectId(projectId);
        invitation.setToken(invitationToken);

        invitationRepository.save(invitation);

        String invitationLink="http://localhost:5173/accept_invitation?token="+invitationToken;
        try {
            emailService.sendEmailWithToken(email,invitationLink);
        } catch (Exception e) {
            throw new InvatitionException(e);
        }
    }

    @Override
    public Invitation acceptInvitation(String token, Long userId) throws InvatitionException {
        Invitation invitation = invitationRepository.findByToken(token);
        if(invitation == null) {throw new InvatitionException("Invalid invitation token");}
        return invitation;
    }

    @Override
    public String getTokenByUserMail(String userEmail) {
        Invitation invitation = invitationRepository.findByEmail(userEmail);
        return invitation.getToken();
    }

    @Override
    public void deleteToken(String token) {
        Invitation invitation = invitationRepository.findByToken(token);
        invitationRepository.delete(invitation);
    }
}
