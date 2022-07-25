import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;

public class GameOptionsSelector extends JFrame {
    private JPanel optionPanel;
    private JLabel difficultyLabel;
    private ButtonGroup difficultyButtons;
    private JRadioButton easy;
    private JRadioButton medium;
    private JRadioButton hard;
    private JButton submitButton;
    private final Color BACKGROUND_COLOR = new Color(30, 63, 32);
    private final Color BUTTONS_COLOR = new Color(52, 88, 48);
    private final Color TEXT_COLOR = new Color(148, 236, 190);
    private int chosenDifficulty;
    private int chosenGridSize;

    public GameOptionsSelector () {
        this.setSize(400, 600);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int MARGIN = 30;

        /** option panel **/
        optionPanel = new JPanel();
        optionPanel.setBounds(0, 0, getWidth(), getHeight());
        optionPanel.setBackground(BACKGROUND_COLOR);
        optionPanel.setLayout(null);
        add(optionPanel);

        /** difficulty label **/
        difficultyLabel = new JLabel("<html><div style='text-align: center;'>" + "Select your difficulty:" + "</div></html>", SwingConstants.CENTER);
        difficultyLabel.setFont(new Font("ComicSans", Font.BOLD, 50));
        difficultyLabel.setForeground(TEXT_COLOR);
        difficultyLabel.setBounds(MARGIN, 2 * MARGIN, getWidth() - 2 * MARGIN, getHeight() / 2 + 2 * MARGIN);
        difficultyLabel.setBorder(BorderFactory.createEmptyBorder(-difficultyLabel.getHeight() + 4 * MARGIN,0,0,0));
        difficultyLabel.setLayout(null);
        optionPanel.add(difficultyLabel);

        /** submit button **/
        submitButton = new JButton("Submit");
        submitButton.setBounds(getWidth() / 2 - 4 * MARGIN, difficultyLabel.getY() + difficultyLabel.getHeight() + MARGIN, 8 * MARGIN, 2 * MARGIN);
        submitButton.setBackground(BUTTONS_COLOR);
        submitButton.setForeground(TEXT_COLOR);
        submitButton.setFocusable(false);
        submitButton.setBorder(BorderFactory.createEmptyBorder());
        submitButton.setFont(difficultyLabel.getFont());
        optionPanel.add(submitButton);

        /** difficulty radio buttons **/
        final float SIZE = 36;
        easy = new JRadioButton("3 x 3");
        easy.setBounds(difficultyLabel.getWidth() / 2 - 2 * MARGIN, difficultyLabel.getHeight() / 2, 4 * MARGIN, MARGIN);
        easy.setBackground(BACKGROUND_COLOR);
        easy.setForeground(TEXT_COLOR);
        easy.setFocusable(false);
        easy.setFont(submitButton.getFont().deriveFont(SIZE));
        easy.setVerticalTextPosition(SwingConstants.CENTER);

        medium = new JRadioButton("4 x 4");
        medium.setBounds(difficultyLabel.getWidth() / 2 - 2 * MARGIN, easy.getY() + easy.getHeight() + MARGIN, 4 * MARGIN, MARGIN);
        medium.setBackground(BACKGROUND_COLOR);
        medium.setForeground(TEXT_COLOR);
        medium.setFocusable(false);
        medium.setFont(submitButton.getFont().deriveFont(SIZE));

        hard = new JRadioButton("5 x 5");
        hard.setBounds(difficultyLabel.getWidth() / 2 - 2 * MARGIN, medium.getY() + medium.getHeight() + MARGIN, 4 * MARGIN, MARGIN);
        hard.setBackground(BACKGROUND_COLOR);
        hard.setForeground(TEXT_COLOR);
        hard.setFocusable(false);
        hard.setFont(submitButton.getFont().deriveFont(SIZE));

        difficultyLabel.add(easy);
        difficultyLabel.add(medium);
        difficultyLabel.add(hard);

        difficultyButtons = new ButtonGroup();
        difficultyButtons.add(easy);
        difficultyButtons.add(medium);
        difficultyButtons.add(hard);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(hard.isSelected()) {
                    chosenDifficulty = 5;
                } else if(medium.isSelected()) {
                    chosenDifficulty = 4;
                } else {
                    chosenDifficulty = 3;
                }
                chosenGridSize = 150 * chosenDifficulty;
                dispose();
                GameFrame newGame = new GameFrame(chosenDifficulty, chosenGridSize);
                newGame.setVisible(true);
            }
        });
    }
}
