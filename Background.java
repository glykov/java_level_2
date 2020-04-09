import java.awt.*;

/*
 * Написать класс Бэкграунд, изменяющий цвет канвы в зависимости от времени внутри приложения
 * */
public class Background {
    private GameCanvas canvas;
    private double time = 0;

    Background(GameCanvas canvas) {
        this.canvas = canvas;
    }

    // меняем цвет фона с частотй примерно 1 р/сек
    public void changeBackground(double dt) {
        time += dt;
        if (time > 1) {
            Color c = new Color(
                    (int) (Math.random() * 255),
                    (int) (Math.random() * 255),
                    (int) (Math.random() * 255)
            );
            canvas.setBackground(c);
            time = 0;
        }
    }
}
