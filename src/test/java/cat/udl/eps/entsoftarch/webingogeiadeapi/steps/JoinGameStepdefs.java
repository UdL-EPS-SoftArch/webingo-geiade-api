package cat.udl.eps.entsoftarch.webingogeiadeapi.steps;

import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Invitation;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Player;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Game;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Card;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.GameRepository;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.UserRepository;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.CardRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import static org.hamcrest.Matchers.is;
import cucumber.api.java.es.E;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.awt.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class JoinGameStepdefs {

    @Autowired
    private StepDefs stepDefs;
    private Invitation invitation;

    @Autowired
    UserRepository playerRepository;
    @Autowired
    GameRepository gameRepository;
    @Autowired
    CardRepository cardRepository;

    private double pricebefore;
    private int walletbefore;
    private int game_id;
    private int card_id;


    @And("^existing game with name \"([^\"]*)\"$")
    public void existingGameWithname(String name){
        Game joc = new Game();
        joc.setName(name);
        Game g = gameRepository.save(joc);
        this.game_id= g.getId();
        this.pricebefore = g.getBingoPrice();
    }


    @And("^the player \"([^\"]*)\" has more wallet than price (\\d+)$")
    public void thePlayerHasMoreWalletThanPrice(String email, int price) {
        Player p = (Player)playerRepository.findByEmail(email);
        p.setWallet(10);
        playerRepository.save(p);
        this.walletbefore = p.getWallet();
        assertThat(p.getWallet(), greaterThanOrEqualTo (price));
    }

    @And("^the player \"([^\"]*)\" has less money (\\d+)$")
    public void thePlayerHasLessMoney(String email, int price){
        Player p = (Player) playerRepository.findByEmail(email);
        p.setWallet(2);
        playerRepository.save(p);
        assertThat(p.getWallet(), lessThan (price));
    }

    @And("^the boolean of beingplaying was not activated for user \"([^\"]*)\"$")
    public void theBooleanOfBeingplayingWasNotActivatedForUser(String email) {
        Player p = (Player) playerRepository.findByEmail(email);
        p.setPlaying(false);
        playerRepository.save(p);
    }

    @And("^a new card has been created \"([^\"]*)\"$")
    public void aNewCardHasBeenCreated(String email) throws Throwable {
        Player p = (Player)playerRepository.findByEmail(email);
        Game g = gameRepository.findById(game_id);
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/cards/{id}", this.card_id)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.player",is(p.getUri())))
                .andExpect(jsonPath("$.game",is(g.getUri())));
    }

    @And("^the boolean of beingplaying has been activated \"([^\"]*)\"$")
    public void theBooleanOfBeingplayingHasBeenActivated(String email) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/players/{email}", email)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.isplaying",is(true)));
    }


    @And("^the jackpot has increased by (\\d+)$")
    public void theJackpotHasIncreasedBy(int price) throws Throwable{
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/games/{id}", this.game_id)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.bingoPrice",is(this.pricebefore+price)));
    }


    @And("^the wallet has decreased by (\\d+) for \"([^\"]*)\"$")
    public void theWalletHasDecreasedByFor(int price, String email) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/players/{email}", email)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.wallet",is(this.walletbefore+price)));
    }

    @And("^the boolean of beingplaying of player \"([^\"]*)\" was activated$")
    public void theBooleanOfBeingplayingOfPlayerWasActivated(String email) {
        Player p = (Player)playerRepository.findByEmail(email);
        p.setPlaying(true);
        playerRepository.save(p);
    }


    @When("^user \"([^\"]*)\" join to a game$")
    public void userJoinToAGame(String email) throws Throwable {
        Game g = gameRepository.findById(game_id);
        JSONObject card = new JSONObject();
        card.put("game", g.getUri());
        card.put("price",2);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(card.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        Player p = (Player) playerRepository.findByEmail(email);
        Card c = cardRepository.findByPlayer(p);
        this.card_id = c.getId();
        

    }
}
