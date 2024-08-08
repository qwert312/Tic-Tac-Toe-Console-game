import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        GameManager gameManager = new GameManager(scan);
        gameManager.start();
    }
}
