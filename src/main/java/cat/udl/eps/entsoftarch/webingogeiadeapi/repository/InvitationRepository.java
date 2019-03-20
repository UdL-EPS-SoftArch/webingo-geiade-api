package cat.udl.eps.entsoftarch.webingogeiadeapi.repository;

import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Game;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Invitation;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Admin;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource

public interface InvitationRepository extends PagingAndSortingRepository<Invitation, Integer> {

    /**
     * Find the list of admin users.
     * @param text String that refers to the name of the admin user.
     * @return a list of the admin users.
     */
    Invitation findById(int id);
}
