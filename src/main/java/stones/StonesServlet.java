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

import controller.MinesController;
import controller.ScoreController;
import controller.StonesController;
import controller.UserController;
import entity.User;
import minesweeper.MinesField;

/**
 * Servlet implementation class StonesServlet
 */
@WebServlet("/stonesServlet")
public class StonesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Inject
	ScoreController scoreController;
	@Inject
	UserController userController;
	@Inject
	StonesController stonesController;
	@Inject
	User user;

	private long stonesStartTime = 0;
	private long stonesTime = 0;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		StonesField fieldStone;
		String stonesNewGame = null;
		String stonesRestartGame = request.getParameter("stonesRestartGame");
		String stonesMenu = request.getParameter("stonesMenu");
		String rowToDo = request.getParameter("row");
		String columnToDo = request.getParameter("column");
		int actualNumRow = 0;
		int actualNumCol = 0;

		if (request.getSession().getAttribute("stonesNewGame") != null
				&& request.getParameter("stonesNewGame") == null) {
			stonesNewGame = (String) request.getSession().getAttribute("stonesNewGame");
		} else if (request.getParameter("stonesNewGame") != null) {
			stonesNewGame = request.getParameter("stonesNewGame");
		}
<<<<<<< HEAD

		if (stonesNewGame == null && stonesController.isNewGame()) {
			stonesNewGame = "true";
=======
		if (!"".equals(stonesNewGame)) {
			fieldStone = new Field(3, 3);
			request.getSession().removeAttribute("fieldStone");
			request.getSession().removeAttribute("stonesNewGame");
			request.getSession().setAttribute("fieldStone", fieldStone);
			request.getSession().setAttribute("stonesNewGame", stonesNewGame);
			stonesStartTime = System.currentTimeMillis();
>>>>>>> origin/master
		}

		if (rowToDo == null) {
			rowToDo = "";
		}
		if (columnToDo == null) {
			columnToDo = "";
		}
<<<<<<< HEAD

		if ("true".equals(stonesNewGame)) {
			if (userController.isLogged()) {
				if (userController.getStonesRows(user.getName(), user.getPasswd()) != 0
						&& userController.getStonesCols(user.getName(), user.getPasswd()) != 0) {
					fieldStone = new StonesField(userController.getStonesRows(user.getName(), user.getPasswd()),
							userController.getStonesCols(user.getName(), user.getPasswd()));
				} else
					fieldStone = new StonesField(3, 3);
			} else
				fieldStone = new StonesField(3, 3);

			request.getSession().removeAttribute("fieldStone");
			request.getSession().removeAttribute("stonesNewGame");
=======
		if (request.getSession().getAttribute("fieldStone") == null) {
			fieldStone = new Field(3, 3);
>>>>>>> origin/master
			request.getSession().setAttribute("fieldStone", fieldStone);
			stonesNewGame = "false";
			request.getSession().setAttribute("stonesNewGame", stonesNewGame);
			stonesController.setWinState(false);
			stonesStartTime = System.currentTimeMillis();
		}
		if ("true".equals(stonesRestartGame)) {
			fieldStone = (StonesField) request.getSession().getAttribute("fieldStone");
			actualNumRow = fieldStone.getRowCount();
			actualNumCol = fieldStone.getColumnCount();
			fieldStone = new StonesField(actualNumRow, actualNumCol);
			request.getSession().removeAttribute("fieldStone");
			request.getSession().removeAttribute("stonesNewGame");
			request.getSession().setAttribute("fieldStone", fieldStone);
			stonesRestartGame = "false";
			request.getSession().setAttribute("stonesNewGame", stonesNewGame);
			stonesController.setWinState(false);
			stonesStartTime = System.currentTimeMillis();
		}

		if (request.getSession().getAttribute("fieldStone") == null
				&& request.getSession().getAttribute("stonesNewGame") == null) {
			fieldStone = new StonesField(3, 3);
			request.getSession().setAttribute("fieldStone", fieldStone);
		} else {
			fieldStone = (StonesField) request.getSession().getAttribute("fieldStone");
			stonesNewGame = (String) request.getSession().getAttribute("stonesNewGame");
			if (stonesController.isWinState()) {
				stonesStartTime = System.currentTimeMillis();
				stonesController.setWinState(false);
			}
		}

		if (stonesMenu == null) {
			stonesMenu = "false";
		} else if ("true".equals(stonesMenu)) {
			stonesNewGame = "";
			request.getSession().removeAttribute("fieldStone");
			request.getSession().removeAttribute("stonesNewGame");
			stonesController.setNewGame(false);
		}
		Position emptyPosition = fieldStone.check();
		int number = checkWin(fieldStone);
		if (number == fieldStone.getRowCount() * fieldStone.getColumnCount()
				&& emptyPosition.getRow() == fieldStone.getRowCount() - 1
				&& emptyPosition.getColumn() == fieldStone.getColumnCount() - 1) {
			rowToDo = "" + number + "";
			columnToDo = "" + number + "";
		}

		if (stonesNewGame != null && !"true".equals(stonesMenu)) {
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
			} else
				printField(out, fieldStone);
			emptyPosition = fieldStone.check();
			number = checkWin(fieldStone);
			out.println(
					"<br><a href=\"?stonesNewGame=true\" class=\"btn btn-primary\" role=\"button\" style=\"margin-top:5px;\">New Game</a><br>");
			out.println(
					"<a href=\"?stonesRestartGame=true\" class=\"btn btn-success\" role=\"button\" style=\"margin-top:5px;\">Restart Game</a><br>");
			out.println(
					"<a href=\"?stonesMenu=true\" class=\"btn btn-danger\" role=\"button\" style=\"margin-top:5px;\">End Game</a><br>");
			if (number == fieldStone.getRowCount() * fieldStone.getColumnCount()
					&& emptyPosition.getRow() == fieldStone.getRowCount() - 1
					&& emptyPosition.getColumn() == fieldStone.getColumnCount() - 1 && !stonesController.isWinState()) {
				stonesTime = (System.currentTimeMillis() - stonesStartTime) / 1000;
				stonesController.setWinState(true);
				// out.println(
				// "<span style=\"color:red;font-weight: bold;\">You
				// WON!!!</span><br><span style=\"color:red;font-weight:
				// bold;\">Your playing time was "
				// + stonesTime + "s. </span>");
				out.println("<script type=\"text/javascript\">alert(\"You WON!!! Your playing time was " + stonesTime
						+ "s.\")</script>");
				if (userController.isLogged()) {
					scoreController.addScore(stonesTime, user.getName(), user.getPasswd(), "stones");
				}
			}
			// out.println("<br>");
		}
	}

	void printField(PrintWriter out, StonesField fieldStone) {
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

	private int checkWin(StonesField fieldStone) {
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
