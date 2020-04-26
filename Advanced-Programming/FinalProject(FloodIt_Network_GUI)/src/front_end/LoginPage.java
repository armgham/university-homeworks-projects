package front_end;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame {//panjareh avval baraye login

    Main main;
    public Image im;
    public ImageIcon ic;
    public String inf = "";
    public String imaddr = "";
    public boolean xx = true;
    public boolean yy = true;
    public LoginPage(Main main){
        this.main = main;
        setLayout(new GridLayout(2, 1));

        JButton rb = new JButton("Register");

        add(rb);

        rb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                JFrame lf = new JFrame("register");
                JLabel lname = new JLabel("name : ");
                JTextArea tname = new JTextArea();
                lf.setLayout(new GridLayout(5, 2));
                JLabel limage = new JLabel("image address : ");
                JButton ib = new JButton("open");
                JLabel lwidth = new JLabel("width : ");
                JLabel lheight = new JLabel("height : ");
                JTextField tw = new JTextField();
                JTextField th = new JTextField();

                lf.add(lname);
                lf.add(tname);
                lf.add(limage);
                lf.add(ib);
                lf.add(lwidth);
                lf.add(tw);
                lf.add(lheight);
                lf.add(th);
                JButton b = new JButton("ok");

                ib.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JFileChooser ch = new JFileChooser();
                        lf.add(ch);
                        if (ch.showOpenDialog(lf) == JFileChooser.APPROVE_OPTION) {
                            System.out.println(ch.getSelectedFile());
                            try {
                                im = ImageIO.read(ch.getSelectedFile());
                                imaddr = ch.getSelectedFile().toString();
                            } catch (Exception e2) {
                            }
                            ic = new ImageIcon(im);
                        }
                    }
                });
                lf.add(b);
                b.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        xx = false;
                        inf = tname.getText()+","+imaddr+","+tw.getText()+","+th.getText();


                        JOptionPane.showMessageDialog(null, "ok!");
                        main.client.clientConnection.sendMessage("login");
                        main.client.clientConnection.sendMessage(inf);
                        yy = false;
                        lf.setVisible(false);


                    }
                });
                lf.setSize(200, 200);
                lf.setVisible(true);

            }
        });


        setSize(200, 200);
        setVisible(true);
    }


}
