import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;


public class MainCircles extends JFrame {
    private static final int POS_X = 400;
    private static final int POS_Y = 200;
    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;

    Sprite[] sprites = new Sprite[10];

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainCircles();
            }
        });
    }

    private MainCircles() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        GameCanvas canvas = new GameCanvas(this);
        add(canvas, BorderLayout.CENTER);
        addMouseListener(new MouseHandler());
        setTitle("Circles");
        initApplication();
        setVisible(true);
    }

    private void initApplication() {
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = new Ball();
        }
    }

    void onCanvasRepainted(GameCanvas canvas, Graphics g, double deltaTime) {
        update(canvas, deltaTime);
        render(canvas, g);
    }

    private void update(GameCanvas canvas, double deltaTime) {
        for (Sprite s : sprites) {
            s.update(canvas, deltaTime);
        }
    }

    private void render(GameCanvas canvas, Graphics g) {
        for (Sprite s : sprites) {
            s.render(canvas, g);
        }
    }

    /*
     *  * Реализовать добавление новых кружков по клику используя ТОЛЬКО массивы
     * ** Реализовать по клику другой кнопки удаление кружков (никаких эррейЛист)
     * */
    class MouseHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent me) {
            int buttonPressed = me.getButton();
            if (buttonPressed == MouseEvent.BUTTON1) {
                sprites = Arrays.copyOf(sprites, sprites.length + 1);
                sprites[sprites.length - 1] = new Ball();
            } else if (buttonPressed == MouseEvent.BUTTON3) {
                if (sprites.length > 0) {
                    sprites = Arrays.copyOf(sprites, sprites.length - 1);
                }
            }
        }
    }

}
