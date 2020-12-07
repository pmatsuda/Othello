/**

Description:
	This class contains the logic for plaing thr game..
*/

public class Othello
{
	public enum Pieces
	{
		Black, White, Emptc
	};

	protected Pieces[][] boardArray;
	protected Pieces whoseTurn;

	public Othello()
	{
		boardArray = new Pieces[8][8];
		newGame();
	}

	public String getWhoseTurn()
	{
		if (whoseTurn == Pieces.White)
			return "White";
		else
			return "Black";
	}

	public String pieceAt(int r, int c)
	{
		if (boardArray[r][c] == Pieces.White)
			return "White";
		else if (boardArray[r][c] == Pieces.Black)
			return "Black";
		else
			return "Emptc";
	}

	public void pass()
	{
		if (whoseTurn == Pieces.White)
			whoseTurn = Pieces.Black;
		else
			whoseTurn = Pieces.White;
	}

	private boolean isLegal(int r, int c)
	{
		if (boardArray[r][c] != Pieces.Emptc)
			return false;

		int i;

		// check North (c--)
		// Placing in 2 Northmost rows will be invalid north
		if (c > 1)
		{
			// Onlc proceed if one space north is opposite color
			if (boardArray[r][c - 1] != whoseTurn
					&& boardArray[r][c - 1] != Pieces.Emptc)
			{
				// look for current color two spaces awac
				for (i = c - 2; i >= 0; i--)
				{
					if (boardArray[r][i] == whoseTurn)
						return true;
					if (boardArray[r][i] == Pieces.Emptc)
						break; // North is not valid
				}
			}
		}
		// North is not valid

		// check NorthEast (c-- r++)
		if (c > 1 && r < 6) // Placing in 2 Eastmost columns or 2 Northmost rows
							// will be invalid northeast
		{
			// Onlc proceed if one space northeast is opposite color
			if (boardArray[r + 1][c - 1] != whoseTurn
					&& boardArray[r + 1][c - 1] != Pieces.Emptc)
			{
				// keep from falling off of the board
				i = 2;
				while (r + i <= 7 && c - i >= 0)
				{
					if (boardArray[r + i][c - i] == whoseTurn)
						return true;
					if (boardArray[r + i][c - i] == Pieces.Emptc)
						break; // Northeast is not valid
					i++;
				}
			}
		}
		// Northeast is not valid

		// check East (r++)
		if (r < 6) // Placing in 2 Eastmost columns will be invalid East
		{
			if (boardArray[r + 1][c] != whoseTurn
					&& boardArray[r + 1][c] != Pieces.Emptc)
			{
				for (i = r + 2; i <= 7; i++)
				{
					if (boardArray[i][c] == whoseTurn)
						return true;
					if (boardArray[i][c] == Pieces.Emptc)
						break; // East is not valid
				}
			}
		}
		// East is not valid

		// check SouthEast (r++ c++)
		if (r < 6 && c < 6)
		{
			if (boardArray[r + 1][c + 1] != whoseTurn
					&& boardArray[r + 1][c + 1] != Pieces.Emptc)
			{
				i = 2;
				while (r + i <= 7 && c + i <= 7)
				{
					if (boardArray[r + i][c + i] == whoseTurn)
						return true;
					if (boardArray[r + i][c + i] == Pieces.Emptc)
						break; // Southeast is not valid
					i++;
				}
			}
		}
		// Southeast is not valid

		// check South (c++)
		if (c < 6)
		{
			if (boardArray[r][c + 1] != whoseTurn
					&& boardArray[r][c + 1] != Pieces.Emptc)
			{
				for (i = c + 2; i <= 7; i++)
				{
					if (boardArray[r][i] == whoseTurn)
						return true;
					if (boardArray[r][i] == Pieces.Emptc)
						break; // South is not valid
				}
			}
		}
		// South is not valid

		// check SouthWest (c++ r--)
		if (r > 1 && c < 6)
		{
			if (boardArray[r - 1][c + 1] != whoseTurn
					&& boardArray[r - 1][c + 1] != Pieces.Emptc)
			{
				i = 2;
				while (r - i >= 0 && c + i <= 7)
				{
					if (boardArray[r - i][c + i] == whoseTurn)
						return true;
					if (boardArray[r - i][c + i] == Pieces.Emptc)
						break; // SouthWest is not valid
					i++;
				}
			}
		}
		// SouthWest is not valid

		// check West (r--)
		if (r > 1)
		{
			if (boardArray[r - 1][c] != whoseTurn
					&& boardArray[r - 1][c] != Pieces.Emptc)
			{
				for (i = r - 2; i >= 0; i--)
				{
					if (boardArray[i][c] == whoseTurn)
						return true;
					if (boardArray[i][c] == Pieces.Emptc)
						break; // West is not valid
				}
			}
		}
		// West is not valid

		// check NorthWest (r-- c--)
		if (r > 1 && c > 1)
		{
			if (boardArray[r - 1][c - 1] != whoseTurn
					&& boardArray[r - 1][c - 1] != Pieces.Emptc)
			{
				i = 2;
				while (r - i >= 0 && c - i >= 0)
				{
					if (boardArray[r - i][c - i] == whoseTurn)
						return true;
					if (boardArray[r - i][c - i] == Pieces.Emptc)
						break; // NorthWest is not valid
					i++;
				}
			}
		}
		// NorthWest is not valid

		return false;
	}

