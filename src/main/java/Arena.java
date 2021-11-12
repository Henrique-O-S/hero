import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;

public class Arena {
    private int height;
    private int width;
    private Hero hero;
    private TextGraphics graphics;
    public Arena(int height, int width, Hero hero, TextGraphics graphics) {
        this.height = height;
        this.width = width;
        this.hero = hero;
        this.graphics = graphics;
    }

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        hero.draw(graphics);
    }

    public boolean canHeroMove(Position position) {
        if (position.getX() > width)
            return false;
        else if (position.getX() < 0)
            return false;
        else if (position.getY() > height)
            return false;
        else if (position.getY() <0)
            return false;
        return true;
    }

    private void moveHero(Position position) {
        if (canHeroMove(position))
            hero.setPosition(position);
    }

    public void processKey(KeyStroke key) throws IOException {
        System.out.println(key);
        if (key.getKeyType() == KeyType.ArrowUp) {
            moveHero(hero.moveUp());
        }
        else if (key.getKeyType() == KeyType.ArrowDown) {
            moveHero(hero.moveDown());
        }
        else if (key.getKeyType() == KeyType.ArrowLeft) {
            moveHero(hero.moveLeft());
        }
        else if (key.getKeyType() == KeyType.ArrowRight) {
            moveHero(hero.moveRight());
        }

    }
}
