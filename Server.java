package server;
import java.net.*;
import java.io.*;



public class Server extends Thread {
    
    private ServerSocket serverSocket;
    private double a;
    private double b;
    private double h;
    private double result;
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    
    
    public Server(int port) {
    
        try {
        serverSocket = new ServerSocket(port);
        } catch (IOException ex) {
        System.out.println(ex);
        }
    
    }
    
    public double Integral(double x) {
        return 1/x;
    }
    
    @Override
    public void run() {
        while (true) {
            try {
            System.out.println("Waiting for connection to port " + ANSI_BLUE +
               serverSocket.getLocalPort() + ANSI_RESET + "...");
            
            Socket socket = serverSocket.accept();
            System.out.println(ANSI_GREEN + "The client is successfully connected to the server!\n");
            
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            
            
            int count = in.readInt();
            //System.out.println("Iterations: " + count);
            for (int j = 0; j < count; j++) {
                a = in.readDouble();
                b = in.readDouble();
                h = in.readDouble();
                
                System.out.println(ANSI_RESET + "The server received the following data from the client: " + ANSI_RED + a + ANSI_RESET + 
                        ", " + ANSI_GREEN + b + ANSI_RESET + ", " + ANSI_BLUE + h);
                
                double ost=(b-a)%h;
                double sum = h*(0.5*(Integral(a)+Integral(a+ost)));
                a=a+ost;
        
                while (a<=b) {
                    sum = sum + h*(0.5*(Integral(a) + Integral(a+h)));
                    a=a+h;
                }
                result = sum;
               
                out.writeDouble(result);
                System.out.println(ANSI_RESET + "The server sent the result: " + ANSI_GREEN + result +  ANSI_RESET + "\n");
                
            }
            
            socket.close();
            
            } catch (IOException ex) {
                System.out.println(ex);
            }
            
        }
       
    }
    
    public static void main(String[] args) {
        int port = 8000;
        
        Thread thread = new Server(port);
        thread.start();
        
    }
    
}
