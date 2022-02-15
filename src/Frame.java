import javax.swing.JFrame;

public class Frame extends JFrame {
    
    Panel panel;

    Frame(String name) {
        panel = new Panel();
        this.setTitle(name);
        this.add(panel);

        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
