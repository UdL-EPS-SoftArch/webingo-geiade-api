package cat.udl.eps.entsoftarch.webingogeiadeapi.steps;

import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Invitation;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Game;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.PlayerRepository;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.UserRepository;
//import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.InvitationRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.http.MediaType;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Player;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;



public class InvitePlayertepdefs {


    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private UserRepository playerrepo;

    @Autowired
    //private InvitationRepository invitationrepo;

    //Scenario 1
    @WithMockUser (username="admin",roles={"USER","ADMIN"})
    @When("^I invite a new player to the game with email \"([^\"]*)\" and message \"([^\"]*)\"$")
    public void iInviteANewPlayerToTheGameWithUsernameAndMessage(String message, String email) throws Throwable {
        // Write code here that turns the phrase above into concrete actions

        Invitation game_invitation = new Invitation();
        game_invitation.setMessage(message);
        game_invitation.setPlayer_invited((Player)playerrepo.findByEmail(email));

        String invitation = stepDefs.mapper.writeValueAsString(game_invitation);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/invitations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invitation)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^There is a player with username \"([^\"]*)\" and email \"([^\"]*)\"$")
    public void thereIsAPlayerWithUsernameAndEmail(String username, String email) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Player player_invited= new Player();
        player_invited.setEmail(email);
        player_invited.setUsername(username);
        playerrepo.save(player_invited);
    }

    @And("^It has not been created any invitation$")
    public void itHasNotBeenCreatedAnyInvitation() throws Exception {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/invitations")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
                //.andExpect(invitationrepo, hasSize(0));
    }

    //Scenario 2
    @And("^It has been invited to game the player with email \"([^\"]*)\" and message \"([^\"]*)\"$")
    public void itHasBeenInvitedToGameThePlayerWithEmailAndMessage(String arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions

        //playerrepo.save(player_invited);
    }
}
