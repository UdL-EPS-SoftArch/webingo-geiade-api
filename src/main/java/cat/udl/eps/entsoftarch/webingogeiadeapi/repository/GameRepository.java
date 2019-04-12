package cat.udl.eps.entsoftarch.webingogeiadeapi.repository;

import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Admin;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Game;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface GameRepository extends PagingAndSortingRepository<Game, Integer> {

  /**
   * Find the game by id.
   * @param id int that refers to the id of the game.
   * @return a game.
   */
  Game findById(int id);
}
