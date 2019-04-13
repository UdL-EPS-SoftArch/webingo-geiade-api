package cat.udl.eps.entsoftarch.webingogeiadeapi.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

