package cat.udl.eps.entsoftarch.webingogeiadeapi.handler;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Game;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Player;
import cat.udl.eps.entsoftarch.webingogeiadeapi.handler.exceptions.JoinGameException;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.CardRepository;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.UserRepository;
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
    public void handleCardPreCreate(Card card) throws JoinGameException{
        Player p = (Player)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Player p2 = (Player) playerRepository.findByEmail(p.getEmail());
        int moneyP= p2.getWallet();
        if (moneyP>card.getPrice() && !p2.isPlaying()){
            p2.setWallet(moneyP-card.getPrice());
            p2.setPlaying(true);
            card.setPlayer(p2);
            card.setNums(card.randomcard());

            Game g = card.getGame();
            double moneyB = g.getBingoPrize();
            g.setBingoPrize(moneyB+card.getPrice());
            playerRepository.save(p2);
            //cardRepository.save(card);
        }

        else if (card.getGame()==null){
            throw new JoinGameException("Game does not exists");
        }
        else if (p2.isPlaying()==true){
            throw new JoinGameException("The player was already playing");
        }
        else if (moneyP<card.getPrice()){
            throw new JoinGameException("The player does not have enough money");
        }
    }

}
