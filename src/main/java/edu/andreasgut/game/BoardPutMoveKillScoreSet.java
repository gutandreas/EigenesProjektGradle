package edu.andreasgut.game;


import java.util.LinkedList;

public class BoardPutMoveKillScoreSet {

    private int level;
    private Board board;
    private Position put;
    private Move move;
    private Position kill;
    private int score;
    private BoardPutMoveKillScoreSet parent;
    private LinkedList<BoardPutMoveKillScoreSet> children = new LinkedList<>();
    private String scoreDetails;

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Position getPut() {
        return put;
    }

    public void setPut(Position put) {
        this.put = put;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public Position getKill() {
        return kill;
    }

    public void setKill(Position kill) {
        this.kill = kill;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public BoardPutMoveKillScoreSet getParent() {
        return parent;
    }

    public void setParent(BoardPutMoveKillScoreSet parent) {
        this.parent = parent;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public LinkedList<BoardPutMoveKillScoreSet> getChildren() {
        return children;
    }

    public void setChildren(LinkedList<BoardPutMoveKillScoreSet> children) {
        this.children = children;
    }

    public String getScoreDetails() {
        return scoreDetails;
    }

    public void setScoreDetails(String scoreDetails) {
        this.scoreDetails = scoreDetails;
    }

    @Override
    public String toString() {


        if (put != null){
            if (kill == null){
                return  "Level " + level + ", Put an " + put + ", Score: " + score + "\n" + board;
                }
            else {
                return  "Level " + level + ", Put an " + put + " mit Kill an " + kill + ", Score: " + score + "\n" + board;
            }
        }

        if (move != null){
            if (kill == null) {
                return "Level " + level + ", " + move + ", Score: " + score + "\n" + board;
            }
            else {
                return  "Level " + level + ", Move an " + move + " mit Kill an " + kill + ", Score: " + score + "\n" + board;
            }
        }


        return null;}
}
