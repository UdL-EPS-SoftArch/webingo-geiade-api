package cat.udl.eps.entsoftarch.webingogeiadeapi.steps;

import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Invitation;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;



public class InvitePlayertepdefs {

    @Autowired
    private StepDefs stepDefs;

    @When("^I invite a new player to the game with username \"([^\"]*)\" and message \"([^\"]*)\"$")
    public void iInviteANewPlayerToTheGameWithUsernameAndMessage(String username, String message) throws Throwable {
        // Write code here that turns the phrase above into concrete actions

        JSONObject invitation = new JSONObject();
        invitation.put("username", username);
        invitation.put("message", message);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(message)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        throw new PendingException();
    }
}
