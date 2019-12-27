package JettersR;
/**
 * This is the class that is executed to start the game in Multi-Thread mode
 *
 * @author: Luke Sullivan
 * @12/10/19
 */
import javax.swing.JFrame;
import javax.imageio.ImageIO;
import java.io.*;

public class MultiMain
{
    public static void main(String[] args)//The same as main(), but multi-threads the game instead
    {
        Game game = new Game();//Creates an instance of Game
        game.frame.setResizable(true);//Determines if the window can be stretched (pixels DO scale with the window)
        game.frame.setTitle(Game.TITLE);//Title
        game.frame.add(game);//Adds the instance of Game
        game.frame.pack();//Sets size of JFrame to our component
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Enables the Program to stop when closed
        try {
            game.frame.setIconImage(ImageIO.read(new File("ImageIcon.png")));
        }
        catch (IOException exc) {
            exc.printStackTrace();
        }
        game.frame.setLocationRelativeTo(null);//Center window
        game.frame.setVisible(true);//Make it show something

        game.multiStart();
    }
}
