import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javafx.scene.Scene;

/**
 * Menu class for the Kitten Game
 * Creates a centered menu with large buttons for game options
 * Uses a color scheme of yellow, pink and brown
 * 
 * @author Dima, Daria, David
 * @version 1.1
 */
public class Menu
{
    private JFrame frame;
    private JPanel menuPanel;
    private Scene gameScene;
    private final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 20);
    private final Dimension BUTTON_SIZE = new Dimension(200, 50);
    
    // Updated color scheme with yellow, pink, and brown
    private final Color BACKGROUND_COLOR = new Color(255, 253, 208); // Light yellow
    private final Color BUTTON_COLOR = new Color(255, 182, 193);     // Light pink
    private final Color TEXT_COLOR = new Color(101, 67, 33);         // Brown
    private final Color HOVER_COLOR = new Color(255, 105, 180);      // Hot pink (for hover effect)
    
    /**
     * Constructor for objects of class Menu with provided frame and scene
     */
    public Menu(JFrame frame, Scene gameScene)
    {
        this.frame = frame;
        this.gameScene = gameScene;
        createMenu();
    }
    
    /**
     * Constructor for objects of class Menu with default frame
     */
    public Menu()
    {
        this.frame = new JFrame("Cat Game");
        this.frame.setSize(800, 600);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null); // Center on screen
        createMenu();
        this.frame.setVisible(true);
    }
    
    /**
     * Creates the centered menu with large buttons
     */
    private void createMenu()
    {
        // Create main panel with BorderLayout
        menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBackground(BACKGROUND_COLOR);
        
        // Create title label
        JLabel titleLabel = new JLabel("Kitten Game", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));
        
        // Create panel for buttons with vertical BoxLayout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Create Start Game button
        JButton startButton = createMenuButton("Start Game");
        startButton.addActionListener(e -> startGameAction());
        
        // Create Quit button
        JButton quitButton = createMenuButton("Quit");
        quitButton.addActionListener(e -> quitGame());
        
        // Create About button
        JButton aboutButton = createMenuButton("About");
        aboutButton.addActionListener(e -> showAboutDialog());
        
        // Add buttons to panel with spacing between them
        buttonPanel.add(startButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(quitButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(aboutButton);
        
        // Create a wrapper panel to center the buttons horizontally
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setBackground(BACKGROUND_COLOR);
        centerPanel.add(buttonPanel);
        
        // Add components to main panel
        menuPanel.add(titleLabel, BorderLayout.NORTH);
        menuPanel.add(centerPanel, BorderLayout.CENTER);
        
        // Add main panel to frame
        frame.setContentPane(menuPanel);
    }
    
    /**
     * Helper method to create a styled menu button
     */
    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setPreferredSize(BUTTON_SIZE);
        button.setMinimumSize(BUTTON_SIZE);
        button.setMaximumSize(BUTTON_SIZE);
        button.setBackground(BUTTON_COLOR);
        button.setForeground(TEXT_COLOR);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add hover effect with updated hover color
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(HOVER_COLOR);
            }
            
            public void mouseExited(MouseEvent e) {
                button.setBackground(BUTTON_COLOR);
            }
        });
        
        return button;
    }
    
    /**
     * Starts the game when Start Game button is clicked
     */
    private void startGameAction()
    {
        // Hide menu panel
        menuPanel.setVisible(false);
        
        if (gameScene != null) {
            // Here you would initialize and show the game
            JOptionPane.showMessageDialog(frame, "Game started!", "Start Game", JOptionPane.INFORMATION_MESSAGE);
            // You would add gameScene to the frame here
        } else {
            JOptionPane.showMessageDialog(frame, "Game scene not initialized!", "Error", JOptionPane.ERROR_MESSAGE);
            // Show menu again if error
            menuPanel.setVisible(true);
        }
    }
    
    /**
     * Quits the game when Quit button is clicked
     */
    private void quitGame()
    {
        int response = JOptionPane.showConfirmDialog(
            frame, 
            "Are you sure you want to quit?", 
            "Quit Game", 
            JOptionPane.YES_NO_OPTION
        );
        
        if (response == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    
    /**
     * Shows the About dialog with game title and authors
     */
    private void showAboutDialog()
    {
        JOptionPane.showMessageDialog(
            frame,
            "Kitten Game\n\nAuthors:\n- Dima\n- Daria\n- David",
            "About",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    /**
     * Sets the game scene
     * 
     * @param gameScene the Scene object to set
     */
    public void setGameScene(Scene gameScene)
    {
        this.gameScene = gameScene;
    }
    
    /**
     * Shows the menu (if it was hidden)
     */
    public void showMenu() {
        menuPanel.setVisible(true);
    }
    
    /**
     * For testing the menu independently
     */
    public static void main(String[] args)
    {
        new Menu();
    }
}