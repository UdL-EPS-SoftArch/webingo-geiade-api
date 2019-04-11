package cat.udl.eps.entsoftarch.webingogeiadeapi.domain;

import java.util.Collection;
import java.util.List;
import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

@Entity
public class Card {
    @Id
    private int id;
    private int [][] nums;
    private int price;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private Game game;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private Card card;
}
