package front_end;

import front_end.network.Client;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;


public class Main {// main haman client

    public static Timer t = new Timer();
    public static JLabel time = new JLabel();
    JFrame f = new JFrame();
    JPanel lp = new JPanel(new GridLayout(2, 3));

    public static HashMap<Address, JPanel> ps = new HashMap<>();
    JPanel cp = new JPanel();

    public static String records = "";
    Color[] c = new Color[6];
    JButton[] b = new JButton[6];

    Client client;



    public Main(int port){
        LoginPage ll = new LoginPage(this);

        while (ll.xx)
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        String per = ll.inf;
        cp.setLayout(new GridLayout(Integer.parseInt(ll.inf.split(",")[3]), Integer.parseInt(ll.inf.split(",")[2])));

        c[0] = Color.GREEN; c[1] = Color.RED; c[2] = Color.BLUE; c[3] = Color.ORANGE; c[4] = Color.PINK; c[5] = Color.BLACK;

        client = new Client(port, ll.inf.split(",")[0]);
        f.setLayout(new BorderLayout());

        //System.out.println(ll.inf);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        for(int i = 0; i < Integer.parseInt(ll.inf.split(",")[3]); i++){
            for(int j = 0; j < Integer.parseInt(ll.inf.split(",")[2]); j++){
                JPanel pp = new JPanel();
                pp.setBorder(new LineBorder(Color.WHITE, 1));
                ps.put(new Address(j, i), pp);
                cp.add(ps.get(new Address(j, i)));
            }
        }

        while (ll.yy){//jlogiri az baz shodane panjare jadid
            try {
                Thread.sleep(1);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        JPanel llp = new JPanel(new GridLayout(4, 1));
        try {

            Image image = ll.ic.getImage(); // transform it
            Image newimg = image.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
            ll.ic = new ImageIcon(newimg);
            llp.add(new JLabel(ll.ic));
        }catch (Exception e){}

        llp.add(lp);
        for(int i = 0; i < b.length; i++){
            b[i] = new JButton("");
            b[i].setBackground(c[i]);
            lp.add(b[i]);
        }
        llp.add(new JLabel(ll.inf.split(",")[0]));
        llp.add(Main.time);
        f.add(cp, BorderLayout.CENTER);
        f.add(llp, BorderLayout.WEST);

        JFrame f2 = new JFrame();//yek panjare jadid
        JButton bGame = new JButton("go to game");
        JButton bRecord = new JButton("Recors");
        JButton bExit = new JButton("save and exit");
        f2.setLayout(new GridLayout(3, 1));
        f2.add(bGame);
        f2.add(bRecord);
        f2.add(bExit);
        bGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f2.setVisible(false);
            }
        });
        bRecord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.clientConnection.sendMessage("record");

                try {
                    JOptionPane.showMessageDialog(null, Main.records);
                }catch (Exception ee){ee.printStackTrace();}

            }
        });
        bExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.clientConnection.sendMessage("save");
                System.exit(0);
            }
        });

        f2.setSize(200, 200);
        f2.setVisible(true);
        while (f2.isVisible()){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        b[0].addActionListener(actionEvent -> action(c[0]));
        b[1].addActionListener(actionEvent -> action(c[1]));//actonlistener ha
        b[2].addActionListener(actionEvent -> action(c[2]));
        b[3].addActionListener(actionEvent -> action(c[3]));
        b[4].addActionListener(actionEvent -> action(c[4]));
        b[5].addActionListener(actionEvent -> action(c[5]));

        JButton bBack = new JButton("back");
        bBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f2.setVisible(true);
                f.setVisible(false);
            }
        });
        f.add(bBack, BorderLayout.SOUTH);
        f.setSize(600, 600);
        f.setVisible(true);

    }


    public void action(Color color){
        client.clientConnection.sendMessage("color");
        client.clientConnection.sendMessage(color);
    }


    public static void main(String[] args){

        new Main(6363);
    }
}
