package cat.udl.eps.entsoftarch.webingogeiadeapi.steps;

import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.*;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.GameRepository;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.PlayerRepository;
import cucumber.api.java.en.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;


public class DeliverPrizesStepdefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;


    private Game game;


    @Given("^There is a game with named \"([^\"]*)\" that has already finished$")
    public void thereIsAGameWithNameThatFinished(String game_name) throws Throwable {

        this.game = new Game();
        this.game.setName(game_name);
        this.game.setFinished(true);
        gameRepository.save(this.game);

        assertTrue(this.game.isFinished());

    }

    @Given("^There is a player with email \"([^\"]*)\" and username \"([^\"]*)\" who won the line$")
    public void thereIsAPlayerWithEmailAndUsernameWhoWonTheLine(String linewinner_email, String lineowinner_username) throws Throwable {

        Player lwinner = playerRepository.findByEmail(linewinner_email);
        System.out.println(lwinner);

        //this.game.setLineWinner(this.lwinner);

        //assertEquals(this.game.getLineWinner().getEmail(), this.lwinner.getEmail());

    }

    @Given("^There is a player with email \"([^\"]*)\" and username \"([^\"]*)\" who won the bingo game$")
    public void thereIsAPlayerWithEmailAndUsernameWhoWonTheBingoGame(String bingowinner_email, String bingowinner_username) throws Throwable {


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
