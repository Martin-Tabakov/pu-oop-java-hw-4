package board;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PopUp extends JDialog {
    /**
     * Constructor for the pop up at the end of the game
     *
     * @param parent  the gameBoard instance
     * @param title   Title of the popUp
     * @param message Message to be displayed
     */
    public PopUp(JFrame parent, String title, String message) {
        super(parent, title, true);

        JPanel panel = new JPanel();
        JLabel label = new JLabel(message);

        panel.add(label);
        getContentPane().add(panel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        this.setLocationRelativeTo(parent);

        setVisible(true);
    }
    public boolean isSelected;
    /**
     * Constructor for the pop up at the end of the game
     *
     * @param parent  the gameBoard instance
     * @param title   Title of the popUp
     */
    public PopUp(JFrame parent, String title) {
        super(parent, title, true);

        JPanel panel = new JPanel();
        JButton button = new JButton("New Game");
        button.addActionListener(e -> {
            isSelected = true;
            dispose();
        });
        panel.add(button);
        getContentPane().add(panel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        this.setLocationRelativeTo(parent);
        setVisible(true);
    }
}
