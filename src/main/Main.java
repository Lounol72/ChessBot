package main;

import game.Game;
import board.Board;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        
        Board board = new Board();
        System.out.println(board.toString());
    }
}
