package com.earnest.Items;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by yfang on 10/29/2017.
 */

@Getter
@Setter
class Cell {
    private Ship ship = null;
    private boolean attacked = false;
}
