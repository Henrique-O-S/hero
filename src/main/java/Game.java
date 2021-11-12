import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Game {
    private int height = 20;
    private int width = 40;
    Screen screen;
    Hero hero = new Hero(10 ,10);
    Arena arena;
    private boolean game = false;
    public Game() {
        try {
            TerminalSize terminalSize = new TerminalSize(width, height);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
            Terminal terminal = terminalFactory.createTerminal();
            //terminal = new DefaultTerminalFactory().createTerminal();
            screen = new TerminalScreen(terminal);
            screen.setCursorPosition(null); // we don't need a cursor
            screen.startScreen(); // screens must be started
            screen.doResizeIfNecessary(); // resize screen if necessary
            arena = new Arena (height-1, width-1, hero, screen.newTextGraphics());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void draw() throws IOException {
        screen.clear();
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        arena.draw(screen.newTextGraphics());
        screen.refresh();
    }




    public void run() throws IOException {
        game = true;
        while(game) {
            draw();
            KeyStroke key = screen.readInput();
            arena.processKey(key);
            if(key.getKeyType() == KeyType.Character && key.getCharacter() == 'q') {
                screen.close();
            }
            else if (key.getKeyType() == KeyType.EOF) {
                game = false;
            }
        }
    }
}