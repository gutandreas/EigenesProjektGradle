package edu.andreasgut.game;

import edu.andreasgut.view.ViewManager;

import java.util.Random;

public class ComputerPlayer extends Player {


    public ComputerPlayer(ViewManager viewManager, String name) {
        super(viewManager, name);
    }



    @Override
    Position put(Board board, int playerIndex) {

        // 1. Priorität
        for (int row = 0; row < 3; row++) {
            for (int field = 0; field < 8; field++) {

                // bildet Mühle wenn 2 Steine über Ringe hinweg
                if (board.isThisMyStone(new Position(row, field), playerIndex)
                        && field%2==1
                        && board.isThisMyStone(new Position((row+1)%3, field), playerIndex)
                        && board.isFieldFree(new Position((row+2)%3, field))) {
                    System.out.println("Computerstrategie: bildet Mühle wenn 2 Steine über Ringe hinweg");
                    return new Position((row+2)%3, field); }

                // bildet Mühle wenn 2 Steine innerhalb von Ring nebeneinander
                if (board.isThisMyStone(new Position(row, field), playerIndex)
                        && board.isThisMyStone(new Position(row, (field+1)%8), playerIndex)){
                    // setzt Stein vor der 2er-Reihe sofern frei
                    if ((field % 2) == 1 && board.isFieldFree(new Position(row, (field + 7) % 8 ))) {
                        System.out.println("Computerstrategie: setzt Stein vor der 2er-Reihe sofern frei");
                        return new Position(row, (field + 7) % 8);
                    }
                    // setzt Stein nach der 2er-Reihe sofern frei
                    if ((field % 2) == 0 && board.isFieldFree(new Position(row ,(field + 2) % 8))) {
                        System.out.println("Computerstrategie: setzt Stein nach der 2er-Reihe sofern frei");
                        return new Position(row, (field + 2) % 8);
                    }
                }

                // bildet Mühle wenn 2 Steine mit Lücke innerhalb von Ring
                if (board.isThisMyStone(new Position(row, field), playerIndex)
                        && board.isThisMyStone(new Position(row, (field+2)%8), playerIndex)
                        &&(field % 2) == 0
                        && board.isFieldFree(new Position(row, (field + 1) % 8))) {
                    System.out.println("Computerstrategie: bildet Mühle wenn 2 Steine mit Lücke innerhalb von Ring");
                    return new Position(row, (field + 1) % 8);
                }

                // blockt wenn 2 Steine über Ringe hinweg
                if (board.isThisMyEnemysStone(new Position(row, field), playerIndex)
                        && field%2==1
                        && board.isThisMyEnemysStone(new Position((row+1)%3, field), playerIndex)
                        && board.isFieldFree(new Position((row+2)%3, field))) {
                    System.out.println("Computerstrategie: blockt wenn 2 Steine über Ringe hinweg");
                    return new Position((row+2)%3, field); }

                // blockt wenn 2 Steine innerhalb von Ring nebeneinander
                if (board.isThisMyEnemysStone(new Position(row, field), playerIndex)
                        && board.isThisMyEnemysStone(new Position(row, (field+1)%8), playerIndex)){
                    // blockt vor der 2er-Reihe sofern frei
                    if ((field % 2) == 1 && board.isFieldFree(new Position(row, (field + 7) % 8 ))) {
                        System.out.println("Computerstrategie: blockt wenn 2 Steine innerhalb von Ring nebeneinander");
                        return new Position(row, (field + 7) % 8);
                    }
                    // blockt nach der 2er-Reihe sofern frei
                    if ((field % 2) == 0 && board.isFieldFree(new Position(row ,(field + 2) % 8))) {
                        System.out.println("Computerstrategie: blockt wenn 2 Steine innerhalb von Ring nebeneinander");
                        return new Position(row, (field + 2) % 8);
                    }
                }

                // blockt wenn 2 Steine mit Lücke innerhalb von Ring
                if (board.isThisMyEnemysStone(new Position(row, field), playerIndex)
                        && board.isThisMyEnemysStone(new Position(row, (field+2)%8), playerIndex)
                        &&(field % 2) == 0
                        && board.isFieldFree(new Position(row, (field + 1) % 8))) {
                    System.out.println("Computerstrategie: blockt wenn 2 Steine mit Lücke innerhalb von Ring");
                    return new Position(row, (field + 1) % 8);
                }



                }
            }


        // 2. Priorität
        for (int row = 0; row < 3; row++) {
            for (int field = 0; field < 8; field++) {


                // wählt Ecke einer leeren Linie oder in Linie mit einem eigenen Stein
                if (board.isFieldFree(new Position(row, field)) && field%2==0) {

                    if (board.isFieldFree(new Position(row, (field + 1) % 8))
                            && (board.isFieldFree(new Position(row, (field + 2) % 8))
                            || board.isThisMyStone(new Position(row, (field + 2) % 8), playerIndex))) {
                        System.out.println("Computerstrategie: wählt Ecke einer leeren Linie oder in Linie mit einem eigenen Stein");
                        return new Position(row, field);
                    }

                    if (board.isFieldFree(new Position(row, (field + 7) % 8))
                            && (board.isFieldFree(new Position(row, (field + 6) % 8))
                            || board.isThisMyStone(new Position(row, (field + 6) % 8), playerIndex))) {
                        System.out.println("Computerstrategie: wählt Ecke einer leeren Linie oder in Linie mit einem eigenen Stein");
                        return new Position(row, field);
                    }
                }
            }}


        // wählt zufälliges leeres Feld
        while (true){
            Random random = new Random();
            Position tempPosition = new Position(random.nextInt(2),  random.nextInt(7));
            if (board.isFieldFree(tempPosition)){
                System.out.println("Computerstrategie: wählt zufälliges leeres Feld");
                return tempPosition;
            }
        }
    }


