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
    Image OImage;
    int screen = 1;
    int players;






    public tttGame(){
        addMouseListener(this);
        // Loads Images
        try
        {
        title = ImageIO.read(new File("logo.png"));
        }
        catch (IOException e){}
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
g.setColor(Color.RED);
g.fillRect(0,0,900,900);
    }

    public void mouseClicked(MouseEvent e){}
    public void mousePressed(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        // on title screen
        if(screen == 1) {
           if(x >= 500 && x <=800 && y>= 600 && y <= 700){
        System.out.println("DEBUG");
        screen = 2;
        players = 2;
           }
        }
    repaint();
    }
    public void mouseReleased(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
}
