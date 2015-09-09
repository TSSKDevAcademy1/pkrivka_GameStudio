package stones;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.ScoreController;
import controller.UserController;
import entity.User;

/**
 * Servlet implementation class StonesServlet
 */
@Named
@WebServlet("/stonesServlet")
public class StonesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Inject
	ScoreController scorecontroller;
	@Inject
	UserController usercontroller;
	@Inject
	User user;

	private long stonesStartTime = 0;
	private long stonesTime = 0;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		Field fieldStone;
		String stonesNewGame = request.getParameter("stonesNewGame");
		String stonesMenu = request.getParameter("stonesMenu");
		String rowToDo = request.getParameter("row");
		String columnToDo = request.getParameter("column");
		if (stonesNewGame == null) {
			stonesNewGame = "";
		}
		if (!"".equals(stonesNewGame)) {
			fieldStone = new Field(2, 2);
			request.getSession().removeAttribute("fieldStone");
			request.getSession().removeAttribute("stonesNewGame");
			request.getSession().setAttribute("fieldStone", fieldStone);
			request.getSession().setAttribute("stonesNewGame", stonesNewGame);
			stonesStartTime = System.currentTimeMillis();
		}
		if (rowToDo == null) {
			rowToDo = "";
		}
		if (columnToDo == null) {
			columnToDo = "";
		}
		if (request.getSession().getAttribute("fieldStone") == null) {
			fieldStone = new Field(2, 2);
			request.getSession().setAttribute("fieldStone", fieldStone);
		} else {
			fieldStone = (Field) request.getSession().getAttribute("fieldStone");
			request.getSession().setAttribute("fieldStone", fieldStone);
			stonesNewGame = (String) request.getSession().getAttribute("stonesNewGame");
		}
		if (stonesMenu == null) {
			stonesMenu = "false";
		} else {
			stonesNewGame = "";
			request.getSession().removeAttribute("fieldStone");
			request.getSession().removeAttribute("stonesNewGame");
		}
		Position emptyPosition = fieldStone.check();
		int number = checkWin(fieldStone);
		if (number == fieldStone.getRowCount() * fieldStone.getColumnCount()
				&& emptyPosition.getRow() == fieldStone.getRowCount() - 1
				&& emptyPosition.getColumn() == fieldStone.getColumnCount() - 1) {
			rowToDo = "" + number + "";
			columnToDo = "" + number + "";
		}
		out.println("<div class=\"col-md-3 col-md-offset-5 center\">");
		out.println(
				"<a href=\"akinator.jsf\" style=\"font-size:35px; margin-top: 10px; text-decoration: none;\" class=\"glyphicon glyphicon-menu-left\"></a>");
		out.println(
				"<a href=\"index.jsf\" style=\"font-size:40px; margin-top: 10px; text-decoration: none;\" class=\"glyphicon glyphicon-th\"></a>");
		out.println(
				"<a href=\"mines.jsf\" style=\"font-size:35px; margin-top: 10px; text-decoration: none;\" class=\"glyphicon glyphicon-menu-right\"></a>");
		out.println("</div>");
		out.println("<div class=\"container\">");
		out.println("<div class=\"col-md-12 center\">");
		out.println("<h1>Welcome in the game N-Puzzle!</h1><br/>");
		if (!"true".equals(stonesNewGame)) {
			out.println("<div class=\"row\">");
			out.println("<div class=\"col-md-6 col-md-offset-3\">");
			out.println("<p class=\"text-justify\">The N-Puzzle is a board game for a single player. It consists of "
					+ "(N^2- 1) numbered squared tiles in random order, and one blank space (\"a missing tile\"). "
					+ "The object of the puzzle is to rearrange the tiles in order by making sliding moves that use the "
					+ "empty space, using the fewest moves. Moves of the puzzle are made by sliding an adjacent tile into the "
					+ "empty space. Only tiles that are horizontally or vertically adjacent to the blank space (not "
					+ "diagonally adjacent) may be moved.</p>");
			out.println(
					"<a href=\"stones.jsf?stonesNewGame=true\" class=\"btn btn-primary\" role=\"button\" style=\"margin-top:5px;\">New Game</a><br>");
			out.println(
					"<a href=\"stonesScore.jsf\" class=\"btn btn-primary\" role=\"button\" style=\"margin-top:5px;\">Hall Of Fame</a><br>");
			out.println(
					"<a href=\"rateStones.jsf\" class=\"btn btn-primary\" role=\"button\" style=\"margin-top:5px;\">Rate Game</a><br>");
			out.println(
					"<a href=\"stonesComments.jsf\" class=\"btn btn-primary\" role=\"button\" style=\"margin-top:5px;\">Leave Comment</a><br>");
			out.println("</div>");
			out.println("</div>");
		}
		if ("true".equals(stonesNewGame)) {
			if (!"".equals(rowToDo) && !"".equals(columnToDo)) {
				if (emptyPosition.getRow() < fieldStone.getRowCount()
						&& Integer.parseInt(rowToDo) == emptyPosition.getRow() + 1
						&& Integer.parseInt(columnToDo) == emptyPosition.getColumn()) {
					fieldStone.move(1, 0);
					printField(out, fieldStone);
				} else if (emptyPosition.getRow() > 0 && Integer.parseInt(rowToDo) == emptyPosition.getRow() - 1
						&& Integer.parseInt(columnToDo) == emptyPosition.getColumn()) {
					fieldStone.move(-1, 0);
					printField(out, fieldStone);
				} else if (emptyPosition.getColumn() < fieldStone.getColumnCount()
						&& Integer.parseInt(columnToDo) == emptyPosition.getColumn() + 1
						&& Integer.parseInt(rowToDo) == emptyPosition.getRow()) {
					fieldStone.move(0, 1);
					printField(out, fieldStone);
				} else
					if (emptyPosition.getColumn() > 0 && Integer.parseInt(columnToDo) == emptyPosition.getColumn() - 1
							&& Integer.parseInt(rowToDo) == emptyPosition.getRow()) {
					fieldStone.move(0, -1);
					printField(out, fieldStone);
				} else
					printField(out, fieldStone);
				stonesTime = (System.currentTimeMillis() - stonesStartTime) / 1000;
			} else
				printField(out, fieldStone);
			emptyPosition = fieldStone.check();
			number = checkWin(fieldStone);
			if (number == fieldStone.getRowCount() * fieldStone.getColumnCount()
					&& emptyPosition.getRow() == fieldStone.getRowCount() - 1
					&& emptyPosition.getColumn() == fieldStone.getColumnCount() - 1) {
				out.println("You WIN!<br>");
				out.println("Your playing time was " + stonesTime + "s.");
				if (usercontroller.isLogged()) {
					scorecontroller.addScore(stonesTime, user.getName(), user.getPasswd(), "stones");
				}
			}
			out.println("<br>");
			out.println(
					"<a href=\"?stonesNewGame=true\" class=\"btn btn-primary\" role=\"button\" style=\"margin-top:5px;\">New Game</a><br>");
			out.println(
					"<a href=\"?stonesMenu=true\" class=\"btn btn-danger\" role=\"button\" style=\"margin-top:5px;\">End Game</a>");
		}

		out.println("</div>");// col-md-12 center
		out.println("</div>");// container
	}

	void printField(PrintWriter out, Field fieldStone) {
		for (int row = 0; row < fieldStone.getRowCount(); row++) {
			for (int column = 0; column < fieldStone.getColumnCount(); column++) {
				if (fieldStone.getTile(row, column).getState() == Tile.State.OPEN) {
					out.print("<a href=\"?row=" + row + "&column=" + column
							+ "\" style=\"text-align:center;box-align:center;font-weight: bold;text-decoration:none;background-image: url(resources/gfx/square1.png);display:inline-block;width:30px;height:30px; opacity:.1;border: solid 1px black;\">&nbsp;</a>");
				} else if (fieldStone.getTile(row, column).getState() == Tile.State.NUMBER) {
					if (fieldStone.getTile(row, column) instanceof NumberTile) {
						NumberTile numberTile = (NumberTile) fieldStone.getTile(row, column);
						out.print("<a href=\"?row=" + row + "&column=" + column
								+ "\" style=\"text-align:center;box-align:center;font-weight: bold;text-decoration:none;background-image: url(resources/gfx/square1.png);display:inline-block;width:30px;height:30px;border: solid 1px black;\">"
								+ numberTile.getValue() + "</a>");
					}
				}
				if (column == fieldStone.getColumnCount() - 1) {
					out.println("<br>");
				}
			}
		}
	}

	private int checkWin(Field fieldStone) {
		int number = 1;
		for (int row = 0; row < fieldStone.getRowCount(); row++) {
			for (int column = 0; column < fieldStone.getColumnCount(); column++) {
				NumberTile numbertile = (NumberTile) fieldStone.getTile(row, column);
				if (fieldStone.getTile(row, column).getState() == Tile.State.NUMBER
						&& numbertile.getValue() == number) {
					number++;
				}
			}
		}
		return number;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
