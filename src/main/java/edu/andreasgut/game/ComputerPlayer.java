package edu.andreasgut.game;

import edu.andreasgut.view.ViewManager;

import java.util.*;

public class ComputerPlayer extends Player {

    GameTree gameTree = new GameTree();


    public ComputerPlayer(ViewManager viewManager, String name) {
        super(viewManager, name);
    }



    @Override
    Position put(Board board, int playerIndex) {

        gameTree.initializeRoot(board);

        ScorePoints putScorePoints = new ScorePoints(100, 5000,20, 20, 30,3, 2, -5000, -30, -200, -100, -2);

        recursivePutBfs(gameTree.getRoot(), putScorePoints, playerIndex, playerIndex, 5);

        System.out.println(gameTree);

        Stack<BoardPutMoveKillScoreSet> winningPath = gameTree.getPath(gameTree.getLeafWithBestScore());
        System.out.println("Gewinnerpfad:");
        while (!winningPath.isEmpty()){
            System.out.println(winningPath.pop());
        }

        System.out.println("Gesetzter Stein: " + gameTree.getBestPut());
        return gameTree.getBestPut();
    }

    private void recursivePutBfs(BoardPutMoveKillScoreSet set, ScorePoints scorePoints, int scorePlayerIndex, int currentPlayerIndex, int levelLimit){

        if (set.getLevel()==levelLimit){
            return;
        }

        int tempCurrentPlayerIndex;

        if (set.getLevel()%2 == 0){
            tempCurrentPlayerIndex = scorePlayerIndex;
        }
        else {
            tempCurrentPlayerIndex = 1 - scorePlayerIndex;
        }

        for (Position freeField : Advisor.getAllFreeFields(set.getBoard())){
            pretendPut(set.getBoard(), freeField, scorePoints, set, scorePlayerIndex, tempCurrentPlayerIndex, set.getLevel()+1);
        }

        if (set.getLevel()%2 == 0){
            gameTree.keepOnlyBestChildren(set, 5);}
        else {
            gameTree.keepOnlyWorstChildren(set, 1);
        }


        for (BoardPutMoveKillScoreSet child : set.getChildren()){
            recursivePutBfs(child, scorePoints, scorePlayerIndex, tempCurrentPlayerIndex, levelLimit);
        }
    }


    private void pretendPut(Board board, Position put, ScorePoints scorePoints, BoardPutMoveKillScoreSet parent, int scorePlayerIndex, int currentPlayerIndex, int level){

        BoardPutMoveKillScoreSet boardPutMoveKillScoreSet1 = new BoardPutMoveKillScoreSet();
        boardPutMoveKillScoreSet1.setPut(put);
        boardPutMoveKillScoreSet1.setLevel(level);

        Board clonedBoard1 = (Board) board.clone();
        clonedBoard1.putStone(put, currentPlayerIndex);

        boardPutMoveKillScoreSet1.setBoard(clonedBoard1);
        boardPutMoveKillScoreSet1.setScore(Advisor.getScore(boardPutMoveKillScoreSet1, scorePoints, scorePlayerIndex, false));



        if (boardPutMoveKillScoreSet1.getBoard().checkMorris(boardPutMoveKillScoreSet1.getPut())){
            for (Position killPosition : Advisor.getAllPossibleKills(clonedBoard1,currentPlayerIndex)){
                BoardPutMoveKillScoreSet boardPutMoveKillScoreSet2 = new BoardPutMoveKillScoreSet();
                boardPutMoveKillScoreSet2.setPut(put);
                boardPutMoveKillScoreSet2.setLevel(level);
                boardPutMoveKillScoreSet2.setKill(killPosition);

                Board clonedBoard2 = (Board) clonedBoard1.clone();
                clonedBoard2.clearStone(killPosition);

                boardPutMoveKillScoreSet2.setBoard(clonedBoard2);
                boardPutMoveKillScoreSet2.setScore(Advisor.getScore(boardPutMoveKillScoreSet2, scorePoints, scorePlayerIndex, false));
                gameTree.addSet(parent, boardPutMoveKillScoreSet2);
            }
        }
        else {
            gameTree.addSet(parent, boardPutMoveKillScoreSet1);
        }

    }


    @Override
    Move move(Board board, int playerIndex, boolean allowedToJump) {


        gameTree.initializeRoot(board);

        ScorePoints moveScorePoints = new ScorePoints(10000, 3000,2000, 20, 30,3, -20000, -5000, -30, -200, -100, -2);

        recursiveMoveBfs(gameTree.getRoot(), moveScorePoints, playerIndex, playerIndex, 5);

        System.out.println(gameTree);

        Stack<BoardPutMoveKillScoreSet> winningPath = gameTree.getPath(gameTree.getLeafWithBestScore());
        System.out.println("Gewinnerpfad:");
        while (!winningPath.isEmpty()){
            System.out.println(winningPath.pop());
        }

        System.out.println("Getätigter Zug: " + gameTree.getBestMove());
        return gameTree.getBestMove();

    }

