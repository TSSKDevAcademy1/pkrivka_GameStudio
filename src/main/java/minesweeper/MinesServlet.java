package minesweeper;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.MinesController;
import controller.MinesSettingsController;
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
	@Inject
	MinesController minesController;
	@Inject
	MinesSettingsController minesSettingsController;
	private long minesStartTime = 0;
	private long minesTime = 0;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		MinesField fieldMine = null;
		String minesNewGame = null;
		String minesRestartGame = request.getParameter("minesRestartGame");
		String minesMenu = request.getParameter("minesMenu");
		String markTile = "false";
		String rowToDo = request.getParameter("row");
		String columnToDo = request.getParameter("column");
		int actualNumRow = 0;
		int actualNumCol = 0;
		int actualNumMin = 0;

		if (request.getSession().getAttribute("minesNewGame") != null && request.getParameter("minesNewGame") == null) {
			minesNewGame = (String) request.getSession().getAttribute("minesNewGame");
		} else if (request.getParameter("minesNewGame") != null) {
			minesNewGame = request.getParameter("minesNewGame");
		}

		if (minesNewGame == null && minesController.isNewGame()) {
			minesNewGame = "true";
		}

		if (request.getSession().getAttribute("markTile") != null && request.getParameter("markTile") == null) {
			markTile = (String) request.getSession().getAttribute("markTile");
		} else if (request.getParameter("markTile") != null) {
			markTile = request.getParameter("markTile");
		}

		if (rowToDo == null) {
			rowToDo = "";
		}
		if (columnToDo == null) {
			columnToDo = "";
		}

		if ("true".equals(minesNewGame)) {
			if (usercontroller.isLogged()) {
				if (usercontroller.getMinesRows(user.getName(), user.getPasswd()) != 0 && usercontroller.getMinesCols(user.getName(), user.getPasswd()) != 0) {
					fieldMine = new MinesField(usercontroller.getMinesRows(user.getName(), user.getPasswd()),
							usercontroller.getMinesCols(user.getName(), user.getPasswd()),
							usercontroller.getMinesMines(user.getName(), user.getPasswd()));
				}else
					fieldMine = new MinesField(5, 5, 1);
			} else
				fieldMine = new MinesField(5, 5, 1);
			request.getSession().removeAttribute("fieldMine");
			request.getSession().removeAttribute("minesNewGame");
			request.getSession().setAttribute("fieldMine", fieldMine);
			minesNewGame = "false";
			request.getSession().setAttribute("minesNewGame", minesNewGame);
			minesController.setWinState(false);
			minesStartTime = System.currentTimeMillis();
		}
		if ("true".equals(minesRestartGame)) {
			fieldMine = (MinesField) request.getSession().getAttribute("fieldMine");
			actualNumRow = fieldMine.getRowCount();
			actualNumCol = fieldMine.getColumnCount();
			actualNumMin = fieldMine.getMineCount();
			fieldMine = new MinesField(actualNumRow, actualNumCol, actualNumMin);
			request.getSession().removeAttribute("fieldMine");
			request.getSession().removeAttribute("minesNewGame");
			request.getSession().setAttribute("fieldMine", fieldMine);
			minesRestartGame = "false";
			request.getSession().setAttribute("minesNewGame", minesNewGame);
			minesController.setWinState(false);
			minesStartTime = System.currentTimeMillis();
		}

		if (request.getSession().getAttribute("fieldMine") == null
				&& request.getSession().getAttribute("minesNewGame") == null) {
			fieldMine = new MinesField(10, 10, 10);
			request.getSession().setAttribute("fieldMine", fieldMine);
		} else {
			fieldMine = (MinesField) request.getSession().getAttribute("fieldMine");
			minesNewGame = (String) request.getSession().getAttribute("minesNewGame");
			if (minesController.isWinState()) {
				minesStartTime = System.currentTimeMillis();
				minesController.setWinState(false);
			}
		}
		if (minesMenu == null) {
			minesMenu = "false";
		} else if ("true".equals(minesMenu)) {
			minesNewGame = "";
			request.getSession().removeAttribute("fieldMine");
			request.getSession().removeAttribute("minesNewGame");
			request.getSession().removeAttribute("markTile");
			minesController.setNewGame(false);
			minesController.setWinState(false);
		}

		if (!"".equals(rowToDo) && !"".equals(columnToDo) && !minesController.isWinState()) {
			if ("true".equals(markTile)) {
				fieldMine.markTile(Integer.parseInt(rowToDo), Integer.parseInt(columnToDo));
			} else
				fieldMine.openTile(Integer.parseInt(rowToDo), Integer.parseInt(columnToDo));
		}

		if (minesNewGame != null && !"true".equals(minesMenu) && fieldMine != null) {
			out.println("<p>Number of remaining mines: " + fieldMine.getRemainingMineCount() + ".</p>");
			if (fieldMine.getState() == GameState.FAILED) {
				minesController.setWinState(true);
				printField(out, fieldMine);
			} else if (fieldMine.getState() == GameState.PLAYING) {
				printField(out, fieldMine);
			} else if (fieldMine.getState() == GameState.SOLVED) {
				if (usercontroller.isLogged() && !minesController.isWinState()) {
					scorecontroller.addScore(minesTime, user.getName(), user.getPasswd(), "mines");
				}
				minesController.setWinState(true);
				printField(out, fieldMine);
			}
			if ("true".equals(markTile)) {
				out.println("<br><a href=\"?markTile=false\" class=\"btn btn-default\" role=\"button\">OPEN</a><br>");
				request.getSession().setAttribute("markTile", markTile);
			} else {
				out.println("<br><a href=\"?markTile=true\" class=\"btn btn-default\" role=\"button\">MARK</a><br>");
				request.getSession().setAttribute("markTile", markTile);
			}
			out.println(
					"<a href=\"?minesNewGame=true\" class=\"btn btn-primary\" role=\"button\" style=\"margin-top:5px;\">New Game</a><br>");
			out.println(
					"<a href=\"?minesRestartGame=true\" class=\"btn btn-success\" role=\"button\" style=\"margin-top:5px;\">Restart Game</a><br>");
			out.println(
					"<a href=\"?minesMenu=true\" class=\"btn btn-danger\" role=\"button\" style=\"margin-top:5px;\">End Game</a><br>");
			if (fieldMine.getState() == GameState.FAILED) {
				out.println(
						"<span style=\"color:red;font-weight: bold;\">You LOSE!!!</span><br><span style=\"color:red;font-weight: bold;\">Your playing time was "
								+ minesTime + "s. </span>");
				out.println("<script type=\"text/javascript\">alert(\"You LOSE!!! Your playing time was " + minesTime
						+ "s.\")</script>");
			} else if (fieldMine.getState() == GameState.SOLVED) {
//				out.println(
//						"<span style=\"color:red;font-weight: bold;\">You WON!!!</span><br><span style=\"color:red;font-weight: bold;\">Your playing time was "
//								+ minesTime + "s. </span>");
				out.println("<script type=\"text/javascript\">alert(\"You WON!!! Your playing time was " + minesTime
						+ "s.\")</script>");
			}
		}
	}

	private void openTiles(MinesField field) {
		for (int row = 0; row < field.getRowCount(); row++) {
			for (int column = 0; column < field.getColumnCount(); column++) {
				field.openTile(row, column);
			}
		}
	}

	private void printField(PrintWriter out, MinesField field) {
		for (int row = 0; row < field.getRowCount(); row++) {
			for (int column = 0; column < field.getColumnCount(); column++) {
				if (field.getTile(row, column).getState() == Tile.State.CLOSED) {
					out.print("<a href=\"?row=" + row + "&column=" + column
							+ "\"><img src=\"resources/gfx/images/closed.png\"></a>");
				} else if (field.getTile(row, column).getState() == Tile.State.OPEN) {
					if (field.getTile(row, column) instanceof Clue) {
						Clue clue = (Clue) field.getTile(row, column);
						out.print("<a href=\"?row=" + row + "&column=" + column
								+ "\"><img src=\"resources/gfx/images/open" + clue.getValue() + ".png\"></a>");
					} else if (field.getTile(row, column) instanceof Mine) {
						out.print("<a href=\"?row=" + row + "&column=" + column
								+ "\"><img src=\"resources/gfx/images/mine.png\"></a>");
					}
				} else if (field.getTile(row, column).getState() == Tile.State.MARKED) {
					out.print("<a href=\"?row=" + row + "&column=" + column
							+ "\"><img src=\"resources/gfx/images/marked.png\"></a>");
				}
				if (column == field.getColumnCount() - 1) {
					out.println("<br>");
				}
			}
		}
		if (!minesController.isWinState()) {
			minesTime = (System.currentTimeMillis() - minesStartTime) / 1000;
		}
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
