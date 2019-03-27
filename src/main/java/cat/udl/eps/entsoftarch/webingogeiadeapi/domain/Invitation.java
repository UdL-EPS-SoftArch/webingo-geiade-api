package cat.udl.eps.entsoftarch.webingogeiadeapi.domain;

import java.util.Collection;
import java.util.List;
import javax.persistence.*;
<<<<<<< HEAD
=======
import javax.validation.constraints.NotBlank;
>>>>>>> 93a63641f54c358f9a3c1a9fc5a633cd2bce1e21

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int id_game;
    private boolean isUnderway;
    private boolean isAccepted;
    private boolean timeout;
    private String message;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private  Player player_invited;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private Player player_who_invited;
}

