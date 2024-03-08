import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
@SuppressWarnings({"CallToPrintStackTrace", "Convert2Lambda"})
public class tttGame extends JPanel implements MouseListener {

    Image title;
    Image background;
    Image xImage;
    Image oImage;
    int screen = 1;
    int players;
    Image tieImage;
    Image p1Win;
    Image p2Win;

    Clip clip;

    boolean actServer = false;
    String sendToServer = null;


    boolean turn = true; //decides who's turn it is (true = 1) (false = 0)

    //THIS IS ALL THE INTS FOR MAIN TABLE
    int a = 0;
    int b = 0;
    int c = 0;
    int d = 0;
    int e1 = 0;
    int f = 0;
    int g1 = 0;
    int h = 0;
    int i = 0;
    //END MAIN TABLE VARIABLES


    public tttGame() {
        addMouseListener(this);


        JButton toggleMusicButton;
        setLayout(null);

        toggleMusicButton = new JButton("Toggle Music");
        toggleMusicButton.setBounds(10, 10, 120, 30); //  the button in the top left corner
        add(toggleMusicButton);

        toggleMusicButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (clip.isRunning()) {
                    clip.stop();
                } else {
                    clip.start();
                    clip.loop(Clip.LOOP_CONTINUOUSLY); //looper
                }
            }
        });


        // Loads Images
        try {
            title = ImageIO.read(new File("logo.png"));
            background = ImageIO.read(new File("bg.png"));
            xImage = ImageIO.read(new File("earth.png"));
            oImage = ImageIO.read(new File("jupiter.png"));
            p1Win = ImageIO.read(new File("winner.png"));
            p2Win = ImageIO.read(new File("winner2.png"));
            tieImage = ImageIO.read(new File("tieGame.png"));
        } catch (IOException e) {
            System.out.println("IMAGE NOT FOUND, MAKE SURE YOU HAVE  \" logo.png \", \"bg.png\", \"earth.png\", \", and \" jupiter.png\" ");
        }


        //below code snippet found from StackOverFlow
        try {

            File soundFile = new File("gff.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();

            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            System.out.println("Unsupported audio file found. use a WAV file.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Cannot Find any Audio file. named \"gff.wav\"");
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            System.out.println("LineUnavailableException.");
            e.printStackTrace();
        }
    }

    //End Code snippet from StackOverFlow
    public void paint(Graphics g) {
        if (screen == 1) {
            startScreen(g);
            musicButton(g);

        } else if (screen == 2) {
            drawBoard(g);
            if (!actServer)
                NetworkingClient();
        } else if (screen == 3) {
            drawP1Win(g);

        } else if (screen == 4) {
            drawP2Win(g);

        } else if (screen == 5) {
            drawTieGame(g);

        } else if (screen == 6) {
            drawSelectPlayer(g);

        }

    }

    public void drawSelectPlayer(Graphics g) {
        g.drawImage(background, 0, 0, null);
        g.drawImage(title, 200, 0, null);

        //draw 1p button
        g.setColor(Color.blue);
        g.fillRect(100, 600, 300, 100);
        g.setColor(Color.WHITE);
        g.drawString("Earth (P1)", 210, 655);


        //draw 2p button
        g.setColor(Color.RED);
        g.fillRect(500, 600, 300, 100);
        g.setColor(Color.WHITE);
        g.drawString("Jupiter (P2)", 610, 655);
    }

    public void musicButton(Graphics g) {
        g.fillRect(0, 0, 80, 30);
        g.setColor(Color.BLACK);
        g.drawString("Toggle Music", 0, 20);
    }

    public void isServer(Graphics g) {
        g.fillRect(0, 0, 80, 30);
        g.setColor(Color.BLACK);
        g.drawString("Act as server (default = false)", 0, 60);
    }

    public void startScreen(Graphics g) {
        g.drawImage(background, 0, 0, null);
        g.drawImage(title, 200, 0, null);

        //draw 1p button
        g.setColor(Color.blue);
        g.fillRect(100, 600, 300, 100);
        g.setColor(Color.WHITE);
        g.drawString("1P START", 210, 655);


        //draw 2p button
        g.setColor(Color.RED);
        g.fillRect(500, 600, 300, 100);
        g.setColor(Color.WHITE);
        g.drawString("2P START", 610, 655);
    }

    public void drawBoard(Graphics g) {

        g.drawImage(background, 0, 0, null);
        g.setColor(Color.WHITE);
        g.fillRect(298, 0, 4, 900);
        g.fillRect(598, 0, 4, 900);
        g.fillRect(0, 298, 900, 4);
        g.fillRect(0, 598, 900, 4);

        // Spot #1
        if (a == 1) {
            g.drawImage(xImage, 0, 0, null);
        } else if (a == 2) {
            g.drawImage(oImage, 0, 0, null);
        }

        // Spot #2
        if (b == 1) {
            g.drawImage(xImage, 302, 0, null);
        } else if (b == 2) {
            g.drawImage(oImage, 302, 0, null);
        }
        // Spot #3
        if (c == 1) {
            g.drawImage(xImage, 602, 0, null);
        } else if (c == 2) {
            g.drawImage(oImage, 602, 0, null);
        }
        // Spot 4
        if (d == 1) {
            g.drawImage(xImage, 0, 300, null);
        } else if (d == 2) {
            g.drawImage(oImage, 0, 300, null);
        }
        //spot 5
        if (e1 == 1) {
            g.drawImage(xImage, 300, 300, null);
        } else if (e1 == 2) {
            g.drawImage(oImage, 300, 300, null);
        }
        //Spot 6
        if (f == 1) {
            g.drawImage(xImage, 600, 300, null);
        } else if (f == 2) {
            g.drawImage(oImage, 600, 300, null);
        }

        //spot 7

        if (g1 == 1) {
            g.drawImage(xImage, 0, 600, null);
        } else if (g1 == 2) {
            g.drawImage(oImage, 0, 600, null);
        }
        //spot 8
        if (h == 1) {
            g.drawImage(xImage, 300, 600, null);
        } else if (h == 2) {
            g.drawImage(oImage, 300, 600, null);
        }

        if (i == 1) {
            g.drawImage(xImage, 600, 600, null);
        } else if (i == 2) {
            g.drawImage(oImage, 600, 600, null);
        }

        checkWinner();
        //g.drawImage(xImage,0,0,null);
        //g.drawImage(oImage,0,0,null);
    }

    public void checkWinner() {
        // P1 Wins: Earth
        if (a == 1 && b == 1 && c == 1)
            screen = 3;

        else if (d == 1 && e1 == 1 && f == 1)
            screen = 3;

        else if (g1 == 1 && h == 1 && i == 1)
            screen = 3;

        else if (g1 == 1 && e1 == 1 && c == 1)
            screen = 3;

        else if (a == 1 && e1 == 1 && i == 1)
            screen = 3;

        else if (a == 1 && d == 1 && g1 == 1)
            screen = 3;

        else if (b == 1 && e1 == 1 && h == 1)
            screen = 3;

        else if (c == 1 && f == 1 && i == 1)
            screen = 3;

        // END P1 Win Statements

        //P2 WINS: JUPITER
        if (a == 2 && b == 2 && c == 2)
            screen = 4;

        else if (d == 2 && e1 == 2 && f == 2)
            screen = 4;

        else if (g1 == 2 && h == 2 && i == 2)
            screen = 4;

        else if (g1 == 2 && e1 == 2 && c == 2)
            screen = 4;

        else if (a == 2 && e1 == 2 && i == 2)
            screen = 4;

        else if (a == 2 && d == 2 && g1 == 2)
            screen = 4;

        else if (b == 2 && e1 == 2 && h == 2)
            screen = 4;

        else if (c == 2 && f == 2 && i == 2)
            screen = 4;
        // END P2 WIN STATEMENTS


        //BEGIN TIE STATEMENTS

        if (screen != 3 && screen != 4) {
            if (a != 0 && b != 0 && c != 0 && d != 0 && e1 != 0 && f != 0 && g1 != 0 && h != 0 && i != 0) // All spots filled but no winner located. TIE.
            {
                screen = 5;
            }
        }


    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        // back to title screen button logic enhancement
        if (screen == 3 || screen == 4 || screen == 5) {
            if (x >= 600 && x <= 700 && y >= 100 && y <= 150) {
                resetGame();
                screen = 1; // Go back to start screen
                repaint();
                return; // Exit the method to avoid further processing
            }
        }


        if (screen == 1) {
            if (x >= 100 && x <= 400 && y >= 600 && y <= 700) {
                screen = 6;
                players = 1;
            } else if (x >= 500 && x <= 800 && y >= 600 && y <= 700) {
                screen = 6;
                players = 2;

            } else if (x >= 0 && x <= 80 && y >= 0 && y <= 30) {
                actServer = !actServer;
                System.out.println(actServer);
            }


        } else if (screen == 6) { // Player select screen
            if (x >= 100 && x <= 400 && y >= 600 && y <= 700) {
                screen = 2;
            } else if (x >= 500 && x <= 800 && y >= 600 && y <= 700) {
                try {
                    xImage = ImageIO.read(new File("jupiter.png"));
                    oImage = ImageIO.read(new File("earth.png"));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                screen = 2;
            }
        } else if (screen == 2 && players == 2) {
            // Spot 1
            if (x <= 300 && y <= 300 && a == 0) {
                if (turn) {
                    a = 1;
                    turn = false;
                    sendToServer = "YTE=";
                } else {
                    a = 2;
                    turn = true;
                    sendToServer = "YTI=";
                }
            }

            // Spot 2
            if (x > 300 && x <= 600 && y <= 300 && b == 0) {
                if (turn) {
                    b = 1;
                    turn = false;
                    sendToServer = "YjE=";
                } else {
                    b = 2;
                    turn = true;
                    sendToServer = "YjI=";
                }
            }

            // Spot 3
            if (x > 600 && y <= 300 && c == 0) {
                if (turn) {
                    c = 1;
                    turn = false;
                    sendToServer = "YzE=";
                } else {
                    c = 2;
                    turn = true;
                    sendToServer = "YzI=";
                }
            }

            // Spot 4
            if (x <= 300 && y > 300 && y <= 600 && d == 0) {
                if (turn) {
                    d = 1;
                    turn = false;
                    sendToServer = "ZDE=";
                } else {
                    d = 2;
                    turn = true;
                    sendToServer = "ZDI";
                }
            }

            // Spot 5
            if (x > 300 && x <= 600 && y > 300 && y <= 600 && e1 == 0) {
                if (turn) {
                    e1 = 1;
                    turn = false;
                    sendToServer = "ZTE=";
                } else {
                    e1 = 2;
                    turn = true;
                    sendToServer = "ZTI=";
                }
            }

            // Spot 6
            if (x > 600 && y > 300 && y <= 600 && f == 0) {
                if (turn) {
                    f = 1;
                    turn = false;
                    sendToServer = "ZjE=";
                } else {
                    f = 2;
                    turn = true;
                    sendToServer = "ZjI=";
                }
            }

            // Spot 7
            if (x <= 300 && y > 600 && g1 == 0) {
                if (turn) {
                    g1 = 1;
                    turn = false;
                    sendToServer = "ZzE=";
                } else {
                    g1 = 2;
                    turn = true;
                    sendToServer = "ZzI=";
                }
            }

            // Spot 8
            if (x > 300 && x <= 600 && y > 600 && h == 0) {
                if (turn) {
                    h = 1;
                    turn = false;
                    sendToServer = "aDE=";
                } else {
                    h = 2;
                    turn = true;
                    sendToServer = "aDI=";
                }
            }

            // Spot 9
            if (x > 600 && y > 600 && i == 0) {
                if (turn) {
                    i = 1;
                    turn = false;
                    sendToServer = "ajE=";
                } else {
                    i = 2;
                    turn = true;
                    sendToServer = "aTI=";
                }
            }
        } else if (screen == 2 && players == 1) // one player game
        {
            // Spot 1
            if (x <= 300 && y <= 300 && a == 0) {
                if (turn) {
                    a = 1;
                    turn = false;
                }
            }

            // Spot 2
            if (x > 300 && x <= 600 && y <= 300 && b == 0) {
                if (turn) {
                    b = 1;
                    turn = false;
                }
            }

            // Spot 3
            if (x > 600 && y <= 300 && c == 0) {
                if (turn) {
                    c = 1;
                    turn = false;
                }
            }

            // Spot 4
            if (x <= 300 && y > 300 && y <= 600 && d == 0) {
                if (turn) {
                    d = 1;
                    turn = false;
                }
            }

            // Spot 5
            if (x > 300 && x <= 600 && y > 300 && y <= 600 && e1 == 0) {
                if (turn) {
                    e1 = 1;
                    turn = false;
                }
            }

            // Spot 6
            if (x > 600 && y > 300 && y <= 600 && f == 0) {
                if (turn) {
                    f = 1;
                    turn = false;
                }
            }

            // Spot 7
            if (x <= 300 && y > 600 && g1 == 0) {
                if (turn) {
                    g1 = 1;
                    turn = false;
                }
            }

            // Spot 8
            if (x > 300 && x <= 600 && y > 600 && h == 0) {
                if (turn) {
                    h = 1;
                    turn = false;
                }
            }

            // Spot 9
            if (x > 600 && y > 600 && i == 0) {
                if (turn) {
                    i = 1;
                    turn = false;
                }
            }
        }
        repaint();

        if (players == 1)
            if (a == 1 || b == 1 || c == 1 || d == 1 || e1 == 1 || f == 1 || h == 1 || g1 == 1 || i == 1)
                computerMove();
    }

    private void resetGame() {
        // Resetting game state for a new game
        a = b = c = d = e1 = f = g1 = h = i = 0;
        turn = true; // Reset turn to player 1
    }

    public void computerMove() {
        if (a == 2 && b == 2 && c == 0 && !turn) {
            c = 2;
        } else if (a == 2 && c == 2 && b == 0 && !turn) {
            b = 2;
        } else if (b == 2 && c == 2 && a == 0 && !turn) {
            a = 2;
        } else if (d == 2 && e1 == 2 && f == 0 && !turn) {
            f = 2;
        } else if (d == 2 && f == 2 && e1 == 0 && !turn) {
            e1 = 2;
        } else if (e1 == 2 && f == 2 && d == 0 && !turn) {
            d = 2;
        } else if (g1 == 2 && h == 2 && i == 0 && !turn) {
            i = 2;
        } else if (g1 == 2 && i == 2 && h == 0 && !turn) {
            h = 2;
        } else if (h == 2 && i == 2 && g1 == 0 && !turn) {
            g1 = 2;
        } else if (a == 2 && d == 2 && g1 == 0 && !turn) {
            g1 = 2;
        } else if (a == 2 && g1 == 2 && d == 0 && !turn) {
            d = 2;
        } else if (d == 2 && g1 == 2 && a == 0 && !turn) {
            a = 2;
        } else if (b == 2 && e1 == 2 && h == 0 && !turn) {
            h = 2;
        } else if (b == 2 && h == 2 && e1 == 0 && !turn) {
            e1 = 2;
        } else if (e1 == 2 && h == 2 && b == 0 && !turn) {
            b = 2;
        } else if (c == 2 && f == 2 && i == 0 && !turn) {
            i = 2;
        } else if (c == 2 && i == 2 && f == 0 && !turn) {
            f = 2;
        } else if (f == 2 && i == 2 && c == 0 && !turn) {
            c = 2;
        } else if (a == 2 && e1 == 2 && i == 0 && !turn) {
            i = 2;
        } else if (a == 2 && i == 2 && e1 == 0 && !turn) {
            e1 = 2;
        } else if (e1 == 2 && i == 2 && a == 0 && !turn) {
            a = 2;
        } else if (g1 == 2 && e1 == 2 && c == 0 && !turn) {
            c = 2;
        } else if (g1 == 2 && c == 2 && e1 == 0 && !turn) {
            e1 = 2;
        } else if (e1 == 2 && c == 2 && g1 == 0 && !turn) {
            g1 = 2;
        }

        // blocks
        else if (a == 1 && b == 1 && c == 0 && !turn) {
            c = 2;
        } else if (a == 1 && c == 1 && b == 0 && !turn) {
            b = 2;
        } else if (b == 1 && c == 1 && a == 0 && !turn) {
            a = 2;
        } else if (d == 1 && e1 == 1 && f == 0 && !turn) {
            f = 2;
        } else if (d == 1 && f == 1 && e1 == 0 && !turn) {
            e1 = 2;
        } else if (e1 == 1 && f == 1 && d == 0 && !turn) {
            d = 2;
        } else if (g1 == 1 && h == 1 && i == 0 && !turn) {
            i = 2;
        } else if (g1 == 1 && i == 1 && h == 0 && !turn) {
            h = 2;
        } else if (h == 1 && i == 1 && g1 == 0 && !turn) {
            g1 = 2;
        } else if (a == 1 && d == 1 && g1 == 0 && !turn) {
            g1 = 2;
        } else if (a == 1 && g1 == 1 && d == 0 && !turn) {
            d = 2;
        } else if (d == 1 && g1 == 1 && a == 0 && !turn) {
            a = 2;
        } else if (b == 1 && e1 == 1 && h == 0 && !turn) {
            h = 2;
        } else if (b == 1 && h == 1 && e1 == 0 && !turn) {
            e1 = 2;
        } else if (e1 == 1 && h == 1 && b == 0 && !turn) {
            b = 2;
        } else if (c == 1 && f == 1 && i == 0 && !turn) {
            i = 2;
        } else if (c == 1 && i == 1 && f == 0 && !turn) {
            f = 2;
        } else if (f == 1 && i == 1 && c == 0 && !turn) {
            c = 2;
        } else if (a == 1 && e1 == 1 && i == 0 && !turn) {
            i = 2;
        } else if (a == 1 && i == 1 && e1 == 0 && !turn) {
            e1 = 2;
        } else if (e1 == 1 && i == 1 && a == 0 && !turn) {
            a = 2;
        } else if (g1 == 1 && e1 == 1 && c == 0 && !turn) {
            c = 2;
        } else if (g1 == 1 && c == 1 && e1 == 0 && !turn) {
            e1 = 2;
        } else if (e1 == 1 && c == 1 && g1 == 0 && !turn) {
            g1 = 2;
        }

        // go center
        else if (e1 == 0 && !turn) {
            e1 = 2;
        }

        // corners
        else if (a == 0 && !turn) {
            a = 2;
        } else if (c == 0 && !turn) {
            c = 2;
        } else if (g1 == 0 && !turn) {
            g1 = 2;
        } else if (i == 0 && !turn) {
            i = 2;
        }

        // sides
        else if (b == 0 && !turn) {
            b = 2;
        } else if (d == 0 && !turn) {
            d = 2;
        } else if (f == 0 && !turn) {
            f = 2;
        } else if (h == 0 && !turn) {
            h = 2;
        }

        turn = true;
        repaint();
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void drawP1Win(Graphics g) {
        g.drawImage(p1Win, 0, 0, null);
        //draw back button
        g.setColor(Color.RED);
        g.fillRect(600, 100, 100, 50);
        g.setColor(Color.WHITE);
        g.drawString("Back", 620, 120);
    }

    public void drawP2Win(Graphics g) {
        g.drawImage(p2Win, 0, 0, null);
        //draw back button
        g.setColor(Color.RED);
        g.fillRect(600, 100, 100, 50);
        g.setColor(Color.WHITE);
        g.drawString("Back", 620, 120);
    }

    public void drawTieGame(Graphics g) {
        g.drawImage(tieImage, 0, 0, null);
        //draw back button
        g.setColor(Color.RED);
        g.fillRect(600, 100, 100, 50);
        g.setColor(Color.WHITE);
        g.drawString("Back", 620, 120);
    }

    public void NetworkingClient() {
        try {
            Socket clientSocket = new Socket("127.0.0.1", 8080);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


            // Sender thread
            Thread sender = new Thread(() -> {
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    String msgToSend = "default";
                    msgToSend = sendToServer;
                    int secondsToSleep = 3;
                    try {
                        Thread.sleep(secondsToSleep * 1000);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }


                    out.println(msgToSend);

                }
            });
            // Receiver thread

            /*
            FORMAT:
            a1 = spot 1 player 1
            a2 = spot 1 player 2
            so on so forth
             */
            Thread receiver = new Thread(() -> {

                try {
                    String msgFromServer = "default";
                    while ((msgFromServer = in.readLine()) != null) {
                        System.out.println("Server: " + msgFromServer);
                        if (msgFromServer.equals("YTE=")) {
                            a = 1;
                            repaint();
                            turn = !turn;
                        } else if (msgFromServer.equals("YjE=")) {
                            turn = !turn;
                            b = 1;
                            repaint();
                        } else if (msgFromServer.equals("YzE=")) {
                            turn = !turn;
                            c = 1;
                            repaint();
                        } else if (msgFromServer.equals("ZDE=")) {
                            turn = !turn;
                            d = 1;
                            repaint();
                        } else if (msgFromServer.equals("ZTE=")) {
                            turn = !turn;
                            e1 = 1;
                            repaint();
                        } else if (msgFromServer.equals("ZjE=")) {
                            turn = !turn;
                            f = 1;
                            repaint();
                        } else if (msgFromServer.equals("ZzE=")) {
                            turn = !turn;
                            g1 = 1;
                            repaint();
                        } else if (msgFromServer.equals("aDE=")) {
                            turn = !turn;
                            h = 1;
                            repaint();
                        } else if (msgFromServer.equals("ajE=")) {
                            turn = !turn;
                            i = 1;
                            repaint();
                        } else if (msgFromServer.equals("YTI=")) {
                            turn = !turn;
                            a = 2;
                            repaint();
                        } else if (msgFromServer.equals("YjI=")) {
                            turn = !turn;
                            b = 2;
                            repaint();
                        } else if (msgFromServer.equals("YzI=")) {
                            turn = !turn;
                            c = 2;
                            repaint();
                        } else if (msgFromServer.equals("ZDI=")) {
                            turn = !turn;
                            d = 2;
                            repaint();
                        } else if (msgFromServer.equals("ZTI=")) {
                            turn = !turn;
                            e1 = 2;
                            repaint();
                        } else if (msgFromServer.equals("ZjI=")) {
                            turn = !turn;
                            f = 2;
                            repaint();
                        } else if (msgFromServer.equals("ZzI=")) {
                            turn = !turn;
                            g1 = 2;
                            repaint();
                        } else if (msgFromServer.equals("aDI=")) {
                            turn = !turn;
                            h = 2;
                            repaint();
                        } else if (msgFromServer.equals("aTI=")) {
                            turn = !turn;
                            i = 2;
                            repaint();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Error reading from server: " + e.getMessage());
                }
            });

            sender.start();
            receiver.start();
        } catch (IOException e) {
            System.out.println("Could not connect to server: " + e.getMessage());
        }
    }

}