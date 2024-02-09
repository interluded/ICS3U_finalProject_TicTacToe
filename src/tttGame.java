import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;
public class tttGame extends JPanel implements MouseListener
{
    Image title;
    Image background;
    Image xImage;
    Image oImage;
    int screen = 1;
    int players;

    boolean turn = true; //decides whos turn it is (true = 1) (false = 0)

    //THIS IS ALL OF THE INTS FOR MAIN TABLE
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


    public tttGame(){
        addMouseListener(this);
        // Loads Images
        try
        {
        title = ImageIO.read(new File("logo.png"));
        background = ImageIO.read(new File( "bg.png"));
        xImage = ImageIO.read(new File("earth.png"));
        oImage = ImageIO.read(new File("jupiter.png"));
        }
        catch (IOException e){
            System.out.println("IMAGE NOT FOUND, MAKE SURE YOU HAVE  \" logo.png \", \"bg.png\", \"earth.png\", \", and \" jupiter.png\" ");
        }
    }

    public void paint(Graphics g){
        if(screen == 1){
            startScreen(g);
        }
        else if (screen== 2){
        drawBoard(g);
        }
    }

    public void startScreen(Graphics g){
        g.drawImage(background,0,0,null);
        g.drawImage(title,200,0,null);

        //draw 1p button
        g.setColor(Color.blue);
        g.fillRect(100,600,300,100);
        g.setColor(Color.WHITE);
        g.drawString("1P START",210,655);


        //draw 2p button
        g.setColor(Color.RED);
        g.fillRect(500,600,300,100);
        g.setColor(Color.WHITE);
        g.drawString("2P START",610,655);
    }
    public void drawBoard(Graphics g){

        g.drawImage(background, 0,0,null);
        g.setColor(Color.WHITE);
        g.fillRect(298,0,4,900);
        g.fillRect(598,0,4,900);
        g.fillRect(0,298,900, 4);
        g.fillRect(0, 598, 900, 4);

// Spot #1
        if (a==1){
            g.drawImage(xImage,0,0,null);
        }
        else if (a ==2){
            g.drawImage(oImage,0,0,null);
        }

// Spot #2
        if(b == 1){
            g.drawImage(xImage,302,0,null);
        }
        else if (b ==2){
            g.drawImage(oImage,302,0,null);
        }
        // Spot #3
        if(c == 1){
            g.drawImage(xImage,602,0,null);
        }
        else if (c ==2){
            g.drawImage(oImage,602,0,null);
        }
// Spot 4
        if(d == 1){
            g.drawImage(xImage,0,300,null);
        }
        else if (d ==2){
            g.drawImage(oImage,0,300,null);
        }
        //spot 5
        if(e1 == 1){
            g.drawImage(xImage,300,300,null);
        }
        else if (e1 ==2){
            g.drawImage(oImage,300,300,null);
        }
//Spot 6
        if(f == 1){
            g.drawImage(xImage,600,300,null);
        }
        else if (f ==2){
            g.drawImage(oImage,600,300,null);
        }

        //spot 7

        if(g1 == 1){
            g.drawImage(xImage,0,600,null);
        }
        else if (g1 ==2){
            g.drawImage(oImage,0,600,null);
        }
//spot 8
        if(h == 1){
            g.drawImage(xImage,300,600,null);
        }
        else if (h ==2){
            g.drawImage(oImage,300,600,null);
        }

        if(i == 1){
            g.drawImage(xImage,600,600,null);
        }
        else if (i ==2){
            g.drawImage(oImage,600,600,null);
        }





        //g.drawImage(xImage,0,0,null);
        //g.drawImage(oImage,0,0,null);
    }

    public void mouseClicked(MouseEvent e){}
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        // on title screen
        if (screen == 1) {
            if (x >= 500 && x <= 800 && y >= 600 && y <= 700) {
                System.out.println("DEBUG");
                screen = 2;
                players = 2;
            }
        } else if (screen == 2) {
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
        }






    repaint();
    }
    public void mouseReleased(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
}
