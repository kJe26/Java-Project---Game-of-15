import javax.swing.*;
import java.awt.*;
public class GameOptionsSelector extends JFrame {
    private final JCustomRadioButton medium;
    private final JCustomRadioButton hard;
    private int chosenDifficulty;
    private int chosenGridSize;

    public GameOptionsSelector () {
        this.setSize(400, 600);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final int MARGIN = 30;
        final Color BACKGROUND_COLOR = new Color(30, 63, 32);
        final Color TEXT_COLOR = new Color(148, 236, 190);

        /** option panel **/
        JPanel optionPanel = new JPanel();
        optionPanel.setBounds(0, 0, getWidth(), getHeight());
        optionPanel.setBackground(BACKGROUND_COLOR);
        optionPanel.setLayout(null);
        add(optionPanel);

        /** difficulty label **/
        JLabel difficultyLabel = new JLabel("<html><div style='text-align: center;'>" + "Select your difficulty:" + "</div></html>", SwingConstants.CENTER);
        difficultyLabel.setFont(new Font("ComicSans", Font.BOLD, 36));
        difficultyLabel.setForeground(TEXT_COLOR);
        difficultyLabel.setBounds(MARGIN, 2 * MARGIN, getWidth() - 2 * MARGIN, getHeight() / 2 + 2 * MARGIN);
        difficultyLabel.setBorder(BorderFactory.createEmptyBorder(-difficultyLabel.getHeight() + 4 * MARGIN,0,0,0));
        difficultyLabel.setLayout(null);
        optionPanel.add(difficultyLabel);

        /** submit button **/
        JCustomButton submitButton = new JCustomButton("Submit");
        submitButton.setBounds(getWidth() / 2 - 4 * MARGIN, difficultyLabel.getY() + difficultyLabel.getHeight() + MARGIN, 8 * MARGIN, 2 * MARGIN);
        optionPanel.add(submitButton);

        /** difficulty radio buttons **/
        JCustomRadioButton easy = new JCustomRadioButton();
        easy.setText(" 3 x 3");
        easy.setBounds(difficultyLabel.getWidth() / 2 - 2 * MARGIN, difficultyLabel.getHeight() / 2, 4 * MARGIN, MARGIN);
        easy.setSelected(true);

        medium = new JCustomRadioButton();
        medium.setText(" 4 x 4");
        medium.setBounds(difficultyLabel.getWidth() / 2 - 2 * MARGIN, easy.getY() + easy.getHeight() + MARGIN, 4 * MARGIN, MARGIN);

        hard = new JCustomRadioButton();
        hard.setText(" 5 x 5");
        hard.setBounds(difficultyLabel.getWidth() / 2 - 2 * MARGIN, medium.getY() + medium.getHeight() + MARGIN, 4 * MARGIN, MARGIN);

        difficultyLabel.add(easy);
        difficultyLabel.add(medium);
        difficultyLabel.add(hard);

        ButtonGroup difficultyButtons = new ButtonGroup();
        difficultyButtons.add(easy);
        difficultyButtons.add(medium);
        difficultyButtons.add(hard);

        submitButton.addActionListener(e -> {
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
        });
    }
}
