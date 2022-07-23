import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

public class MainMenu extends JFrame {
    private JLabel imageLabel;              //label for the image
    private JPanel controlPanel;            //control panel for the buttons
    private JButton startButton;            //start button
    private JButton scoreBoardButton;       //score board button
    private JButton helpButton;             //help button
    private JButton exitButton;             //exit button
    private final Color BACKGROUND_COLOR = new Color(30, 63, 32);
    private final Color BUTTONS_COLOR = new Color(52, 88, 48);
    private final Color TEXT_COLOR = new Color(148, 236, 190);

    public MainMenu() {
        this.setSize(600, 800);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);

        int MARGIN = 60;
        /** adding the logo to the top of the start screen **/
        ImageIcon image = new ImageIcon("logo.png");
        Image temp = image.getImage();
        temp = temp.getScaledInstance(getWidth() - 2 * MARGIN, getWidth() / 2 - MARGIN, Image.SCALE_SMOOTH);
        image = new ImageIcon(temp);
        imageLabel = new JLabel(image);
        imageLabel.setSize(image.getIconWidth() + 2 * MARGIN, image.getIconHeight() + MARGIN);
        imageLabel.setBackground(BACKGROUND_COLOR);
        imageLabel.setOpaque(true);
        add(imageLabel);

        /** setting up the control panel **/
        controlPanel = new JPanel();
        controlPanel.setSize(getWidth(), getHeight());
        controlPanel.setBackground(BACKGROUND_COLOR);
        controlPanel.setBounds(0, imageLabel.getHeight(), getWidth(), getHeight() - imageLabel.getHeight());
        controlPanel.setLayout(null);
        add(controlPanel);

        /** adding the start button to the panel **/
        startButton = new JButton("Start Game");
        startButton.setBounds(MARGIN, MARGIN, getWidth() - 2 * MARGIN, getWidth() / 10);
        startButton.setBackground(BUTTONS_COLOR);
        startButton.setBorder(BorderFactory.createEmptyBorder());
        startButton.setFocusable(false);
        startButton.setFont(new Font("ComicSans", Font.BOLD, 36));
        startButton.setForeground(TEXT_COLOR);
        Map<TextAttribute, Object> attributes = new HashMap<>();
        attributes.put(TextAttribute.TRACKING, 0.1);
        startButton.setFont(startButton.getFont().deriveFont(attributes));
        controlPanel.add(startButton);

        /** adding the scoreboard button to the panel **/
        scoreBoardButton = new JButton("Scoreboard");
        scoreBoardButton.setBounds(MARGIN, startButton.getX() + MARGIN * 3 / 2, getWidth() - 2 * MARGIN, getWidth() / 10);
        scoreBoardButton.setBackground(BUTTONS_COLOR);
        scoreBoardButton.setBorder(BorderFactory.createEmptyBorder());
        scoreBoardButton.setFocusable(false);
        scoreBoardButton.setForeground(TEXT_COLOR);
        scoreBoardButton.setFont(startButton.getFont().deriveFont(attributes));
        controlPanel.add(scoreBoardButton);

        /** adding the help button to the panel **/
        helpButton = new JButton("Help");
        helpButton.setBounds(MARGIN, 2 * (startButton.getHeight() + MARGIN), getWidth() - 2 * MARGIN, getWidth() / 10);
        helpButton.setBackground(BUTTONS_COLOR);
        helpButton.setBorder(BorderFactory.createEmptyBorder());
        helpButton.setFocusable(false);
        helpButton.setForeground(TEXT_COLOR);
        helpButton.setFont(startButton.getFont().deriveFont(attributes));
        controlPanel.add(helpButton);

        /** adding the exit button to the panel **/
        exitButton = new JButton("Exit");
        exitButton.setBounds(MARGIN, 3 * startButton.getHeight() + 5 * MARGIN / 2, getWidth() - 2 * MARGIN, getWidth() / 10);
        exitButton.setBackground(BUTTONS_COLOR);
        exitButton.setBorder(BorderFactory.createEmptyBorder());
        exitButton.setFocusable(false);
        exitButton.setForeground(TEXT_COLOR);
        exitButton.setFont(startButton.getFont().deriveFont(attributes));
        controlPanel.add(exitButton);

        /** setting what each button has to do when pressed **/
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                GameFrame newGame = new GameFrame();
                newGame.setVisible(true);
            }
        });

        exitButton.addActionListener(e -> System.exit(0));
    }

    public static void main(String[] args) {
        MainMenu newGame = new MainMenu();
        newGame.setVisible(true);
        newGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
