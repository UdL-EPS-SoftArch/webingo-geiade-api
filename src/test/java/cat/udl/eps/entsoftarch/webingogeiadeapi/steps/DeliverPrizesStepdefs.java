package cat.udl.eps.entsoftarch.webingogeiadeapi.steps;

import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.*;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.*;
import cucumber.api.PendingException;
import cucumber.api.java.en.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.junit.Assert.*;
import org.json.JSONObject;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class DeliverPrizesStepdefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private UserRepository playerRepository;

    @Autowired
    private CardRepository cardRepository;

    private Game game;
    private Player lwinner, bwinner;
    private Card cardline, cardbingo;

    private int gamePrice = 5;
    private int numberOfPlayers = 10;
    private double lineRatio = 0.25;


    @And("^There is a game named \"([^\"]*)\" that has already finished$")
    public void thereIsAGameWithNameThatFinished(String game_name) throws Throwable {

        this.game.setName(game_name);
        this.game.setFinished(true);
        gameRepository.save(this.game);

    }


    @And("^There is a line prize and a bingo prize in the current game$")
    public void thereIsALinePrizeAndABingoPrizeInTheCurrentGame() throws Exception {

        cardbingo = new Card();
        cardline = new Card();

        int[]total = {15,49,51,67,84,4,20,56,71,89,12,44,34,64,75,9,55,63,78,90};
        int[][] bingonums ={{4,20,56,71,89},{12,44,34,64,75},{9,55,63,78,90}} ;
        int[][] linianums ={{4,56},{15,49,51,67,84},{11,55,63,65,80}} ;
        this.cardbingo.setNums(bingonums);
        this.cardline.setNums(linianums);
        this.game.setNums(total);
        this.game.setLinePrize(numberOfPlayers*gamePrice*lineRatio);
        this.game.setBingoPrize(numberOfPlayers*gamePrice*(1-lineRatio));

        gameRepository.save(this.game);
        cardRepository.save(this.cardbingo);
        cardRepository.save(this.cardline);

        JSONObject game0bject = new JSONObject();
        game0bject.put("linePrize", this.game.getLinePrize());
        game0bject.put("bingoPrize", this.game.getBingoPrize());

        stepDefs.result = stepDefs.mockMvc.perform(
                get("/games/{id}", this.game.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bingoPrize", is(greaterThan (0.0))))
                .andExpect(jsonPath("$.linePrize", is(greaterThan (0.0))));

    }

    @And("^The player \"([^\"]*)\" won the line and the player \"([^\"]*)\" won the bingo$")
    public void thePlayerWonTheLineAndThePlayerWonTheBingo(String linewinner_email, String bingowinner_email) throws Throwable {

        lwinner = (Player) playerRepository.findByEmail(linewinner_email);
        bwinner = (Player) playerRepository.findByEmail(bingowinner_email);
        this.game = new Game();
        this.game.setBingoWinner(bwinner);
        this.game.setLineWinner(lwinner);
        game.setNumberofplayers(numberOfPlayers);
        game.setPrice(gamePrice);
        playerRepository.save(bwinner);
        playerRepository.save(lwinner);
        gameRepository.save(this.game);

        assertEquals(this.game.getLineWinner(), lwinner);
        assertEquals(this.game.getBingoWinner(), bwinner);

        JSONObject game0bject = new JSONObject();

        game0bject.put("name", "name");
        game0bject.put("lineWinner", lwinner.getUri());
        game0bject.put("bingoWinner", bwinner.getUri());

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(game0bject.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

    }


    @When("^I deliver prizes to the winning players$")
    public void iDeliverPrizesToTheWinningPlayers() {
        double oldwalletline = lwinner.getWallet();
        double oldwalletbingo = bwinner.getWallet();
        lwinner.setWallet(oldwalletline + this.game.getLinePrize());
        lwinner.setWallet(oldwalletbingo + this.game.getBingoPrize());
        playerRepository.save(lwinner);
        playerRepository.save(bwinner);

        //assertEquals(0, lwinner.getWallet().compareTo(oldwalletline+this.game.getLinePrize()));
        //assertEquals(0, bwinner.getWallet().compareTo(oldwalletbingo+this.game.getBingoPrize()));
    }

    @Given("^The player \"([^\"]*)\" won the line and bingo at the same game$")
    public void thePlayerWonTheLineAndBingoAtTheSameGame(String winner_email) throws Throwable {

        Player winner = (Player) playerRepository.findByEmail(winner_email);
        lwinner = winner;
        bwinner = winner;
        this.game = new Game();
        this.game.setBingoWinner(bwinner);
        this.game.setLineWinner(lwinner);
        playerRepository.save(bwinner);
        playerRepository.save(lwinner);
        gameRepository.save(this.game);

        assertEquals(this.game.getLineWinner(), lwinner);
        assertEquals(this.game.getBingoWinner(), bwinner);
    }

    @Given("^There is a game with named \"([^\"]*)\" that didn't finish yet$")
    public void givenThereIsAGameWithNamedThatDidnTFinishYet(String game_name) throws Throwable {

        this.game = new Game();
        this.game.setName(game_name);
        this.game.setFinished(false);
        gameRepository.save(this.game);

        assertFalse(this.game.isFinished());
    }

    @Then("^The game is completely finished$")
    public void theGameIsCompletelyFinished() {
        this.game.setPrice(0);
        this.game.setNumberofplayers(0);
        this.game.setLineWinner(null);
        this.game.setBingoWinner(null);
        //gameRepository.delete(this.game);
    }

    @When("^I deliver prizes to the only winner$")
    public void iDeliverPrizesToTheOnlyWinner() {
        double oldwallet = bwinner.getWallet();
        lwinner.setWallet(oldwallet + this.game.getLinePrize() + this.game.getBingoPrize());
        playerRepository.save(bwinner);

        //assertEquals(0, bwinner.getWallet().compareTo(oldwallet+this.game.getLinePrize()+this.game.getBingoPrize()));
    }

    @And("^There is a bad line prize and a bingo prize in the current game$")
    public void thereIsABadLinePrizeAndABingoPrizeInTheCurrentGame() throws  Exception{
        cardbingo = new Card();
        cardline = new Card();

        int[]total = {15,49,51,67,84,4,20,56,71,89,12,44,34,64,75,9,55,63,78,90};
        int[][] bingonums ={{4,20,56,71,89},{12,44,34,64,75},{9,55,63,78,90}} ;
        int[][] linianums ={{4,56},{15,49,51,67,84},{11,55,63,65,80}} ;
        this.cardbingo.setNums(bingonums);
        this.cardline.setNums(linianums);
        this.game.setNums(total);
        this.game.setLinePrize(-4);
        this.game.setBingoPrize(0);

        gameRepository.save(this.game);
        cardRepository.save(this.cardbingo);
        cardRepository.save(this.cardline);

        JSONObject game0bject2 = new JSONObject();
        game0bject2.put("linePrize", this.game.getLinePrize());
        game0bject2.put("bingoPrize", this.game.getBingoPrize());

        stepDefs.result = stepDefs.mockMvc.perform(
                get("/games/{id}", this.game.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bingoPrize", is(greaterThan (0.0))))
                .andExpect(jsonPath("$.linePrize", is(greaterThan (0.0))));
    }
}
