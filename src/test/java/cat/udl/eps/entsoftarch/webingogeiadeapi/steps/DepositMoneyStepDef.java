package cat.udl.eps.entsoftarch.webingogeiadeapi.steps;

import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Player;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.UserRepository;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.json.JSONObject;
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
    public void walletIs(String username, double cash) throws Throwable {
        this.player = (Player) playerRepository.findByEmail(username);

        stepDefs.result = stepDefs.mockMvc.perform(
                get("/players/{username}", this.player.getUsername())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.wallet", is(cash)));
    }

    @When("^I deposit (\\d+) euros in \"([^\"]*)\"$")
    public void asIDepositEurosIn(int cash, String username) throws Throwable {

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
