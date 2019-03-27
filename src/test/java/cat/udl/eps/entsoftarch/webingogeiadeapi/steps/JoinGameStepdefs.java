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
    GameRepository gameRepository;
    CardRepository cardRepository;

    private Player player;
    private Game game;
    private double pricebefore;
    private int walletbefore;


    @When("^I join to a game with name \"([^\"]*)\"$")
    public void iJoinToAGameWithName(String name) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/games/{name}", name)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
                //.andExpect(status().isOk);
        
    }

    @And("^existing game with name \"([^\"]*)\"$")
    public void existingGameWithname(String name) throws Throwable {
        Game joc = new Game();
        joc.setName(name);
        this.game = gameRepository.save(joc);
    }

    @And("^the player \"([^\"]*)\" has more wallet than price$")
    public void thePlayerHasMoreWalletThanPrice(String email, int price) throws Throwable{
        this.player = (Player)playerRepository.findByEmail(email);
        this.player.setWallet(10);
        playerRepository.save(this.player);
        assertThat(player.getWallet(), greaterThanOrEqualTo (price));
    }


    @And("^a new Card is added to the player$")
    public void aNewCardIsAddedToThePlayer() throws Throwable {
        Card carta = new Card();
        carta.setNums(carta.randomcard());
        //carta.set
        //card.player = this.player; falta que s'actualitzi el model
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(card.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^the jackpot is increased by (\\d+)$")
    public void theJackpotIsIncreasedBy(int price) throws Throwable{
        this.pricebefore=this.game.getBingoPrice();
        this.game.setBingoPrice(this.pricebefore+price);
        gameRepository.save(this.game);
    }

    @And("^the player \"([^\"]*)\" has less money$")
    public void thePlayerHasLessMoney(String email, int price) throws Throwable {
        this.player = (Player) playerRepository.findByEmail(email);
        assertThat(player.getWallet(), lessThan (price));
    }

    @And("^the wallet is decreased by (\\d+)$")
    public void theWalletIsDecreasedBy(int price) throws Throwable{
        this.walletbefore=this.player.getWallet();
        this.player.setWallet(this.walletbefore+price);
        playerRepository.save(this.player);
    }

    @And("^the jackpot has increased by (\\d+)$")
    public void theJackpotHasIncreasedBy(int price) throws Throwable{
        String email = this.player.getEmail();
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/players/{email}", email)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.price",is(this.pricebefore+price)));
    }

    @And("^the wallet has decreased by (\\d+)$") // arreglar aixo
    public void theWalletHasDecreasedBy(int price) throws Throwable{
        String name = this.game.getName();
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/games/{name}", name)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.wallet",is(this.walletbefore+price)));
    }
}
