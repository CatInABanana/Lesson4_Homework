import java.util.Random;
import java.util.Scanner;

public class Tic_tac_toe {

    public static final int size = 5;
    public static final int target = 3;
    public static final char dot_empty = '•';
    public static final char dot_human = 'X';
    public static final char dot_ai = 'O';
    public static final String empty = " ";
    public static char symbol;
    public static char[][] map = new char[size][size];
    public static int last_row, last_column;

    public static void main(String[] args) {
        initMap();
        printMap();
        game();
    }

    public static void initMap() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map[i][j] = dot_empty;
            }
        }
    }

    private static void printMap() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(map[i][j] + empty);
            }
            System.out.print("\n");
        }
    }

    private static void game() {
        while (true) {
            playerMove();
            printMap();
            if (checkEnd(dot_human, "Вы выиграли!")) {
                System.exit(0);
            }

            aiMove();
            printMap();
            if (checkEnd(dot_ai, "К сожалению, Вы проиграли...")) {
                System.exit(0);
            }

        }
    }

    private static void playerMove() {
        int row, column;
        symbol = dot_human;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Строка: ");
        row = scanner.nextInt() - 1;
        System.out.println("Столбец: ");
        column = scanner.nextInt() - 1;
        if (cellValidation(row, column, symbol)) {
            map[row][column] = dot_human;
            last_row = row;
            last_column = column;
        } else playerMove();
    }

    private static boolean cellValidation(int row, int column, char symbol) {
        if (map[row][column] == dot_empty) {
            return true;
        } else if (symbol == dot_human) {
            System.out.println("Клетка занята. Попробуйте ещё раз.");
            return false;
        } else return false;
    }

    private static void aiMove() {
        Random random = new Random();
        symbol = dot_ai;
        last_row = random.nextInt(size);
        last_column = random.nextInt(size);
        if (cellValidation(last_row, last_column, symbol)) {
            map[last_row][last_column] = dot_ai;
            System.out.println("Ходит бот");
        } else aiMove();
    }

    private static boolean checkEnd(char dot_human, String s) {
        if (checkWin(dot_human)) {
            System.out.println(s);
            return true;
        } else if (MapFull()) {
            System.out.println("Ничья!");
            return true;
        } else return false;
    }

    private static boolean checkWin(char playerSymbol) {
        for (int i = 0; i < size; i++) {
            if (checkLine(1, 0, 0, i)) return true;
        }
        for (int i = 0; i < size; i++) {
            if (checkLine(0, i, 1, 0)) return true;
        }
        return checkLine(1, 0, 1, 0) && checkLine(-1, size - 1, 1, 0);
    }
    private static boolean checkLine(int xCoef, int xShift, int yCoef, int yShift) {
        char savedSymbol = dot_empty;
        int savedSymbolCount = 0;
        for (int i = 0; i < size; i++)
        {
            int x = i * xCoef + xShift;
            int y = i * yCoef + yShift;
            char symbol = map[x][y];
            if (symbol == savedSymbol)
            {
                savedSymbolCount++;
            }
            else
            {
                savedSymbol = symbol;
                savedSymbolCount = 1;
            }
            if (symbol != dot_empty && savedSymbolCount == target)
            {
                return true;
            }
        }
        return false;
    }

    private static boolean MapFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (map[i][j] == dot_empty)
                    return false;
            }
        }
        return true;
    }
}