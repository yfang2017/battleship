package com.earnest.Items;

public class Position {
    int row;
    int col;

    Position(int[] position) {
        this.row = position[0];
        this.col = position[1];
    }

    Position(String position) throws User.positionException{
       this(convert(position));
    }

    private static int[] convert(String input) throws User.positionException {
        int[] result = new int[2];
        result[0] = input.charAt(0) - 'A';
        if (input.length() == 2) {
            result[1] = input.charAt(1) - '1';
        } else {
            result[1] = (input.charAt(1) - '0')*10 + (input.charAt(2) - '0') -1;
        }
        return result;
    }
}
