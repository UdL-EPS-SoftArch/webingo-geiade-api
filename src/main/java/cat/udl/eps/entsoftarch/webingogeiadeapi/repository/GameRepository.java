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
   * Find the list of admin users.
   * @param text String that refers to the name of the admin user.
   * @return a list of the admin users.
   */
  //List<Game> findByNameContaining(@Param("text") String text);
  Game findById(int id);

  Game findByName(String name);
}
