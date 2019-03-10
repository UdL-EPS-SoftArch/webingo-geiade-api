package cat.udl.eps.entsoftarch.webingogeiadeapi.domain;

import java.util.Collection;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

@Entity
public class Invitation {
    @Id @NotBlank
    private int id;
    private int id_game;
    private Player from, to;
    private int time;
    private boolean isUnderway;
}
