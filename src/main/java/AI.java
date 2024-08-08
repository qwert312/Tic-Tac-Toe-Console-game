import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class AI {
    private String symbol;

    public AI(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public List<Integer> makeMove(Board3x3 board) {
        List<Integer> randomCoordinates = getRandomCoordinates(board);
        board.setSymbol(symbol, randomCoordinates.get(0), randomCoordinates.get(1));
        return randomCoordinates;
    }

    private List<Integer> getRandomCoordinates(Board3x3 board) {
        Random rand = new Random();
        List<List<Integer>> possibleCoordinates = new ArrayList<>();
        String[][] boardCopy = board.copyOfBoard();

        IntStream.range(0, boardCopy.length)
                .boxed()
                .flatMap(rowInd ->
                        IntStream
                                .range(0, boardCopy[rowInd].length)
                                .filter(colInd -> boardCopy[rowInd][colInd].equals(" "))
                                .mapToObj(colInd -> List.of(rowInd, colInd)))
                .forEach(possibleCoordinates::add);

        return possibleCoordinates.get(rand.nextInt(0, possibleCoordinates.size()));
    }

    public String toString() {
        return "AI (" + symbol + ")";
    }


}
