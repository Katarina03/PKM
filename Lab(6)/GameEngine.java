package ua.edu.chmnu.ki.networks.tcp.Lab06_Game;

import java.io.IOException;

public class GameEngine implements Runnable {
    Client client1;
    Client client2;

    //Выбор игры
    TicTacToe game;

    public GameEngine(Client client1, Client client2) throws IOException {
        //Ожидание клиента 1
        this.client1 = client1;
        //Ожидание клиента 2
        this.client2 = client2;

        //создание игры
        game = new TicTacToe();
    }

    @Override
    public void run() {

        //initial state
        client1.write("initial state\n" + game.arrayToString());
        client2.write("initial state\n" + game.arrayToString());

        boolean exit = false;
        boolean chance = true;
        String c1Mark = "x";
        String c2Mark = "o";

        while (!exit) {
            if (chance) {
                exit = play(client1, client2, c1Mark);
                chance = false;
            }
            else {
                exit = play(client2, client1, c2Mark);
                chance = true;
            }

        }
        client1.close();
        client2.close();
    }

    private boolean play(Client var1, Client var2, String mark) {
        String msg;
        try {
            boolean marked = false;
            while (!marked) {
                try {
                    var1.write("Please enter from 1-9: ");
                    msg = var1.read();    //считывание цифры
                    game.cardinal(msg, mark); //отсылка выбраного значения
                    marked = true;     // выходит при удачном ходе
                } catch (PositionAlreadyMarkedException ge) {
                    var1.write("Position already occupied\nSelect another");
                }
            }

            //Обновление информации у игроков
            var1.write(game.arrayToString());
            var2.write(game.arrayToString());

            var1.write("Wait for another player move.........");
            var2.write("Your move!...........");
        } catch (PlayerWonException pwe) {
            var1.write("\n............Victoria!!!.........\n.....You won " + pwe);
            var2.write("\n....Better luck next time....\n.You Lost " + pwe);
            var1.write(game.arrayToString());
            var2.write(game.arrayToString());
            return true;
        } catch (GameDrawException gde) {
            var1.write("...........Game Draw!...........");
            var2.write("...........Game Draw!...........");
            var1.write(game.arrayToString());
            var2.write(game.arrayToString());
            return true;
        }
        return false;
    }
}
