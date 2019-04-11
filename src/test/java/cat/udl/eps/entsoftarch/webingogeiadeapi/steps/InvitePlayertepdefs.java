package cat.udl.eps.entsoftarch.webingogeiadeapi.steps;

import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Invitation;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Game;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.PlayerRepository;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.UserRepository;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.InvitationRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.http.MediaType;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Player;

import java.rmi.UnexpectedException;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;



public class InvitePlayertepdefs {

    @Autowired
    private StepDefs stepDefs;

    private Invitation game_invitation;

    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private PlayerRepository playerRepository;

    //Scenario 1
    @WithMockUser
    @When("^I create an invitation with message \"([^\"]*)\"$")
    public void iInviteANewPlayerToTheGameWithUsernameAndMessage(String message) throws Throwable {

        this.game_invitation = new Invitation();
        this.game_invitation.setMessage(message);
        this.game_invitation.setAccepted(false);
        this.game_invitation.setTimeout(false);
        this.game_invitation.setUnderway(false);

        String invitation = stepDefs.mapper.writeValueAsString(game_invitation);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/invitations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invitation)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

    }

    @And("^There is not a player with username \"([^\"]*)\" and email \"([^\"]*)\"$")
    public void thereIsNotAPlayerWithUsernameAndEmail(String username, String email) throws Throwable {

        Player player_not_found= new Player();
        String email_error = "danierror@webingo.cat";
        player_not_found.setEmail(email_error);
        player_not_found.setUsername(username);
        player_not_found.setPassword("password");
        playerRepository.save(player_not_found);

    }

    @And("^It has not been created any invitation$")
    public void itHasNotBeenCreatedAnyInvitation() throws Exception {

        stepDefs.result = stepDefs.mockMvc.perform(
                get("/invitations/{id}", this.game_invitation.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    //Scenario 2
    @And("^It has been invited to game the player")
    public void itHasBeenInvitedToGameThePlayerWithEmailAndMessage() throws Throwable {

        stepDefs.result = stepDefs.mockMvc.perform(
                get("/invitations/")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @And("^There is a player with username \"([^\"]*)\" and email \"([^\"]*)\" who I invite$")
    public void thereIsAPlayerWithUsernameAndEmail(String username, String email) throws Throwable {

        Player player1= new Player();
        player1.setEmail(email);
        player1.setUsername(username);
        player1.setPassword("password");
        playerRepository.save(player1);

        this.game_invitation.setPlayerInvited(player1);
        this.game_invitation.setId_game(6); //for example
        invitationRepository.save(this.game_invitation);

    }

    @And("^I already invited the player with email \"([^\"]*)\" and username \"([^\"]*)\"$")
    public void iAlreadyInvitedThePlayerWithEmailAndMessage(String email, String username) throws Throwable {

        Invitation sameinvitation = new Invitation();
        sameinvitation.setAccepted(false);
        sameinvitation.setTimeout(false);
        sameinvitation.setUnderway(false);


        Player already_invited = new Player();
        already_invited.setEmail(email);
        already_invited.setUsername(username);
        already_invited.setPassword("password");
        playerRepository.save(already_invited);

        sameinvitation.setPlayerInvited(already_invited);

        String invitation = stepDefs.mapper.writeValueAsString(sameinvitation);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/invitations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invitation)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        invitationRepository.save(sameinvitation);
    }
}