    @Override
    Position[] move(Board board, int playerIndex, boolean allowedToJump) {
        Position[] positions = new Position[2];

        Board clonedBoard = (Board) board.clone();

        // 1. Priorität: Versucht Mühlen zu schliessen
        for (int row = 0; row < 3; row++) {
            for (int field = 0; field < 7; field++) {

                Position from = new Position(row, field);
                Position to;

                if (board.isThisMyStone(from, playerIndex)) {

                    to = new Position(row, (field + 1) % 8);

                    if (checkIfMoveBuildsMorris(board, playerIndex, positions, clonedBoard, from, to)) return positions;

                    to = new Position(row, (field + 7) % 8);

                    if (checkIfMoveBuildsMorris(board, playerIndex, positions, clonedBoard, from, to)) return positions;

                    if (field % 8 == 1) { // Felder mit Verbindung zu mind. 1 anderer Reihe

                        if (row == 0 || row == 1) {

                            to = new Position(row + 1, field);

                            if (checkIfMoveBuildsMorris(board, playerIndex, positions, clonedBoard, from, to))
                                return positions;
                        }

                        if (row == 1 || row == 2) {

                            to = new Position(row - 1, field);

                            if (checkIfMoveBuildsMorris(board, playerIndex, positions, clonedBoard, from, to))
                                return positions;
                        }

                    }
                }

            }
        }

        // 2. Priorität: Wählt zufälligen Stein und fährt in zufällige Richtung
        Random random = new Random();
        Position from;
        Position to;

        while (true){
            int row = random.nextInt(2);
            int field= random.nextInt(7);
            from = new Position(row, field);
            if (board.isThisMyStone(from, playerIndex)){
                if (board.isFieldFree(new Position(row, (field+1)%8))){
                    to = new Position(row, (field+1)%8);
                    positions[0] = from;
                    positions[1] = to;
                    System.out.println("Computerstrategie: Erhöht bei zufälligem Stein das Feld um 1");
                    return positions;
                }

                if (board.isFieldFree(new Position(row, (field+7)%8))){
                    to = new Position(row, (field+7)%8);
                    positions[0] = from;
                    positions[1] = to;
                    System.out.println("Computerstrategie: Reduziert bei zufälligem Stein das Feld um 1");
                    return positions;
                }

                if (field%2 == 1 && (row == 0 || field == 1) && board.isFieldFree(new Position(row+1, field))){
                    to = new Position(row+1, field);
                    positions[0] = from;
                    positions[1] = to;
                    System.out.println("Computerstrategie: Erhöht bei zufälligem Stein die Reihe um 1");
                    return positions;
                }

                if (field%2 == 1 && (row == 1 || field == 2) && board.isFieldFree(new Position(row-1, field))){
                    to = new Position(row-1, field);
                    positions[0] = from;
                    positions[1] = to;
                    System.out.println("Computerstrategie: Reduziert bei zufälligem Stein die Reihe um 1");
                    return positions;
                }

            }
        }
    }

    private boolean checkIfMoveBuildsMorris(Board board, int playerIndex, Position[] positions, Board clonedBoard, Position from, Position to) {
        if (board.isFieldFree(to)) {
            clonedBoard.move(from, to, playerIndex);
            if (clonedBoard.checkMorris(to)) {
                positions[0] = from;
                positions[1] = to;
                return true;
            } else {
                clonedBoard.move(to, from, playerIndex); // setzt Stein zurück
            }
        }
        return false;
    }


    @Override
    Position kill(Board board, int otherPlayerIndex) {
        Position position = new Position();
        int i;
        int j;

        loop:{
        for (i = 0; i < 3; i++) {
            for (j = 0; j < 8; j++) {
                if (board.checkKill(new Position(i,j), otherPlayerIndex)) {
                    position.setRing(i);
                    position.setField(j);

                    break loop;
                }
            }
        }}
        return position;
    }

}
