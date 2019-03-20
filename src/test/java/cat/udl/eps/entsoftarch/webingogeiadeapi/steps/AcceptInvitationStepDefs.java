package cat.udl.eps.entsoftarch.webingogeiadeapi.steps;

import static org.hamcrest.Matchers.is;
import cucumber.api.PendingException;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class AcceptInvitationStepDefs {

    private static final Logger logger = LoggerFactory.getLogger(RegisterPlayerStepDef.class);

    @Autowired
    private StepDefs stepDefs;


    @When("^I accept a new invitation with username \"([^\"]*)\" and game id \"([^\"]*)\"$")
    public void iAcceptANewInvitation(String username, int id) throws Throwable {
        // Write code here that turns the phrase above into concrete actions

        stepDefs.result = (ResultActions) stepDefs.mockMvc.perform(

                get("/invitations/{id}", id)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
        //.andExpect("$.isAccepted", ResultActions(false))
        //.andDo("$isAccepted", equals(true));
    }
}
