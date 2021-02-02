package ru.stqa.pft.sandbox;

public class Point {
    public double x;
    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Метод для расчета расстояния между 2х точек по их координатам
     */

    public double distance (Point anotherPoint) {
        return Math.sqrt(((this.x - anotherPoint.x) * (this.x - anotherPoint.x)) + ((this.y - anotherPoint.y)*(this.y - anotherPoint.y)));
    }
}
