package cat.udl.eps.entsoftarch.webingogeiadeapi.repository;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Game;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Invitation;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Player;

@RepositoryRestResource

public interface InvitationRepository extends PagingAndSortingRepository<Invitation, Integer> {

    Optional <Invitation> findByPlayerInvited (Player playerInvited);
}
