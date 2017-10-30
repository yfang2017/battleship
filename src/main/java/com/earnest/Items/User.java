package com.earnest.Items;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * Created by yfang on 10/29/2017.
 * This is the main class of the game.
 * Each user will have their own board and fleet, where fleet is a 1D array of Ships and board is a 2D array of Cell.
 * Each cell will have a flag to record if it has been attacked, and a pointer to the ship occupying this cell.
 * The ship will have a integer to record the size of the ship, and a flag to record if the ship is sunk.
 * A Hashmap is also maintained to record the positions for the whole fleet.
 * User input is supposed to be a string like "A1","C6", "F10"...
 */

@Slf4j
public class User {
    private int userID;
    Cell[][] board;
    Ship[] fleet;
    HashMap<Ship, Cell[]> fleetPositions;

    public User(int userID) {
        this.userID = userID;
        fleetPositions = new HashMap<>();
    }

    // This method is the initialize phase of the phase.
    // It will create the board with the initial positions of the fleet.
    // To be implemented...
    public void initiate() {
    }

    // This is the required implementation of attack method.
    // If the input attack position is out of the board, it will log an error.
    // Otherwise, the cell at the attack position will be checked to determine the result.
    // More specifically, if the attacked flag is true, it will return "Already taken";
    // if the cell points to the null, it will return "Miss";
    // if the cell points to some ship, it will return "Hit";
    // or if all the cells the ship occupies have been attacked, it will return "Sunk";
    // or if all ship in the fleet has sunken, it will return "Win".
    public String attack(User targetUser, String[] targetPositions) {
        try {
            int[] targetIndex = convert(targetPositions[0]);
            int row = targetIndex[0];
            int col = targetIndex[1];
            if(targetUser.board[row][col].isAttacked()) {
                return "Already Taken";
            }

            targetUser.board[row][col].setAttacked(true);

            Ship ship = targetUser.board[row][col].getShip();
            if(ship == null) {
                return "Miss";
            }

            if(!checkSunk(ship, targetUser)) {
                return "Hit";
            } else {
                for(Ship otherShip : targetUser.fleet) {
                    if(!otherShip.isSunk()) {
                        return "Sunk";
                    }
                }
                return "Win";
            }
        } catch (positionException e) {
            log.error("not valid input position");
            return null;
        }
    }

    // This is the method to set the ship on the board.
    // Based on the current Battleship rule, it is only needed for the initialization phase.
    // It is implemented here for the test purpose only.
    // After the initialization method is implemented, this method should be changed to private.
    void setShipPosition (Ship ship, String start, String end) throws positionException, shipException {
        try {
            int[] startPosition = convert(start);
            int[] endPosition = convert(end);
            int row1 = startPosition[0];
            int col1 = startPosition[1];
            int row2 = endPosition[0];
            int col2 = endPosition[1];
            Cell[] positions = new Cell[ship.getType().getSize()];

            int count = 0;
            if (col1 == col2 && Math.abs(row1 - row2) == ship.getType().getSize() - 1) {
                for (int i = Math.min(row1, row2); i <= Math.max(row1, row2); i++) {
                    positions[count] = board[i][col1];
                    if (positions[count].getShip() != null) {   // if the cell is already occupied,
                        while (count > 0) {                     // an exception will be thrown,
                            positions[--count].setShip(null);   // and all previous positions for this ship will be reset.
                        }
                        throw new shipException();
                    }
                    count++;
                    board[i][col1].setShip(ship);
                }
            } else if (row1 == row2 && Math.abs(col1 - col2) == ship.getType().getSize() - 1) {
                for (int i = Math.min(col1, col2); i <= Math.max(col1, col2); i++) {
                    positions[count] = board[row1][i];
                    if (positions[count].getShip() != null) {
                        while (count > 0) {
                            positions[--count].setShip(null);
                        }
                        throw new shipException();
                    }
                    count++;
                    board[row1][i].setShip(ship);
                }
            } else {
                throw new positionException();
            }

            fleetPositions.put(ship, positions);
            log.info("Position set for the ship");
        } catch (positionException e) {
            throw e;
        }
    }

    //This is the method to check if a given ship is sunk or not.
    private boolean checkSunk(Ship ship, User user) {
        Cell[] positions = user.fleetPositions.get(ship);
        for (Cell position: positions) {
            if (!position.isAttacked()) {
                return false;
            }
        }
        ship.setSunk(true);
        return true;
    }

    // This is the method to convert the user input(supposedly a string) to the coordinates on the board.
    private int[] convert(String input) throws positionException {
        int[] result = new int[2];
        result[0] = input.charAt(0) - 'A';
        if (input.length() == 2) {
            result[1] = input.charAt(1) - '1';
        } else {
            result[1] = (input.charAt(1) - '0')*10 + (input.charAt(2) - '0') -1;
        }

        if (result[0] < 0 || result[0] >= board.length || result[1] < 0 || result[1] >= board[0].length) {
            throw new positionException();
        } else {
            return result;
        }
    }

    static class positionException extends Exception {
    }

    static class shipException extends Exception {
    }
}
