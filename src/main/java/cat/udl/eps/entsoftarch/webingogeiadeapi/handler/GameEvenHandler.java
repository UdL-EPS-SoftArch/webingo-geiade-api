package cat.udl.eps.entsoftarch.webingogeiadeapi.handler;

import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Card;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Game;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Player;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.CardRepository;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
@RepositoryEventHandler
public class GameEvenHandler {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    GameRepository gameRepository;

    public int contL=0;
    public int contB=0;
    @HandleBeforeSave
    public void handleGamePreCreate(Game game) {
        if (game.getBingoWinner()!=null){
            Boolean real = true;
            Player p = game.getBingoWinner();
            Card c = cardRepository.findByPlayer(p);
            int [] gamesN = game.getNums();
            int [][] playerN = c.getNums();

            for (int i=0; i<3; i++) {
                if (numerosDeLaLineaEstanDits(playerN[i],gamesN)==false){
                    real = false;
                }
            }
            if (real == false){
                game.setBingoWinner(null);
            }

            gameRepository.save(game);
        }

        else if(game.getLineWinner()!=null){
            Boolean real = false;
            Player p = game.getBingoWinner();
            Card c = cardRepository.findByPlayer(p);
            int [] gamesN = game.getNums();
            int [][] playerN = c.getNums();
            for (int i=0; i<3; i++) {
                if (numerosDeLaLineaEstanDits(playerN[i],gamesN)==true){
                    real = true;
                }
            }
            if (real == false){
                game.setLineWinner(null);
            }
            gameRepository.save(game);

        }
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
