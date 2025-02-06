package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class BowAndArrowGame extends JPanel implements ActionListener, KeyListener {
    private int bowX = 50, bowY = 200, arrowSpeed = 10;
    private ArrayList<Rectangle> arrows;
    private ArrayList<Rectangle> targets;
    private Timer timer;
    private Random random;
    private int score = 0;

    public BowAndArrowGame() {
        setPreferredSize(new Dimension(500, 300));
        setBackground(Color.WHITE);
        setFocusable(true);
        addKeyListener(this);
        arrows = new ArrayList<>();
        targets = new ArrayList<>();
        random = new Random();
        timer = new Timer(20, this);
        timer.start();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(bowX, bowY, 10, 50);
        g.setColor(Color.RED);
        for (Rectangle arrow : arrows) g.fillRect(arrow.x, arrow.y, arrow.width, arrow.height);
        g.setColor(Color.BLUE);
        for (Rectangle target : targets) g.fillRect(target.x, target.y, target.width, target.height);
        g.setColor(Color.BLACK);
        g.drawString("Score: " + score, 10, 20);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < arrows.size(); i++) {
            Rectangle arrow = arrows.get(i);
            arrow.x += arrowSpeed;
            if (arrow.x > 500) arrows.remove(i);
        }
        for (int i = 0; i < targets.size(); i++) {
            Rectangle target = targets.get(i);
            target.x -= 5;
            if (target.x < 0) targets.remove(i);
        }
        if (random.nextInt(100) < 2) targets.add(new Rectangle(500, random.nextInt(250), 30, 30));
        for (int i = 0; i < arrows.size(); i++) {
            for (int j = 0; j < targets.size(); j++) {
                if (arrows.get(i).intersects(targets.get(j))) {
                    arrows.remove(i);
                    targets.remove(j);
                    score += 10;
                    break;
                }
            }
        }
        repaint();
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && bowY > 0) bowY -= 10;
        if (e.getKeyCode() == KeyEvent.VK_DOWN && bowY < 250) bowY += 10;
        if (e.getKeyCode() == KeyEvent.VK_SPACE) arrows.add(new Rectangle(bowX + 10, bowY + 20, 10, 5));
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {


    }
}