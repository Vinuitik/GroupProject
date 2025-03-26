import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Method;

/**
 * Menu class for the Kitten Game
 * Creates a centered menu with instruction text and top menu bar
 * Uses a color scheme of yellow, pink and brown
 * 
 * @author Dima, Daria, David
 * @version 1.1
 */
public class Menu
{
    private JFrame frame;
    private JPanel menuPanel;
    
    // Updated color scheme with yellow, pink, and brown
    private final Color BACKGROUND_COLOR = new Color(255, 253, 208); // Light yellow
    private final Color TEXT_COLOR = new Color(101, 67, 33);         // Brown
    private final Color TITLE_COLOR = new Color(255, 105, 180);      // Hot pink for title
    
    /**
     * Constructor for objects of class Menu with default frame
     */
    public Menu()
    {
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
        // Create main panel with BorderLayout
        menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBackground(BACKGROUND_COLOR);
        
        // Create title label with pink color
        JLabel titleLabel = new JLabel("Kitten Game", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));  // Made larger
        titleLabel.setForeground(TITLE_COLOR);  // Set to pink
        titleLabel.setBorder(BorderFactory.createEmptyBorder(80, 0, 0, 0));  // More top padding
        
        // Create instruction text
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(BACKGROUND_COLOR);
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
        wrapper.setBackground(BACKGROUND_COLOR);
        wrapper.add(centerPanel);
        
        // Add components to panel
        menuPanel.add(titleLabel, BorderLayout.NORTH);
        menuPanel.add(wrapper, BorderLayout.CENTER);
        
        // Set the panel as content pane
        frame.setContentPane(menuPanel);
    }
    
    /**
     * Starts the game when Start Game is selected
     */
    private void startGameAction()
    {
        try {
            // Hide this menu window
            frame.setVisible(false);
            
            // Launch GameScene
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
                frame.setVisible(true);
            }
            
        } catch (Exception e) {
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
     * For testing the menu independently
     */
    public static void main(String[] args)
    {
        new Menu();
    }
}