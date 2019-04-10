package cat.udl.eps.entsoftarch.webingogeiadeapi.steps;

import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.*;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.GameRepository;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import cucumber.api.PendingException;
import cucumber.api.java.en.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import javax.persistence.ManyToOne;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeliverPrizesStepdefs {

    @Autowired
    private StepDefs stepDefs;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private Game game;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private Player lwinner;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private Player bwinner;

    @Given("^There is a game with named \"([^\"]*)\" that has finished$")
    public void thereIsAGameWithNameThatFinished(String game_name) throws Throwable {

        this.game = new Game();
        game.setName(game_name);
        game.setFinished(true);
    }

    @Given("^There is a player with email \"([^\"]*)\" and username \"([^\"]*)\" who won the line$")
    public void thereIsAPlayerWithEmailAndUsernameWhoWonTheLine(String linewinner_email, String lineowinner_username) throws Throwable {

        this.lwinner = new Player();
        this.game = new Game();

        this.lwinner.setUsername(lineowinner_username);
        this.lwinner.setPassword("password");
        this.lwinner.setEmail(linewinner_email);
        this.game.setName("Game 1");
        this.game.setLineWinner(lwinner);

        this.game.getLineWinner().getEmail().compareTo(lineowinner_username);

    }

    @Given("^There is a player with email \"([^\"]*)\" and username \"([^\"]*)\" who won the bingo game$")
    public void thereIsAPlayerWithEmailAndUsernameWhoWonTheBingoGame(String bingowinner_email, String bingowinner_username) throws Throwable {

        this.bwinner = new Player();
        this.game = new Game();

        this.bwinner.setUsername(bingowinner_username);
        this.bwinner.setPassword("password");
        this.bwinner.setEmail(bingowinner_email);
        this.game.setName("Game 1");
        this.game.setBingoWinner(bwinner);

        this.game.getBingoWinner().getEmail().compareTo(bingowinner_username);

    }

    @Given("^There is a line prize and a bingo prize in the current game$")
    public void thereIsALinePrizeAndABingoPrizeInTheCurrentGame() throws Exception {

        this.game = new Game();
        this.game.setBingoPrize(80);
        this.game.setLinePrize(20);

        String lineprize = String.valueOf(this.game.getLineWinner());
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/prizes/line")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(lineprize)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        String bingoprize = String.valueOf(this.game.getBingoWinner());
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/prizes/bingo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bingoprize)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

    }

    @And("^I delete the bingo and line prizes$")
    public void iDeleteTheBingoAndLinePrizes() {
    }

    @And("^I deliver prizes to the players with email \"([^\"]*)\" and \"([^\"]*)\"$")
    public void iDeliverPrizesToThePlayersWithEmailAnd(String arg0, String arg1) throws Throwable {


    }

}
