package cat.udl.eps.entsoftarch.webingogeiadeapi.steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import //falta elimport al invitation

public class JoinGameStepdefs {

    @Autowired
    private StepDefs stepDefs;
    private Invitation invitation;


    @And("^a new \"([^\"]*)\" is added to the player$")
    public void aNewIsAddedToThePlayer(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^the \"([^\"]*)\" is added to the player$")
    public void theIsAddedToThePlayer(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^the \"([^\"]*)\" is increased by the card \"([^\"]*)\"$")
    public void theIsIncreasedByTheCard(String arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^the \"([^\"]*)\" is decreased by the card \"([^\"]*)\"$")
    public void theIsDecreasedByTheCard(String arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^the error message is \"([^\"]*)\"$")
    public void theErrorMessageIs(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }


    @When("^I join to a game$")
    public void iJoinToAGame() {
    }
}
