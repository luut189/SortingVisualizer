import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Frame extends JFrame implements ChangeListener {
    
    Panel panel;
    JSlider slider = new JSlider(1, 50, Panel.size);

    Frame(String name) {
        panel = new Panel();
        this.setTitle(name);
        this.add(panel);
        
        slider.addChangeListener(this);
        this.add(slider, BorderLayout.SOUTH);
        
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

	@Override
	public void stateChanged(ChangeEvent e) {
        Panel.size = slider.getValue();
		Panel.length = Panel.width/Panel.size;
		Panel.newArray();
        repaint();
	}
}
