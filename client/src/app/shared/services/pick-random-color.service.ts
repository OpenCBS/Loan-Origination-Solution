import { Injectable } from '@angular/core';

@Injectable()
export class PickRandomColorService {
    public arr = [];
    public colors: Object = {
        orange: '#de7e1c',
        cyan: '#20a5b3',
        pink: '#9e2690',
        blue: '#3799ec',
    };

    constructor() {
        for (let prop in this.colors) {
            if (this.colors.hasOwnProperty(prop)) {
                this.arr.push(prop);
            }
        }
    }

    getRandomColor(num) {
        return this.colors[this.arr[num]];
    }

    pickRandomProperty(obj) {
        let result;
        let count = 0;
        for (let prop in obj) {
            if (Math.random() < 1 / ++count) {
                result = prop;
            }
        }
        return result;
    }
}
