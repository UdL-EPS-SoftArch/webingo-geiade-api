package cat.udl.eps.entsoftarch.webingogeiadeapi.steps;

import static org.hamcrest.Matchers.is;

import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Invitation;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Player;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.InvitationRepository;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.UserRepository;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AcceptInvitationStepDefs {

    private static final Logger logger = LoggerFactory.getLogger(RegisterPlayerStepDef.class);

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InvitationRepository invitationRepository;
    private Invitation invitation;

    @And("^Invitation \"([^\"]*)\" is already created and was sent from email \"([^\"]*)\" to email \"([^\"]*)\"$")
    public void invitationIsAlreadyCreatedAndWasSentFromEmailToEmail(String invitation, String email1, String email2) throws Throwable {
        Player player1 = (Player) userRepository.findByEmail(email1);
        Player player2 = (Player) userRepository.findByEmail(email2);

        this.invitation = new Invitation();
        this.invitation.setId_game(1);
        this.invitation.setPlayerWhoInvited(player1);
        this.invitation.setPlayerInvited(player2);
        this.invitation.setAccepted(false);
        this.invitation.setTimeout(false);
        this.invitation.setUnderway(false);
        this.invitation.setMessage("Hi there, join me playing webbingo");

        String invite = stepDefs.mapper.writeValueAsString(this.invitation);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/invitations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invite)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^The game is the desired one$")
    public void theGameIsTheDesiredOne() {
        invitationRepository.findById(this.invitation.getId());
        this.invitation.setAccepted(true);
        invitationRepository.save(this.invitation);
    }

    @When("^I accept the invitation \"([^\"]*)\"$")
    public void iAcceptTheInvitation(String invitation) throws Throwable {
        invitationRepository.findById(this.invitation.getId());
        this.invitation.setAccepted(true);
        String invite = stepDefs.mapper.writeValueAsString(this.invitation);
        stepDefs.result = stepDefs.mockMvc.perform(
                patch("/invitations/{id}", this.invitation.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invite)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

    }

    @And("The game has already finished or is underway$")
    public void theGameHasAlreadyFinishedOrIsUnderway() throws Throwable {
        invitationRepository.findById(this.invitation.getId());
        this.invitation.setUnderway(true);
        invitationRepository.save(this.invitation);
    }

    @And("^Timeout has been exceeded$")
    public void timeoutHasBeenExceeded() throws Throwable {
        invitationRepository.findById(this.invitation.getId());
        this.invitation.setTimeout(true);
        invitationRepository.save(this.invitation);
    }

    @And("^The game is not the desired one$")
    public void theGameIsNotTheDesiredOne() {
        invitationRepository.findById(this.invitation.getId());
        this.invitation.setAccepted(false);
        invitationRepository.save(this.invitation);
    }

    @When("^I reject the invitation \"([^\"]*)\"$")
    public void iRejectTheInvitation(String invitation) throws Throwable {
        invitationRepository.findById(this.invitation.getId());
        this.invitation.setAccepted(false);
        String invite = stepDefs.mapper.writeValueAsString(this.invitation);
        stepDefs.result = stepDefs.mockMvc.perform(
                patch("/invitations/{id}", this.invitation.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invite)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }
}