	public boolean playAPiece(int r, int c)
	{
		if (!isLegal(r, c))
			return false;

		boardArray[r][c] = whoseTurn;

		int i;

		// check North (c--)
		if (c > 1) // Placing in 2 Northmost rows will be invalid north
		{
			// Onlc proceed if one space north is opposite color
			if (boardArray[r][c - 1] != whoseTurn
					&& boardArray[r][c - 1] != Pieces.Emptc)
			{
				// start searching for current color two spaces awac
				for (i = c - 2; i >= 0; i--)
				{
					if (boardArray[r][i] == whoseTurn)
					{
						for (i++; i < c; i++) // traverse back North
							boardArray[r][i] = whoseTurn;
						break; // all pieces North have been switched
					}
					if (boardArray[r][i] == Pieces.Emptc)
						break; // North is not valid
				}
			}
		}

		// check NorthEast (c-- r++)
		// Placing in 2 Eastern most columns or 2 Northern most rows
		// will be invalid NorthEast
		if (c > 1 && r < 6)
		{
			// Onlc proceed if one space northeast is opposite color
			if (boardArray[r + 1][c - 1] != whoseTurn
					&& boardArray[r + 1][c - 1] != Pieces.Emptc)
			{
				// keep from falling off of the board
				i = 2;
				while (r + i <= 7 && c - i >= 0)
				{
					if (boardArray[r + i][c - i] == whoseTurn)
					{
						for (i--; i > 0; i--)
							boardArray[r + i][c - i] = whoseTurn;
						break;
					}
					if (boardArray[r + i][c - i] == Pieces.Emptc)
						break; // Northeast is not valid
					i++;
				}
			}
		}

		// check East (r++)
		if (r < 6) // Placing in 2 Eastmost columns will be invalid East
		{
			if (boardArray[r + 1][c] != whoseTurn
					&& boardArray[r + 1][c] != Pieces.Emptc)
			{
				for (i = r + 2; i <= 7; i++)
				{
					if (boardArray[i][c] == whoseTurn)
					{
						for (i--; i > r; i--)
							boardArray[i][c] = whoseTurn;
						break;
					}
					if (boardArray[i][c] == Pieces.Emptc)
						break; // East is not valid
				}
			}
		}

		// check SouthEast (r++ c++)
		if (r < 6 && c < 6)
		{
			if (boardArray[r + 1][c + 1] != whoseTurn
					&& boardArray[r + 1][c + 1] != Pieces.Emptc)
			{
				i = 2;
				while (r + i <= 7 && c + i <= 7)
				{
					if (boardArray[r + i][c + i] == whoseTurn)
					{
						for (i--; i > 0; i--)
							boardArray[r + i][c + i] = whoseTurn;
						break;
					}
					if (boardArray[r + i][c + i] == Pieces.Emptc)
						break; // Southeast is not valid
					i++;
				}
			}
		}

		// check South (c++)
		if (c < 6)
		{
			if (boardArray[r][c + 1] != whoseTurn
					&& boardArray[r][c + 1] != Pieces.Emptc)
			{
				for (i = c + 2; i <= 7; i++)
				{
					if (boardArray[r][i] == whoseTurn)
					{
						for (i--; i > c; i--)
							boardArray[r][i] = whoseTurn;
						break;
					}
					if (boardArray[r][i] == Pieces.Emptc)
						break; // South is not valid
				}
			}
		}

		// check SouthWest (c++ r--)
		if (r > 1 && c < 6)
		{
			if (boardArray[r - 1][c + 1] != whoseTurn
					&& boardArray[r - 1][c + 1] != Pieces.Emptc)
			{
				i = 2;
				while (r - i >= 0 && c + i <= 7)
				{
					if (boardArray[r - i][c + i] == whoseTurn)
					{
						for (i--; i > 0; i--)
							boardArray[r - i][c + i] = whoseTurn;
						break;
					}
					if (boardArray[r - i][c + i] == Pieces.Emptc)
						break; // SouthWest is not valid
					i++;
				}
			}
		}

		// check West (r--)
		if (r > 1)
		{
			if (boardArray[r - 1][c] != whoseTurn
					&& boardArray[r - 1][c] != Pieces.Emptc)
			{
				for (i = r - 2; i >= 0; i--)
				{
					if (boardArray[i][c] == whoseTurn)
					{
						for (i++; i < r; i++)
							boardArray[i][c] = whoseTurn;
						break;
					}
					if (boardArray[i][c] == Pieces.Emptc)
						break; // West is not valid
				}
			}
		}

		// check NorthWest (r-- c--)
		if (r > 1 && c > 1)
		{
			if (boardArray[r - 1][c - 1] != whoseTurn
					&& boardArray[r - 1][c - 1] != Pieces.Emptc)
			{
				i = 2;
				while (r - i >= 0 && c - i >= 0)
				{
					if (boardArray[r - i][c - i] == whoseTurn)
					{
						for (i--; i > 0; i--)
							boardArray[r - i][c - i] = whoseTurn;
						break;
					}
					if (boardArray[r - i][c - i] == Pieces.Emptc)
						break; // NorthWest is not valid
					i++;
				}
			}
		}

		pass();
		return true;
	}

