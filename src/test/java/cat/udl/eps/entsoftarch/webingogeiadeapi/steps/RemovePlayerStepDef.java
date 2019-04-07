package cat.udl.eps.entsoftarch.webingogeiadeapi.steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static cat.udl.eps.entsoftarch.webingogeiadeapi.steps.AuthenticationStepDefs.authenticate;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RemovePlayerStepDef {

    @Autowired
    private StepDefs stepDefs;

    //Scenario 1 - Remove a player as admin
    @And("^There is player with username \"([^\"]*)\", email \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void thereIsPlayerWithUsernameEmailAndPassword(String username, String email, String password) throws JSONException {

        JSONObject player_register = new JSONObject();
        player_register.put("username", username);
        player_register.put("email", email);
        player_register.put("password", password);
    }

    @When("^I remove a player with username \"([^\"]*)\"$")
    public void iRemoveAPlayerWithUsername(String username) throws Throwable {

        stepDefs.result = stepDefs.mockMvc.perform(
                delete("/players/{username}", username)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(authenticate()))
                .andExpect(status().isNoContent());

    }

    @And("^The player with username \"([^\"]*)\" has been removed$")
    public void thePlayerWithUsernameHasBeenRemoved(String username) throws Throwable {

        stepDefs.result = stepDefs.mockMvc.perform(
                get("/players/{username}", username)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }


    //Scenario 2: Remove a player without authenticating

    @When("^I remove a player with username \"([^\"]*)\" without authenticating$")
    public void iRemoveAPlayerWithUsernameWithoutAuthenticating(String username) throws Throwable {

        stepDefs.result = stepDefs.mockMvc.perform(
                delete("/players/{username}", username)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @And("^It has not been removed the player with username \"([^\"]*)\"$")
    public void itHasNotBeenRemovedThePlayerWithUsername(String username) throws Throwable {

        stepDefs.result = stepDefs.mockMvc.perform(
                get("/players/{username}", username)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }



    // Scenario 3: Remove a player being a User
    @When("^I remove a player with username \"([^\"]*)\" being a User$")
    public void iRemoveAPlayerWithUsernameBeingAUser(String username) throws Throwable {

        stepDefs.result = stepDefs.mockMvc.perform(
                delete("/players/{username}", username)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }


    // Scenario 4: Remove an unexisting player as admin
    @When("^I remove an unexisting player with username \"([^\"]*)\"$")
    public void iRemoveAnUnexistingPlayerWithUsername(String username) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                delete("/players/{username}", username)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(authenticate()))
                .andExpect(status().isNoContent());
    }

}
