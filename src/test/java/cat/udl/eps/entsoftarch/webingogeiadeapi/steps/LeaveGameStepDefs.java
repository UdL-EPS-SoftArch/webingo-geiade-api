package cat.udl.eps.entsoftarch.webingogeiadeapi.steps;

import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Player;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.PlayerRepository;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.UserRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


public class LeaveGameStepDefs {

    @Autowired
    PlayerRepository p;

    UserRepository playerRepository;

    private Player player;

    //@Given("^I login with  password \"([^\"]*)\" and a username \"([^\"]*)\" and with a Game \"([^\"]*)\" and with Card \"([^\"]*)\"$")
    public void iLoginWithPasswordAndAUsernameAndWithAGameAndWithCard(String arg0, String arg1, String arg2, String arg3) throws Throwable {
        Player pl = p.findById(username).orElse(null);

        stepDefs.result = stepDefs.mockMvc.perform(
                patch("/players/{username}", username)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
        ;
    }


    @And("^the player \"([^\"]*)\" leaves a Game \"([^\"]*)\"$")
    public void thePlayerLeavesAGame(String email, String game) throws Throwable {
        this.player = (Player) playerRepository.findByEmail(email);

    }

    @And("^is playing with Card \"([^\"]*)\"$")
    public void isPlayingWithCard(String card) throws Throwable {

        Card card= new Card();
        card

    }

    @When("^I leave a game when I'm playing with username \"([^\"]*)\"$")
    public void iLeaveAGameWhenIMPlayingWithUsername(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }


    @And("^\"([^\"]*)\" becomes null$")
    public void becomesNull(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^isPaying_card\" is removed$")
    public void ispaying_cardIsRemoved() throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                delete("/player/card/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(authenticate()));
    }
}
