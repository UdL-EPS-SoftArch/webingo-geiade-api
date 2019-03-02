package cat.udl.eps.entsoftarch.webingogeiadeapi.domain;

import java.util.Collection;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.smartcardio.Card;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

@Entity
public class Game {
    @Id
    private int id;
    private int [] nums;
    // private List<Card> cardsInGame, players;
    private double linePrice, bingoPrice;
    private Player lineWinner, bingoWinner;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_PLAYER");
    }

}
