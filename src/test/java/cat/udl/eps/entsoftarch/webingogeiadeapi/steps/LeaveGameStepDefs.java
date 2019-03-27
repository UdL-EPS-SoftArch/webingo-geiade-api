package cat.udl.eps.entsoftarch.webingogeiadeapi.steps;

import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Card;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Game;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Player;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.GameRepository;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.CardRepository;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.UserRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static cat.udl.eps.entsoftarch.webingogeiadeapi.steps.AuthenticationStepDefs.authenticate;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class LeaveGameStepDefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private UserRepository playerRepository;
    private Player player;

    @Autowired
    GameRepository gameRepository;
    private Game game1;

    @Autowired
    CardRepository cardRepository;
    private Card card1;

    @And("^Player \"([^\"]*)\" is in game \"([^\"]*)\" and playing with card (\\d+)$")
    public void playerIsInGameAndPlayingWithCard(String email, String game, int id_card) throws Throwable {
        //this.player = (Player) playerRepository.findByEmail(email);

        /*Player jugador = new Player();
        jugador.setEmail(email);
        jugador.setUsername("xxx");
        jugador.setPassword("1234");*/

        this.player = (Player) playerRepository.findByEmail(email);

        Game joc = new Game();
        joc.setName(game);
        this.game1 = gameRepository.save(joc);

        this.card1.setId(id_card);
        this.card1.setPlayer(this.player);
        this.card1.setGame(this.game1);
        cardRepository.save(this.card1);



    }

    /*@When("^I leave a game when I'm playing with username email \"([^\"]*)\"$")
    public void iLeaveAGameWhenIMPlayingWithUsernameEmail(String email) throws Throwable {
        this.player.setGame(new Game());
        this.game1 = this.player.getGame();
        //int id_game = game1.getId();

        stepDefs.result = stepDefs.mockMvc.perform(
                delete("/games/{id_game}", id_game)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(authenticate()));

    }*/


    @When("^I leave a game when I'm playing with Card (\\d+)$")
    public void iLeaveAGameWhenIMPlayingWithCard(int id_card) throws Throwable{

        stepDefs.result = stepDefs.mockMvc.perform(
                delete("/cards/{id_card}", id_card)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(authenticate()));
    }

   /* @And("^Card (\\d+) is removed$")
    public void isRemoved(int id_card) throws Exception {

        stepDefs.result = stepDefs.mockMvc.perform(
                delete("/cards/{id_card}", id_card)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(authenticate()));

        // esborrar associacio resource delete el subrecurs(5.4.1)

    }
    */


    //-------- T H E N ---------------
    
    /*@And("^It has been removed the game in the player with username \"([^\"]*)\"$")
    public void itHasBeenRemovedTheGameInThePlayerWithUsername(String email) throws Throwable {

        this.player = (Player) playerRepository.findByEmail(email);
        this.game1 = this.player.getGame();

        int id_game = this.game1.getId();

        stepDefs.result = stepDefs.mockMvc.perform(
                get("/games/{id_game}", id_game)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }*/

    @And("^It has been removed the game in the player with card (\\d+)$")
    public void itHasBeenRemovedTheGameInThePlayerWithCard(int id_card) throws Throwable{

        this.card1 = cardRepository.findById(id_card);

        stepDefs.result = stepDefs.mockMvc.perform(
                get("/cards/{id_card}", id_card)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }


    /*@And("^Card (\\d+) has been removed$")
    public void cardHasBeenRemoved(int id_card) throws Throwable {

        stepDefs.result = stepDefs.mockMvc.perform(
                get("/cards/{id_card}", id_card)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }*/

}
