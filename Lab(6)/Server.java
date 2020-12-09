package ua.edu.chmnu.ki.networks.tcp.Lab06_Game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {

        int port = 5557;
        ServerSocket serverSocket = new ServerSocket(port);
        Socket socket = null;

        while (true) {
            //сокет с прослушиванием игрока 1
            System.out.println("waiting client 1...........");
            socket = serverSocket.accept();
            Client c1 = new Client(socket);
            System.out.println("client 1 joined..........");

            //сокет с прослушиванием игрока 2
            System.out.println("waiting client 2...........");
            socket = serverSocket.accept();
            Client c2 = new Client(socket);
            System.out.println("client 2 joined..........");

            //начало игры
            GameEngine ge = new GameEngine(c1, c2);
            ge.run();
        }
    }
}
