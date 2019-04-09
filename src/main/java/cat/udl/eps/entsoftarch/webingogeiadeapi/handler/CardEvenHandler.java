package cat.udl.eps.entsoftarch.webingogeiadeapi.handler;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Game;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Card;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Card;

import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.AdminRepository;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.UserRepository;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.CardRepository;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class CardEvenHandler {
    @Autowired
    UserRepository playerRepository;

    @Autowired
    CardRepository cardRepository;

    @HandleAfterCreate
    public void handleCardPreCreate(Card card) {
        Player p = (Player)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Player p2 = (Player) playerRepository.findByEmail(p.getEmail());
        int moneyP= p2.getWallet();
        if (moneyP>card.getPrice() && p2.isPlaying()==false){
            p2.setWallet(moneyP-card.getPrice());
            p2.setPlaying(true);
            card.setPlayer(p2);
            card.setNums(card.randomcard());

            Game g = card.getGame();
            double moneyB = g.getBingoPrice();
            g.setBingoPrice(moneyB+card.getPrice());
            playerRepository.save(p2);
        }
        else{
            card=null;
        }
        cardRepository.save(card);
    }

}
