import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @author abhinaw sarang
 * @created on 01-27-2020
 * @version 1.0
 * @author Rohit Kumar Singh
 * @modified on 01-28-2020
 * @version 2.0
 */
public class DrawShapeOnMouseClick extends JPanel {

	private static final long serialVersionUID = 1L;

	private Map<Point, String> shapeOrigin;
	private String selectedShapeName;
	private String draggedShapeName = "";
	private List<Point> trianglePoints;
	private List<Point> circlePoints;
	private Boolean firstPoint = false;
	private Boolean secondPoint = false;
	private Point firstPointLocation;
	private Point secondPointLocation;

	public DrawShapeOnMouseClick() {

		shapeOrigin = new HashMap<>();
		trianglePoints = new ArrayList<>();
		circlePoints = new ArrayList<>();
		this.setPreferredSize(new Dimension(1600, 800));
		this.setVisible(true);
		addMouseListener(new DrawBoardMouseListener());
	}

	@Override
	public void paintComponent(Graphics graphics) {

		try {
			super.paintComponent(graphics);
			Graphics2D graphicsDimension = (Graphics2D) graphics;
			graphicsDimension.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			if (firstPoint && secondPoint) {
				Connections objConn = new Connections();
				objConn.DotToDotConnection(graphicsDimension,
						firstPointLocation.x, firstPointLocation.y,
						secondPointLocation.x, secondPointLocation.y);
				firstPoint = false;
				secondPoint = false;
			}
			for (Point p1 : shapeOrigin.keySet()) {
				String currShape = shapeOrigin.get(p1);
				System.out.println("Curr Shape...." + currShape);
				if (currShape.equalsIgnoreCase("rectangle")) {
					graphicsDimension.drawRect(p1.x, p1.y, 100, 80);
				} else if (currShape.equalsIgnoreCase("square")) {
					graphicsDimension.drawRect(p1.x, p1.y, 80, 80);
				} else if (currShape.equalsIgnoreCase("circle")) {
					graphicsDimension.drawOval(p1.x-40, p1.y-40, 80, 80);
					graphicsDimension.fillOval(p1.x-3, p1.y-3, 6, 6);
					System.out.println("Adding point to circle");
					circlePoints.add(new Point(p1.x-3, p1.y-3));
				} else if (currShape.equalsIgnoreCase("triangle")) {
					graphicsDimension.drawPolygon(new int[] { p1.x - 40, p1.x, p1.x + 40 },
							new int[] { p1.y + 40, p1.y - 40, p1.y + 40 }, 3);
					
					graphicsDimension.fillOval(p1.x - 4, p1.y - 40 + 8 - 4,
							8, 8);
					graphicsDimension.fillOval(p1.x - 40 + 8 - 4,
							p1.y + 40 - 8 - 4, 8, 8);
					graphicsDimension.fillOval(p1.x + 40 - 8 - 4,
							p1.y + 40 - 8 - 4, 8, 8);
					
					System.out.println("Adding point to triangle");
					trianglePoints.add(new Point(p1.x - 4, p1.y - 40 + 8 - 4));
					trianglePoints.add(new Point(p1.x - 40 + 8 - 4, p1.y + 40 - 8 - 4));
					trianglePoints.add(new Point(p1.x + 40 - 8 - 4, p1.y + 40 - 8 - 4));
				} else {
					System.out.println("Shape not selected");
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	private class DrawBoardMouseListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent event) {
			List<Point> allPoints = new ArrayList<>();
			allPoints.addAll(trianglePoints);
			allPoints.addAll(circlePoints);
			boolean selectingSecond = firstPoint;
			for (Point point : allPoints) {
				if (new Rectangle2D.Double(event.getX() - 10, event.getY() - 10, 20, 20).contains(point)) {
					if(!firstPoint) {
						firstPoint = true;
						firstPointLocation = point;
					} else {
						secondPoint = true;
						secondPointLocation = point;
					}
					break;
				}
			}
			if (firstPoint && secondPoint) {
				repaint();
			} else if ((!firstPoint) || (selectingSecond && !secondPoint)) {
				firstPoint = false;
				secondPoint = false;
				try {
					selectedShapeName = ClickedShape.shapeName;
					if (selectedShapeName.isEmpty() || (selectedShapeName == null)) {
						System.out.println("selectedShapeName");
						JOptionPane.showMessageDialog(null, "Please select a shape");
					} else {
						shapeOrigin.put(new Point(event.getX(), event.getY()), selectedShapeName);
						repaint();
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent event) {
			if (!firstPoint) {
				try {
					for (Point point : shapeOrigin.keySet()) {
						if (new Rectangle2D.Double(event.getX() - 80, event.getY() - 80, 120, 120).contains(point)) {
							draggedShapeName = shapeOrigin.get(point);
							shapeOrigin.remove(point);
							break;
						}
					}
					for (Point point : circlePoints) {
						if (new Rectangle2D.Double(event.getX() - 80, event.getY() - 80, 120, 120).contains(point)) {
							System.out.println("Removing point from circle" + point);
							circlePoints.remove(point);
							break;
						}
					}
					for (Point point : trianglePoints) {
						if (new Rectangle2D.Double(event.getX() - 80, event.getY() - 80, 120, 120).contains(point)) {
							System.out.println("Removing point from triangle" + point);
							trianglePoints.remove(point);
							break;
						}
					}
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent event) {
			if (!firstPoint) {
				try {
					shapeOrigin.put(new Point(event.getX(), event.getY()), draggedShapeName);
					repaint();
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			}
		}
	}
}
