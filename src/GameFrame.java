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
    private int DIFFICULTY;
    private int DIMENSION;    //dimension of the game panel
    private int MARGIN;        //margin constant
    protected static JLabel score;           //score will be displayed on this
    protected static int scoreCounter;       //counts how many click were made until game completed
    private final Color TEXT_COLOR = new Color(148, 236, 190);

    public GameFrame(int difficulty, int dimension) {
        this.DIFFICULTY = difficulty;
        this.DIMENSION = dimension;
        this.MARGIN = DIMENSION / 10;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(null);
        setSize(DIMENSION, DIMENSION + MARGIN);

        /** initializing the menubar **/
        menuBar = new JPanel();
        menuBar.setBounds(0, 0, getWidth(), MARGIN * 3 / 4);
        menuBar.setBackground(new Color(52, 88, 48));
        menuBar.setLayout(null);
        this.add(menuBar);

        /** initializing the restart button **/
        restartButton = new JButton("Restart");
        restartButton.setBorder(BorderFactory.createEmptyBorder());         //remove border
        Map<TextAttribute, Object> attributes = new HashMap<>();
        attributes.put(TextAttribute.TRACKING, 0.12);
        restartButton.setFont(new Font("ComicSans", Font.BOLD, MARGIN * 2 / 5));
        restartButton.setFont(restartButton.getFont().deriveFont(attributes));          //set the text attributes
        restartButton.setForeground(TEXT_COLOR);
        restartButton.setBackground(null);
        restartButton.setBounds(MARGIN / 2, 0, menuBar.getWidth() / 4, menuBar.getHeight());     //align the button
        restartButton.setHorizontalAlignment(SwingConstants.LEFT);
        restartButton.setFocusable(false);
        restartButton.setContentAreaFilled(false);
        restartButton.addActionListener(this);
        menuBar.add(restartButton);

        /** initializing the exit button **/
        exitButton = new JButton("Finish");
        exitButton.setBorder(BorderFactory.createEmptyBorder());
        exitButton.setFont(restartButton.getFont().deriveFont(attributes));
        exitButton.setForeground(TEXT_COLOR);
        exitButton.setBackground(null);
        exitButton.setBounds(menuBar.getWidth() - (MARGIN / 2) - (menuBar.getWidth() / 4), 0, menuBar.getWidth() / 4, menuBar.getHeight());
        exitButton.setHorizontalAlignment(SwingConstants.RIGHT);
        exitButton.setFocusable(false);
        exitButton.setContentAreaFilled(false);
        menuBar.add(exitButton);

        /** score counter display **/
        score = new JLabel();
        score.setBounds(menuBar.getWidth() / 4, 0, menuBar.getWidth() / 2, menuBar.getHeight());
        score.setText("Your score: " + scoreCounter);
        score.setBackground(null);
        score.setFont(restartButton.getFont().deriveFont(attributes));
        score.setForeground(new Color(231, 29, 54));
        score.setHorizontalAlignment(SwingConstants.CENTER);
        menuBar.add(score);

        /** adding the game to the frame **/
        setTitle("Tili Toli");
        setResizable(false);
        game = new TiliToli(DIFFICULTY, DIMENSION, MARGIN);
        game.setBounds(0, menuBar.getHeight(), getWidth(), getHeight());
        game.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, TEXT_COLOR));
        add(game, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                MainMenu backToMainMenu = new MainMenu();
                backToMainMenu.setVisible(true);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==restartButton) {
            this.remove(game);
            game = new TiliToli(DIFFICULTY, DIMENSION, MARGIN);
            game.setBounds(0, menuBar.getHeight(), getWidth(), getHeight());
            game.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, TEXT_COLOR));
            add(game, BorderLayout.CENTER);
            scoreCounter = 0;
            score.setText("Your score: " + scoreCounter);
            SwingUtilities.updateComponentTreeUI(this);
        }
    }
}
