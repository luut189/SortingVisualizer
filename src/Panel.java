import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Panel extends JPanel {

    static final int width = 500;
    int height;

    static int size = 10;
    
    static int x = 0;
    static int y = 0;

    static int length = width/size-50/size;
    static int[] arr;

    static int colorIndex = -1;

    static boolean isSorted = false;
    static boolean isRunning = false;

    static boolean hasSound = true;

    static boolean isBubble = false, isInsertion = false, isShuffling = false;

    static final int delay = 60;

    static Random rand = new Random();

    JPanel textPanel = new JPanel();
    static JLabel currentSort = new JLabel("No algorithm is being used!");
    static JLabel soundStatus = new JLabel("Sound: Enable");

    Panel(int height) {
        this.height = height;
        this.setPreferredSize(new Dimension(width, height));
        this.setFocusable(true);
        this.setLayout(new BorderLayout());
        this.addKeyListener(new keyAdapter());
        
        textPanel.setLayout(new GridLayout(2,1));
        this.add(textPanel, BorderLayout.NORTH);
        
        currentSort.setFont(new Font("Nunito", Font.BOLD, 14));
        currentSort.setFocusable(false);
        textPanel.add(currentSort);
        
        soundStatus.setFont(new Font("Nunito", Font.BOLD, 14));
        soundStatus.setFocusable(false);
        textPanel.add(soundStatus);

        createArray();
    }

    public void createArray() {
        arr = new int[length];
        for(int i = 0; i < length; i++) {
            arr[i] = i;
        }
        repaint();
    }
    
    static int index = 0;
    static int randomIndex = Integer.MAX_VALUE;
    public void newArray() {
        index = 0;
        isShuffling = true;
        Timer shuffle = new Timer(0, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(index < length) arr = shuffle(arr);
                else {
                    ((Timer) e.getSource()).stop();
                    randomIndex = Integer.MAX_VALUE;
                    isShuffling = false;
                    currentSort.setText("Shuffled");
                }
                repaint();
            }

        });
        shuffle.start();
    }

    public int[] shuffle(int[] arr) {
        randomIndex = rand.nextInt(arr.length);
        int temp = arr[randomIndex];
        arr[randomIndex] = arr[index];
        arr[index] = temp;

        if(hasSound) {
            try {
                Sound.tone(arr[randomIndex]*(arr.length%100), 1);
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }

        index++;
        return arr;
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
            if((isBubble || isInsertion) && !isShuffling) {
                if(i == BubbleSort.compareIndex || i == InsertionSort.arrayIndex) {
                    g.setColor(Color.red);
                } else if(i == BubbleSort.compareIndex+1 || i == InsertionSort.compareIndex) {
                    g.setColor(Color.green);
                }
            } else if(isShuffling) {
                if(i == Panel.index) {
                    g.setColor(Color.cyan);
                } else if(i == Panel.randomIndex) {
                    g.setColor(Color.magenta);
                }
            }
            if(i < colorIndex+1 && checkSort(arr)) {
                g.setColor(gradGreen[i%3]);
            }
            g.fillRect(i*size+25, height-arr[i]*size, size, size*arr[i]);
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
                    if(hasSound) {
                        try {
                            Sound.tone(arr[colorIndex]*(arr.length%100), 1);
                        } catch (LineUnavailableException e1) {
                            e1.printStackTrace();
                        }
                    }
                    repaint();
                }
            }
    
        });
        colorTime.start();
    }

    

    public void sort() {
        if(!checkSort(arr)) isRunning = true;
        else isRunning = false;
        colorIndex = -1;

        if(isBubble) {
            BubbleSort.compareIndex = 0;
            BubbleSort.arrayIndex = 0;
            Timer bubble = new Timer(0, new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if(checkSort(arr)) {
                        BubbleSort.compareIndex = Integer.MAX_VALUE;
                        isRunning = false;
                        changeColor();
                        currentSort.setText("Finished");;
                        ((Timer)e.getSource()).stop();
                    } else if(isBubble) {
                        if(isRunning) arr = BubbleSort.swapBubble(arr);
                    }
                    repaint();
                }
            });
            bubble.start();
        } else if(isInsertion) {
            InsertionSort.arrayIndex = 1;
            Timer insertion = new Timer(0, new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if(checkSort(arr)) {
                        InsertionSort.compareIndex = Integer.MAX_VALUE;
                        InsertionSort.arrayIndex = Integer.MAX_VALUE;
                        InsertionSort.startOfIteration = false;
                        isRunning = false;
                        changeColor();
                        currentSort.setText("Finished");;
                        ((Timer) e.getSource()).stop();
                    } else if(isInsertion) {
                        if(isRunning)  arr = InsertionSort.swapInsertion(arr);
                    }
                    repaint();
                }
            });
            insertion.start();
        }
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
                    if(isBubble) {
                        currentSort.setText("Bubble Sort is running");
                        if(!isRunning && !isShuffling) {
                            sort();
                        }
                    } else if(isInsertion) {
                        currentSort.setText("Insertion Sort is running");
                        if(!isRunning && !isShuffling) {
                            sort();
                        }
                    } else {
                        currentSort.setText("No algorithm was choosen");
                    }
                    break;
                case KeyEvent.VK_SPACE:
                    newArray();
                    if(isShuffling) {
                        currentSort.setText("Shuffling...");
                    }
                    repaint();
                    break;
                case KeyEvent.VK_E:
                    hasSound = !hasSound;
                    if(hasSound) {
                        soundStatus.setText("Sound: Enable");
                    } else {
                        soundStatus.setText("Sound: Disable");
                    }
                    break;
                case KeyEvent.VK_S:
                    if(size < 50) {
                        if(!isRunning && !isShuffling) {
                            size++;
                            colorIndex = -1;
                            length = width/size-50/size;
                            createArray();
                            repaint();
                        }
                    }
                    break;
                case KeyEvent.VK_W:
                    if(size > 1) {
                        if(!isRunning && !isShuffling) {
                            colorIndex = -1;
                            size--;
                            length = width/size-50/size;
                            createArray();
                            repaint();
                        }
                    }
                    break;
                case KeyEvent.VK_1:
                    if(!isRunning && !isShuffling) {
                        isBubble = true;
                        isInsertion = false;
                        currentSort.setText("Bubble Sort is running");
                    }
                    break;
                case KeyEvent.VK_2:
                    if(!isRunning && !isShuffling) {
                        isInsertion = true;
                        isBubble = false;
                        currentSort.setText("Insertion Sort is running");
                    }
                default:
                    break;
            }
        }
    }
}
