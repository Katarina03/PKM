package ua.edu.chmnu.ki.networks.tcp.Lab06_Game;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    Socket socket;
    InputStream is;
    OutputStream os;

    Client(Socket socket) throws IOException {
        this.socket = socket;
        is = socket.getInputStream();
        os = socket.getOutputStream();
    }


    public String read(){
        String msg = "";
        boolean exit = false;
        while (!exit){
            try {
                if (is.available() > 0) {
                    int d;
                    while ((d = is.read()) != 38) {
                        msg = msg + (char) d;
                    }
                    exit = true;
                }
            } catch (IOException e) {
                System.out.println("Error reading msg..........");
            }
        }
        return msg;
    }

    public void write(String msg){
        try {
            os.write((msg+"&").getBytes());
            os.flush();
        } catch (IOException e) {
            System.out.println("Error sending msg...........");
        }
    }

    public void close() {
        try {
            socket.close();
            os.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
