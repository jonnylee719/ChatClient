/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author Jonathan
 */
public class ChatClient {
    
    private JTextField outgoing;
    private JTextArea incoming;
    private PrintWriter writer;
    private BufferedReader reader;
    private Socket sock;
    private Font bigFont;
    
    
    
    public void go(){
        bigFont = new Font("Comic Sans", Font.BOLD, 30);
        
        JFrame theFrame = new JFrame("Ludicrously simple chat client");
        theFrame.setFont(bigFont);
        theFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        JPanel mainPanel = new JPanel();
        
        outgoing = new JTextField(20);
        outgoing.setFont(bigFont);
        
        incoming = new JTextArea(15, 50);
        incoming.setLineWrap(true);
        incoming.setWrapStyleWord(true);
        incoming.setEditable(false);
        incoming.setFont(bigFont);
        
        JScrollPane qScroller = new JScrollPane(incoming);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        JButton send = new JButton("Send");
        send.setFont(bigFont);
        send.addActionListener(new mySendListener());
        
        Thread readerThread = new Thread(new IncomingReader());
        readerThread.start();
        
        mainPanel.add(incoming);
        mainPanel.add(outgoing);
        mainPanel.add(send);
        setUpSocket();
        theFrame.setSize(1000,900);
        theFrame.setLocation(1300, 700);
        theFrame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        theFrame.setVisible(true);
        
        
    }
    
    public void setUpSocket(){
        try{
            sock = new Socket("127.0.0.1", 5000);
            writer = new PrintWriter(sock.getOutputStream());
            InputStreamReader istream = new InputStreamReader(sock.getInputStream());
            reader = new BufferedReader(istream);
            
            System.out.println("Networking established");
            
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        new ChatClient().go();
        
    }

    public class mySendListener implements ActionListener {
        public void actionPerformed(ActionEvent a){
            writer.println(outgoing.getText());
            writer.flush();
        }
    }
    
    private class IncomingReader implements Runnable{
        
        public void run(){
            String message;
            try{
                while((message = reader.readLine())!= null){
                    System.out.println("read " + message);
                    message = reader.readLine();
                    
                }
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
        
    }
    
}
