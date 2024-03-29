import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame implements ActionListener {
    private static JPanel menuBar;       //menubar
    private final JCustomButton restartButton;      //restart button
    private GameModel gameModel;                //game model
    private GameView gameView;                //game view
    private GameController gameController;      //game controller
    private final int DIFFICULTY;
    private final int DIMENSION;    //dimension of the game panel
    private final int MARGIN;        //margin constant
    private static JLabel score;           //score will be displayed on this
    private static int scoreCounter;       //counts how many click were made until game completed
    private final Color TEXT_COLOR = new Color(148, 236, 190);
    private JPanel gamePanel;
    private int timerValue;

    public GameFrame(int difficulty, int dimension) {
        this.DIFFICULTY = difficulty;
        this.DIMENSION = dimension;
        this.MARGIN = DIMENSION / 10;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final Color BACKGROUND_COLOR = new Color(52, 88, 48);
        final Color FOREGROUND_COLOR = new Color(231, 29, 54);
        final float FONT_SIZE = (float)MARGIN * 2 / 5;

        setLayout(new BorderLayout());
        setSize(DIMENSION, DIMENSION + 2 * MARGIN);

        /** initializing the menubar **/
        menuBar = new JPanel();
        menuBar.setBounds(0, 0, DIMENSION, MARGIN * 3 / 4);
        menuBar.setBackground(BACKGROUND_COLOR);
        menuBar.setLayout(new FlowLayout(FlowLayout.CENTER, MARGIN, 0));

        /** initializing the restart button **/
        restartButton = new JCustomButton(" Restart");
        restartButton.setBounds(MARGIN / 2, 0, menuBar.getWidth() / 4, menuBar.getHeight());     //align the button
        restartButton.setFont(restartButton.getFont().deriveFont(FONT_SIZE));
        restartButton.setContentAreaFilled(false);
        restartButton.addActionListener(this);
        menuBar.add(restartButton);

        /** score counter display **/
        score = new JLabel();
        score.setBounds(menuBar.getWidth() / 4, 0, menuBar.getWidth() / 2, menuBar.getHeight());
        score.setText("Your score: " + scoreCounter);
        score.setBackground(null);
        score.setFont(restartButton.getFont().deriveFont(FONT_SIZE));
        score.setForeground(FOREGROUND_COLOR);
        score.setHorizontalAlignment(SwingConstants.CENTER);
        menuBar.add(score);

        /** initializing the exit button **/
        //exit button
        JCustomButton exitButton = new JCustomButton("Finish");
        exitButton.setBounds(menuBar.getWidth() - (MARGIN / 2) - (menuBar.getWidth() / 4), 0, menuBar.getWidth() / 4, menuBar.getHeight());
        exitButton.setFont(exitButton.getFont().deriveFont(FONT_SIZE));
        exitButton.setContentAreaFilled(false);
        menuBar.add(exitButton);

        /** adding the game to the frame **/
        setTitle("Tili Toli");
        setResizable(false);

        gameModel = new GameModel(DIFFICULTY, DIMENSION, MARGIN, this);
        gameView = new GameView(gameModel);
        gameController = new GameController(gameModel, gameView);
        gameView.addMouseListener(gameController);

        gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout());
        gamePanel.add(gameView, BorderLayout.CENTER);

        gamePanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, TEXT_COLOR));
        gamePanel.setBounds(0, menuBar.getHeight(), DIMENSION, DIMENSION);
        gameController.newGame();

        /** timer panel **/

        JPanel timerPanel = new JPanel();
        timerPanel.setBounds(0, menuBar.getHeight() + gamePanel.getHeight(), DIMENSION, menuBar.getHeight() + 1);
        timerPanel.setBackground(BACKGROUND_COLOR);
        timerPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, TEXT_COLOR));

        JLabel timerLabel = new JLabel("Your time playing: " + timerValue);
        timerPanel.add(timerLabel, BorderLayout.CENTER);
        timerLabel.setFont(restartButton.getFont());
        timerLabel.setForeground(FOREGROUND_COLOR);

        Timer timer = new Timer(1000, e -> {
            timerValue++;
            timerLabel.setText("Your time playing: " + timerValue);
        });

        timer.start();

        add(menuBar, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);
        add(timerPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);

        exitButton.addActionListener(e -> {
            dispose();
            MainMenu backToMainMenu = new MainMenu();
            backToMainMenu.setVisible(true);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == restartButton) {
            this.remove(gamePanel);

            timerValue = 0;

            gameModel = new GameModel(DIFFICULTY, DIMENSION, MARGIN, this);
            gameView = new GameView(gameModel);
            gameController = new GameController(gameModel, gameView);
            gameView.addMouseListener(gameController);

            gamePanel = new JPanel();
            gamePanel.setLayout(new BorderLayout());
            gamePanel.add(gameView, BorderLayout.CENTER);

            gamePanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, TEXT_COLOR));
            gamePanel.setBounds(0, menuBar.getHeight(), getWidth(), getHeight() - 3 * menuBar.getHeight());
            add(gamePanel, BorderLayout.CENTER);
            gameController.newGame();

            scoreCounter = 0;
            score.setText("Your score: " + scoreCounter);
            SwingUtilities.updateComponentTreeUI(this);
        }
    }

    public void setScore() {
        GameFrame.score.setText("Your score: " + scoreCounter);
    }

    public void setScoreCounter(int scoreCounter) {
        GameFrame.scoreCounter = scoreCounter;
    }

    public int getScoreCounter() {
        return scoreCounter;
    }
}