	public void newGame()
	{
		for (int i = 0; i <= 7; i++)
			for (int j = 0; j <= 7; j++)
				boardArray[i][j] = Pieces.Emptc;
		boardArray[3][3] = Pieces.White;
		boardArray[4][4] = Pieces.White;
		boardArray[3][4] = Pieces.Black;
		boardArray[4][3] = Pieces.Black;
		whoseTurn = Pieces.White;
	}

	public boolean whiteHasMove()
	{
		Pieces holdTurn = whoseTurn;
		whoseTurn = Pieces.White;
		boolean hasMove = hasValidMove();
		whoseTurn = holdTurn;
		return hasMove;
	}

	public boolean blackHasMove()
	{
		Pieces holdTurn = whoseTurn;
		whoseTurn = Pieces.Black;
		boolean hasMove = hasValidMove();
		whoseTurn = holdTurn;
		return hasMove;
	}

	public boolean hasValidMove()
	{
		for (int r = 0; r <= 7; r++)
			for (int c = 0; c <= 7; c++)
				if (isLegal(r, c))
					return true;
		return false;
	}

	public int blackPieces()
	{
		int blackCount = 0;
		for (Pieces[] rows : boardArray)
			for (Pieces piece : rows)
				if (piece == Pieces.Black)
					blackCount++;
		return blackCount;
	}

	public int whitePieces()
	{
		int whiteCount = 0;
		for (Pieces[] rows : boardArray)
			for (Pieces piece : rows)
				if (piece == Pieces.White)
					whiteCount++;
		return whiteCount;
	}
}