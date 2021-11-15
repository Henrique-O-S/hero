import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Arena {
    private int height;
    private int width;
    private Hero hero;
    private TextGraphics graphics;
    private List<Wall> walls;
    private List<Coin> coins;
    public Arena(int height, int width, Hero hero, TextGraphics graphics) {
        this.height = height;
        this.width = width;
        this.hero = hero;
        this.graphics = graphics;
        this.walls = createWalls();
        this.coins = createCoins();
    }

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        hero.draw(graphics);
        for (Wall wall : walls)
            wall.draw(graphics);
        for (Coin coin : coins)
            coin.draw(graphics);
    }

    public boolean canHeroMove(Position position) {
        if (position.getX() > width - 1)
            return false;
        else if (position.getX() < 0)
            return false;
        else if (position.getY() > height - 1)
            return false;
        else if (position.getY() <0)
            return false;
        for (int i = 0; i < walls.size(); i++) {
            Wall wall = walls.get(i);
            if (wall.getPosition().equals(position)){
                return false;
            }
        }
        return true;
    }

    private void moveHero(Position position) {
        if (canHeroMove(position))
            hero.setPosition(position);
        retrieveCoins();
    }

    public void retrieveCoins() {
        for(int i = 0; i < coins.size(); i++) {
            if (hero.getPosition().equals(coins.get(i).getPosition())) {
                coins.remove(i);
                draw(graphics);
            }
        }
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

    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();
        for (int c = 0; c < width; c++) {
            walls.add(new Wall(c, 0));
            walls.add(new Wall(c, height - 1));
        }
        for (int r = 1; r < height - 1; r++) {
            walls.add(new Wall(0, r));
            walls.add(new Wall(width - 1, r));
        }
        return walls;
    }

    private List<Coin> createCoins() {
        Random random = new Random();
        ArrayList<Coin> coins = new ArrayList<>();
        int x, y;
        for (int i = 0; i < 5; i++) {
            do{
                x = random.nextInt(width - 2) +  1;
                y = random.nextInt(height - 2) + 1;
            } while(new Position(x, y).equals(hero.getPosition()));
            coins.add(new Coin(x, y));
        }
        return coins;
    }
}
