package problem;

import java.util.Random;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;

public class Triangle {

    // Этот класс используется только для вывода треугольников на экран

    private Point pt1;
    private Point pt2;
    private Point pt3;

    private double R;
    private double G;
    private double B;

    Triangle (Point p1, Point p2, Point p3)
    {
        pt1 = p1;
        pt2 = p2;
        pt3 = p3;
        Random rnd = new Random();
        R = rnd.nextDouble();
        G = rnd.nextDouble();
        B = rnd.nextDouble();
    }

    public Point[] getPoints()
    {
        return new Point[] { pt1, pt2, pt3 };
    }
    public double[] getColor() { return new double[] { R, G, B }; }

    private void line(GL2 gl, Point p1, Point p2)
    {
        gl.glColor3d(R, G, B);
        gl.glBegin(GL.GL_LINES);
        gl.glVertex2d(p1.x / 10, p1.y / 10);
        gl.glVertex2d(p2.x / 10, p2.y / 10);
        gl.glEnd();
    }

    public void render(GL2 gl)
    {
        pt1.render_color(gl, R, G, B);
        line(gl, pt1, pt2);
        pt2.render_color(gl, R, G, B);
        line(gl, pt2, pt3);
        pt3.render_color(gl, R, G, B);
        line(gl, pt3, pt1);
        gl.glColor3d(0.0, 0.0, 0.0);
        gl.glPointSize(1);
    }
}
