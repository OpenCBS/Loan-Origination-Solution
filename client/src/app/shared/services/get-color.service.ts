/**
 * Created by Chyngyz on 3/16/2017.
 */
import { Injectable } from '@angular/core';

const COLORS = [
    '',
    'orange',
    'cyan',
    'pink',
    'blue',
    'purple',
    'almond',
    'gold',
    'aqua',
    'yellow'
];

@Injectable()
export class ColorService {

    constructor() {
    }

    getColor(index) {
        return COLORS[index];
    }
}