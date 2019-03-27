package cat.udl.eps.entsoftarch.webingogeiadeapi.domain;

import java.util.Collection;
import java.util.List;
import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Game extends UriEntity<Integer>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String name;

    private int [] nums;
    // private List<Card> cardsInGame, players;
    private double linePrice, bingoPrice;
    private Player lineWinner, bingoWinner;
    private int price;

    /**
     * This function returns a collection of player credentials.
     * Returns the collection of granted authority for the user.
     */
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_PLAYER");
    }

    @Override
    public Integer getId() {
        return id;
    }
}
