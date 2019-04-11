package cat.udl.eps.entsoftarch.webingogeiadeapi.steps;

import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Game;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Player;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Card;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.GameRepository;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.UserRepository;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.CardRepository;

public class ShowResultsStepdefs {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    UserRepository playerRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    private StepDefs stepDefs;

    @And("^user \"([^\"]*)\" join to a game \"([^\"]*)\"$")
    public void userJoinToAGame(String email, String name) throws Throwable {
        Game g = gameRepository.findByName(name);
        Card c = new Card();
        JSONObject card = new JSONObject();
        card.put("game", g.getUri());
        card.put("price",3);
        card.put("nums", c.randomcard());
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(card.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^\"([^\"]*)\" has won the bingo for the game \"([^\"]*)\"$")
    public void hasWonTheBingoForTheGame(String email, String game) throws Throwable {
        Game g = gameRepository.findByName(game);
        Player p = (Player) playerRepository.findByEmail(email);
        Card c = cardRepository.findByPlayer(p);
        int z=0;
        int [][] nums = c.getNums();
        int [] totalnums = new int[15];
        for (int i=0; i<3; i++) {
            for (int j = 0; j < 5; j++) {
                totalnums[z] = nums[i][j];
                z++;
            }
        }
        g.setNums(totalnums);
        gameRepository.save(g);
    }

    @And("^\"([^\"]*)\" has won the line for the game \"([^\"]*)\"$")
    public void hasWonTheLineForTheGame(String email, String game) throws Throwable {
        Game g = gameRepository.findByName(game);
        Player p = (Player) playerRepository.findByEmail(email);
        Card c = cardRepository.findByPlayer(p);
        if (c==null){
            System.out.println("buit");
        }
        if (c!=null){
            System.out.println("ple");
        }
        System.out.println(c.getNums());
        int z=0;
        int [][] nums = c.getNums();
        int [] totalnums = new int[15];

        for (int j = 0; j < 5; j++) {
            totalnums[z] = nums[0][j];
            z++;
        }
        g.setNums(totalnums);
        gameRepository.save(g);
    }

    @And("^\"([^\"]*)\" has been added at the bingo winner variable of the game \"([^\"]*)\"$")
    public void hasBeenAddedAtTheBingoWinnerVariableOfTheGame(String email, String game) throws Throwable {
        Game g = gameRepository.findByName(game);
        Player p = (Player) playerRepository.findByEmail(email);
        assertThat(g.getBingoWinner(), is (p));
    }

    @And("^\"([^\"]*)\" has been added at the line winner variable of the game \"([^\"]*)\"$")
    public void hasBeenAddedAtTheLineWinnerVariableOfTheGame(String email, String game) throws Throwable {
        Game g = gameRepository.findByName(game);
        Player p = (Player) playerRepository.findByEmail(email);
        assertThat(g.getLineWinner(), is (p));
    }


    @When("^player \"([^\"]*)\" sing line \"([^\"]*)\"$")
    public void playerSingLine(String email, String game) throws Throwable {
        Game g = gameRepository.findByName(game);
        Player p = (Player) playerRepository.findByEmail(email);
        JSONObject joc = new JSONObject();
        joc.put("lineWinner", p.getUri());
        stepDefs.result = stepDefs.mockMvc.perform(
                patch("/games/{id}", g.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(joc.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @When("^player \"([^\"]*)\" sing bingo \"([^\"]*)\"$")
    public void playerSingBingo(String email, String game) throws Throwable {
        Game g = gameRepository.findByName(game);
        Player p = (Player) playerRepository.findByEmail(email);
        JSONObject joc = new JSONObject();
        joc.put("bingoWinner", p.getUri());
        stepDefs.result = stepDefs.mockMvc.perform(
                patch("/games/{id}", g.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(joc.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }
}
