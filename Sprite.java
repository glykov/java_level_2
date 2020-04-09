import java.awt.*;

public abstract class Sprite {
    protected double x;
    protected double y;
    protected double halfWidth;
    protected double halfHeight;

    protected double getLeft() {
        return x - halfWidth;
    }

    protected void setLeft(double left) {
        x = left + halfWidth;
    }

    protected double getRight() {
        return x + halfWidth;
    }

    protected void setRight(double right) {
        x = right - halfWidth;
    }

    protected double getTop() {
        return y - halfHeight;
    }

    protected void setTop(double top) {
        y = top + halfHeight;
    }

    protected double getBottom() {
        return y + halfHeight;
    }

    protected void setBottom(double bottom) {
        y = bottom + halfHeight;
    }

    protected double getWidth() {
        return 2 * halfWidth;
    }

    protected double getHeight() {
        return 2 * halfHeight;
    }

    void update(GameCanvas canvas, double deltaTime) {
    }

    void render(GameCanvas canvas, Graphics g) {
    }
}
