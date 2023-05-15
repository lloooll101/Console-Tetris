import java.util.*;

public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        Tetris tetris = new Tetris(10, 20);

        for(int i = -100000; i < 100000; i++){
            System.out.print(tetris.display());
            tetris.tick(scanner.nextLine());
        }
        scanner.close();
    }
}
