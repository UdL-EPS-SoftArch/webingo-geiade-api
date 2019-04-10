package cat.udl.eps.entsoftarch.webingogeiadeapi.handler;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.*;
import cat.udl.eps.entsoftarch.webingogeiadeapi.handler.exception.InvitationCreateException;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.InvitationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.*;

@Component
@RepositoryEventHandler
public class InvitationEventHandler {
    final Logger logger = LoggerFactory.getLogger(Invitation.class);

    @Autowired
    InvitationRepository invitationRepository;

    @HandleBeforeCreate
    public void handleInvitationPreCreate(Invitation invitation) {
        logger.info("Before creating: {}", invitation.toString());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Player player = (Player) authentication.getPrincipal();

        if (invitationRepository.count() > 0) {
            throw new InvitationCreateException("You have already invited this ");
        }

    }

    @HandleBeforeSave
    public void handleInvitationPreSave(Invitation invitation){
        logger.info("Before updating: {}", invitation.toString());
    }

    @HandleBeforeDelete
    public void handleInvitationPreDelete(Invitation invitation){
        logger.info("Before deleting: {}", invitation.toString());
    }

    @HandleBeforeLinkSave
    public void handleInvitationPreLinkSave(Invitation invitation, Object o) {
        logger.info("Before linking: {} to {}", invitation.toString(), o.toString());
    }

    @HandleAfterCreate
    public void handleInvitationPostCreate(Invitation invitation){
        logger.info("After creating: {}", invitation.toString());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Player player = (Player) authentication.getPrincipal();
        invitation.setPlayer_who_invited(player);

        if (invitation.isUnderway())
            throw new InvitationCreateException("The invitation is already sent or accepted");
        else if (invitation.isAccepted())
            throw new InvitationCreateException("The invitation is already accepted");
        else if (invitation.isTimeout())
            throw new InvitationCreateException("The invitation has reached the timeout for being accepted");
    }

    @HandleAfterSave
    public void handleInvitationPostSave(Invitation invitation){
        logger.info("After updating: {}", invitation.toString());
    }

    @HandleAfterDelete
    public void handleInvitationPostDelete(Invitation invitation){
        logger.info("After deleting: {}", invitation.toString());
    }

    @HandleAfterLinkSave
    public void handleInvitationPostLinkSave(Invitation invitation, Object o) {
        logger.info("After linking: {} to {}", invitation.toString(), o.toString());
    }
}
