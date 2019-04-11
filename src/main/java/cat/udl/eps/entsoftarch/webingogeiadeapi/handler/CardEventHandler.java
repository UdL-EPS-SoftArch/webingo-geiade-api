package cat.udl.eps.entsoftarch.webingogeiadeapi.handler;

import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Card;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Player;
import cat.udl.eps.entsoftarch.webingogeiadeapi.handler.exception.ActionForbiddenException;
import cat.udl.eps.entsoftarch.webingogeiadeapi.handler.exception.LeaveGameException;
import cat.udl.eps.entsoftarch.webingogeiadeapi.handler.exception.NotAuthorizedException;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.CardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class CardEventHandler {
    final Logger logger = LoggerFactory.getLogger(Card.class);

    @Autowired
    private CardRepository cardRepository;


    @HandleBeforeCreate
    public void handleCardPreCreate(Card card)throws ActionForbiddenException{
        System.out.println("dins handler");
        System.out.println(card);

        logger.info("After creating: {}", card.toString());
        if (card.getPlayer() == null || card.getGame() == null ){
            throw new ActionForbiddenException("Error while creating card");
        }
        cardRepository.save(card);
    }

    @HandleAfterSave
    public void handleCardPostSave(Card card)throws ActionForbiddenException{
        System.out.println("After save");

        System.out.println(card);
        cardRepository.save(card);
    }

    @HandleBeforeDelete
    public void handleCardPreDelete(Card card) throws LeaveGameException, NotAuthorizedException {
        logger.info("Before deleting: {}", card.toString());
        System.out.println("-.-.-.-.-.-.-.-");
        System.out.println(card.getPlayer());

        Player p = (Player) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Card repoCard = this.cardRepository.findById(card.getId());
        System.out.println(repoCard);

        if (!repoCard.getPlayer().getUsername().equals(p.getUsername())) {
            throw new NotAuthorizedException("Can't leave if it's not your card ");
        }
        if (repoCard.getPlayer() == null){
            throw new LeaveGameException("No game to leave");
        }


    }


}
