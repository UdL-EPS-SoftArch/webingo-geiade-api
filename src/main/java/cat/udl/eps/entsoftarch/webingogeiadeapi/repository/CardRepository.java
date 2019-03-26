package cat.udl.eps.entsoftarch.webingogeiadeapi.repository;

import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Card;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CardRepository extends PagingAndSortingRepository<Card, Integer> {

  /**
   * Find the list of admin users.
   * @param id int that refers to the name of the admin user.
   * @return a list of the admin users.
   */
  //List<Game> findByNameContaining(@Param("text") String text);
  Card findById(int id);
}
