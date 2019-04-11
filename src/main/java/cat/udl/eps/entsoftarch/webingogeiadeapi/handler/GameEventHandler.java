package cat.udl.eps.entsoftarch.webingogeiadeapi.handler;

import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Card;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Game;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Player;
import cat.udl.eps.entsoftarch.webingogeiadeapi.handler.exceptions.ShowResultException;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.CardRepository;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
@RepositoryEventHandler
public class GameEventHandler {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    GameRepository gameRepository;

    public int contL=0;
    public int contB=0;
    @HandleAfterSave
    public void handleGamePreSave(Game game) throws ShowResultException{
        System.out.println("Dins handler!!!!!!!!!!!!!");
        System.out.println(game);
        if (game.getBingoWinner()!=null){
            Boolean real = true;
            Player p = game.getBingoWinner();
            System.out.println("game");
            System.out.println(game);
            Card c = cardRepository.findByPlayer(p);
            int [] gamesN = game.getNums();
            System.out.println("persona");
            System.out.println(p);
            System.out.println("carta");
            System.out.println(c);
            System.out.println("FORA HANDLER");
            if (gamesN == null){
                throw new ShowResultException("The game does not have any number yet! It is not posible to sing Bingo");
            }
            int [][] playerN = c.getNums();

            for (int i=0; i<3; i++) {
                if (numerosDeLaLineaEstanDits(playerN[i],gamesN)==false){
                    real = false;
                }
            }
            if (real == false){
                throw new ShowResultException("The player does not have all the bingo numbers!!");
            }
            gameRepository.save(game);
        }

        else if(game.getLineWinner()!=null){
            Boolean real = false;
            Player p = game.getBingoWinner();
            Card c = cardRepository.findByPlayer(p);
            int [] gamesN = game.getNums();
            int [][] playerN = c.getNums();
            if (gamesN == null){
                throw new ShowResultException("The game does not have any number yet! It is not posible to sing line");
            }
            for (int i=0; i<3; i++) {
                if (numerosDeLaLineaEstanDits(playerN[i],gamesN)==true){
                    real = true;
                }
            }
            if (real == false){
                throw new ShowResultException("The player does not have all the line numbers!!");
            }


        }
        gameRepository.save(game);
    }

    public boolean numerosDeLaLineaEstanDits(int [] playerN, int [] gameN){
        for (int j = 0; j < playerN.length; j++) {
            Boolean trobat = false;
            for (int z = 0; z <gameN.length;z++){
                if (playerN[j] == gameN[z]){
                    trobat = true;
                }
            }
            if (trobat == false){
                return false;
            }

        }
        return true;
    }

}
