import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Method;

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
    private final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 20);
    private final Dimension BUTTON_SIZE = new Dimension(200, 50);
    
    // Updated color scheme with yellow, pink, and brown
    private final Color BACKGROUND_COLOR = new Color(255, 253, 208); // Light yellow
    private final Color BUTTON_COLOR = new Color(255, 182, 193);     // Light pink
    private final Color TEXT_COLOR = new Color(101, 67, 33);         // Brown
    private final Color HOVER_COLOR = new Color(255, 105, 180);      // Hot pink (for hover effect)
    
    private JButton aboutButton; // Reference to about button to add it later
    private JPanel buttonPanel;  // Reference to button panel to add new buttons
    private JButton startButton; // Reference to start button for spacebar
    
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
        
        // Add key listener for spacebar to "click" start button
        KeyListener spaceListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    // Simulate clicking the start button
                    startButton.doClick();
                }
            }
        };
        
        frame.addKeyListener(spaceListener);
        menuPanel.addKeyListener(spaceListener);
        
        frame.setFocusable(true);
        frame.requestFocusInWindow();
        
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
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Create Start Game button
        startButton = createMenuButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startGameAction();
            }
        });
        
        // Create Quit button
        JButton quitButton = createMenuButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                quitGame();
            }
        });
        
        // Create Help button (replacing About)
        JButton helpButton = createMenuButton("Help");
        helpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showHelpAndAbout();
            }
        });
        
        // Create About button but don't add it yet
        aboutButton = createMenuButton("About");
        aboutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAboutDialog();
            }
        });
        
        // Add buttons to panel with spacing between them
        buttonPanel.add(startButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(quitButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(helpButton);
        
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
        try {
            // Hide this menu window
            frame.setVisible(false);
            
            // Launch GameScene directly using reflection (works in BlueJ)
            try {
                // Get the GameScene class
                Class<?> gameSceneClass = Class.forName("GameScene");
                
                // Get the main method
                Method mainMethod = gameSceneClass.getMethod("main", String[].class);
                
                // Invoke main method with empty args
                mainMethod.invoke(null, (Object) new String[0]);
                
                // Close this frame after successful launch
                frame.dispose();
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                    null,
                    "Could not launch game automatically. Please right-click on GameScene and select 'Run JavaFX Application'.",
                    "Launch Error",
                    JOptionPane.ERROR_MESSAGE
                );
                ex.printStackTrace();
                frame.setVisible(true);
            }
            
        } catch (Exception e) {
            // If there's an error, show the menu again
            frame.setVisible(true);
            e.printStackTrace();
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
     * Shows the Help dialog and adds the About button
     */
    private void showHelpAndAbout()
    {
        JOptionPane.showMessageDialog(
            frame,
            "Press SPACE to make the kitten jump.\n" +
            "Avoid obstacles to earn points!\n" +
            "Your score increases the longer you survive.",
            "Help",
            JOptionPane.INFORMATION_MESSAGE
        );
        
        // Add the About button if it's not already added
        if (!buttonPanel.isAncestorOf(aboutButton)) {
            buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            buttonPanel.add(aboutButton);
            buttonPanel.revalidate();
            buttonPanel.repaint();
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
        // Use the event dispatch thread for Swing components
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Menu();
            }
        });
    }
}