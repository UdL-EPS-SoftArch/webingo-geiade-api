package cat.udl.eps.entsoftarch.webingogeiadeapi.handler;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.*;
import cat.udl.eps.entsoftarch.webingogeiadeapi.handler.exception.InvitationException;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.InvitationRepository;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Component
@RepositoryEventHandler
public class InvitationEventHandler {
    final Logger logger = LoggerFactory.getLogger(Invitation.class);

    @Autowired
    InvitationRepository invitationRepository;

    @Autowired
    PlayerRepository playerRepository;

    @HandleBeforeCreate
    public void handleInvitationPreCreate(Invitation invitation)  {
        logger.info("Before creating: {}", invitation.toString());

        if (invitationRepository.findByPlayerInvited(invitation.getPlayerInvited()).isPresent()) {
            throw new InvitationException("You have already invited this player to this game ");
        }

    }

    @HandleAfterCreate
    public void handleInvitationPostCreate(Invitation invitation) throws InvitationException{
        logger.info("After creating: {}", invitation.toString());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Player player_playing = (Player) authentication.getPrincipal();
        invitation.setPlayerWhoInvited(player_playing);

        if (invitation.isUnderway())
            throw new InvitationException("The invitation is already sent or accepted");
        else if (invitation.isAccepted())
            throw new InvitationException("The invitation is already accepted");
        else if (invitation.isTimeout())
            throw new InvitationException("The invitation has reached the timeout for being accepted");

        invitationRepository.save(invitation);
    }

    @HandleBeforeSave
    public void handleInvitationPostSave(Invitation invitation) throws InvitationException{
        logger.info("After updating: {}", invitation.toString());
        // invitationRepository.save(invitation);
        if (invitation.isTimeout())
            throw new InvitationException("The invitation has reached the timeout");
        else if (invitation.isUnderway())
            throw new InvitationException("The game of that invitation has already begun");
        else if (!invitation.isAccepted())
            throw new InvitationException("The invitation is not accepted from the user");
    }

}
