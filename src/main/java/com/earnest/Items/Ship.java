package com.earnest.Items;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by yfang on 10/29/2017.
 * The checkSunk method will check if all cells the ship occupies have been attacked. 
 */

@Getter
@Setter
class Ship {
    private int size;
    private Cell[] positions;

    Ship(int size) {
        this.size = size;
    }

    boolean checkSunk() {
        for (Cell position: positions) {
            if (!position.isAttacked()) {
                return false;
            }
        }
        return true;
    }
}
