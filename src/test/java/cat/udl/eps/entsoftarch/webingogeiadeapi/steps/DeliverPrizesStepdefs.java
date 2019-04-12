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


public class DeliverPrizesStepdefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    UserRepository playerRepository;


    private Game game;
    private Player lwinner, bwinner;


    @Given("^There is a game with named \"([^\"]*)\" that has already finished$")
    public void thereIsAGameWithNameThatFinished(String game_name) throws Throwable {

        this.game = new Game();
        this.game.setName(game_name);
        this.game.setFinished(true);
        gameRepository.save(this.game);

        assertTrue(this.game.isFinished());

    }

    @Given("^The player \"([^\"]*)\" won the line$")
    public void thereIsAPlayerWithEmailAndUsernameWhoWonTheLine(String linewinner_email) throws Throwable {

        lwinner = (Player) playerRepository.findByEmail(linewinner_email);
        lwinner.setUsername("username");
        this.game.setLineWinner(lwinner);
        //playerRepository.save(lwinner);

        //assertEquals(this.game.getLineWinner(), lwinner);

    }

    @And("^The player \"([^\"]*)\" won the bingo$")
    public void thePlayerWonTheBingo(String bingowinner_email) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        bwinner = (Player) playerRepository.findByEmail(bingowinner_email);
        bwinner.setUsername("username");
        this.game.setBingoWinner(bwinner);
        //playerRepository.save(bwinner);

        //assertEquals(this.game.getBingoWinner(), bwinner);
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

}
