import javafx.application.Application;
import javafx.stage.Stage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Menu class for the Kitten Game
 * Creates a centered menu with instruction text and top menu bar
 * Uses a color scheme of yellow, pink and brown
 * 
 * @author Dima, Daria, David
 * @version 1.2
 */
public class Menu extends Application {
    private JFrame frame;
    private JPanel menuPanel;
    
    // Updated color scheme with yellow, pink, and brown
    private final Color BACKGROUND_COLOR = new Color(255, 253, 208); // Light yellow
    private final Color TEXT_COLOR = Color.WHITE;         // White
    private final Color TITLE_COLOR = new Color(255, 105, 180);      // Hot pink for title
    
    @Override
    public void start(Stage primaryStage) {
        // We'll keep the original Swing-based menu creation
        this.frame = new JFrame("Cat Game");
        this.frame.setSize(800, 600);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null); // Center on screen
        
        createMenuBar();
        createMenu();
        
        // Add key listener for spacebar
        frame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    startGameAction();
                }
            }
        });
        
        frame.setFocusable(true);
        frame.requestFocus();
        
        this.frame.setVisible(true);
    }
    
    /**
     * Creates the menu bar with File and Help menus
     */
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // Create File menu
        JMenu fileMenu = new JMenu("File");
        
        // Create Start Game menu item
        JMenuItem startGameItem = new JMenuItem("Start Game");
        startGameItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startGameAction();
            }
        });
        
        // Create Quit menu item
        JMenuItem quitItem = new JMenuItem("Quit");
        quitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(
                    frame, 
                    "Do you want to Quit?", 
                    "Quit Game", 
                    JOptionPane.YES_NO_OPTION
                );
                
                if (response == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        
        fileMenu.add(startGameItem);
        fileMenu.addSeparator();
        fileMenu.add(quitItem);
        
        // Create Help menu
        JMenu helpMenu = new JMenu("Help");
        
        // Create Controls menu item
        JMenuItem helpItem = new JMenuItem("Controls");
        helpItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showHelpDialog();
            }
        });
        
        // Create About menu item
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAboutDialog();
            }
        });
        
        helpMenu.add(aboutItem);
        helpMenu.add(helpItem);
        
        // Add menus to menu bar
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        
        frame.setJMenuBar(menuBar);
    }
    
    /**
     * Creates the centered menu with instruction text
     */
    private void createMenu()
    {
        // Create main panel with background image
        menuPanel = new BackgroundPanel();
        menuPanel.setLayout(new BorderLayout());
        
        // Create title label with pink color
        JLabel titleLabel = new JLabel("Kitten Game", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));  // Made larger
        titleLabel.setForeground(TITLE_COLOR);  // Set to pink
        titleLabel.setBorder(BorderFactory.createEmptyBorder(80, 0, 0, 0));  // More top padding
        
        // Create instruction text
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false); // Make panel transparent
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Create instruction label with proper alignment
        JLabel instructionLabel = new JLabel("Press SPACE to start the game");
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 24));
        instructionLabel.setForeground(TEXT_COLOR);
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel orLabel = new JLabel("or");
        orLabel.setFont(new Font("Arial", Font.BOLD, 24));
        orLabel.setForeground(TEXT_COLOR);
        orLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel menuLabel = new JLabel("Navigate to \"File\" at the top left and click \"Start Game\"");
        menuLabel.setFont(new Font("Arial", Font.BOLD, 24));
        menuLabel.setForeground(TEXT_COLOR);
        menuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Adjust the vertical and horizontal alignments
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(instructionLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(orLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(menuLabel);
        centerPanel.add(Box.createVerticalGlue());
        
        // Create wrapper panel to center horizontally
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);
        wrapper.add(centerPanel);
        
        // Add components to panel
        menuPanel.add(titleLabel, BorderLayout.NORTH);
        menuPanel.add(wrapper, BorderLayout.CENTER);
        
        // Set the panel as content pane
        frame.setContentPane(menuPanel);
    }
    
    /**
     * Custom JPanel with background image
     */
    private class BackgroundPanel extends JPanel {
        private BufferedImage backgroundImage;
        
        public BackgroundPanel() {
            try {
                // Load the background image
                backgroundImage = ImageIO.read(new File("background.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
                // Fallback to default color if image can't be loaded
                setBackground(BACKGROUND_COLOR);
            }
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                // Scale image to fit the panel
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                g2d.dispose();
            }
        }
    }
    
    /**
     * Starts the game when Start Game is selected
     */
    private void startGameAction()
    {
        try {
            // Hide this menu window
            frame.setVisible(false);
            frame.dispose();
            
            // Launch GameScene
            javafx.application.Platform.runLater(() -> {
                try {
                    Stage gameStage = new Stage();
                    GameScene gameScene = new GameScene();
                    gameScene.start(gameStage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(
                        null,
                        "Could not launch game: " + ex.getMessage(),
                        "Launch Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            // If there's an error, show the menu again
            frame.setVisible(true);
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
     * Shows the Help dialog with game controls
     */
    private void showHelpDialog()
    {
        JOptionPane.showMessageDialog(
            frame,
            "Kitten Game\n JUMP = SPACE/UP\n RESTART = R/SPACE",
            "Help",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    /**
     * Main method to launch the application
     */
    public static void main(String[] args) {
        launch(args);
    }
}