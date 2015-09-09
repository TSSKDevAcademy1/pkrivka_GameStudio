package minesweeper;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.ScoreController;
import controller.UserController;
import entity.User;

/**
 * Servlet implementation class MinesServlet
 */
@WebServlet("/minesServlet")
public class MinesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Inject
	ScoreController scorecontroller;
	@Inject
	UserController usercontroller;
	@Inject
	User user;
	private long minesStartTime = 0;
	private long minesTime = 0;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		Field fieldMine;
		String rowToDo = request.getParameter("row");
		String columnToDo = request.getParameter("column");
		String minesNewGame = request.getParameter("minesNewGame");
		String minesMenu = request.getParameter("minesMenu");
		String checkMark = request.getParameter("checkMark");

		if (rowToDo == null) {
			rowToDo = "";
		}
		if (columnToDo == null) {
			columnToDo = "";
		}
		if (minesNewGame == null) {
			minesNewGame = "";
		}
		if (checkMark == null) {
			checkMark = "false";
		}
		if (!"".equals(minesNewGame)) {
			fieldMine = new Field(10, 10, 10);
			request.getSession().setAttribute("fieldMine", fieldMine);
			request.getSession().setAttribute("minesNewGame", minesNewGame);
			minesStartTime = System.currentTimeMillis();
		}
		if (request.getSession().getAttribute("fieldMine") == null) {
			fieldMine = new Field(10, 10, 10);
			request.getSession().setAttribute("fieldMine", fieldMine);
		} else {
			fieldMine = (Field) request.getSession().getAttribute("fieldMine");
			request.getSession().setAttribute("fieldMine", fieldMine);
			minesNewGame = (String) request.getSession().getAttribute("minesNewGame");
		}
		if (minesMenu == null) {
			minesMenu = "false";
		} else {
			minesNewGame = "";
			request.getSession().removeAttribute("fieldMine");
			request.getSession().removeAttribute("minesNewGame");
		}
		if (!"".equals(rowToDo) && !"".equals(columnToDo)) {
			if ("true".equals(checkMark)) {
				fieldMine.markTile(Integer.parseInt(rowToDo), Integer.parseInt(columnToDo));
			} else if ("false".equals(checkMark)) {
				fieldMine.openTile(Integer.parseInt(rowToDo), Integer.parseInt(columnToDo));
			}
		}
		out.println("<div class=\"col-md-3 col-md-offset-5 center\">");
		out.println("<a href=\"stones.jsf\" style=\"font-size:35px; margin-top: 10px; text-decoration: none;\" class=\"glyphicon glyphicon-menu-left\"></a>");
		out.println("<a href=\"index.jsf\" style=\"font-size:40px; margin-top: 10px; text-decoration: none;\" class=\"glyphicon glyphicon-th\"></a>");
		out.println("<a href=\"akinator.jsf\" style=\"font-size:35px; margin-top: 10px; text-decoration: none;\" class=\"glyphicon glyphicon-menu-right\"></a>");
		out.println("</div>");
		out.println("<div class=\"container\">");
		out.println("<div class=\"col-md-12 center\">");
		out.println("<h1>Welcome in the game MineSweeper!</h1><br/>");
		if (!"true".equals(minesNewGame)) {
			out.println("<div class=\"row\">");
			out.println("<div class=\"col-md-6 col-md-offset-3\">");
			out.println("<p class=\"text-justify\">Minesweeper is a single-player puzzle video game. "
					+ "The objective of the game is to clear a rectangular board containing hidden "
					+ "mines without detonating any of them, with help from clues about the number of neighboring "
					+ "mines in each field. The game originates from the 1960s, and has been written for many "
					+ "computing platforms in use today</p>");
			out.println(
					"<a href=\"mines.jsf?minesNewGame=true\" class=\"btn btn-primary\" role=\"button\" style=\"margin-top:5px;\">New Game</a><br>");
			out.println(
					"<a href=\"minesScore.jsf\" class=\"btn btn-primary\" role=\"button\" style=\"margin-top:5px;\">Hall Of Fame</a><br>");
			out.println(
					"<a href=\"rateMines.jsf\" class=\"btn btn-primary\" role=\"button\" style=\"margin-top:5px;\">Rate Game</a><br>");
			out.println(
					"<a href=\"minesComments.jsf\" class=\"btn btn-primary\" role=\"button\" style=\"margin-top:5px;\">Leave Comment</a><br>");
			out.println("</div>");
			out.println("</div>");
		}
		if ("true".equals(minesNewGame)) {
			out.println("<p>Number of remaining mines: " + fieldMine.getRemainingMineCount() + ".</p>");
			if (fieldMine.getState() == GameState.FAILED) {
				openTiles(fieldMine);
				printField(out, fieldMine, checkMark);
				out.println("You LOOSE!");
				out.println("Your playing time was " + minesTime + "s.");
			} else if (fieldMine.getState() == GameState.SOLVED) {
				openTiles(fieldMine);
				printField(out, fieldMine, checkMark);
				out.println("You WIN!");
				out.println("Your playing time was " + minesTime + "s.");
				if (usercontroller.isLogged()) {
					scorecontroller.addScore(minesTime, user.getName(), user.getPasswd(), "mines");
				}
			} else if (fieldMine.getState() == GameState.PLAYING) {
				printField(out, fieldMine, checkMark);
				out.println("Your playing time is " + minesTime + "s.");
			}
			out.println("<br>");
			out.println("<a href=\"?minesNewGame=true\" class=\"btn btn-primary\" role=\"button\">New Game</a>");
			if ("true".equals(checkMark) && "true".equals(minesNewGame)) {
				out.println("<a href=\"?checkMark=false\" class=\"btn btn-success\" role=\"button\">OPEN</a><br>");
			} else if ("false".equals(checkMark) && "true".equals(minesNewGame)) {
				out.println("<a href=\"?checkMark=true\" class=\"btn btn-success\" role=\"button\">MARK</a><br>");
			}
			out.println(
					"<a href=\"?minesMenu=true\" class=\"btn btn-danger\" role=\"button\" style=\"margin-top:5px;\">End Game</a>");
		}
		
		out.println("</div>");// col-md-12 center
		out.println("</div>");// container
	}

	private void openTiles(Field field) {
		for (int row = 0; row < field.getRowCount(); row++) {
			for (int column = 0; column < field.getColumnCount(); column++) {
				field.openTile(row, column);
			}
		}
	}

	private void printField(PrintWriter out, Field field, String checkMark) {
		for (int row = 0; row < field.getRowCount(); row++) {
			for (int column = 0; column < field.getColumnCount(); column++) {
				if (field.getTile(row, column).getState() == Tile.State.CLOSED) {
					out.print("<a href=\"?row=" + row + "&column=" + column + "&checkMark=" + checkMark
							+ "\"><img src=\"resources/gfx/images/closed.png\"></a>");
				} else if (field.getTile(row, column).getState() == Tile.State.OPEN) {
					if (field.getTile(row, column) instanceof Clue) {
						Clue clue = (Clue) field.getTile(row, column);
						out.print("<a href=\"?row=" + row + "&column=" + column + "&checkMark=" + checkMark
								+ "\"><img src=\"resources/gfx/images/open" + clue.getValue() + ".png\"></a>");
					} else if (field.getTile(row, column) instanceof Mine) {
						out.print("<a href=\"?row=" + row + "&column=" + column + "&checkMark=" + checkMark
								+ "\"><img src=\"resources/gfx/images/mine.png\"></a>");
					}
				} else if (field.getTile(row, column).getState() == Tile.State.MARKED) {
					out.print("<a href=\"?row=" + row + "&column=" + column + "&checkMark=" + checkMark
							+ "\"><img src=\"resources/gfx/images/marked.png\"></a>");
				}
				if (column == field.getColumnCount() - 1) {
					out.println("<br>");
				}
			}
		}
		minesTime = (System.currentTimeMillis() - minesStartTime) / 1000;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
