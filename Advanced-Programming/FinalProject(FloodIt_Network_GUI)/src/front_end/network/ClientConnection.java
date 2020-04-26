package front_end.network;

import front_end.Address;
import front_end.Main;


import javax.swing.*;
import java.awt.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class ClientConnection extends Thread {// klase client coonection ke thred ast

    Socket socket;
    Client client;
    public ObjectInputStream ois;
    public ObjectOutputStream oos;

    public boolean isStart = false;

    public ClientConnection(Socket socket, Client client){
        this.client = client;
        this.socket = socket;


    }

    @Override
    public void run(){
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            while (true){
                while (ois.equals(null)){
                    Thread.sleep(1);
                }


                String gget = (String)ois.readObject();
                if(gget.equals("j")) {
                    //System.out.println("jjjjjjj");
                    convert((String) ois.readObject());
                }
                else if(gget.equals("wa")){
                    JOptionPane.showMessageDialog(null, (String)ois.readObject());
                }

                else if(gget.equals("finish")){// ertebat ba server
                    JOptionPane.showMessageDialog(null, client.name+" "+(String)ois.readObject());
                    sendMessage("time");
                    sendMessage(Main.time.getText());
                    Main.t.stop();
                }else if(gget.equals("rec")){
                    Main.records = (String)ois.readObject();
                }else if(gget.equals("start"))
                    Main.t.start();
                else if(gget.equals("score"))
                    JOptionPane.showMessageDialog(null, "SCORE : " + (int)ois.readObject());
                //for(HashMap<Address, >)


            }


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                socket.close();
                ois.close();
                oos.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void convert(String str){
        String[] ss = str.split(" ");
        for(int i = 0; i < ss.length; i++){
            String[] sx = ss[i].split(",");
           Address a= new Address(Integer.parseInt(sx[0]), Integer.parseInt(sx[1]));
            Main.ps.get(a).setBackground(colorOfCode(sx[2]));
            //System.out.println(Main.ps.get(a));
        }
    }
    Color colorOfCode(String str){
        if(str.equals("0")) //cs[0] = Color.GREEN; cs[1] = Color.RED; cs[2] = Color.BLUE; cs[3] = Color.ORANGE; cs[4] = Color.PINK; cs[5] = Color.BLACK;
            return Color.GREEN;
        else if(str.equals("1"))
            return Color.RED;
        else if(str.equals("2"))
            return Color.BLUE;
        else if(str.equals("3"))
            return Color.ORANGE;
        else if(str.equals("4"))
            return Color.PINK;
        return Color.BLACK;
    }
    public void sendMessage(Object message){

        try {
            oos.writeObject(message);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
