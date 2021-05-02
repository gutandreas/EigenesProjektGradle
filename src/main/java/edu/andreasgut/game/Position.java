package edu.andreasgut.game;

public class Position implements Comparable {
    int ring;
    int field;

    public Position() {
    }

    public Position(int ring, int field) {
        this.ring = ring;
        this.field = field;
    }

    public int getRing() {
        return ring;
    }

    public int getField() {
        return field;
    }

    public void setRing(int ring) {
        this.ring = ring;
    }

    public void setField(int field) {
        this.field = field;
    }



    @Override
    public int compareTo(Object o) {
        Position position = (Position) o;
        if (ring == position.getRing() && field == position.getField()) return 0;
        if (ring == position.getRing() && field > position.getField()) return 1;
        if (ring > position.getRing()) return 1;
        return -1;
    }
}
