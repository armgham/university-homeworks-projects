package front_end.network;


import java.net.Socket;

public class Client {
    Socket socket;//client ha
    public ClientConnection clientConnection;

    public String name;
    public Client(int port, String name){
        this.name = name;
        try {
            socket = new Socket("127.0.0.1", port);
            clientConnection = new ClientConnection(socket, this);
            clientConnection.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
