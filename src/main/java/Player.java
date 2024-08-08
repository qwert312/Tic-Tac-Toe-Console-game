public class Player {
    private String symbol;

    public Player(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public void makeMove(Board3x3 board, int row, int col) throws IllegalArgumentException {
        board.setSymbol(symbol, row, col);
    }

    public String toString() {
        return "Player (" + symbol + ")";
    }
}
