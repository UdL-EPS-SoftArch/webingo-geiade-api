package cat.udl.eps.entsoftarch.webingogeiadeapi.steps;

import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.*;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.*;
import cucumber.api.PendingException;
import cucumber.api.java.en.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import java.lang.Double;


public class DeliverPrizesStepdefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    UserRepository playerRepository;


    private Game game;
    private Player lwinner, bwinner;

    private int numberofplayers = 20;


    @Given("^There is a game with named \"([^\"]*)\" that has already finished$")
    public void thereIsAGameWithNameThatFinished(String game_name) throws Throwable {

        this.game = new Game();
        this.game.setName(game_name);
        this.game.setFinished(true);
        gameRepository.save(this.game);

        assertTrue(this.game.isFinished());

    }


    @And("^There is a line prize and a bingo prize in the current game$")
    public void thereIsALinePrizeAndABingoPrizeInTheCurrentGame() throws Exception {

        assertThat(this.game.getBingoPrize(), greaterThan (0.0));
        assertThat(this.game.getLinePrize(), greaterThan (0.0));
    }

    @And("^The player \"([^\"]*)\" won the line and the player \"([^\"]*)\" won the bingo$")
    public void thePlayerWonTheLineAndThePlayerWonTheBingo(String linewinner_email, String bingowinner_email) throws Throwable {

        lwinner = (Player) playerRepository.findByEmail(linewinner_email);
        bwinner = (Player) playerRepository.findByEmail(bingowinner_email);
        this.game.setBingoWinner(bwinner);
        this.game.setLineWinner(lwinner);
        this.game.setNumberofplayers(10);
        this.game.setPrice(5);
        this.game.setLinePrize(game.getNumberofplayers()*this.game.getPrice()*0.25);
        this.game.setBingoPrize((game.getPrice()*numberofplayers) - (game.getLinePrize()));
        playerRepository.save(bwinner);
        playerRepository.save(lwinner);
        gameRepository.save(this.game);

        assertEquals(this.game.getLineWinner(), lwinner);
        assertEquals(this.game.getBingoWinner(), bwinner);
    }


    @When("^I deliver prizes to the winning players$")
    public void iDeliverPrizesToTheWinningPlayers() {
        double oldwalletline = lwinner.getWallet();
        double oldwalletbingo = bwinner.getWallet();
        lwinner.setWallet(oldwalletline + this.game.getLinePrize());
        lwinner.setWallet(oldwalletbingo + this.game.getBingoPrize());
        playerRepository.save(lwinner);
        playerRepository.save(bwinner);

        //assertEquals(0, lwinner.getWallet().compareTo(oldwalletline+game.getLinePrize()));
        //assertEquals(0, bwinner.getWallet().compareTo(oldwalletbingo+game.getBingoPrize()));
    }

    @And("^The player \"([^\"]*)\" won the line and bingo at the same game$")
    public void thePlayerWonTheLineAndBingoAtTheSameGame(String winner_email) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Player winner = (Player) playerRepository.findByEmail(winner_email);
        this.game.setBingoWinner(winner);
        this.game.setLineWinner(winner);
        this.game.setNumberofplayers(10);
        this.game.setPrice(5);
        this.game.setLinePrize(game.getNumberofplayers()*this.game.getPrice()*0.25);
        this.game.setBingoPrize((game.getPrice()*numberofplayers) - (game.getLinePrize()));
        playerRepository.save(winner);
        gameRepository.save(this.game);

        assertEquals(this.game.getLineWinner(), winner);
        assertEquals(this.game.getBingoWinner(), winner);
    }
}
