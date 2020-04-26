package back_end;

import java.io.Serializable;
import java.util.ArrayList;

public class Person implements Comparable<Person>, Serializable {

    public static ArrayList<Person> ps = new ArrayList<>();// klase marboot be bazikon
    String name;

    String imageAdd;
    Integer score = 0;

    public Person(String name, String imageAdd){
        this.name = name;
        this.imageAdd = imageAdd;
        ps.add(this);

    }
    public static String getAllString(){
        String a = "";
        for(Person p : Person.ps)
            a += p.name + "  " + p.score +"\n";
        return a;
    }

    public static int Score(int t){
        return t;
    }
    public void setScore(Integer score){
        this.score = score;
    }
    @Override
    public int compareTo(Person p) {
        return this.score.compareTo(p.score);
    }
}
