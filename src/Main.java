import javax.swing.*;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;



@SuppressWarnings({ "CallToPrintStackTrace", "deprecation" ,"ConstantValue" })
public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        boolean keyGenAsk = false;
        int hoster;
        boolean hosterisValid = false;
        boolean auth = false;
        boolean doBrowser = true;
        String linodeHost = "https://marclmao.site";
        String gCloudHost = "https://interluded.tech";
        String budWareHost = "https://indevelopment4.com";
        String url = "";
        System.out.println("Key needed for authentication, Generate? (Y/N)");
        String wantKey = scan.nextLine();
        if (wantKey.equalsIgnoreCase("Y"))
            keyGenAsk = true;
        else {
            System.exit(0);
        }


        if (keyGenAsk) {
            System.out.println("Press 1 for the host, Press 2 if you already have a key you would like to paste in: ");
            hoster = scan.nextInt();
            scan.nextLine();

            while (!hosterisValid) {
                if (hoster == 1) {
                    url = budWareHost;
                    hosterisValid = true;
                }
                else if(hoster == 2){
                    url = null;
                    doBrowser = false;
                    hosterisValid = true;
                }
                else {
                    System.out.println("No valid option was submitted");
                    System.out.println("Press 1 for the host, Press 2 if you already have a key you would like to paste in: ");
                }
                if (!hosterisValid) {
                    hoster = scan.nextInt();
                }
            }
            if (doBrowser) {
                if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();
                    try {
                        desktop.browse(new URI(url));
                    } catch (IOException | URISyntaxException e) {
                        e.printStackTrace();
                    }
                } else {
                    Runtime runtime = Runtime.getRuntime();
                    try {
                        runtime.exec("xdg-open " + url);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            System.out.println("Input Key:");
            String key = scan.nextLine();

            if (key.length() >= 12) {
                char char1 = key.charAt(5);
                char char2 = key.charAt(8);
                char char3 = key.charAt(11);

                int num1 = Character.getNumericValue(char1);
                int num2 = Character.getNumericValue(char2);
                int num3 = Character.getNumericValue(char3);

                double VuEabaAi23 = num1 + num2 + num3;
                double aASm2U = VuEabaAi23 / 2;
                if (key.length() == 12 && key.startsWith("KEY_") && aASm2U == 8) {
                    auth = true;
                    System.out.println("You have been authenticated.");
                }
            }
        }

        if (auth) {
            JFrame frame = new JFrame();
            frame.setBounds(0, 0, 900, 900);
            frame.setTitle("Rap Tac Toe");
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            tttGame game = new tttGame();
            frame.add(game);
            frame.setVisible(true);

        } else {
            System.out.println("Not authenticated");
        }
    }
}