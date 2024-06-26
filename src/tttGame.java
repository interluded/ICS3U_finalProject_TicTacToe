import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.util.Timer;
import java.util.TimerTask;
@SuppressWarnings({
        "CallToPrintStackTrace",
        "Convert2Lambda"
})
public class tttGame extends JPanel implements MouseListener {

    Image OnePstart;
    Image TwoPstart;

    Image ShopImage;

    boolean valid_move = false;
    String ADtxtContent = "";

    boolean foundWinner = false;
    String DLtxtContent = "";
    Image AD_Small;
    Image NS_Small;
    Image NoStylist;
    Image AmericanDream;
    Image title;
    Image background;
    Image xImage;
    Image oImage;
    boolean easy_mode = true;
    int screen = 1;
    int players;
    Image tieImage;
    Image p1Win;
    Image p2Win;
    Image DieLit_Small;
    Image DieLit;

    Clip clip;
    boolean AD_owned;
    boolean DL_owned;

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
    int coins;

    private Timer timer;

    private Timer timer5Min;

    public tttGame() {

        addMouseListener(this);
        FileToStringReader reader = new FileToStringReader();
        String fileContents = reader.readFileToString("/Users/marcuskongjika/Downloads/ICS3U_finalProject_TicTacToe/src/coins.txt");
        System.out.println("File Contents:");
        System.out.println(fileContents);
        int coinsMethod = Integer.parseInt(fileContents);
        System.out.println(coinsMethod);

        FileToStringReader reader1 = new FileToStringReader();
        ADtxtContent = reader1.readFileToString("/Users/marcuskongjika/Downloads/ICS3U_finalProject_TicTacToe/src/AD.txt");
        System.out.println("File Contents:");
        System.out.println(ADtxtContent);

        FileToStringReader reader2 = new FileToStringReader();
        DLtxtContent = reader2.readFileToString("/Users/marcuskongjika/Downloads/ICS3U_finalProject_TicTacToe/src/DL.txt");
        System.out.println("File Contents:");
        System.out.println(DLtxtContent);

        coins = coinsMethod;
        JButton toggleMusicButton;
        setLayout(null);

        toggleMusicButton = new JButton("Toggle Music");
        toggleMusicButton.setBounds(10, 10, 120, 30); //  the button in the top left corner
        add(toggleMusicButton);

        // runs on shutdown, to save coin value to coins.txt.
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                fileWriter(); // call fileWriter on shutdown
            }
        }));

        // actionlistner for invisible JButton.
        toggleMusicButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (clip.isRunning()) {
                    clip.stop();

                } else {
                    clip.start();
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                }
            }
        });

        // Loads Images
        try {
            title = ImageIO.read(new File("logoTRANSPARENT.png"));
            background = ImageIO.read(new File("NS.png"));
            NoStylist = ImageIO.read(new File("NS.png"));
            xImage = ImageIO.read(new File("Utopia.png"));
            oImage = ImageIO.read(new File("liltecca.png"));
            p1Win = ImageIO.read(new File("winner.png"));
            p2Win = ImageIO.read(new File("winner2.png"));
            tieImage = ImageIO.read(new File("tieGame.png"));
            DieLit_Small = ImageIO.read(new File("DieLit.jpg"));
            DieLit = ImageIO.read(new File("bg.png"));
            AmericanDream = ImageIO.read(new File("AD.png"));
            NS_Small = ImageIO.read(new File("NS_Small.png"));
            AD_Small = ImageIO.read(new File("AD_Small.png"));
            OnePstart = ImageIO.read(new File("p1.png"));
            TwoPstart = ImageIO.read(new File("p2.png"));
            ShopImage = ImageIO.read(new File("shop.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            File soundFile = new File("Bane.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException e) {
            System.out.println("Unsupported audio file found. use a WAV file.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            System.out.println("LineUnavailableException.");
            e.printStackTrace();
        }

        // starts new timer
        timer = new Timer();
        // makes a new task based on the timer
        timer.scheduleAtFixedRate(new TimerTask() {
            // thread start
            public void run() {
                checkWinner();
                repaint();
            }
        }, 0, 100); // 0  delay at the start, 1 second between calls. starts before game starts but its fine it doesnt take up too many resources.

        //timer for every 5 minutes played for coins.
        timer5Min = new Timer();
        timer5Min.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                coins = coins + 5;
                System.out.println("Coins incremented by 5, total now: " + coins);
            }
        }, 300000, 300000);
    }

    public void paint(Graphics g) {
        if (screen == 1) {
            startScreen(g);
            musicButton(g);
        } else if (screen == 2) {
            drawBoard(g);
        } else if (screen == 3) {
            drawP1Win(g);
        } else if (screen == 4) {
            drawP2Win(g);
        } else if (screen == 5) {
            drawTieGame(g);
        } else if (screen == 6) {
            drawSelectPlayer(g);
            if (players == 1) {
                g.setColor(Color.ORANGE);
                g.fillRoundRect(350, 500, 200, 50, 20, 20);
                g.setColor(Color.BLACK);

                g.drawString("Select Difficulty:", 360, 530); // Adjust x y

                if (easy_mode) {
                    g.drawString("Easy Mode", 450, 530);
                } else if (!easy_mode) {
                    g.drawString("Hard Mode", 450, 530);
                }
            }
        } else if (screen == 7) {
            shopScreen(g);
        }
    }
    // draws the select player screen
    public void drawSelectPlayer(Graphics g) {
        g.drawImage(background, 0, 0, null);
        g.drawImage(title, 300, 200, null);

        //draw 1p button
        g.setColor(Color.BLUE);
        g.fillRoundRect(100, 630, 200, 50, 20, 20);
        g.setColor(Color.WHITE);
        g.drawString("Utopia as Player 1", 150, 655);

        //draw 2p button
        g.setColor(Color.blue);
        g.fillRoundRect(550, 630, 200, 50, 20, 20);
        g.setColor(Color.WHITE);
        g.drawString("TEC as player 1", 600, 655);
    }

    // draws the shop screen, doesnt handle any of the transactions
    public void shopScreen(Graphics g) {
        g.drawImage(background, 0, 0, null);
        g.setColor(Color.WHITE);
        Font myFont = new Font("Courier New", 1, 20);
        g.setFont(myFont);
        g.drawString("Buy for 10 Coins!", 150, 250);
        g.drawImage(AD_Small, 150, 300, null);
        g.setColor(Color.WHITE);
        g.drawString("Buy for 20 Coins!", 500, 250);
        g.drawImage(DieLit_Small, 500, 300, null);
        g.setColor(Color.BLACK);
        g.fillRoundRect(300, 750, 300, 100, 20, 20);
        g.setColor(Color.WHITE);
        g.drawString("Return to Start Screen ", 320, 800);
        g.fillRoundRect(300, 650, 300, 100, 20, 20);
        g.setColor(Color.black);
        Font font2 = new Font("Courier New", 1, 15);
        g.setFont(font2);
        g.drawString("Return to default background", 320, 700);

    }

    //JButton doesnt like showing for some reason (on mac os), so i decided to put this there as it's not visable.
    public void musicButton(Graphics g) {
        g.fillRoundRect(0, 0, 80, 30, 20, 20);
        g.setColor(Color.BLACK);
        g.drawString("Toggle Music", 0, 20);
    }

    // inital screen.
    public void startScreen(Graphics g) {
        g.drawImage(background, 0, 0, null);
        g.drawImage(title, 300, 200, null);
        Font font3 = new Font("Courier New", 1, 50);
        g.setFont(font3);
        g.setColor(Color.WHITE);
        g.drawString("Rap Tac Toe", 300, 200);
        Font myFont2 = new Font("Courier New", 1, 10);
        g.setFont(myFont2);
        //draw 1p button
        g.drawImage(OnePstart, 120, 655, null);

        // draw shop button
        g.drawImage(ShopImage, 350, 700, null);
        g.setColor(Color.white);

        g.drawString("COINS:" + coins, 350, 720);

        //draw 2p button
        g.drawImage(TwoPstart, 610, 655, null);

    }

    // draws the board along with drawing images.
    public void drawBoard(Graphics g) {

        g.drawImage(background, 0, 0, null);
        g.setColor(Color.WHITE);
        g.fillRoundRect(298, 0, 4, 900, 20, 20);
        g.fillRoundRect(598, 0, 4, 900, 20, 20);
        g.fillRoundRect(0, 298, 900, 4, 20, 20);
        g.fillRoundRect(0, 598, 900, 4, 20, 20);

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
    }

    // checks for a winner.
    public void checkWinner() {
        // p1 wi

        //P2 WINS
        if (a == 2 && b == 2 && c == 2) {
            screen = 4;
            coins = coins + 1;
            foundWinner = true;
        } else if (d == 2 && e1 == 2 && f == 2) {
            screen = 4;
            coins = coins + 1;
            foundWinner = true;
        } else if (g1 == 2 && h == 2 && i == 2) {
            screen = 4;
            coins = coins + 1;
            foundWinner = true;
        } else if (g1 == 2 && e1 == 2 && c == 2) {
            screen = 4;
            coins = coins + 1;
            foundWinner = true;
        } else if (a == 2 && e1 == 2 && i == 2) {
            screen = 4;
            coins = coins + 1;
            foundWinner = true;
        } else if (a == 2 && d == 2 && g1 == 2) {
            screen = 4;
            coins = coins + 1;
            foundWinner = true;
        } else if (b == 2 && e1 == 2 && h == 2) {
            screen = 4;
            coins = coins + 1;
            foundWinner = true;
        } else if (c == 2 && f == 2 && i == 2) {
            screen = 4;
            coins = coins + 1;
            foundWinner = true;
        }
        if (a == 1 && b == 1 && c == 1) {
            screen = 3;
            coins = coins + 1;
            foundWinner = true;
        } else if (d == 1 && e1 == 1 && f == 1) {
            screen = 3;
            coins = coins + 1;
            foundWinner = true;
        } else if (g1 == 1 && h == 1 && i == 1) {
            screen = 3;
            coins = coins + 1;
            foundWinner = true;
        } else if (g1 == 1 && e1 == 1 && c == 1) {
            screen = 3;
            coins = coins + 1;
            foundWinner = true;
        } else if (a == 1 && e1 == 1 && i == 1) {
            screen = 3;
            coins = coins + 1;
            foundWinner = true;
        } else if (a == 1 && d == 1 && g1 == 1) {
            screen = 3;
            coins = coins + 1;
            foundWinner = true;
        } else if (b == 1 && e1 == 1 && h == 1) {
            screen = 3;
            coins = coins + 1;
            foundWinner = true;
        } else if (c == 1 && f == 1 && i == 1) {
            screen = 3;
            coins = coins + 1;
            foundWinner = true;
        }
        // END P1 Win Statements

        // END P2 WIN STATEMENTS

        //BEGIN TIE STATEMENTS

        if (screen != 3 && screen != 4) {
            if (a != 0 && b != 0 && c != 0 && d != 0 && e1 != 0 && f != 0 && g1 != 0 && h != 0 && i != 0) // All spots filled but no winner located. TIE.
            {
                screen = 5;
            }
        }

    }
    // unused mouseclicked method
    public void mouseClicked(MouseEvent e) {}

    // this is where  all the mouse movements happen
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        // back to title screen button logic
        if (screen == 3 || screen == 4 || screen == 5) {
            if (x >= 600 && x <= 700 && y >= 100 && y <= 150) {
                resetGame();
                screen = 1; // Go back to start screen
                repaint();
                return;
            }
        }
        // handles where you are going depending and where you click
        if (screen == 1) {
            if (x >= 120 && x <= 400 && y >= 655 && y <= 803) {
                screen = 6; // player select
                players = 1;

               // g.drawImage(OnePstart, 120, 655, null);
            } else if (x >= 610 && x <= 760 && y >= 655 && y <= 753) {
                screen = 6;
                players = 2;

                //         g.drawImage(TwoPstart, 610, 655, null);

            } else if (x >= 300 && x <= 600 && y >= 750 && y <= 850) {
                screen = 7;
            }

        } else if (screen == 6) { // Player select screen
            if (x >= 100 && x <= 400 && y >= 600 && y <= 700) {

                screen = 2;
                try {
                    xImage = ImageIO.read(new File("Utopia.png"));
                    oImage = ImageIO.read(new File("liltecca.png"));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            } else if (x >= 500 && x <= 800 && y >= 600 && y <= 700) {
                try {

                    // swapping around images if TEC button is pressed
                    xImage = ImageIO.read(new File("liltecca.png"));
                    oImage = ImageIO.read(new File("Utopia.png"));

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                screen = 2;
            }
            if (screen == 6) {
                // check if the click is within the bounds of the difficulty button
                if (x >= 350 && x <= 550 && y >= 500 && y <= 550) {
                    easy_mode = !easy_mode; // Toggle the difficulty mode
                    repaint(); // repaint the screen  visually show the change
                }
            }
        } else if (screen == 7) {

            if (x >= 150 && x <= 450 && y >= 300 && y <= 600) {
                if (!AD_owned && coins >= 10 && !ADtxtContent.equalsIgnoreCase("AD_owned")) {
                    coins = coins - 10;
                    background = AmericanDream;
                    System.out.println("coins changed from " + (coins + 10) + "to " + coins);
                    repaint();
                    fileWriterAD(); // calls the filewriter to write the AD.txt file
                    AD_owned = true;
                } else if (AD_owned || ADtxtContent.equalsIgnoreCase("AD_owned")) {
                    background = AmericanDream;
                    repaint();
                } else {
                    JOptionPane.showMessageDialog(this, "You do not have enough coins!");
                }
            }

            if (x >= 500 && x <= 800 && y >= 300 && y <= 618) {
                if (!DL_owned && !DLtxtContent.equalsIgnoreCase("DL_owned")) {
                    if (coins >= 20) {
                        coins -= 20; // Deduct coins
                        background = DieLit; // Update background
                        System.out.println("Coins changed to: " + coins);
                        repaint();
                        DL_owned = true; // Mark as owned
                        fileWriterDL(); // Write owner to file
                    } else {
                        JOptionPane.showMessageDialog(this, "You do not have enough coins!");
                    }
                } else {
                    background = DieLit; // Set background if already owned
                    repaint();
                }
                // default background setter
            } else if (x >= 300 && x <= 600 && y >= 650 && y <= 750) {
                background = NoStylist;
                repaint();
            } else if (x >= 300 && x <= 600 && y >= 750 && y <= 850) {
                screen = 1;
            }
        } else if (screen == 2 && players == 2) {
            // Spot 1
            if (x <= 300 && y <= 300 && a == 0) {
                if (turn) {
                    a = 1;
                    turn = false;
                } else {
                    a = 2;
                    turn = true;
                }
            }

            // Spot 2
            if (x > 300 && x <= 600 && y <= 300 && b == 0) {
                if (turn) {
                    b = 1;
                    turn = false;
                } else {
                    b = 2;
                    turn = true;
                }
            }

            // Spot 3
            if (x > 600 && y <= 300 && c == 0) {
                if (turn) {
                    c = 1;
                    turn = false;
                } else {
                    c = 2;
                    turn = true;
                }
            }

            // Spot 4
            if (x <= 300 && y > 300 && y <= 600 && d == 0) {
                if (turn) {
                    d = 1;
                    turn = false;
                } else {
                    d = 2;
                    turn = true;
                }
            }

            // Spot 5
            if (x > 300 && x <= 600 && y > 300 && y <= 600 && e1 == 0) {
                if (turn) {
                    e1 = 1;
                    turn = false;
                } else {
                    e1 = 2;
                    turn = true;
                }
            }

            // Spot 6
            if (x > 600 && y > 300 && y <= 600 && f == 0) {
                if (turn) {
                    f = 1;
                    turn = false;
                } else {
                    f = 2;
                    turn = true;
                }
            }

            // Spot 7
            if (x <= 300 && y > 600 && g1 == 0) {
                if (turn) {
                    g1 = 1;
                    turn = false;
                } else {
                    g1 = 2;
                    turn = true;
                }
            }

            // Spot 8
            if (x > 300 && x <= 600 && y > 600 && h == 0) {
                if (turn) {
                    h = 1;
                    turn = false;
                } else {
                    h = 2;
                    turn = true;
                }
            }

            // Spot 9
            if (x > 600 && y > 600 && i == 0) {
                if (turn) {
                    i = 1;
                    turn = false;
                } else {
                    i = 2;
                    turn = true;
                }
            }
        } else if (screen == 2 && players == 1) // one player game
        {
            // Spot 1
            if (x <= 300 && y <= 300 && a == 0) {
                if (turn) {
                    a = 1;
                    turn = false;
                    valid_move = true;
                }
            }

            // Spot 2
            if (x > 300 && x <= 600 && y <= 300 && b == 0) {
                if (turn) {
                    b = 1;
                    turn = false;
                    valid_move = true;
                }
            }

            // Spot 3
            if (x > 600 && y <= 300 && c == 0) {
                if (turn) {
                    c = 1;
                    turn = false;
                    valid_move = true;
                }
            }

            // Spot 4
            if (x <= 300 && y > 300 && y <= 600 && d == 0) {
                if (turn) {
                    d = 1;
                    turn = false;
                    valid_move = true;
                }
            }

            // Spot 5
            if (x > 300 && x <= 600 && y > 300 && y <= 600 && e1 == 0) {
                if (turn) {
                    e1 = 1;
                    turn = false;
                    valid_move = true;
                }
            }

            // Spot 6
            if (x > 600 && y > 300 && y <= 600 && f == 0) {
                if (turn) {
                    f = 1;
                    turn = false;
                    valid_move = true;
                }
            }

            // Spot 7
            if (x <= 300 && y > 600 && g1 == 0) {
                if (turn) {
                    g1 = 1;
                    turn = false;
                    valid_move = true;
                }
            }

            // Spot 8
            if (x > 300 && x <= 600 && y > 600 && h == 0) {
                if (turn) {
                    h = 1;
                    turn = false;
                    valid_move = true;
                }
            }

            // Spot 9
            if (x > 600 && y > 600 && i == 0) {
                if (turn) {
                    i = 1;
                    turn = false;
                    valid_move = true;
                }
            }
        }
        repaint();

        if (valid_move) {
            if (players == 1 && !easy_mode) {
                if (a == 1 || b == 1 || c == 1 || d == 1 || e1 == 1 || f == 1 || h == 1 || g1 == 1 || i == 1)
                    computerMove();
                valid_move = false;
            }
            if (players == 1 && easy_mode && !foundWinner) {
                if (a == 1 || b == 1 || c == 1 || d == 1 || e1 == 1 || f == 1 || h == 1 || g1 == 1 || i == 1)
                    easyComputerMove();
                valid_move = false;
            }
        }
    }

    //resets the game to defaults
    public void resetGame() {
        // Resetting game state for a new game
        a = b = c = d = e1 = f = g1 = h = i = 0;
        foundWinner = false;
        turn = true; // Reset turn to player 1

    }

    // one player non random stratigized mover
    public void computerMove() {

        if (a == 2 && b == 2 && c == 0) {
            c = 2;
        } else if (a == 1 && g1 == 1 && d == 0) {
            d = 2;
        } else if (c == 1 && i == 1 && f == 0) {
            f = 2;
        } else if (b == 2 && c == 2 && d == 0) {
            f = 2;
        } else if (a == 2 && c == 2 && b == 0) {
            b = 2;
        } else if (b == 2 && c == 2 && a == 0) {
            a = 2;
        } else if (d == 2 && e1 == 2 && f == 0) {
            f = 2;
        } else if (d == 2 && f == 2 && e1 == 0) {
            e1 = 2;
        } else if (e1 == 2 && f == 2 && d == 0) {
            d = 2;
        } else if (g1 == 2 && h == 2 && i == 0) {
            i = 2;
        } else if (g1 == 2 && i == 2 && h == 0) {
            h = 2;
        } else if (h == 2 && i == 2 && g1 == 0) {
            g1 = 2;
        } else if (a == 2 && d == 2 && g1 == 0) {
            g1 = 2;
        } else if (a == 2 && g1 == 2 && d == 0) {
            d = 2;
        } else if (d == 2 && g1 == 2 && a == 0) {
            a = 2;
        } else if (b == 2 && e1 == 2 && h == 0) {
            h = 2;
        } else if (b == 2 && h == 2 && e1 == 0) {
            e1 = 2;
        } else if (e1 == 2 && h == 2 && b == 0) {
            b = 2;
        } else if (c == 2 && f == 2 && i == 0) {
            i = 2;
        } else if (c == 2 && i == 2 && f == 0) {
            f = 2;
        } else if (f == 2 && i == 2 && c == 0) {
            c = 2;
        } else if (a == 2 && e1 == 2 && i == 0) {
            i = 2;
        } else if (a == 2 && i == 2 && e1 == 0) {
            e1 = 2;
        } else if (e1 == 2 && i == 2 && a == 0) {
            a = 2;
        } else if (g1 == 2 && e1 == 2 && c == 0) {
            c = 2;
        } else if (g1 == 2 && c == 2 && e1 == 0) {
            e1 = 2;
        } else if (e1 == 2 && c == 2 && g1 == 0) {
            g1 = 2;
        } else if (a == 0 && b == 0 && c == 1 && d == 0 && e1 == 2 && f == 0 && g1 == 0 && h == 0 && i == 0) {
            h = 2;
        } else if (a == 1 && b == 0 && c == 0 && d == 0 && e1 == 2 && f == 0 && g1 == 0 && h == 0 && i == 0) {
            h = 2;
        }

        // blocks
        else if (a == 1 && b == 1 && c == 0) {
            c = 2;
        } else if (a == 1 && c == 1 && b == 0) {
            b = 2;
        } else if (b == 1 && c == 1 && a == 0) {
            a = 2;
        } else if (d == 1 && e1 == 1 && f == 0) {
            f = 2;
        } else if (d == 1 && f == 1 && e1 == 0) {
            e1 = 2;
        } else if (e1 == 1 && f == 1 && d == 0) {
            d = 2;
        } else if (g1 == 1 && h == 1 && i == 0) {
            i = 2;
        } else if (g1 == 1 && i == 1 && h == 0) {
            h = 2;
        } else if (h == 1 && i == 1 && g1 == 0) {
            g1 = 2;
        } else if (a == 1 && d == 1 && g1 == 0) {
            g1 = 2;
        } else if (a == 1 && g1 == 1 && d == 0) {
            d = 2;
        } else if (d == 1 && g1 == 1 && a == 0) {
            a = 2;
        } else if (b == 1 && e1 == 1 && h == 0) {
            h = 2;
        } else if (b == 1 && h == 1 && e1 == 0) {
            e1 = 2;
        } else if (e1 == 1 && h == 1 && b == 0) {
            b = 2;
        } else if (c == 1 && f == 1 && i == 0) {
            i = 2;
        } else if (c == 1 && i == 1 && f == 0) {
            f = 2;
        } else if (f == 1 && i == 1 && c == 0) {
            c = 2;
        } else if (a == 1 && e1 == 1 && i == 0) {
            i = 2;
        } else if (a == 1 && i == 1 && e1 == 0) {
            e1 = 2;
        } else if (e1 == 1 && i == 1 && a == 0) {
            a = 2;
        } else if (g1 == 1 && e1 == 1 && c == 0) {
            c = 2;
        } else if (g1 == 1 && c == 1 && e1 == 0) {
            e1 = 2;
        } else if (e1 == 1 && c == 1 && g1 == 0) {
            g1 = 2;
        }

        // go center
        else if (e1 == 0) {
            e1 = 2;
        }

        // corners
        else if (a == 0) {
            a = 2;
        } else if (c == 0) {
            c = 2;
        } else if (g1 == 0) {
            g1 = 2;
        } else if (i == 0) {
            i = 2;
        }

        // sides
        else if (b == 0) {
            b = 2;
        } else if (d == 0) {
            d = 2;
        } else if (f == 0) {
            f = 2;
        } else if (h == 0) {
            h = 2;
        }

        turn = true;
        repaint();
    }

    // easy computer move, (just random, 0 strategy involved)
    public void easyComputerMove() {
        if (players == 1 && easy_mode && !turn) { // computer move easy and computer turn
            boolean moveMade = false;
            int attempts = 0; //  avoids potential infinite loop if all spots are filled

            while (!moveMade && attempts < 50) { // limits to 50 rolls
                int tile = (int)(Math.random() * 9) + 1; // random tile select

                // Check if the selected tile is empty and place the marker
                if (tile == 1 && a == 0) {
                    a = 2;
                    moveMade = true;
                } else if (tile == 2 && b == 0) {
                    b = 2;
                    moveMade = true;
                } else if (tile == 3 && c == 0) {
                    c = 2;
                    moveMade = true;
                } else if (tile == 4 && d == 0) {
                    d = 2;
                    moveMade = true;
                } else if (tile == 5 && e1 == 0) {
                    e1 = 2;
                    moveMade = true;
                } else if (tile == 6 && f == 0) {
                    f = 2;
                    moveMade = true;
                } else if (tile == 7 && g1 == 0) {
                    g1 = 2;
                    moveMade = true;
                } else if (tile == 8 && h == 0) {
                    h = 2;
                    moveMade = true;
                } else if (tile == 9 && i == 0) {
                    i = 2;
                    moveMade = true;
                }

                attempts++;
            }

            if (moveMade) {
                turn = true; // change turn back to the player
                repaint(); // repaint the board to show the new move
            }
        }
    }

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    // draws player one win screen
    public void drawP1Win(Graphics g) {
        g.drawImage(background,0,0,null);
        g.drawImage(p1Win, 0, 0, null);
        //draw back button
        g.setColor(Color.RED);
        g.fillRoundRect(600, 100, 100, 50, 20, 20);
        g.setColor(Color.WHITE);
        g.drawString("Back", 620, 120);
    }

    // draws player 2 win screen
    public void drawP2Win(Graphics g) {
        g.drawImage(background,0,0,null);
        g.drawImage(p2Win, 0, 0, null);
        //draw back button
        g.setColor(Color.RED);
        g.fillRoundRect(600, 100, 100, 50, 20, 20);
        g.setColor(Color.WHITE);
        g.drawString("Back", 620, 120);
    }

    // draws the tie game screen
    public void drawTieGame(Graphics g) {
        g.drawImage(background,0,0,null);
        g.drawImage(tieImage, 0, 0, null);
        //draw back button
        g.setColor(Color.RED);
        g.fillRoundRect(600, 100, 100, 50, 20, 20);
        g.setColor(Color.WHITE);
        g.drawString("Back", 620, 120);
    }

    //reads text file in at end of the
    public String readTextFile(String filePath) {
        // starts the stringbuilder
        StringBuilder contentBuilder = new StringBuilder();
        // uses filereader to read charicter by charicter.
        try (FileReader reader = new FileReader(filePath)) {
            int character;
            while ((character = reader.read()) != -1) {
                contentBuilder.append((char) character);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    // writes coins value to same file on exit.
    public void fileWriter() {
        // specify the file name (full path needed)
        String fileName = "/Users/marcuskongjika/Downloads/ICS3U_finalProject_TicTacToe/src/coins.txt";
        String content = Integer.toString(coins);
        try {
            // creates new filewriter & buffered
            FileWriter fileWriter = new FileWriter(fileName, false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(content);
            //closes when done
            bufferedWriter.close();

            //debug
            System.out.println("file written successfully");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

    // writes the AD.txt file with "AD_owned" in order to show ownership.
    public void fileWriterAD() {
        // specify the file name (full path needed)
        String fileNameAD = "/Users/marcuskongjika/Downloads/ICS3U_finalProject_TicTacToe/src/AD.txt";
        String contentAD = "AD_owned";
        try {
            // creates new filewriter & buffered
            FileWriter fileWriter = new FileWriter(fileNameAD, false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(contentAD);
            //closes when done
            bufferedWriter.close();

            //debug
            System.out.println("file written successfully");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

    // writes the DL.txt file with "DL_owned" in order to show ownership
    public void fileWriterDL() {
        // specify the file name (full path needed)
        String fileNameDL = "/Users/marcuskongjika/Downloads/ICS3U_finalProject_TicTacToe/src/DL.txt";
        String contentDL = "DL_owned";
        try {
            // creates new filewriter & buffered
            FileWriter fileWriter = new FileWriter(fileNameDL, false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(contentDL);
            //closes when done
            bufferedWriter.close();
            //debug
            System.out.println("file written successfully");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
}
