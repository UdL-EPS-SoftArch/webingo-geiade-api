package cat.udl.eps.entsoftarch.webingogeiadeapi.steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;

public class InvitePlayertepdefs {
    @When("^I invite a new player to the game with username \"([^\"]*)\"$")
    public void iInviteANewPlayerToTheGameWithUsername(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^It has not been invited any player to the game$")
    public void itHasNotBeenInvitedAnyPlayerToTheGame() {
    }

    @When("^I invite a X numner os players to the game with username \"([^\"]*)\", username \"([^\"]*)\", username \"([^\"]*)\",$")
    public void iInviteAXNumnerOsPlayersToTheGameWithUsernameUsernameUsername(String arg0, String arg1, String arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I invite a new player with username \"([^\"]*)\" who is playing in another game$")
    public void iInviteANewPlayerWithUsernameWhoIsPlayingInAnotherGame(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^It has been created the Invitation to the current game$")
    public void itHasBeenCreatedTheInvitationToTheCurrentGame() {
        
    }

    @And("^It has not been invited the player with username \"([^\"]*)\"$")
    public void itHasNotBeenInvitedThePlayerWithUsername(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
