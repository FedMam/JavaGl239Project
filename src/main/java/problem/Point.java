package problem;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import java.util.Random;

/**
 * Класс точки
 */
public class Point {
    /**
     * x - координата точки
     */
    double x;
    /**
     * y - координата точки
     */
    double y;

    int num_of_triangles;

    /**
     * Конструктор точки
     *
     * @param x         координата
     * @param y         координата y
     */
    Point(double x, double y) {
        this.x = x;
        this.y = y;
        num_of_triangles = 0;
    }

    /**
     * Получить случайную точку
     *
     * @return случайная точка
     */
    static Point getRandomPoint() {
        Random r = new Random();
        double nx = (double) r.nextInt(500) / 25;
        double ny = (double) r.nextInt(500) / 25;
        return new Point(nx - 10, ny - 10);
    }

    /**
     * Рисование точки
     *
     * @param gl переменная OpenGl для рисования
     */
    void render(GL2 gl) {
        gl.glColor3d(0.0, 0.0, 0.0);
        gl.glPointSize(3);
        gl.glBegin(GL.GL_POINTS);
        gl.glVertex2d(x / 10, y / 10);
        gl.glEnd();
        gl.glPointSize(1);
    }

    void render_color(GL2 gl, double r, double g, double b) {
        gl.glColor3d(r, g, b);
        gl.glPointSize(3);
        gl.glBegin(GL.GL_POINTS);
        gl.glVertex2d(x / 10, y / 10);
        gl.glEnd();
        gl.glPointSize(1);
        gl.glColor3d(0.0, 0.0, 0.0);
    }

    /**
     * Получить строковое представление точки
     *
     * @return строковое представление точки
     */
    @Override
    public String toString() {
        return "Точка с координатами: " + x + ", " + y;
    }
}
