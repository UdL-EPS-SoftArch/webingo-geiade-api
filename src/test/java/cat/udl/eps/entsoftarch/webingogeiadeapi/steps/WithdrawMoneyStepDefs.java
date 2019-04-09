package cat.udl.eps.entsoftarch.webingogeiadeapi.steps;

import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Player;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.UserRepository;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;


public class WithdrawMoneyStepDefs {

    @Autowired
    private UserRepository userRepository;
    private Player player1;
    private Player player2;
    private Player player3;


    @When("^I want to get back some of my money (\\d+) from the wallet of \"([^\"]*)\"$")
    public void iWantToGetBackSomeOfMyMoneyFromTheWalletOf(int money, String email) throws Throwable {

        player1 = new Player();
        this.player1 = (Player) userRepository.findByEmail(email);
        this.player1.setWallet(this.player1.getWallet()-money);
        userRepository.save(this.player1);
    }

    @And("^Wallet has the correct amount of money left$")
    public void walletHasTheCorrectAmountOfMoneyLeft() {

    }

    @When("^I want to get back some money (\\d+) from some wallet of \"([^\"]*)\" with username \"([^\"]*)\"$")
    public void iWantToGetBackSomeMoneyFromSomeWalletOfWithUsername(int money, String email1, String email2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        player2 = new Player();
        player3 = new Player();
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
