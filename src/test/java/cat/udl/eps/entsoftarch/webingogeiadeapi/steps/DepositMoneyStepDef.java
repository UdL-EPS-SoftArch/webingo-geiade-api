package cat.udl.eps.entsoftarch.webingogeiadeapi.steps;

import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Game;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Player;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.PlayerRepository;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.UserRepository;
import cucumber.api.PendingException;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

public class DepositMoneyStepDef {
    @Autowired
    UserRepository playerRepository;
    @Autowired
    private StepDefs stepDefs;

    @When("^As \"([^\"]*)\" I deposit money (\\d+) euros in my wallet$")
    public void asIDepositMoneyEurosInMyWallet(String username, int cash) throws Throwable {
        Player player = (Player) playerRepository.findByEmail(username);
        player.setWallet(cash);
        playerRepository.save(player);
    }

    @And("^\"([^\"]*)\" wallet is (\\d+)$")
    public void walletIs(String username, int cash) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/players/{username}", username)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.wallet", is(cash)));
    }

    @When("^As \"([^\"]*)\" I deposit (\\d+) euros in \"([^\"]*)\"$")
    public void asIDepositEurosIn(String arg0, int arg1, String arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions

    }
}
