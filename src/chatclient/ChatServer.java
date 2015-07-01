/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 *
 * @author Jonathan
 */
public class ChatServer {
    
    ArrayList clientOutputStreams;
    
    public class ClientHandler implements Runnable{
        BufferedReader reader;
        Socket sock;
        
        public ClientHandler(Socket clientSocket){
            try{
                sock = clientSocket;
                InputStreamReader iStream = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(iStream);
                
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        public void run(){
            String message;
            try{
                while((message = reader.readLine())!=null){
                    System.out.println("reading " + message);
                    tellEveryone(message);
                }
                
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

        public static void main (String[] args){
            new ChatServer().go();
        }
        
        public void go(){
            clientOutputStreams = new ArrayList();
            try{
                ServerSocket serverSock = new ServerSocket(5000);
                
                while(true){
                  Socket clientSocket = serverSock.accept();
                  PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                  clientOutputStreams.add(writer);
                  
                  Thread t = new Thread(new ClientHandler(clientSocket));
                  t.start();
                  System.out.println("got a connection.");
                }
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
        
        public void tellEveryone(String msg){
            
            Iterator it = clientOutputStreams.iterator();
            while(it.hasNext()){
                try{
                    PrintWriter writer = (PrintWriter) it.next();
                    writer.println(msg);
                    writer.flush();
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    
    
}
