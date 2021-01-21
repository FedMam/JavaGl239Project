package problem;

import javax.media.opengl.GL2;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Math;

/**
 * Класс задачи
 */
public class Problem {
    /**
     * текст задачи
     */
    public static final String PROBLEM_TEXT = "ПОСТАНОВКА ЗАДАЧИ:\n" +
        "Множество точек на плоскости назовем дваждытреугольным, если \n каждая точка этого " +
        "множества является вершиной хотя бы двух \n правильных треугольников, построенных " +
        "по точкам множества. \n Определить, удовлетворяет ли заданное множество точек этому \n" +
        "свойству. ";

    /**
     * заголовок окна
     */
    public static final String PROBLEM_CAPTION = "Итоговый проект ученика 10-2 Мамаева Фёдора";

    /**
     * путь к файлу
     */
    private static final String FILE_NAME = "points.txt";

    /**
     * список точек
     */
    private ArrayList<Point> points;

    /**
     * список возможных треугольников
     */
    private ArrayList<Triangle> triangles;

    /**
     * точность
     */
    private int precision;
    public void setPrecision(int p) { precision = p; triangles.clear(); }
    public int getPrecision() { return precision; }

    /**
     * Конструктор класса задачи
     */
    public Problem() {
        points = new ArrayList<>(); triangles = new ArrayList<>();
        precision = 5;
    }

    /**
     * Добавить точку
     *
     * @param x      координата X точки
     * @param y      координата Y точки
     */
    public void addPoint(double x, double y) {
        Point point = new Point(x, y);
        points.add(point);
    }

    // distance from one point to another
    private double dist(Point p1, Point p2)
    {
        return Math.round(Math.sqrt((p1.x - p2.x) * (p1.x - p2.x)  * Math.pow(10, precision) * Math.pow(10, precision) + (p1.y - p2.y) * (p1.y - p2.y)  * Math.pow(10, precision) * Math.pow(10, precision)));
    }

    /**
     * Решить задачу
     */
    public boolean solve() {
        // перебираем тройки точек
        if (points.size() < 3) return false;

        for (int i = 0; i < points.size(); i++)
        {
            Point d = points.get(i);
            d.num_of_triangles = 0;
            points.set(i, d);
        }


        int a = 0, b = 1, c = 2;

        while (a <= points.size() - 3) {
            if (points.get(a) != points.get(b) && points.get(a) != points.get(c)) {
                double t = dist(points.get(a), points.get(b));
                if (t == dist(points.get(a), points.get(c)) && t == dist(points.get(b), points.get(c))) {
                    Point pt = points.get(a);
                    pt.num_of_triangles++;
                    points.set(a, pt);
                    pt = points.get(b);
                    pt.num_of_triangles++;
                    points.set(b, pt);
                    pt = points.get(c);
                    pt.num_of_triangles++;
                    points.set(c, pt);
                    triangles.add(new Triangle(points.get(a), points.get(b), points.get(c)));
                }
            }
            c += 1;
            if (c >= points.size()) {
                b += 1;
                c = b + 1;
                if (b >= points.size() - 1) {
                    a += 1;
                    b = a + 1;
                    c = b + 1;
                }
            }
        }

        for (int i = 0; i < points.size(); i++) {
            if (points.get(i).num_of_triangles < 2) {
                return false;
            }
        }

        return true;
    }

    /**
     * Загрузить задачу из файла
     */
    public void loadFromFile() {
        points.clear();
        triangles.clear();
        try {
            File file = new File(FILE_NAME);
            Scanner sc = new Scanner(file);
            precision = sc.nextInt();
            // пока в файле есть непрочитанные строки
            while (sc.hasNextLine()) {
                double x = sc.nextDouble();
                double y = sc.nextDouble();
                Point point = new Point(x, y);
                points.add(point);
            }
        } catch (Exception ex) {
            System.out.println("Ошибка чтения из файла: " + ex);
        }
    }

    /**
     * Сохранить задачу в файл
     */
    public void saveToFile() {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME));
            out.print(precision);
            out.print(" ");
            for (Point point : points) {
                out.printf("%." + precision + "f %." + precision + "f ", point.x, point.y);
            }
            out.close();
        } catch (IOException ex) {
            System.out.println("Ошибка записи в файл: " + ex);
        }
    }

    /**
     * Добавить заданное число случайных точек
     *
     * @param n кол-во точек
     */
    public void addRandomPoints(int n) {
        for (int i = 0; i < n; i++) {
            Point p = Point.getRandomPoint();
            points.add(p);
        }
    }

    /**
     * Очистить задачу
     */
    public void clear() {
        points.clear();
        triangles.clear();
    }

    /**
     * Нарисовать задачу
     *
     * @param gl переменная OpenGL для рисования
     */
    public void render(GL2 gl) {
        for (Point point : points) {
            point.render(gl);
        }

        for (Triangle triangle : triangles) {
            triangle.render(gl);
        }
    }
}
