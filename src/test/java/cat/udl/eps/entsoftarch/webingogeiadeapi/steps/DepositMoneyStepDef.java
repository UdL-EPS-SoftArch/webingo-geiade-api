package cat.udl.eps.entsoftarch.webingogeiadeapi.steps;

import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Game;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Player;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.PlayerRepository;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.UserRepository;
import cucumber.api.PendingException;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    private Player player;

    @When("^As \"([^\"]*)\" I deposit money (\\d+) euros in my wallet$")
    public void asIDepositMoneyEurosInMyWallet(String username, int cash) throws Throwable {
        this.player = (Player) playerRepository.findByEmail(username);

        JSONObject playerObject = new JSONObject();
        playerObject.put("toWallet", cash);

        stepDefs.result = stepDefs.mockMvc.perform(
                patch("/players/{username}", this.player.getUsername())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(playerObject.toString())
                    .accept(MediaType.APPLICATION_JSON)
                    .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^\"([^\"]*)\" wallet is (\\d+)$")
    public void walletIs(String username, int cash) throws Throwable {
        this.player = (Player) playerRepository.findByEmail(username);
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/players/{username}", this.player.getUsername())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.wallet", is(cash)));
    }

    @And("^\"([^\"]*)\" wallet is equal (\\d+)$")
    public void walletIsEqual(String username, int cash) throws Throwable {
        this.player = (Player) playerRepository.findByEmail(username);
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/players/{username}", this.player.getUsername())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.wallet", is(cash)));
    }

    @When("^As \"([^\"]*)\" I deposit (\\d+) euros in \"([^\"]*)\"$")
    public void asIDepositEurosIn(String username, int money, String username2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        // AQUI S'HAURIA D?AFEGIR UN PATCH/PUT O AMB EL REPOSITORY

    }

    @When("^As \"([^\"]*)\" I deposit money - (\\d+) euros in my wallet$")
    public void asIDepositMoneyEurosInMyWalletNegative(String username, int cash) throws Throwable {
        this.player = (Player) playerRepository.findByEmail(username);

        JSONObject playerObject = new JSONObject();
        playerObject.put("toWallet", -cash);

        stepDefs.result = stepDefs.mockMvc.perform(
                patch("/players/{username}", this.player.getUsername())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerObject.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }
}
