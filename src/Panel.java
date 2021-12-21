import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Panel extends JPanel {

    static final int width = 900;
    static final int height = 900;

    static int size = 10;
    
    static int x = 0;
    static int y = 0;

    static int length = width/size;
    static int[] arr;

    static final int delay = 60;
    
    Timer timer;
    static Random rand = new Random();

    Panel() {
        this.setPreferredSize(new Dimension(width, height));
        this.setFocusable(true);
        newArray();
        timer = new Timer(delay, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
            
        });
        timer.start();
        timer.setRepeats(true);
    }

    public static void newArray() {
        arr = new int[length];
        for(int i = 0; i < length; i++) {
            arr[i] = rand.nextInt((length-1))+1;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        /*
        for(int i = 0; i < width/size; i++) {
            g.drawLine(0, i*size, width, i*size);
        }
        for(int i = 0; i < height/size; i++) {
            g.drawLine(i*size, 0, i*size, height);
        }
        */
        int space = 0;
        g.setColor(new Color(204, 62, 68));
        for(int i = 0; i < length; i++) {
            g.fillRect(i*size+space, 0, size, size*arr[i]);
            space+=size;
        }
    }
}
