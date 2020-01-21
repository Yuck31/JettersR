package JettersRLevelEditor;
/**
 * Class that starts the Level Editor
 *
 * @author: Luke Sullivan
 * @1/3/20
 */
import JettersR.*;
import JettersR.GameStates.*;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import java.io.*;

public class LevelEditorMain implements MenuListener, ActionListener
{
    public static JMenuBar menuBar;
    //
    public static JMenu FILE;
    public static JMenuItem NEW, LOAD, SAVE, SAVEAS;
    
    public static JMenu EDIT;
    public static JMenuItem SETPERIMETER;
    //
    public static JFrame dialogFrame;
    
    public static void main(String[] args)
    {
        Game game = new Game();//Creates an instance of Game
        
        Game.key = new LE_Keyboard();
        Game.gsm = new LE_GameStateManager(Game.key);//Creates the Game's GameStateManager.
        
        game.frame.setResizable(true);//Determines if the window can be stretched (pixels DO scale with the window)
        game.frame.setTitle(Game.TITLE);//Title
        game.frame.add(game);//Adds the instance of Game
        game.frame.pack();//Sets size of JFrame to our component
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Enables the Program to stop when closed
        try {
            Game.frame.setIconImage(ImageIO.read(new File("JettersRLevelEditor/Images/LE_ImageIcon.png")));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Game.screenStretch = true;
        game.frame.setLocationRelativeTo(null);//Center window
        //game.frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        game.frame.setVisible(true);//Make it show something

        LevelEditorMain lem = new LevelEditorMain();
        game.start();
    }
    
    public LevelEditorMain()
    {
        dialogFrame = new JFrame();
        createMenuBar();
    }
    
    public void createMenuBar()
    {
        menuBar = new JMenuBar();
        //
        FILE = new JMenu("File");
        menuBar.add(FILE);
        
        
        NEW = new JMenuItem("Create New Level");
        FILE.add(NEW);
        NEW.addActionListener(this);
        
        LOAD = new JMenuItem("Load Level");
        FILE.add(LOAD);
        LOAD.addActionListener(this);
        
        SAVE = new JMenuItem("Save");
        FILE.add(SAVE);
        SAVE.addActionListener(this);
        
        SAVEAS = new JMenuItem("Save As");
        FILE.add(SAVEAS);
        SAVEAS.addActionListener(this);
        
        
        FILE.addMenuListener(this);
        //
        EDIT = new JMenu("Edit");
        menuBar.add(EDIT);
        
        
        SETPERIMETER = new JMenuItem("Set Perimeter");
        EDIT.add(SETPERIMETER);
        SETPERIMETER.addActionListener(this);
        
        
        EDIT.addMenuListener(this);
        //
        Game.frame.setJMenuBar(menuBar);
    }
    
    public void displayErrorDialog()
    {
        JOptionPane.showMessageDialog(dialogFrame,
            "You clicked on something that creates this dialog. Shame on you!",
            "What the heck man!?",
            JOptionPane.ERROR_MESSAGE);
    }
    
    public void menuSelected(MenuEvent e)
    {
        
    }
    
    public void menuDeselected(MenuEvent e)
    {
        
    }
    
    public void menuCanceled(MenuEvent e)
    {
        
    }
    
    public void actionPerformed(ActionEvent e)
    {
        displayErrorDialog();
    }
}
