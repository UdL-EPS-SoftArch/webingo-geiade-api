package cat.udl.eps.entsoftarch.webingogeiadeapi.config;

import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Admin;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Game;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.Player;
import cat.udl.eps.entsoftarch.webingogeiadeapi.domain.User;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.AdminRepository;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.GameRepository;
import cat.udl.eps.entsoftarch.webingogeiadeapi.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;

@Configuration
public class AuthenticationConfig extends GlobalAuthenticationConfigurerAdapter {

  @Value("${default-password}")
  String defaultPassword;

  @Autowired BasicUserDetailsService basicUserDetailsService;
  @Autowired AdminRepository adminRepository;
  @Autowired PlayerRepository playerRepository;
  @Autowired GameRepository gameRepository;

  @Override
  public void init(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .userDetailsService(basicUserDetailsService)
        .passwordEncoder(User.passwordEncoder);

    if (!adminRepository.existsById("admin")) {
      Admin admin = new Admin();
      admin.setEmail("admin@webingo.org");
      admin.setUsername("admin");
      admin.setPassword(defaultPassword);
      admin.encodePassword();
      adminRepository.save(admin);
    }
    if (!playerRepository.existsById("user")) {
      Player user = new Player();
      user.setEmail("user@webingo.org");
      user.setUsername("user");
      user.setPassword(defaultPassword);
      user.encodePassword();
      playerRepository.save(user);
    }
    if (!playerRepository.existsById("player1")) {
      Player player = new Player();
      player.setEmail("player1@webingo.org");
      player.setUsername("player1");
      player.setPassword(defaultPassword);
      player.encodePassword();
      playerRepository.save(player);
    }
    if (!playerRepository.existsById("player2")) {
      Player player2 = new Player();
      player2.setEmail("player2@webingo.org");
      player2.setUsername("player2");
      player2.setPassword(defaultPassword);
      player2.encodePassword();
      playerRepository.save(player2);
    }

    if (!playerRepository.existsById("player3")) {
      Player player = new Player();
      player.setEmail("player3@webingo.org");
      player.setUsername("player3");
      player.setPassword(defaultPassword);
      player.encodePassword();
      player.setWallet(100);
      playerRepository.save(player);
    }

    if (!gameRepository.existsById(1)) {
      Game game = new Game();
      game.setName("game1");
//      game.setId(3);
      gameRepository.save(game);
    }
  }
}
