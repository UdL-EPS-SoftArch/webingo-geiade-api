package cat.udl.eps.entsoftarch.webingogeiadeapi.steps;

import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Player;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.UserRepository;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


public class WithdrawMoneyStepDefs {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRepository playerRepository;

    @Autowired
    private StepDefs stepDefs;

    private Player player;

    @And("^\"([^\"]*)\" wallet has (\\d+) euros$")
    public void walletHasEuros(String username, int money) throws Throwable {
        this.player = (Player) playerRepository.findByEmail(username);
        this.player.setWallet(money);
        playerRepository.save(this.player);
    }

    @When("^As \"([^\"]*)\" I withdraw (\\d+) euros from my wallet$")
    public void asIWithdrawMoneyEurosFromMyWallet(String username, int money) throws Throwable {
        this.player = (Player) playerRepository.findByEmail(username);

        JSONObject playerObject = new JSONObject();
        playerObject.put("fromWallet", money);

        stepDefs.result = stepDefs.mockMvc.perform(
                patch("/players/{username}", this.player.getUsername())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerObject.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }


    @When("^I withdraw (\\d+) euros in \"([^\"]*)\"$")
    public void asIWithdrawEurosIn(int money, String username) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        // AQUI S'HAURIA D?AFEGIR UN PATCH/PUT O AMB EL REPOSITORY

        this.player = (Player) playerRepository.findByEmail(username);

        JSONObject playerObject = new JSONObject();
        playerObject.put("fromWallet", money);

        stepDefs.result = stepDefs.mockMvc.perform(
                patch("/players/{username}", this.player.getUsername())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerObject.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

    }

}
