package cat.udl.eps.entsoftarch.webingogeiadeapi.steps;

import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Card;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Game;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Player;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.GameRepository;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.CardRepository;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.UserRepository;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.json.JSONObject;
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
        this.player1 = (Player) playerRepository.findByEmail(email);

        this.game1 = new Game();
        this.game1.setName(game);
        gameRepository.save(this.game1);

        this.card1 = new Card();
        this.card1.setId(id_card);
        this.card1.setPlayer(this.player1);
        this.card1.setGame(this.game1);
        cardRepository.save(this.card1);

        /*JSONObject card = new JSONObject();
        card.put("id", id_card);
        card.put("game", this.game1);
        card.put("player", this.player1);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.card1.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());*/

        stepDefs.result = stepDefs.mockMvc.perform(
                get("/cards/{id_card}", this.card1.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(authenticate()))
                .andExpect(status().isOk());


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

        stepDefs.result = stepDefs.mockMvc.perform(
                get("/cards/{id_card}", id_card)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }


    // SCENARIO 2

    @When("^I want to leave a game with a card of another player and is not associated with the logged player\\.$")
    public void iWantToLeaveAGameWithACardOfAnotherPlayerAndIsNotAssociatedWithTheLoggedPlayer() throws Throwable{

        stepDefs.result = stepDefs.mockMvc.perform(
                delete("/cards/{id_card}", this.card1.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(authenticate()))
                .andExpect(status().isUnauthorized());
    }
}