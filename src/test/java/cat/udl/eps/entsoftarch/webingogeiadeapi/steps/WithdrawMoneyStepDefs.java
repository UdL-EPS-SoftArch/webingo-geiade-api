package cat.udl.eps.entsoftarch.webingogeiadeapi.steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

public class WithdrawMoneyStepDefs {
    @Given("^I login with username \"([^\"]*)\" with password \"([^\"]*)\"$")
    public void iLoginWithUsernameWithPassword(String arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I want to get back some of my money (\\d+) from wallet$")
    public void iWantToGetBackSomeOfMyMoneyFromWallet(int arg0) {
    }

    @And("^Wallet has the correct amount of money left$")
    public void walletHasTheCorrectAmountOfMoneyLeft() {
    }
    
    @When("^I want to get back some money (\\d+) from some wallet \"([^\"]*)\"$")
    public void iWantToGetBackSomeMoneyFromSomeWallet(int arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^He is not the username \"([^\"]*)\" of that wallet$")
    public void heIsNotTheUsernameOfThatWallet(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^The money has not been withdrawed from the wallet$")
    public void theMoneyHasNotBeenWithdrawedFromTheWallet() {
    }
}
