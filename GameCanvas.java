import javax.swing.*;
import java.awt.*;

class GameCanvas extends JPanel {
    MainCircles wnd;
    Background bg;
    long lastFrameTime;

    GameCanvas(MainCircles wnd) {
        this.wnd = wnd;
        lastFrameTime = System.nanoTime();
        bg = new Background(this);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        long currentTime = System.nanoTime();
        double deltaTime = (currentTime - lastFrameTime) * 0.000000001;
        lastFrameTime = currentTime;
        wnd.onCanvasRepainted(this, g, deltaTime);
        // меняем цвет фона
        bg.changeBackground(deltaTime);
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        repaint();
    }

    public int getLeft() {
        return 0;
    }

    public int getRight() {
        return getWidth() - 1;
    }

    public int getTop() {
        return 0;
    }

    public int getBottom() {
        return getHeight() - 1;
    }
}
