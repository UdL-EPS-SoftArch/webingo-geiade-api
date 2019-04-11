package cat.udl.eps.entsoftarch.webingogeiadeapi.handler;

import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Player;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterLinkSave;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeLinkSave;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class PlayerEventHandler {
    final Logger logger = LoggerFactory.getLogger(Player.class);

    @Autowired
    PlayerRepository playerRepository;

    @HandleBeforeCreate
    public void handlePlayerPreCreate(Player player) {
        logger.info("Before creating: {}", player.toString());
    }

    @HandleBeforeSave
    public void handlePlayerPreSave(Player player){
        logger.info("Before updating: {}", player.toString());
    }

    @HandleBeforeDelete
    public void handlePlayerPreDelete(Player player){
        logger.info("Before deleting: {}", player.toString());
    }

    @HandleBeforeLinkSave
    public void handlePlayerPreLinkSave(Player player, Object o) {
        logger.info("Before linking: {} to {}", player.toString(), o.toString());
    }

    @HandleAfterCreate
    public void handlePlayerPostCreate(Player player){
        logger.info("After creating: {}", player.toString());
        player.encodePassword();
        playerRepository.save(player);
    }

    @HandleAfterSave
    public void handlePlayerPostSave(Player player){
        logger.info("After updating: {}", player.toString());
        if (player.isPasswordEncoded() == false) {
            player.encodePassword();
            playerRepository.save(player);
        }
    }

    @HandleAfterDelete
    public void handlePlayerPostDelete(Player player){
        logger.info("After deleting: {}", player.toString());
    }

    @HandleAfterLinkSave
    public void handlePlayerPostLinkSave(Player player, Object o) {
        logger.info("After linking: {} to {}", player.toString(), o.toString());
    }
}
