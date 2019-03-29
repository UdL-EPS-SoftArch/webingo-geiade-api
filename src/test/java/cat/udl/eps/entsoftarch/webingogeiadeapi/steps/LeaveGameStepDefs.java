package cat.udl.eps.entsoftarch.webingogeiadeapi.steps;

import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Card;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Game;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Player;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.GameRepository;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.CardRepository;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
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
    private Player player1;

    @Autowired
    GameRepository gameRepository;
    private Game game1;

    @Autowired
    CardRepository cardRepository;
    private Card card1;

    @Given("^Player \"([^\"]*)\" is in game \"([^\"]*)\" and playing with card (\\d+)$")
    public void playerIsInGameAndPlayingWithCard(String email, String game, int id_card) throws Throwable {

        this.player1 = new Player();
        this.game1 = new Game();
        this.card1 = new Card();

        this.player1 = (Player) playerRepository.findByEmail(email);

        this.game1.setName(game);
        gameRepository.save(this.game1);

        this.card1.setId(id_card);
        this.card1.setPlayer(this.player1);
        this.card1.setGame(this.game1);
        cardRepository.save(this.card1);

        String card_created = stepDefs.mapper.writeValueAsString(this.card1);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(card_created)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

    }

    @When("^I leave a game when I'm playing with a Card")
    public void iLeaveAGameWhenIMPlayingWithCard() throws Throwable{

        stepDefs.result = stepDefs.mockMvc.perform(
                delete("/cards/{id_card}", this.card1.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(authenticate()))
                .andExpect(status().isNoContent());
    }



    //-------- T H E N ---------------

    @And("^It has been removed the game in the player with card (\\d+)$")
    public void itHasBeenRemovedTheGameInThePlayerWithCard(int id_card) throws Throwable{

        this.card1 = cardRepository.findById(id_card);

        stepDefs.result = stepDefs.mockMvc.perform(
                get("/cards/{id_card}", id_card)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }


    // SCENARIO 2

    @Given("^There is card (\\d+) in the game \"([^\"]*)\" without a player$")
    public void thereIsCardInTheGameWithoutAPlayer(int id_card, String game) throws Throwable {

        this.player1 = new Player();
        this.game1 = new Game();
        this.card1 = new Card();

        this.game1.setName(game);
        gameRepository.save(this.game1);

        this.card1.setId(id_card);
        this.card1.setGame(this.game1);
        cardRepository.save(this.card1);

        String card_created = stepDefs.mapper.writeValueAsString(this.card1);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(card_created)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }


    @When("^I leave a game when I'm not playing$")
    public void iLeaveAGameWhenIMNotPlaying() throws Throwable {

        stepDefs.result = stepDefs.mockMvc.perform(
                delete("/cards/player/{id_player}", this.player1.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(authenticate()))
                .andExpect(status().isUnauthorized());

    }


    @And("^The system throws an error$")
    public void theSystemThrowsAnError() {

    }
}