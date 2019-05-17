package cat.udl.eps.entsoftarch.webingogeiadeapi.domain;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import javax.persistence.*;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Card extends UriEntity<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int [][] nums = new int [3][5];
    private int price;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private Game game;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private Player player;

    @Override
    public Integer getId() {return id;}

    public int[][] randomcard(){
        Random rand = new Random();
        int [][] numeros = new int [3][5];
        for (int i=0; i<3; i++){
            for (int j=0; j<5; j++){
                numeros[i][j]= 0;
            }
        }
        int temp;
        for (int i=0; i<3; i++){
            for (int j=0; j<5; j++){
                temp = rand.nextInt(100);
                while (isAlreadyAdded(numeros, temp)){
                    temp = rand.nextInt(100);
                }
                numeros[i][j]= temp;
            }
        }
        sortNumbers(numeros);
        return numeros;

    }

    private void sortNumbers (int [][] numeros){
        for (int i=0; i<3; i++){
            Arrays.sort(numeros[i]);}
    }

    private boolean isAlreadyAdded(int [][]nums, int x){
        for (int i=0; i<3; i++){
            for (int j=0; j<5; j++){
                if (x == nums[i][j]){
                    return true;
                }
            }
        }
        return false;
    }
}

