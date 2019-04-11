package cat.udl.eps.entsoftarch.webingogeiadeapi.handler;

import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Game;
import cat.udl.eps.entsoftarch.webingogeiadeapi.handler.exception.GameException;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class GameEventHandler {
    final Logger logger = LoggerFactory.getLogger(Game.class);

    @Autowired
    GameRepository gameRepository;

    @HandleBeforeCreate
    public void handleGamerPreCreate(Game game) {
        logger.info("Before creating: {}", game.toString());
    }

    @HandleBeforeSave
    public void handleGamePreSave(Game game){
        logger.info("Before updating: {}", game.toString());
    }

    @HandleBeforeDelete
    public void handleGamePreDelete(Game game){
        logger.info("Before deleting: {}", game.toString());
    }

    @HandleBeforeLinkSave
    public void handleGamePreLinkSave(Game game, Object o) {
        logger.info("Before linking: {} to {}", game.toString(), o.toString());
    }

    @HandleAfterCreate
    public void handleGamePostCreate(Game game){
        logger.info("After creating: {}", game.toString());
    }

    @HandleAfterSave
    public void handleGamePostSave(Game game){
        logger.info("After updating: {}", game.toString());
        if (game.isFinished()) {
            if ((game.getLineWinner() == null) || (game.getBingoWinner() == null) ) {
                throw new GameException("Game has finished without a line or bingo winner");
            }
        }
    }

    @HandleAfterDelete
    public void handleGamePostDelete(Game game){
        logger.info("After deleting: {}", game.toString());
    }

    @HandleAfterLinkSave
    public void handleGamePostLinkSave(Game game, Object o) {
        logger.info("After linking: {} to {}", game.toString(), o.toString());
    }
}
