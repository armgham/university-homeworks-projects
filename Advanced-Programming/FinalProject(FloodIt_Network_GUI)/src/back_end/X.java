package back_end;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class X {
    static HashMap<Address, X> xs = new HashMap<>();

    Address a;
    Color c;

    boolean isAdded = false;


    public X(Color c, Address a){//klase marboot be har khane az bazi
        this.c = c;
        this.a = a;
        xs.put(a, this);
    }


    private static ArrayList<X> getAllFromW(X p){
        ArrayList<X> afw = new ArrayList<>();


        afw.add(p);
        p.isAdded = true;
        if(p.a.x > 0)//tabe bazgashti
            if(X.xs.get(new Address(p.a.x - 1, p.a.y)).c.equals(p.c) && !X.xs.get(new Address(p.a.x - 1, p.a.y)).isAdded)
                afw.addAll(getAllFromW(X.xs.get(new Address(p.a.x - 1, p.a.y))));
        if(p.a.y > 0)
            if(X.xs.get(new Address(p.a.x, p.a.y -1 )).c.equals(p.c) && !X.xs.get(new Address(p.a.x, p.a.y - 1)).isAdded)
                afw.addAll(getAllFromW(X.xs.get(new Address(p.a.x, p.a.y - 1))));
        if(p.a.x < Server.width - 1)
            if(X.xs.get(new Address(p.a.x + 1, p.a.y)).c.equals(p.c) && !X.xs.get(new Address(p.a.x + 1, p.a.y)).isAdded)
                afw.addAll(getAllFromW(X.xs.get(new Address(p.a.x + 1, p.a.y))));
        if(p.a.y < Server.height - 1)
            if(X.xs.get(new Address(p.a.x, p.a.y + 1)).c.equals(p.c) && !X.xs.get(new Address(p.a.x, p.a.y + 1)).isAdded)
                afw.addAll(getAllFromW(X.xs.get(new Address(p.a.x, p.a.y + 1))));
        return afw;
    }
    static ArrayList<X> getAllFrom(X p){
        ArrayList<X> af = new ArrayList<>();
        for(X x : getAllFromW(p)){
            af.add(x);
            x.isAdded = false;
        }
        return af;
    }
    static HashSet<X> getAllFromIfW(X p, Color c){
        HashSet<X> afiw = new HashSet<>();
        if(p.a.x > 0)
            if(X.xs.get(new Address(p.a.x - 1, p.a.y)).c.equals(c))
                afiw.addAll(getAllFromW(X.xs.get(new Address(p.a.x - 1, p.a.y))));
        if(p.a.y > 0)
            if(X.xs.get(new Address(p.a.x, p.a.y - 1)).c.equals(c))
                afiw.addAll(getAllFromW(X.xs.get(new Address(p.a.x, p.a.y - 1))));
        if(p.a.x < Server.width - 1)
            if(X.xs.get(new Address(p.a.x + 1, p.a.y)).c.equals(c))
                afiw.addAll(getAllFromW(X.xs.get(new Address(p.a.x + 1, p.a.y))));
        if(p.a.y < Server.height - 1)
            if(X.xs.get(new Address(p.a.x, p.a.y + 1)).c.equals(c))
                afiw.addAll(getAllFromW(X.xs.get(new Address(p.a.x, p.a.y + 1))));

        return afiw;
    }
    static HashSet<X> getAllFromIf(X p, Color c){
        HashSet<X> afi = new HashSet<>();
        for(X x : getAllFromIfW(p, c)){
            afi.add(x);
            x.isAdded = false;
        }
        return afi;
    }
    static ArrayList<X> getAllLeft(){
        return getAllFrom(X.xs.get(new Address(0,0)));
    }

    static ArrayList<X> getAllRight(){
        return getAllFrom(X.xs.get(new Address(Server.height - 1,Server.width - 1)));
    }
    static void setColorAllLeft(Color co){
        for(X x : getAllLeft())
            x.c = co;
    }

    static void setColorAllRight(Color co){
        for(X x : getAllRight())
            x.c = co;
    }
    public static boolean gameOver(){
        if(X.getAllRight().size() + X.getAllLeft().size() == Server.height*Server.width)
            return true;

        boolean res = true;
        int s = getAllFromIf(X.xs.get(new Address(0, 0)), Server.cs[0]).size();
        for (int i = 1; i < Server.cs.length; i++){
            if(s != getAllFromIf(X.xs.get(new Address(0, 0)), Server.cs[i]).size()) {
                if(Server.cs[i].equals(X.xs.get(new Address(Server.height - 1, Server.width - 1))))
                    continue;
                res = false;
            }
        }

        if(res)
            return true;
        res = true;
        s = getAllFromIf(X.xs.get(new Address(Server.height - 1, Server.width - 1)), Server.cs[0]).size();

        for (int i = 1; i < Server.cs.length; i++){
            if(s != getAllFromIf(X.xs.get(new Address(Server.height - 1, Server.width - 1)), Server.cs[i]).size())
                if(Server.cs[i].equals(X.xs.get(new Address(0, 0))))
                    continue;
                res = false;
        }
        if(res)
            return true;
        return false;
    }

}