    private void recursiveMoveBfs(BoardPutMoveKillScoreSet set, ScorePoints scorePoints, int scorePlayerIndex, int currentPlayerIndex, int levelLimit){

        if (set.getLevel()==levelLimit){
            return;
        }

        int tempCurrentPlayerIndex;

        if (set.getLevel()%2 == 0){
            tempCurrentPlayerIndex = scorePlayerIndex;
        }
        else {
            tempCurrentPlayerIndex = 1 - scorePlayerIndex;
        }

        for (Move move : Advisor.getAllPossibleMoves(set.getBoard(), currentPlayerIndex)){
            pretendMove(set.getBoard(), move, scorePoints, set, scorePlayerIndex, tempCurrentPlayerIndex, set.getLevel()+1);
        }

        if (set.getLevel()%2 == 0){
            gameTree.keepOnlyBestChildren(set, 5);}
        else {
            gameTree.keepOnlyWorstChildren(set, 1);
        }


        for (BoardPutMoveKillScoreSet child : set.getChildren()){
            recursiveMoveBfs(child, scorePoints, scorePlayerIndex, tempCurrentPlayerIndex, levelLimit);
        }
    }


    private void pretendMove(Board board, Move move, ScorePoints scorePoints, BoardPutMoveKillScoreSet parent, int scorePlayerIndex, int currentPlayerIndex, int level){

        BoardPutMoveKillScoreSet boardPutMoveKillScoreSet1 = new BoardPutMoveKillScoreSet();
        boardPutMoveKillScoreSet1.setMove(move);
        boardPutMoveKillScoreSet1.setLevel(level);

        Board clonedBoard1 = (Board) board.clone();
        clonedBoard1.move(move, currentPlayerIndex);

        boardPutMoveKillScoreSet1.setBoard(clonedBoard1);
        boardPutMoveKillScoreSet1.setScore(Advisor.getScore(boardPutMoveKillScoreSet1, scorePoints, scorePlayerIndex, false));



        if (boardPutMoveKillScoreSet1.getBoard().checkMorris(boardPutMoveKillScoreSet1.getMove().getTo())){
            for (Position killPosition : Advisor.getAllPossibleKills(clonedBoard1,currentPlayerIndex)){
                BoardPutMoveKillScoreSet boardPutMoveKillScoreSet2 = new BoardPutMoveKillScoreSet();
                boardPutMoveKillScoreSet2.setMove(move);
                boardPutMoveKillScoreSet2.setLevel(level);
                boardPutMoveKillScoreSet2.setKill(killPosition);

                Board clonedBoard2 = (Board) clonedBoard1.clone();
                clonedBoard2.clearStone(killPosition);

                boardPutMoveKillScoreSet2.setBoard(clonedBoard2);
                boardPutMoveKillScoreSet2.setScore(Advisor.getScore(boardPutMoveKillScoreSet2, scorePoints, scorePlayerIndex, false));
                gameTree.addSet(parent, boardPutMoveKillScoreSet2);
            }
        }
        else {
            gameTree.addSet(parent, boardPutMoveKillScoreSet1);
        }

        /*LinkedList<BoardPutMoveKillScoreSet> list = new LinkedList<>();
        BoardPutMoveKillScoreSet boardPutMoveKillScoreSet1 = new BoardPutMoveKillScoreSet();
        boardPutMoveKillScoreSet1.setMove(move);
        boardPutMoveKillScoreSet1.setLevel(level);

        Board clonedBoard1 = (Board) board.clone();


        *//*System.out.println();
        System.out.println(boardPutMoveKillScoreSet1.getMove());
        System.out.println(boardPutMoveKillScoreSet1.getBoard());*//*


       if (clonedBoard1.checkMorris(move.getTo())){
           for (Position killPosition : Advisor.getAllPossibleKills(clonedBoard1, playerIndex)){
           BoardPutMoveKillScoreSet boardPutMoveKillScoreSet2 = new BoardPutMoveKillScoreSet();
           boardPutMoveKillScoreSet2.setKill(killPosition);
           boardPutMoveKillScoreSet2.setLevel(level);

           Board clonedBoard2 = (Board) clonedBoard1.clone();
           clonedBoard2.clearStone(killPosition);
           boardPutMoveKillScoreSet2.setBoard(clonedBoard2);

           *//*System.out.println();
           System.out.println(boardPutMoveKillScoreSet2.getMove());
           System.out.println(boardPutMoveKillScoreSet2.getBoard());*//*
           boardPutMoveKillScoreSet2.setScore(Advisor.getMoveScore(clonedBoard2, move, scorePoints, playerIndex, false));
           gameTree.addSet(parent, boardPutMoveKillScoreSet2);
           list.add(boardPutMoveKillScoreSet2);}
       }
       else {
           list.add(boardPutMoveKillScoreSet1);
           boardPutMoveKillScoreSet1.setScore(Advisor.getMoveScore(clonedBoard1, move, scorePoints, playerIndex, false));
           clonedBoard1.move(move, playerIndex);
           boardPutMoveKillScoreSet1.setBoard(clonedBoard1);
           gameTree.addSet(parent, boardPutMoveKillScoreSet1);
       }*/

        //return list;
    }



    @Override
    Position kill(Board board, int ownPlayerIndex, int otherPlayerIndex) {

        return gameTree.getBestKill();

    }


}
