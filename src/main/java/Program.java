import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        GameManager gameManager = new GameManager(scanner);
        gameManager.run();
    }
}
