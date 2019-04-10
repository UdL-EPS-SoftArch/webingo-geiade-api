package cat.udl.eps.entsoftarch.webingogeiadeapi.handler;

import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Player;
import cat.udl.eps.entsoftarch.webingogeiadeapi.handler.exception.DepositMoneyException;
import cat.udl.eps.entsoftarch.webingogeiadeapi.handler.exception.NotAuthorizedException;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.PlayerRepository;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.UserRepository;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class PlayerEventHandler {
    final Logger logger = LoggerFactory.getLogger(Player.class);

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    UserRepository userRepository;

    @HandleBeforeCreate
    public void handlePlayerPreCreate(Player player) {
        logger.info("Before creating: {}", player.toString());
    }

    @HandleBeforeSave
    public void handlePlayerPreSave(Player player) throws DepositMoneyException, NotAuthorizedException {

        logger.info("Before updating: {}", player.toString());
        // TEST IF WHO IS MAKING THE DEPOSIT IS THE OWNER OF THE WALLET
        Player p = (Player) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (p.getUsername().equals(player.getUsername()) == false) {
            throw new NotAuthorizedException("User not authorized to do this");
        }

        if (player.getToWallet() != 0) {
            int wallet = player.getWallet();
            int value = player.getToWallet();
            if (value < 5) {
                throw new DepositMoneyException("Not enought money");
            }
            player.setWallet(wallet + value);
            player.setToWallet(0);
            System.out.println(player.toString());
        }
        else if (player.getFromWallet() != 0) {
            //fer la comprovacio dels minims i maxims a retirar!!!!
        }

        // playerRepository.save(player);

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
