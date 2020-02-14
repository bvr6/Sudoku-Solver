// Brendan Russ (bvr6)
import java.util.List;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Sudoku {
	static boolean isFullSolution(int[][] board) {
		if (reject(board)){
			return false;
		}
		for (int i = 0; i < 9; i++){
			for (int j = 0; j < 9; j++){
				if (board[i][j] == 0){
					return false;
				}
			}
		}
		return true;
	}

	static boolean reject(int[][] board) {
		// This takes a partial solution, and returns true if it is impossible to continue with this board.
		// For example, if there are two 3’s on one row, there is no reason to keep solving this board.
		// CHEAT SHEET: board[columns][rows]
		int[] testValues = {0, 0, 0, 0, 0, 0, 0, 0, 0};

		// Test all horizontal lines
		for (int i = 0; i < 9; i++){
			for (int j = 0; j < 9; j++){
				if (board[i][j] != 0){
					if (testValues[board[i][j] - 1] == 1){
					//	System.out.println("Horizontal line test failed. Conflict in cell: " + (i + 1) + ", " + (j + 1));
						return true;
					}
					testValues[board[i][j] - 1]++; // increment the index of this line by 1
				}
			}
			Arrays.fill(testValues, 0); // reset contents of array
		}
		// Test all vertical lines
		for (int i = 0; i < 9; i++){
			for (int j = 0; j < 9; j++){
				if (board[j][i] != 0){
					if (testValues[board[j][i] - 1] == 1){
					//	System.out.println("Vertical line test failed. Conflict in cell: " + (i + 1) + ", " + (j + 1));
						return true;
					}
					testValues[board[j][i] - 1]++; // increment the index of this line by 1
				}
			}
			Arrays.fill(testValues, 0); // reset contents of array
		}
		// Test all boxes
		for (int i = 0; i < 9; i = i + 3){
			int constant = i;
			while (i <= constant + 2){
				for (int j = 0; j <= 2; j++){
					if (board[i][j] != 0){
						if (testValues[board[i][j] - 1] == 1){
						//	System.out.println("Box test (1) failed. Conflict in cell: " + (i + 1) + ", " + (j + 1));
							return true;
						}
						testValues[board[i][j] - 1]++; // increment the index of this line by 1
					}
				}
				i++;	// increment 1 when horizontal line of box is complete
			}
			i = constant; // resets back to the constant
			Arrays.fill(testValues, 0); // reset contents of array
			while (i <= constant + 2){
				for (int j = 3; j <= 5; j++){
					if (board[i][j] != 0){
						if (testValues[board[i][j] - 1] == 1){
						//	System.out.println("Box test (2) failed. Conflict in cell: " + (i + 1) + ", " + (j + 1));
							return true;
						}
						testValues[board[i][j] - 1]++; // increment the index of this line by 1
					}
				}
				i++;	// increment 1 when horizontal line of box is complete
			}
			i = constant; // resets back to the constant
			Arrays.fill(testValues, 0); // reset contents of array
			while (i <= constant + 2){
				for (int j = 6; j <= 8; j++){
					if (board[i][j] != 0){
						if (testValues[board[i][j] - 1] == 1){
						//	System.out.println("Box test (3) failed. Conflict in cell: " + (i + 1) + ", " + (j + 1));
							return true;
						}
						testValues[board[i][j] - 1]++; // increment the index of this line by 1
					}
				}
				i++;	// increment 1 when horizontal line of box is complete
			}
			i = constant;
			Arrays.fill(testValues, 0);
		}
		return false;
	}
	
	/**
	 * This takes a partial solution, and constructs a new partial solution.
	 * @param board, booleanBoard
	 * @return null if unable to extend anymore, return 
	 */

	// for booleanBoard, true means that the cell can be edited
	static int[][] extend(int[][] board, boolean[][] booleanBoard) {
		// TODO: Complete this method
		int[][] newBoard = new int[9][9];
		for (int i = 0; i < 9; i++){
			for (int j = 0; j < 9; j++){
				newBoard[i][j] = board[i][j];
			}
		}
		for (int i = 0; i < 9; i++){
			for (int j = 0; j < 9; j++){
				if (newBoard[i][j] == 0 && booleanBoard[i][j]){
					newBoard[i][j]++;
					return newBoard;
				}
			}
		}
		return null;		// if no more can be extended, return null
	}

	/**
	 * The partner to extend. This takes a partial solution, and constructs another new partial solution.
	 * It will change the most-recently-placed number to the next possible option.
	 * @param board, booleanBoard
	 * @return board if partial solution is correct, null if there are no more possible options
	 */
	static int[][] next(int[][] board, boolean[][] booleanBoard) {
		// TODO: Complete this method
		int[][] newBoard = new int[9][9];
		for (int i = 0; i < 9; i++){
			for (int j = 0; j < 9; j++){
				newBoard[i][j] = board[i][j];
			}
		}
		for (int i = 0; i < 9; i++){
			for (int j = 0; j < 9; j++){
					if (booleanBoard[i][j] && newBoard[i][j] < 9 && newBoard[i][j] != 0){
						newBoard[i][j]++;
						return newBoard;
					}
					if (booleanBoard[i][j] && newBoard[i][j] == 9){
						return null;
					}
				}
			}
		return null;
	}

	static void testIsFullSolution() {
		// TODO: Complete this method
		// Test false solutions
		int[][] falseBoard1 =
		{{0, 0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0, 0}};
		// Rejected by the reject method
		int[][] falseBoard2 =
		{{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};
		// Rejected because of reject method (box contains two of same value)
		int[][] falseBoard3 =
		{{1, 2, 3, 4, 5, 6, 7, 8, 9},
		{4, 5, 6, 7, 8, 9, 1, 2, 3},
		{7, 8, 9, 1, 2, 3, 4, 5, 6},
		{2, 3, 4, 5, 6, 7, 8, 9, 1},
		{9, 1, 2, 3, 4, 5, 6, 7, 8},
		{5, 6, 7, 8, 9, 1, 2, 3, 4},
		{8, 9, 1, 2, 3, 4, 5, 6, 7},
		{3, 4, 5, 6, 7, 8, 9, 1, 2},
		{6, 7, 8, 9, 1, 2, 3, 4, 5}};
		// Rejected because of reject method (line contains two of same value)
		int[][] falseBoard4 =
		{{1, 2, 3, 4, 5, 6, 7, 8, 8},
		{4, 5, 6, 7, 8, 9, 1, 2, 3},
		{7, 8, 9, 1, 2, 3, 4, 5, 6},
		{2, 3, 4, 5, 6, 7, 8, 9, 1},
		{9, 1, 2, 3, 4, 5, 6, 7, 8},
		{5, 6, 7, 8, 9, 1, 2, 3, 4},
		{8, 9, 1, 2, 3, 4, 5, 6, 7},
		{3, 4, 5, 6, 7, 8, 9, 1, 2},
		{6, 7, 8, 9, 1, 2, 3, 4, 5}};
		// Rejected because not full solution
		int[][] falseBoard5 =
		{{1, 2, 3, 4, 5, 6, 7, 8, 9},
		{4, 5, 6, 7, 8, 9, 1, 2, 0},
		{7, 8, 9, 1, 2, 3, 4, 5, 6},
		{2, 3, 4, 5, 6, 7, 8, 9, 1},
		{5, 6, 7, 8, 9, 1, 2, 3, 4},
		{8, 9, 1, 2, 3, 4, 5, 6, 7},
		{3, 4, 5, 6, 7, 8, 9, 1, 2},
		{6, 7, 8, 9, 1, 2, 3, 4, 5},
		{9, 1, 2, 3, 4, 5, 6, 7, 8}};
		int[][][] allFalseBoards = {falseBoard1, falseBoard2, falseBoard3, falseBoard4, falseBoard5,
			readBoard("1-trivial.su"), readBoard("2-easy.su"), readBoard("3-medium.su"), readBoard("4-hard.su"), readBoard("5-evil.su"), readBoard("emptyboard.su")};
		System.out.println("Test for isFullSolution()...output should be false: ");
		for (int[][] test : allFalseBoards){
			System.out.println(isFullSolution(test));
		}
		System.out.println();
		// Test true solutions
		int[][] trueBoard1 =
		{{1, 2, 3, 4, 5, 6, 7, 8, 9},
		{4, 5, 6, 7, 8, 9, 1, 2, 3},
		{7, 8, 9, 1, 2, 3, 4, 5, 6},
		{2, 3, 4, 5, 6, 7, 8, 9, 1},
		{5, 6, 7, 8, 9, 1, 2, 3, 4},
		{8, 9, 1, 2, 3, 4, 5, 6, 7},
		{3, 4, 5, 6, 7, 8, 9, 1, 2},
		{6, 7, 8, 9, 1, 2, 3, 4, 5},
		{9, 1, 2, 3, 4, 5, 6, 7, 8}};
		int[][] trueBoard2 =
		{{2, 9, 6, 3, 1, 8, 5, 7, 4},
		{5, 8, 4, 9, 7, 2, 6, 1, 3},
		{7, 1, 3, 6, 4, 5, 2, 8, 9},
		{6, 2, 5, 8, 9, 7, 3, 4, 1},
		{9, 3, 1, 4, 2, 6, 8, 5, 7},
		{4, 7, 8, 5, 3, 1, 9, 2, 6},
		{1, 6, 7, 2, 5, 3, 4, 9, 8},
		{8, 5, 9, 7, 6, 4, 1, 3, 2},
		{3, 4, 2, 1, 8, 9, 7, 6, 5}};
		int[][] trueBoard3 =
		{{4, 5, 2, 3, 9, 1, 8, 7, 6},
		{3, 1, 8, 6, 7, 5, 2, 9, 4},
		{6, 7, 9, 4, 2, 8, 3, 1, 5},
		{8, 3, 1, 5, 6, 4, 7, 2, 9},
		{2, 4, 5, 9, 8, 7, 1, 6, 3},
		{9, 6, 7, 2, 1, 3, 5, 4, 8},
		{7, 9, 6, 8, 5, 2, 4, 3, 1},
		{1, 8, 3, 7, 4, 9, 6, 5, 2},
		{5, 2, 4, 1, 3, 6, 9, 8, 7}};
		int[][][] allTrueBoards = {trueBoard1, trueBoard2, trueBoard3};
		System.out.println("Test for isFullSolution()...output should be true: ");
		for (int[][] test : allTrueBoards){
			System.out.println(isFullSolution(test));
		}
		System.out.println();
	}

	static void testReject() {
		// TODO: Complete this method
		// Test rejected solutions
		// Rejected by the reject method
		int[][] rejectBoard1 =
		{{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};
		// Rejected because of reject method (box contains two of same value)
		int[][] rejectBoard2 =
		{{1, 2, 3, 4, 5, 6, 7, 8, 9},
		{4, 5, 6, 7, 8, 9, 1, 2, 3},
		{7, 8, 9, 1, 2, 3, 4, 5, 6},
		{2, 3, 4, 5, 6, 7, 8, 9, 1},
		{9, 1, 2, 3, 4, 5, 6, 7, 8},
		{5, 6, 7, 8, 9, 1, 2, 3, 4},
		{8, 9, 1, 2, 3, 4, 5, 6, 7},
		{3, 4, 5, 6, 7, 8, 9, 1, 2},
		{6, 7, 8, 9, 1, 2, 3, 4, 5}};
		// Rejected because of reject method (line contains two of same value)
		int[][] rejectBoard3 =
		{{1, 2, 3, 4, 5, 6, 7, 8, 8},
		{4, 5, 6, 7, 8, 9, 1, 2, 3},
		{7, 8, 9, 1, 2, 3, 4, 5, 6},
		{2, 3, 4, 5, 6, 7, 8, 9, 1},
		{9, 1, 2, 3, 4, 5, 6, 7, 8},
		{5, 6, 7, 8, 9, 1, 2, 3, 4},
		{8, 9, 1, 2, 3, 4, 5, 6, 7},
		{3, 4, 5, 6, 7, 8, 9, 1, 2},
		{6, 7, 8, 9, 1, 2, 3, 4, 5}};
		// Rejected because not full solution
		int[][][] allRejectedBoards = {rejectBoard1, rejectBoard2, rejectBoard3};
		System.out.println("Test for reject()...output should be true: ");
		for (int[][] test : allRejectedBoards){
			System.out.println(reject(test));
		}
		System.out.println();
		// Test accepted solutions
		int[][] acceptBoard1 =
		{{1, 2, 3, 4, 5, 6, 7, 8, 9},
		{4, 5, 6, 7, 8, 9, 1, 2, 3},
		{7, 8, 9, 1, 2, 3, 4, 5, 6},
		{2, 3, 4, 5, 6, 7, 8, 9, 1},
		{5, 6, 7, 8, 9, 1, 2, 3, 4},
		{8, 9, 1, 2, 3, 4, 5, 6, 7},
		{3, 4, 5, 6, 7, 8, 9, 1, 2},
		{6, 7, 8, 9, 1, 2, 3, 4, 5},
		{9, 1, 2, 3, 4, 5, 6, 7, 8}};
		int[][] acceptBoard2 =
		{{2, 9, 6, 3, 1, 8, 5, 7, 4},
		{5, 8, 4, 9, 7, 2, 6, 1, 3},
		{7, 1, 3, 6, 4, 5, 0, 8, 9},
		{6, 2, 5, 8, 9, 7, 3, 4, 1},
		{9, 3, 1, 4, 2, 6, 8, 5, 7},
		{4, 7, 8, 5, 3, 1, 0, 2, 6},
		{1, 6, 7, 2, 5, 3, 4, 9, 8},
		{8, 5, 9, 7, 6, 4, 1, 3, 2},
		{3, 4, 2, 1, 8, 9, 7, 6, 5}};
		int[][] acceptBoard3 =
		{{4, 5, 2, 3, 9, 1, 8, 7, 6},
		{3, 1, 8, 6, 7, 5, 2, 9, 4},
		{6, 7, 9, 4, 2, 8, 3, 1, 5},
		{8, 3, 1, 0, 0, 4, 7, 2, 9},
		{2, 4, 5, 9, 0, 7, 1, 6, 3},
		{9, 6, 7, 2, 1, 3, 5, 4, 8},
		{7, 9, 6, 8, 5, 2, 4, 3, 1},
		{1, 8, 3, 7, 4, 9, 6, 5, 2},
		{5, 2, 4, 1, 3, 6, 9, 8, 7}};
		int[][] acceptBoard4 =
		{{0, 0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0, 0}};

		int[][][] allAcceptedBoards = {acceptBoard1, acceptBoard2, acceptBoard3, acceptBoard4,
			readBoard("1-trivial.su"), readBoard("2-easy.su"), readBoard("3-medium.su"), readBoard("4-hard.su"), readBoard("5-evil.su"), readBoard("emptyboard.su")};
		System.out.println("Test for reject()...output should be false: ");
		for (int[][] test : allAcceptedBoards){
			System.out.println(reject(test));
		}
		System.out.println();
	}

	static void testExtend() {
		// TODO: Complete this method
		int[][] board1 = {
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0}
		};
		int[][] board2 = {
			{1, 2, 3, 0, 4, 0, 5, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0}
		};
		int[][] board3 = {
			{1, 2, 3, 4, 5, 6, 7, 8, 9},
			{4, 5, 6, 7, 8, 9, 1, 2, 3},
			{7, 8, 9, 1, 2, 3, 4, 5, 6},
			{2, 3, 4, 5, 6, 7, 8, 9, 1},
			{5, 6, 7, 8, 9, 1, 2, 3, 4},
			{8, 9, 1, 2, 3, 4, 5, 6, 7},
			{3, 4, 5, 6, 7, 8, 9, 1, 2},
			{6, 7, 8, 9, 1, 2, 3, 4, 5},
			{9, 1, 2, 3, 4, 5, 6, 7, 8}
		};
		boolean[][] booleanBoard1 = getBooleanBoard(board1);
		boolean[][] booleanBoard2 = getBooleanBoard(board2);
		boolean[][] booleanBoard3 = getBooleanBoard(board3);
		System.out.println("Testing testExtend()...");
		printBoard(extend(board1, booleanBoard1));
		System.out.println();
		printBoard(extend(board2, booleanBoard2));
		System.out.println();
		System.out.println("Board should be null:");
		printBoard(extend(board3, booleanBoard3));
		System.out.println();
	}

	static void testNext() {
		// TODO: Complete this method
		int[][] board1 = {
			{1, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0}
		};
		int[][] board2 = {
			{1, 2, 3, 4, 5, 6, 7, 8, 8},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0}
		};
		int[][] board3 = {
			{1, 2, 3, 4, 5, 6, 7, 8, 9},
			{4, 5, 6, 7, 8, 9, 1, 2, 3},
			{7, 8, 9, 1, 2, 3, 4, 5, 6},
			{2, 3, 4, 5, 6, 7, 8, 9, 1},
			{5, 6, 7, 8, 9, 1, 2, 3, 4},
			{8, 9, 1, 2, 3, 4, 5, 6, 7},
			{3, 4, 5, 6, 7, 8, 9, 1, 2},
			{6, 7, 8, 9, 1, 2, 3, 4, 5},
			{9, 1, 2, 3, 4, 5, 6, 7, 8}
		};
		boolean[][] booleanBoard1 = getBooleanBoard(board1);
		booleanBoard1[0][0] = true;
		boolean[][] booleanBoard2 = getBooleanBoard(board2);
		booleanBoard2[0][8] = true;
		boolean[][] booleanBoard3 = getBooleanBoard(board3);
		System.out.println("Testing testNext()...");
		printBoard(next(board1, booleanBoard1));
		System.out.println();
		printBoard(next(board2, booleanBoard2));
		System.out.println();
		System.out.println("Should return null.");
		printBoard(next(board3, booleanBoard3));
		System.out.println();
	}

	static void printBoard(int[][] board) {
		if(board == null) {
			System.out.println("This board is returned null");
			return;
		}
		for(int i = 0; i < 9; i++) {
			if(i == 3 || i == 6) {
				System.out.println("----+-----+----");
			}
			for(int j = 0; j < 9; j++) {
				if(j == 2 || j == 5) {
					if (board[i][j] == 0)
					System.out.print("_ | ");
					else
					System.out.print(board[i][j] + " | ");
				} else {
					if (board[i][j] == 0)
					System.out.print("_");
					else
					System.out.print(board[i][j]);
				}
			}
			System.out.print("\n");
		}
	}

	static int[][] readBoard(String filename) {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get(filename), Charset.defaultCharset());
		} catch (IOException e) {
			return null;
		}
		int[][] board = new int[9][9];
		int val = 0;
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				try {
					val = Integer.parseInt(Character.toString(lines.get(i).charAt(j)));
				} catch (Exception e) {
					val = 0;
				}
				board[i][j] = val;
			}
		}
		return board;
	}

	static int[][] solve(int[][] board) {
		boolean[][] booleanBoard = getBooleanBoard(board);
		if(reject(board)) return null;		// called via recursion (reject)
		if(isFullSolution(board)) return board;		// called via recursion (accept!)
		int[][] attempt = extend(board, booleanBoard);		
		while (attempt != null) {
			int[][] solution = solve(attempt);
			if(solution != null) return solution;
			attempt = next(attempt, getBooleanBoard(board));
		}
		return null;
	}
	// for booleanBoard, true means that the cell can be edited
	static boolean[][] getBooleanBoard(int[][] board){
		boolean[][] dummyBoard = new boolean[9][9];
		for (int i = 0; i < 9; i++){
			for (int j = 0; j < 9; j++){
				if (board[i][j] != 0){
					dummyBoard[i][j] = false;
				}
				else {
					dummyBoard[i][j] = true;
				}
			}
		}
		return dummyBoard;
	}

	public static void main(String[] args) {
		if(args[0].equals("-t")) {
			testIsFullSolution();
			testReject();
			testExtend();
			testNext();
		
		} else {
			int[][] board = readBoard(args[0]);
			System.out.println("Here is your given board:");
			printBoard(board);
			if (reject(board)){
				System.out.println("This board is unsolvable!");
			}
			else if (isFullSolution(board)){
				System.out.println("This board is already solved!");
			}
			else{
				int[][] solution = solve(board);
				if (solution == null){
					System.out.println("This board is unsolvable!");
				}
				else{
					System.out.println("\nSolution:");
					printBoard(solve(board));
				}
			}
		}
	}
}

