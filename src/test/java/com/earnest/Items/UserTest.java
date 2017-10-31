package com.earnest.Items;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserTest {
    private User userTwo = new User(2);

    @Before
    public void setUp() throws Exception {
        // for the test, the board is 10x10 in size.
        // each user has 5 ships, carrier, battleship, cruiser, submarine, destroyer.
        int m = 10;
        int n = 10;
        userTwo.board = new Cell[m][n];
        userTwo.board = new Cell[m][n];
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                userTwo.board[i][j] = new Cell();
            }
        }


        Ship[] fleet = new Ship[5];
        fleet[0] = new Ship(ShipType.Carrier);   //Carrier
        fleet[1] = new Ship(ShipType.Battleship);   //Battleship
        fleet[2] = new Ship(ShipType.Cruiser);   //Cruiser
        fleet[3] = new Ship(ShipType.Submarine);   //Submarine
        fleet[4] = new Ship(ShipType.Destroyer);   //Destroyer

        try {
            userTwo.setShipPosition(fleet[0], "B1", "B5");
            userTwo.setShipPosition(fleet[1], "A1", "A4");
            userTwo.setShipPosition(fleet[2], "C1", "C3");
            userTwo.setShipPosition(fleet[3], "D1", "D3");
            userTwo.setShipPosition(fleet[4], "E1", "F1");
        } catch (User.positionException e) {
            log.error("Not valid input position");
        } catch (User.shipException e) {
            log.error("Ship cannot overlap");
        }

        try {
            userTwo.setShipPosition(fleet[0], "A1", "A4");
        } catch (User.positionException e) {
            log.error("Not valid input position");
        } catch (User.shipException e) {
            log.error("Ship cannot overlap");
        }

        try {
            userTwo.setShipPosition(fleet[0], "A7", "A11");
        } catch (User.positionException e) {
            log.error("Not valid input position");
        } catch (User.shipException e) {
            log.error("Ship cannot overlap");
        }

        try {
            userTwo.setShipPosition(fleet[0], "A5", "E5");
        } catch (User.positionException e) {
            log.error("Not valid input position");
        } catch (User.shipException e) {
            log.error("Ship cannot overlap");
        }
    }

    @Test
    public void attack() throws Exception {
        printBoard();
        assertEquals("Hit", userTwo.attack(new Position("A1")));
        printBoard();
        assertEquals("Already Taken", userTwo.attack(new Position("A1")));
        assertEquals("Hit", userTwo.attack(new Position("A2")));
        assertEquals("Hit", userTwo.attack(new Position("A3")));
        assertEquals("Sunk", userTwo.attack(new Position("A4")));
        assertEquals("Miss", userTwo.attack(new Position("A5")));
        assertEquals("Miss", userTwo.attack(new Position("A6")));
        assertEquals("Already Taken", userTwo.attack(new Position("A6")));
        printBoard();
        assertEquals("Hit", userTwo.attack(new Position("B1")));
        assertEquals("Hit", userTwo.attack(new Position("B2")));
        assertEquals("Hit", userTwo.attack(new Position("B3")));
        assertEquals("Hit", userTwo.attack(new Position("B4")));
        assertEquals("Sunk", userTwo.attack(new Position("B5")));
        assertEquals("Hit", userTwo.attack(new Position("C1")));
        assertEquals("Hit", userTwo.attack(new Position("C2")));
        assertEquals("Sunk", userTwo.attack(new Position("C3")));
        assertEquals("Hit", userTwo.attack(new Position("D1")));
        assertEquals("Hit", userTwo.attack(new Position("D2")));
        assertEquals("Sunk", userTwo.attack(new Position("D3")));
        assertEquals("Hit", userTwo.attack(new Position("E1")));
        assertEquals("Win", userTwo.attack(new Position("F1")));
        printBoard();
        assertEquals(null, userTwo.attack(new Position("O1")));
        assertEquals(null, userTwo.attack(new Position("A11")));
        printBoard();
    }

    private void printBoard() {
        int m = userTwo.board.length;
        int n = userTwo.board[0].length;
        char[][] printableBoard = new char[m][n];

        //'o' means empty cell
        //'+' means cell occupied by the ship
        //'x' means cell that has been attacked
        //'*' means cell occupied by the ship and has been attacked.

        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                Cell tmp = userTwo.board[i][j];
                if(tmp.getShip() != null && tmp.isAttacked()) {
                    printableBoard[i][j] = '*';
                } else if(tmp.getShip() != null) {
                    printableBoard[i][j] = '+';
                } else if(tmp.isAttacked()) {
                    printableBoard[i][j] = 'x';
                } else {
                    printableBoard[i][j] = 'o';
                }
                System.out.print(printableBoard[i][j]);
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }
}