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

    static int bubbleIndex = 0;
    static int compareBubble = Integer.MAX_VALUE;

    static int colorIndex = -1;

    static boolean isSorted = false;
    static boolean isRunning = false;

    static final int delay = 60;
    
    Timer timer;
    static Random rand = new Random();

    Panel() {
        this.setPreferredSize(new Dimension(width, height));
        this.setFocusable(true);
        this.addKeyListener(new keyAdapter());
        newArray();
    }

    public void newArray() {
        arr = new int[length];
        for(int i = 0; i < length; i++) {
            arr[i] = rand.nextInt((length-1))+1;
        }
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        /*
        for(int i = 0; i < width/size; i++) {
            g.drawLine(0, i*size, width, i*size);
        }
        for(int i = 0; i < height/size; i++) {
            g.drawLine(i*size, 0, i*size, height);
        }
        */
        Color[] grad = {new Color(130, 130, 130), new Color(168, 168, 168), new Color(206, 206, 206)};
        Color[] gradGreen = {new Color(48, 99, 61), new Color(67, 138, 85), new Color(85, 176, 108)};

        for(int i = 0; i < length; i++) {
            g.setColor(grad[i%3]);
            if(i == compareBubble) {
                g.setColor(Color.red);
            } else if(i == compareBubble+1) {
                g.setColor(Color.green);
            }
            if(i < colorIndex+1) {
                g.setColor(gradGreen[i%3]);
            }
            g.fillRect(i*size, height-arr[i]*size, size, size*arr[i]);
        }
    }
    
    public void swap(int[] arr, int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    public void changeColor() {
        Timer colorTime = new Timer(10, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(colorIndex == arr.length-1) {
                    ((Timer) e.getSource()).stop();
                } else {
                    colorIndex++;
                    repaint();
                }
            }
    
        });
        colorTime.start();
    }

    public int[] swapBubble(int[] arr) {
        if(arr[compareBubble] > arr[compareBubble+1]) {
            swap(arr, compareBubble, compareBubble+1);
        }

        if((compareBubble+1) >= (arr.length- bubbleIndex - 1)) {
            bubbleIndex++;
            compareBubble = 0;
        } else compareBubble++;

        return arr;
    }

    public void bubbleSort() {
        if(!checkSort(arr)) isRunning = true;
        else isRunning = false;

        compareBubble = 0;
        bubbleIndex = 0;
        Timer bubble = new Timer(0, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(checkSort(arr)) {
                    compareBubble = Integer.MAX_VALUE;
                    isRunning = false;
                    changeColor();
                    ((Timer)e.getSource()).stop();
                } else {
                    if(isRunning) arr = swapBubble(arr);
                }
                repaint();
            }
        });
        bubble.start();
    }

    public boolean checkSort(int[] arr) {
        for(int i = 0; i < arr.length-1; i++) {
            if(arr[i] > arr[i+1]) {
                return false;
            }
        }
        return true;
    }

    class keyAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            switch(key) {
                case KeyEvent.VK_Q:
                    isRunning = !isRunning;
                    break;
                case KeyEvent.VK_R:
                    if(!isRunning) bubbleSort();
                    break;
                case KeyEvent.VK_SPACE:
                    newArray();
                    repaint();
                    break;
                case KeyEvent.VK_S:
                    if(size < 50) {
                        if(!isRunning) {
                            size++;
                            length = width/size;
                            newArray();
                            repaint();
                        }
                    }
                    break;
                case KeyEvent.VK_W:
                    if(size > 1) {
                        if(!isRunning) {
                            size--;
                            length = width/size;
                            newArray();
                            repaint();
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
