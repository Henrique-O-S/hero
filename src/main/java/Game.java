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
            screen = new TerminalScreen(terminal);
            screen.setCursorPosition(null); // we don't need a cursor
            screen.startScreen(); // screens must be started
            screen.doResizeIfNecessary(); // resize screen if necessary
            arena = new Arena (height, width, hero, screen.newTextGraphics());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void draw() throws IOException {
        screen.clear();
        arena.draw(screen.newTextGraphics());
        screen.refresh();
    }




    public void run() throws IOException {
        game = true;
        while(game) {
            draw();
            KeyStroke key = screen.readInput();
            arena.processKey(key);
            if (arena.verifyMonsterCollision()) {
                System.out.println("You lost.");
                draw();
                while (game) {
                    key = screen.readInput();
                    if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q') {
                        screen.close();
                        game = false;
                    }
                }
            }
            if(key.getKeyType() == KeyType.Character && key.getCharacter() == 'q') {
                screen.close();
            }
            else if (key.getKeyType() == KeyType.EOF) {
                game = false;
            }
        }
    }
}