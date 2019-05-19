package cat.udl.eps.entsoftarch.webingogeiadeapi.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Invitation extends UriEntity<Integer>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int idGame;
    private boolean isUnderway;
    private boolean isAccepted;
    private boolean timeout;
    private String message;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private  Player playerInvited;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private Player playerWhoInvited;

    @Override
    public Integer getId() {return id;}
}
