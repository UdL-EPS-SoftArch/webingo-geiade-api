package cat.udl.eps.entsoftarch.webingogeiadeapi.handler;

import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Card;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Player;
import cat.udl.eps.entsoftarch.webingogeiadeapi.handler.exception.LeaveGameException;
import cat.udl.eps.entsoftarch.webingogeiadeapi.handler.exception.NotAuthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class CardEventHandler {
    final Logger logger = LoggerFactory.getLogger(Card.class);

    @HandleBeforeDelete
    public void handleCardPreDelete(Card card) throws LeaveGameException, NotAuthorizedException {
        logger.info("Before deleting: {}", card.toString());

        Player p = (Player) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!card.getPlayer().getUsername().equals(p.getUsername())) {
            throw new NotAuthorizedException("Can't leave if it's not your card ");
        }
        if (card.getPlayer() == null){
            throw new LeaveGameException("No game to leave");
        }


    }


}
