package back_end;

import java.awt.*;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class Server {//server aslie bazi


    public static Color[] cs = new Color[6];

    Random r = new Random();
    public int order = 0;
    ServerSocket server;

    public static int width, height;

    ArrayList<ServerConnection> ps = new ArrayList<>();

    public Server(int port){
        cs[0] = Color.GREEN; cs[1] = Color.RED; cs[2] = Color.BLUE; cs[3] = Color.ORANGE; cs[4] = Color.PINK; cs[5] = Color.BLACK;




        try {
            server = new ServerSocket(port);
            System.out.println("is up");


            for (int i = 0; i < 2; i++){
                ps.add(new ServerConnection(server.accept(), i, this));
                ps.get(i).start();
                System.out.println("P "+ i + " is read");
            }

            ps.get(0).sendMessage("start");
            ps.get(1).sendMessage("start");//ertebat ba clent
            Thread.sleep(500);
            for(int i = 0; i < height; i++){
                for(int j = 0; j < width; j++){
                    new X(cs[r.nextInt(6)], new Address(j, i));
                }
            }
            String j = "";

            for(Map.Entry<Address, X> entry : X.xs.entrySet()) {
                Address addr = entry.getKey();
                X value = entry.getValue();
                j += addr.x+","+addr.y+","+codeOfColor(value.c)+" ";
            }


            ps.get(0).sendMessage("j");
            ps.get(0).sendMessage(j);
            Thread.sleep(100);
            ps.get(1).sendMessage("j");
            ps.get(1).sendMessage(j);

            System.out.println("by");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                server.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
    public static String codeOfColor(Color colo){
        if(colo.equals(Color.GREEN))
            return "0";
        else if(colo.equals(Color.RED))
            return "1";
        else if(colo.equals(Color.BLUE))
            return "2";
        else if(colo.equals(Color.ORANGE))
            return "3";
        else if(colo.equals(Color.PINK))
            return "4";
        else if(colo.equals(Color.BLACK))
            return "5";
        return "0";
    }
    public static void main(String[] args){
        new Server(6363);
    }
}
