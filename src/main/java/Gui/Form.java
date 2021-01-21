package Gui;

import problem.Point;
import problem.Problem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Класс формы приложения
 */
public class Form extends JFrame {
    /**
     * панель для отображения OpenGL
     */
    private JPanel GLPlaceholder;
    private JPanel root;
    private JTextField xPointField;
    private JTextField yPointField;
    private JButton randomBtn;
    private JTextField pointCntField;
    private JButton loadFromFileBtn;
    private JButton saveToFileBtn;
    private JButton clearBtn;
    private JButton solveBtn;
    private JLabel problemText;
    private JButton addPoint;
    private JLabel precisionLabel;
    private JTextField fpTextField;
    private JButton setPrecBtn;
    private JLabel answer;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    /**
     * таймер
     */
    private final Timer timer;
    /**
     * рисовалка OpenGL
     */
    private final RendererGL renderer;

    /**
     * Конструктор формы
     */
    private Form() {
        super(Problem.PROBLEM_CAPTION);
        // инициализируем OpenGL
        renderer = new RendererGL();
        // связываем элемент на форме с рисовалкой OpenGL
        GLPlaceholder.setLayout(new BorderLayout());
        GLPlaceholder.add(renderer.getCanvas());
        // указываем главный элемент формы
        getContentPane().add(root);
        // задаём размер формы
        setSize(getPreferredSize());
        // показываем форму
        setVisible(true);
        // обработчик зарытия формы
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new Thread(() -> {
                    renderer.close();
                    timer.stop();
                    System.exit(0);
                }).start();
            }
        });
        // тинициализация таймера, срабатывающего раз в 100 мсек
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onTime();
            }
        });
        timer.start();
        initWidgets();
    }

    /**
     * Инициализация виджетов
     */
    private void initWidgets() {
        // задаём текст полю описания задачи
        problemText.setText("<html>" + Problem.PROBLEM_TEXT.replaceAll("\n", "<br>"));

        addPoint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double x = Double.parseDouble(xPointField.getText());
                double y = Double.parseDouble(yPointField.getText());
                renderer.problem.addPoint(x, y);
                answer.setText("Ответ");
            }
        });
        randomBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renderer.problem.addRandomPoints(Integer.parseInt(pointCntField.getText()));
                answer.setText("Ответ");
            }
        });
        loadFromFileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renderer.problem.loadFromFile();
                answer.setText("Ответ");
                precisionLabel.setText("Учитывать знаков после запятой (" + renderer.problem.getPrecision() + "):");
            }
        });
        saveToFileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renderer.problem.saveToFile();
            }
        });
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renderer.problem.clear();
                answer.setText("Ответ");
            }
        });
        solveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fpTextField.setText(String.valueOf(renderer.problem.getPrecision()));
                precisionLabel.setText("Учитывать знаков после запятой (" + renderer.problem.getPrecision() + "):");
                if (renderer.problem.solve())
                    answer.setText("Ответ: ДА");
                else answer.setText("Ответ: НЕТ");
            }
        });
        setPrecBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renderer.problem.setPrecision(Integer.parseInt(fpTextField.getText()));
                precisionLabel.setText("Учитывать знаков после запятой (" + renderer.problem.getPrecision() + "):");
                answer.setText("Ответ");
            }
        });
    }

    /**
     * Событие таймера
     */
    private void onTime() {
        // события по таймеру
    }

    /**
     * Главный метод
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        new Form();
    }
}
