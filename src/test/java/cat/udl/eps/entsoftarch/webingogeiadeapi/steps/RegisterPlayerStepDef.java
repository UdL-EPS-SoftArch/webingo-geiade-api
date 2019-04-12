package cat.udl.eps.entsoftarch.webingogeiadeapi.steps;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Player;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.UserRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class RegisterPlayerStepDef {

  private static final Logger logger = LoggerFactory.getLogger(RegisterPlayerStepDef.class);

  public static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Autowired
  private StepDefs stepDefs;

  private Player player;

  @Autowired
  private UserRepository playerRepository;

  @When("^I register a new player with username \"([^\"]*)\", email \"([^\"]*)\" and password \"([^\"]*)\"$")
  public void iRegisterANewPlayer(String username, String email, String password) throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    JSONObject player = new JSONObject();
    player.put("username", username);
    player.put("email", email);
    player.put("password", password);
    stepDefs.result = stepDefs.mockMvc.perform(
        post("/players")
            .contentType(MediaType.APPLICATION_JSON)
            .content(player.toString())
            .accept(MediaType.APPLICATION_JSON)
            .with(AuthenticationStepDefs.authenticate()))
        .andDo(print());
  }

  @And("^It has been created a player with username \"([^\"]*)\" and email \"([^\"]*)\", the password is not returned$")
  public void itHasBeenCreatedAPlayer(String username, String email) throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    stepDefs.result = stepDefs.mockMvc.perform(
        get("/players/{username}", username)
            .accept(MediaType.APPLICATION_JSON)
            .with(AuthenticationStepDefs.authenticate()))
        .andDo(print())
        .andExpect(jsonPath("$.email", is(email)))
        .andExpect(jsonPath("$.password").doesNotExist());
  }

  @And("^It has not been created a player with username \"([^\"]*)\"$")
  public void itHasNotBeenCreatedAPlayerWithUsername(String username) throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    stepDefs.result = stepDefs.mockMvc.perform(
        get("/players/{username}", username)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @When("^I change \"([^\"]*)\" password to \"([^\"]*)\"$")
  public void iChangePasswordTo(String mail, String password) throws Throwable {
    this.player = (Player) playerRepository.findByEmail(mail);

    System.out.println(this.player.toString());

    JSONObject playerObject = new JSONObject();
    playerObject.put("password", password);

    stepDefs.result = stepDefs.mockMvc.perform(
            patch("/players/{username}", this.player.getUsername())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(playerObject.toString())
                    .accept(MediaType.APPLICATION_JSON)
                    .with(AuthenticationStepDefs.authenticate()))
            .andDo(print());
  }
}
