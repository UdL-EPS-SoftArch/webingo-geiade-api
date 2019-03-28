package cat.udl.eps.entsoftarch.webingogeiadeapi.handler;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Game;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Player;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Card;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Card;

import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.AdminRepository;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class CardEvenHandler {
    @HandleAfterCreate
    public void handleCardPreCreate(Card card) {
        Player p = (Player)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int moneyP= p.getWallet();
        p.setWallet(moneyP-card.getPrice());
        p.setPlaying(true);
        card.setPlayer(p);
        card.setNums(card.randomcard());

        Game g = card.getGame();
        double moneyB = g.getBingoPrice();
        g.setBingoPrice(moneyB+card.getPrice());
    }

}
