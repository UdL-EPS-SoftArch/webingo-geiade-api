package cat.udl.eps.entsoftarch.webingogeiadeapi.config;

import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Admin;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Card;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@Configuration
public class RepositoryRestConfig extends RepositoryRestConfigurerAdapter {
    @Autowired Environment environment;

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Admin.class);
        config.exposeIdsFor(Player.class);
        config.exposeIdsFor(Card.class);
    }
}