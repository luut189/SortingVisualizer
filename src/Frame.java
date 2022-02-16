import java.awt.*;
import javax.swing.*;

public class Frame extends JFrame {
    
    Panel panel;
    JPanel control = new JPanel();
    JLabel title;
    JTextArea keybind, sorting;

    Frame(String name, int height) {
        panel = new Panel(height);
        setupPanel(height);
        this.setTitle(name);
        this.add(panel, BorderLayout.WEST);
        this.add(control, BorderLayout.EAST);

        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void setupPanel(int height) {
        control.setPreferredSize(new Dimension(200,height));
        control.setFocusable(false);
        control.setBackground(new Color(37, 37, 38));

        title = new JLabel("Keybinding");
        title.setFont(new Font("Nunito", Font.BOLD, 20));
        title.setForeground(Color.white);
        
        keybind = new JTextArea("R - Start sorting\n" + 
                                "Q - Pause\n" +
                                "Space - Get new array\n" +
                                "W - Increase array\' length\n" +
                                "S - Decrease array\' length\n");
        keybind.setBackground(new Color(37, 37, 38));
        keybind.setForeground(Color.white);
        keybind.setFont(new Font("Nunito", Font.BOLD, 12));
        keybind.setEditable(false);
        keybind.setFocusable(false);

        sorting = new JTextArea("Algorithms List:\n" +
                                "1 - Bubble Sort\n" +
                                "2 - Insertion Sort");
        sorting.setBackground(new Color(37, 37, 38));
        sorting.setForeground(Color.white);
        sorting.setFont(new Font("Nunito", Font.BOLD, 12));
        sorting.setEditable(false);
        sorting.setFocusable(false);

        control.add(title);
        control.add(keybind);
        control.add(sorting);
    }
}
