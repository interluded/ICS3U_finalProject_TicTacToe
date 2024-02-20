import javax.swing.*;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
            JFrame frame = new JFrame();
            frame.setBounds(0, 0, 900, 900);
            frame.setTitle("Tic Tac Toe");
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            tttGame game = new tttGame();
            frame.add(game);
            frame.setVisible(true);
        }
}