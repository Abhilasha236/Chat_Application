package chat.application;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.text.*;
import java.net.*;

public class Server {
    JTextField text;
    JPanel p2;
    static Box vertical =Box.createVerticalBox();
    static JFrame f=new JFrame();
    static DataOutputStream dout;

    Server(){
        f.setLayout(null);
        JPanel p1=new JPanel();
        p1.setBackground(new Color(228,107,50));
        p1.setBounds(0,0,350,60);
        p1.setLayout(null);
        f.add(p1);

        ImageIcon i1= new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2=i1.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);
        JLabel arr=new JLabel(i3);
        arr.setBounds(5,20,20,20);
        p1.add(arr);

        arr.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });

        ImageIcon i4= new ImageIcon(ClassLoader.getSystemResource("icons/dp.jpg"));
        Image i5=i4.getImage().getScaledInstance(40,40,Image.SCALE_DEFAULT);
        ImageIcon i6=new ImageIcon(i5);
        JLabel dp=new JLabel(i6);
        dp.setBounds(30,9,40,40);
        p1.add(dp);

        ImageIcon i7= new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8=i7.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i9=new ImageIcon(i8);
        JLabel video=new JLabel(i9);
        video.setBounds(215,17,25,25);
        p1.add(video);

        ImageIcon i10= new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11=i10.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i12=new ImageIcon(i11);
        JLabel phone=new JLabel(i12);
        phone.setBounds(265,17,25,25);
        p1.add(phone);

        ImageIcon i13= new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14=i13.getImage().getScaledInstance(10,20,Image.SCALE_DEFAULT);
        ImageIcon i15=new ImageIcon(i14);
        JLabel dots=new JLabel(i15);
        dots.setBounds(310,20,10,20);
        p1.add(dots);

        JLabel name=new JLabel("David");
        name.setBounds(85,16,100,15);
        name.setForeground(Color.white);
        name.setFont(new Font("Times_Roman",Font.BOLD,18));
        p1.add(name);

        JLabel status=new JLabel("online");
        status.setBounds(90,36,100,15);
        status.setForeground(Color.white);
        status.setFont(new Font("SAN_SERIF",Font.ITALIC,10));
        p1.add(status);

        p2=new JPanel();
        p2.setBounds(5,65,340,485);
        f.add(p2);

        text=new JTextField();
        text.setBounds(5,555,285,40);
        text.setFont(new Font("SAN_SERIF",Font.PLAIN,18));
        f.add(text);

        ImageIcon send= new ImageIcon(ClassLoader.getSystemResource("icons/send.png"));
        Image send1=send.getImage().getScaledInstance(40,25,Image.SCALE_DEFAULT);
        ImageIcon send2=new ImageIcon(send1);
        JLabel arr1=new JLabel(send2);
        arr1.setBounds(295,562,40,25);
        f.add(arr1);

        f.setSize(350,600);
        f.setLocation(200,50);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.white);
        f.setVisible(true);

        arr1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ae) {
                try {
                    String msg = text.getText();

                    JPanel p3 = formatLabel(msg);

                    p2.setLayout(new BorderLayout());

                    JPanel right = new JPanel(new BorderLayout());
                    right.add(p3, BorderLayout.LINE_END);
                    vertical.add(right);
                    vertical.add(Box.createVerticalStrut(15));

                    p2.add(vertical, BorderLayout.PAGE_START);

                    dout.writeUTF(msg);

                    text.setText(" ");

                    f.repaint();
                    f.invalidate();
                    f.validate();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    public static JPanel formatLabel(String msg){
        JPanel panel=new JPanel();
        panel.setLayout((new BoxLayout(panel,BoxLayout.Y_AXIS)));
        JLabel op= new JLabel("<html><p style=\"width:100px\">" + msg + "</p></html>");
        op.setFont(new Font("Thoma",Font.BOLD,16));
        op.setBackground(new Color(0,255,255));
        op.setOpaque(true);
        op.setBorder(new EmptyBorder(10,15,10,50));
        panel.add(op);

        Calendar cal= Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");

        JLabel time=new JLabel();
        time.setText(sdf.format(cal.getTime()));
        time.setFont(new Font("Thoma",Font.PLAIN,10));
        panel.add(time);

        return panel;
    }

    public static void main(String[] args){
        new Server();

        try{
            ServerSocket skt=new ServerSocket(5000);
            while(true){
                Socket s=skt.accept();
                DataInputStream din=new DataInputStream(s.getInputStream());
                dout=new DataOutputStream((s.getOutputStream()));

                while(true){
                    String mssg=din.readUTF();
                    JPanel panel=formatLabel(mssg);

                    JPanel left=new JPanel((new BorderLayout()));
                    left.add(panel,BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();

                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

