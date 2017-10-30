package com.earnest.Items;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by yfang on 10/29/2017.
 */
enum ShipType {
    Carrier(5),
    Battleship(4),
    Cruiser(3),
    Submarine(3),
    Destroyer(2);

    private final int size;

    ShipType(int size) {
        this.size = size;
    }

    int getSize() {
        return size;
    }
}


@Getter
@Setter
class Ship {
    private final ShipType type;
    private boolean sunk;

    Ship(ShipType type) {
        this.type = type;
        sunk = false;
    }
}
