package cat.udl.eps.entsoftarch.webingogeiadeapi.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Game extends UriEntity<Integer>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String name;

    private boolean isFinished;
    private int numberofplayers;
    private int [] nums, randomList;
    private double linePrize, bingoPrize;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private Player lineWinner, bingoWinner;
    private int price;

    @Override
    public Integer getId() {
        return id;
    }

    public void generateRandoms(){
        this.randomList = new int [90];
        for (int x=0; x<this.randomList.length;x++){
            this.randomList[x]=x+1;
        }
        this.randomList = shuffleArray(this.randomList);
    }

    private static int [] shuffleArray(int [] array){
        List<Integer> list = new ArrayList<>();
        for (int i : array){
            list.add(i);
        }
        Collections.shuffle(list);

        for (int i = 0; i<list.size(); i++){
            array[i] = list.get(i);
        }
        return array;
    }
}

