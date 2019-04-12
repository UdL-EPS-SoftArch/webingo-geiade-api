package cat.udl.eps.entsoftarch.webingogeiadeapi.repository;

import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Player;
import java.util.List;

import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PlayerRepository extends PagingAndSortingRepository<Player, String> {
  /**
   * Find players with username containing given text.
   * @param text Text that should be included in retrieved players' usernames.
   * @return A list of players.
   */
  List<Player> findByUsernameContaining(@Param("text") String text);
  Player findByEmail(String email);
}
