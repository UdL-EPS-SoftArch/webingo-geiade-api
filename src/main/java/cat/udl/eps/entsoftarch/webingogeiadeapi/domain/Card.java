package cat.udl.eps.entsoftarch.webingogeiadeapi.domain;

import java.util.*;
import javax.persistence.*;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import static java.util.Arrays.sort;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Card {
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

    public List<Integer> randomcard(){
        Random rand = new Random();
        int [][] numeros = new int [3][5];
        for (int i=0; i<3; i++){
            for (int j=0; j<5; j++){
                numeros[i][j]= 0;
            }
        }
        int temp;
        List<Integer> sortedList = new ArrayList<Integer>();;
        for (int i=0; i<3; i++){
            for (int j=0; j<5; j++){
                temp = rand.nextInt(100);
                while (isAlreadyAdded(numeros,temp)==true){
                    temp = rand.nextInt(100);
                }
                numeros[i][j]= temp;
                sortedList.add(numeros[i][j]);
            }
        }
        Collections.sort(sortedList);
       return sortedList;
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

