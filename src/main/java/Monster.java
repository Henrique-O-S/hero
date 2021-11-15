import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Monster extends Element{
    public Monster(int x, int y) {
        super(x, y);
    }

    public Position getPosition() {
        return super.getPosition();
    }

    public void setPosition(Position position) {
        super.setPosition(position);
    }

    public Position move(Hero hero) {
        if (getPosition().getX() > hero.getPosition().getX()) {
            if (getPosition().getY() > hero.getPosition().getY())
                return new Position(getPosition().getX() - 1, getPosition().getY() - 1);
            if (getPosition().getY() < hero.getPosition().getY())
                return new Position(getPosition().getX() - 1, getPosition().getY() + 1);
            if (getPosition().getY() == hero.getPosition().getY())
                return new Position(getPosition().getX() - 1, getPosition().getY());
        }
        else if (getPosition().getX() < hero.getPosition().getX()) {
            if (getPosition().getY()> hero.getPosition().getY())
                return new Position(getPosition().getX() + 1,getPosition().getY() - 1);
            if (getPosition().getY() < hero.getPosition().getY())
                return new Position(getPosition().getX() + 1, getPosition().getY() + 1);
            if (getPosition().getY() == hero.getPosition().getY())
                return new Position(getPosition().getX() + 1, getPosition().getY());
        }
        else if (getPosition().getX() == hero.getPosition().getX()) {
            if (getPosition().getY()> hero.getPosition().getY())
                return new Position(getPosition().getX(), getPosition().getY() - 1);
            if (getPosition().getY() < hero.getPosition().getY())
                return new Position(getPosition().getX(), getPosition().getY() + 1);
        }
        return null;
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#FFFF33"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(getPosition().getX(), getPosition().getY()), "M");
    }
}
