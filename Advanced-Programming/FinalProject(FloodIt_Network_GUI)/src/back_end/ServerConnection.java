package back_end;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

public class ServerConnection extends Thread {// klase server connection ke thred ast

    Server server;
    Socket socket;

    Person person;
    int name;

    String info;

    ObjectOutputStream oos;
    ObjectInputStream ois;

    public ServerConnection(Socket socket, int name, Server server){
        this.name = name;
        this.server = server;
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
                String sget = (String)ois.readObject();

                if(sget.equals("login")) {
                    FileInputStream fileInputStream = null;
                    ObjectInputStream objectInputStream = null;
                    try {
                        fileInputStream = new FileInputStream("persons.ser");
                        objectInputStream = new ObjectInputStream(fileInputStream);
                        Person.ps = (ArrayList<Person>)objectInputStream.readObject();
                        fileInputStream.close();
                    }catch (Exception e){
                    }
                    info = (String) ois.readObject();
                    person = new Person(info.split(",")[0], info.split(",")[1]);
                    Server.width = Integer.parseInt(info.split(",")[2]);
                    Server.height = Integer.parseInt(info.split(",")[3]);
                }

                if(sget.equals("record")){
                    sendMessage("rec");
                    sendMessage(Person.getAllString());
                }
                if(sget.equals("time")) {
                    int t = Integer.parseInt((String) ois.readObject());// ertebat ba client
                    sendMessage("score");
                    person.setScore(Person.Score(t));
                    sendMessage(Person.Score(t));
                }
                if(sget.equals("save")){
                    FileOutputStream fileOutputStream = null;
                    ObjectOutputStream objectOutputStream = null;
                    try {
                        fileOutputStream = new FileOutputStream("persons.ser");
                        objectOutputStream = new ObjectOutputStream(fileOutputStream);
                        objectOutputStream.writeObject(Person.ps);
                        fileOutputStream.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                if(sget.equals("color")) {
                    Color get = (Color) ois.readObject();
                    if (server.order == name) {
                        if (server.order == 0) {
                            if (!X.xs.get(new Address(Server.height - 1, Server.width - 1)).c.equals(get) && !X.xs.get(new Address(0, 0)).c.equals(get)) {
                                X.setColorAllLeft(get);
                                server.order = 1;
                            } else {
                                sendMessage("wa");
                                sendMessage("you cant choose your opponents color!");
                                continue;
                            }
                        } else {
                            if (!X.xs.get(new Address(0, 0)).c.equals(get) && !X.xs.get(new Address(Server.height - 1, Server.width - 1)).equals(get)) {
                                X.setColorAllRight(get);
                                server.order = 0;
                            }
                            else {
                                sendMessage("wa");
                                sendMessage("you cant choose your opponents color!");
                                continue;
                            }
                        }
                    }
                    else {
                        sendMessage("wa");
                        sendMessage("its not your turn!");
                        continue;
                    }
                        String j = "";
                        for (Map.Entry<Address, X> entry : X.xs.entrySet()) {
                            Address addr = entry.getKey();
                            X value = entry.getValue();
                            j += addr.x + "," + addr.y + "," + Server.codeOfColor(value.c) + " ";
                        }
                        if(X.gameOver()){
                            server.ps.get(0).sendMessage("finish");
                            server.ps.get(1).sendMessage("finish");

                            if(X.getAllLeft().size() > X.getAllRight().size()) {
                                server.ps.get(0).sendMessage("You Win!");
                                server.ps.get(1).sendMessage("You Lose!");
                                continue;
                            }else {
                                server.ps.get(0).sendMessage("You Lose!");
                                server.ps.get(1).sendMessage("You Win!");
                                continue;
                            }
                        }
                        server.ps.get(0).sendMessage("j");
                        server.ps.get(0).sendMessage(j);
                        server.ps.get(1).sendMessage("j");
                        server.ps.get(1).sendMessage(j);
                    }
                }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                socket.close();
                ois.close();
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void sendMessage(Object message){

        try {
            oos.writeObject(message);
            //System.out.print("             "+((JPanel)message).getBackground());
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
