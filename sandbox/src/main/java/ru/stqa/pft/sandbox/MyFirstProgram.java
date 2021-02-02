package ru.stqa.pft.sandbox;

public class MyFirstProgram {
	public static void main(String[] args) {
		System.out.println("Hello, world!");

		Point myPoint = new Point(10, 0);
		Point anotherPoint = new Point(5, 0);
		System.out.println(myPoint.distance(anotherPoint));
	}

	/** Функция расчета расстояния между 2х точек по их координатам
	 *
	 *  public static double distance (Point p1, Point p2) {
	 *		double s;
	 *		s = Math.sqrt(((p2.x - p1.x) * (p2.x - p1.x)) + ((p2.y - p2.y)*(p2.y - p2.y)));
	 *		return s;
	 *  }
	 */
}
