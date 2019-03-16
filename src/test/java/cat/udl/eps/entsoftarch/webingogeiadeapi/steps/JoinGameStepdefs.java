package cat.udl.eps.entsoftarch.webingogeiadeapi.steps;

import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Invitation;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Player;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Game;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.UserRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
// import falta elimport al invitation

import java.awt.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class JoinGameStepdefs {

    @Autowired
    private StepDefs stepDefs;
    private Invitation invitation;

    @Autowired
    UserRepository playerRepository;

    private Player player;
    private Game game;


    @When("^I join to a game with name \"([^\"]*)\"$")
    public void iJoinToAGameWithName(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^existing game with name \"([^\"]*)\" and price (\\d+)$")
    public void existingGameWithName(String name, int price) throws Throwable {
        JSONObject game = new JSONObject();
        game.put("name", name);
        game.put("price", price);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(game.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^the player \"([^\"]*)\" has more wallet than price$")
    public void thePlayerHasMoreWalletThanPrice(String email) {
        this.player = (Player) playerRepository.findByEmail(email);
        int playerWallet = this.player.getWallet();
        // comprobació playerWallet > this.game.getPrice();
    }
}
