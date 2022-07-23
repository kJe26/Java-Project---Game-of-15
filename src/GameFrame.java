import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

public class GameFrame extends JFrame implements ActionListener {
    public static JPanel menuBar;       //menubar
    private final JButton restartButton;      //restart button
    private final JButton exitButton;         //exit button
    private TiliToli game;                //the game itself
    private final int DIMENSION = 600;    //dimension of the game panel - constant
    private final int MARGIN = DIMENSION / 10;        //margin constant
    protected static JLabel score;           //score will be displayed on this
    protected static int scoreCounter;       //counts how many click were made until game completed
    private final Color TEXTCOLOR = new Color(148, 236, 190);

    public GameFrame() {
        setLayout(null);
        setSize(DIMENSION, DIMENSION + MARGIN);

        /** initializing the menubar **/
        menuBar = new JPanel();
        menuBar.setBounds(0, 0, getWidth(), MARGIN / 2);
        menuBar.setBackground(new Color(52, 88, 48));
        this.add(menuBar);

        /** initializing the restart button **/
        restartButton = new JButton("Restart");
        restartButton.setBorder(BorderFactory.createEmptyBorder());         //remove border
        Map<TextAttribute, Object> attributes = new HashMap<>();
        attributes.put(TextAttribute.TRACKING, 0.12);
        restartButton.setFont(new Font("ComicSans", Font.BOLD, 18));
        restartButton.setFont(restartButton.getFont().deriveFont(attributes));          //set the text attributes
        restartButton.setForeground(TEXTCOLOR);
        restartButton.setBackground(null);
        menuBar.add(restartButton);
        menuBar.setLayout(null);
        restartButton.setBounds(MARGIN / 2, 0, menuBar.getWidth() / 4, menuBar.getHeight());     //align the button
        restartButton.setHorizontalAlignment(SwingConstants.LEFT);
        restartButton.setFocusable(false);
        restartButton.setContentAreaFilled(false);
        restartButton.addActionListener(this);

        /** initializing the exit button **/
        exitButton = new JButton("Exit");
        exitButton.setBorder(BorderFactory.createEmptyBorder());
        exitButton.setFont(new Font("ComicSans", Font.BOLD, 18));
        exitButton.setFont(exitButton.getFont().deriveFont(attributes));
        exitButton.setForeground(TEXTCOLOR);
        exitButton.setBackground(null);
        menuBar.add(exitButton);
        exitButton.setBounds(menuBar.getWidth() - (MARGIN / 2) - (menuBar.getWidth() / 4), 0, menuBar.getWidth() / 4, menuBar.getHeight());
        exitButton.setHorizontalAlignment(SwingConstants.RIGHT);
        exitButton.setFocusable(false);
        exitButton.setContentAreaFilled(false);

        /** score counter display **/
        score = new JLabel();
        score.setBounds(menuBar.getWidth() / 2 - MARGIN, 0, getWidth() - (MARGIN / 2) - exitButton.getWidth(), menuBar.getHeight());
        score.setText("Your score: " + scoreCounter);
        score.setBackground(null);
        attributes.put(TextAttribute.TRACKING, 0.12);
        score.setFont(new Font("ComicSans", Font.BOLD, 18));
        score.setFont(score.getFont().deriveFont(attributes));
        score.setForeground(new Color(231, 29, 54));
        GameFrame.menuBar.add(score);

        /** adding the game to the frame **/
        setTitle("Tili Toli");
        setResizable(false);
        game = new TiliToli(4, DIMENSION, MARGIN);
        game.setBounds(0, menuBar.getHeight(), getWidth(), getHeight());
        game.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, TEXTCOLOR));
        add(game, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);

        exitButton.addActionListener(e -> System.exit(0));
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==restartButton) {
            this.remove(game);
            game = new TiliToli(4, DIMENSION, MARGIN);
            game.setBounds(0, menuBar.getHeight(), getWidth(), getHeight());
            game.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, TEXTCOLOR));
            add(game, BorderLayout.CENTER);
            scoreCounter = 0;
            score.setText("Your score: " + scoreCounter);
            SwingUtilities.updateComponentTreeUI(this);
        }
    }
    public static void main(String[] args) {
        GameFrame newGame = new GameFrame();
        newGame.setVisible(true);
        newGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
