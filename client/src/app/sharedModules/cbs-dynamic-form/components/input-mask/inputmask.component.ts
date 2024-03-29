/*
    Customized fork of input-mask component from NG-Prime

    Port of jQuery MaskedInput by DigitalBush as a Native Angular2 Component in Typescript without jQuery
    https://github.com/digitalBush/jquery.maskedinput/

    Copyright (c) 2007-2014 Josh Bush (digitalbush.com)

    Permission is hereby granted, free of charge, to any person
    obtaining a copy of this software and associated documentation
    files (the "Software"), to deal in the Software without
    restriction, including without limitation the rights to use,
    copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the
    Software is furnished to do so, subject to the following
    conditions:

    The above copyright notice and this permission notice shall be
    included in all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
    EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
    OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
    NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
    HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
    WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
    FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
    OTHER DEALINGS IN THE SOFTWARE.
*/
import {
  Component,
  ElementRef,
  AfterViewInit,
  OnDestroy,
  Input,
  forwardRef,
  Output,
  EventEmitter, Inject
} from '@angular/core';
import { NG_VALUE_ACCESSOR, ControlValueAccessor } from '@angular/forms';

/* tslint:disable */
export const INPUTMASK_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => InputMaskComponent),
  multi: true
};

@Component({
    selector: 'cbs-input-mask',
    template: `<input
            [attr.type]="type"
            [attr.name]="name"
            [value]="value||''"
            [ngStyle]="style"
            [ngClass]="styleClass"
            [attr.placeholder]="placeholder"
            [attr.size]="size"
            [attr.maxlength]="maxlength"
            [attr.tabindex]="tabindex"
            [disabled]="disabled"
            [readonly]="readonly"
            (focus)="onFocus($event)"
            (blur)="onBlur($event)"
            (keydown)="onKeyDown($event)"
            (keypress)="onKeyPress($event)"
            (input)="onInput($event)"
            (paste)="handleInputChange($event)">`,
    providers: [INPUTMASK_VALUE_ACCESSOR]
})
export class InputMaskComponent implements AfterViewInit,OnDestroy,ControlValueAccessor {
    @Input() mask: string;
    @Input() type = 'text';
    @Input() slotChar = '_';
    @Input() autoClear = true;
    @Input() style: string;
    @Input() styleClass: string;
    @Input() placeholder: string;
    @Input() size: number;
    @Input() maxlength: number;
    @Input() tabindex: string;
    @Input() disabled: boolean;
    @Input() readonly: boolean;
    @Input() unmask: boolean;
    @Input() name: string;
    @Output() onComplete: EventEmitter<any> = new EventEmitter();
    value: any;

    input: HTMLInputElement;
    defs: any;
    tests: any[];
    partialPosition: any;
    firstNonMaskPos: number;
    lastRequiredNonMaskPos: any;
    len: number;
    oldVal: string;
    buffer: any;
    defaultBuffer: string;
    focusText: string;
    caretTimeoutId: any;
    androidChrome: boolean;
    onModelChange: Function = () => {};
    onModelTouched: Function = () => {};

    constructor(public el: ElementRef) {}

    ngAfterViewInit() {
        this.input = this.el.nativeElement.children[0];
        this.tests = [];
        this.partialPosition = this.mask.length;
        this.len = this.mask.length;
        this.firstNonMaskPos = null;
        this.defs = {
            '9': '[0-9]',
            'a': '[A-Za-z]',
            '*': '[A-Za-z0-9]'
        };

        let ua = this.getUserAgent();
        this.androidChrome = /chrome/i.test(ua) && /android/i.test(ua);

        let maskTokens = this.mask.split('');
        for (let i = 0; i < maskTokens.length; i++) {
            let c = maskTokens[i];
            if (c === '?') {
                this.len--;
                this.partialPosition = i;
            } else if (this.defs[c]) {
                this.tests.push(new RegExp(this.defs[c]));
                if (this.firstNonMaskPos === null) {
                    this.firstNonMaskPos = this.tests.length - 1;
                }
                if (i < this.partialPosition){
                    this.lastRequiredNonMaskPos = this.tests.length - 1;
                }
            } else {
                this.tests.push(null);
            }
        }

        this.buffer = [];
        for (let i = 0; i < maskTokens.length; i++) {
            let c = maskTokens[i];
            if (c !== '?') {
                if (this.defs[c]) {
                    this.buffer.push(this.getPlaceholder(i));
                } else {
                    this.buffer.push(c);
                }
            }
        }
        this.defaultBuffer = this.buffer.join('');
        this.focusText = this.input.value;

        this.checkVal(); // Perform initial check for existing values
    }

    writeValue(value: any): void {
        let _this = this;
        this.value = value;

        if (this.input) {
            if (this.value === undefined || this.value === null) {
                this.input.value = '';
            } else {
                this.input.value = this.value;
                this.checkVal();
            }
            setTimeout(() => {
                _this.writeBuffer();
                _this.checkVal();
            }, 10);
        }
    }
    registerOnChange(fn: Function): void {
        this.onModelChange = fn;
    }

    registerOnTouched(fn: Function): void {
        this.onModelTouched = fn;
    }
    setDisabledState(val: boolean): void {
        this.disabled = val;
    }

    getUserAgent(): string {
        return navigator.userAgent;
    }

    caret(first?: number, last?: number) {
        let range, begin, end;

        if (!this.input.offsetParent||this.input !== document.activeElement) {
            return;
        }

        if (typeof first === 'number') {
            begin = first;
            end = (typeof last === 'number') ? last : begin;
            if (this.input.setSelectionRange) {
                this.input.setSelectionRange(begin, end);
            } else if (this.input['createTextRange']) {
                range = this.input['createTextRange']();
                range.collapse(true);
                range.moveEnd('character', end);
                range.moveStart('character', begin);
                range.select();
            }
        } else {
            if (this.input.setSelectionRange) {
                begin = this.input.selectionStart;
                end = this.input.selectionEnd;
            } else if (document['selection'] && document['selection'].createRange) {
                range = document['selection'].createRange();
                begin = 0 - range.duplicate().moveStart('character', -100000);
                end = begin + range.text.length;
            }
            return {begin: begin, end: end};
        }
    }

    isCompleted(): boolean {
        for (let i = this.firstNonMaskPos; i <= this.lastRequiredNonMaskPos; i++) {
            if (this.tests[i] && this.buffer[i] === this.getPlaceholder(i)) {
                return false;
            }
        }

        return true;
    }

    getPlaceholder(i: number) {
        if (i < this.slotChar.length) {
            return this.slotChar.charAt(i);
        }
        return this.slotChar.charAt(0);
    }

    seekNext(pos) {
        while (++pos < this.len && !this.tests[pos]) {};
        return pos;
    }

    seekPrev(pos) {
        while (--pos >= 0 && !this.tests[pos]) {};
        return pos;
    }

    shiftL(begin:number,end:number) {
        let i, j;

        if (begin<0) {
            return;
        }

        for (i = begin, j = this.seekNext(end); i < this.len; i++) {
            if (this.tests[i]) {
                if (j < this.len && this.tests[i].test(this.buffer[j])) {
                    this.buffer[i] = this.buffer[j];
                    this.buffer[j] = this.getPlaceholder(j);
                } else {
                    break;
                }

                j = this.seekNext(j);
            }
        }
        this.writeBuffer();
        this.caret(Math.max(this.firstNonMaskPos, begin));
    }

    shiftR(pos) {
        let i, c, j, t;

        for (i = pos, c = this.getPlaceholder(pos); i < this.len; i++) {
            if (this.tests[i]) {
                j = this.seekNext(i);
                t = this.buffer[i];
                this.buffer[i] = c;
                if (j < this.len && this.tests[j].test(t)) {
                    c = t;
                } else {
                    break;
                }
            }
        }
    }

    handleAndroidInput(e) {
        let curVal = this.input.value;
        let pos = this.caret();
        if (this.oldVal && this.oldVal.length && this.oldVal.length > curVal.length ) {
            // a deletion or backspace happened
            this.checkVal(true);
            while (pos.begin > 0 && !this.tests[pos.begin-1]) {
                pos.begin--;
            }

            if (pos.begin === 0) {
               while (pos.begin < this.firstNonMaskPos && !this.tests[pos.begin]) {
                   pos.begin++;
               }
            }
            this.caret(pos.begin,pos.begin);
        } else {
            while (pos.begin < this.len && !this.tests[pos.begin]) {
                pos.begin++;
            }

            this.caret(pos.begin,pos.begin);
        }

        if (this.isCompleted()) {
            this.onComplete.emit();
        }
    }
    onBlur(e) {
        this.onModelTouched();
        this.checkVal();
        this.updateModel(e);

        if (this.input.value !== this.focusText) {
            let event = document.createEvent('HTMLEvents');
            event.initEvent('change', true, false);
            this.input.dispatchEvent(event);
        }
    }
    onKeyDown(e) {
        if (this.readonly) {
            return;
        }

        let k = e.which||e.keyCode,
            pos,
            begin,
            end;
        let iPhone = /iphone/i.test(this.getUserAgent());
        this.oldVal = this.input.value;
        // backspace, delete, and escape get special treatment
        if (k === 8 || k === 46 || (iPhone && k === 127)) {
            pos = this.caret();
            begin = pos.begin;
            end = pos.end;


            if (end - begin === 0) {
                begin = k !==46 ? this.seekPrev(begin) : (end=this.seekNext(begin-1));
                end= k === 46 ? this.seekNext(end) : end;
            }
            this.clearBuffer(begin, end);
            this.shiftL(begin, end - 1);
            this.updateModel(e);

            e.preventDefault();
        } else if( k === 13 ) { // enter
            this.onBlur(e);
            this.updateModel(e);
        } else if (k === 27) { // escape
            this.input.value = this.focusText;
            this.caret(0, this.checkVal());
            this.updateModel(e);
            e.preventDefault();
        }
    }

    onKeyPress(e) {
        if (this.readonly){
            return;
        }

        let k = e.which || e.keyCode,
            pos = this.caret(),
            p,
            c,
            next,
            completed;

        if (e.ctrlKey || e.altKey || e.metaKey || k < 32) {
            return;
        } else if ( k && k !== 13 ) {
            if (pos.end - pos.begin !== 0){
                this.clearBuffer(pos.begin, pos.end);
                this.shiftL(pos.begin, pos.end-1);
            }

            p = this.seekNext(pos.begin - 1);
            if (p < this.len) {
                c = String.fromCharCode(k);
                if (this.tests[p].test(c)) {
                    this.shiftR(p);

                    this.buffer[p] = c;
                    this.writeBuffer();
                    next = this.seekNext(p);

                    if(/android/i.test(this.getUserAgent())){
                        // Path for CSP Violation on FireFox OS 1.1
                        let proxy = () => {
                            this.caret(next);
                        };

                        setTimeout(proxy,0);
                    }else{
                        this.caret(next);
                    }
                    if(pos.begin <= this.lastRequiredNonMaskPos){
                         completed = this.isCompleted();
                     }
                }
            }
            e.preventDefault();
        }

        this.updateModel(e);

        if (completed) {
            this.onComplete.emit();
        }
    }

    clearBuffer(start, end) {
        let i;
        for (i = start; i < end && i < this.len; i++) {
            if (this.tests[i]) {
                this.buffer[i] = this.getPlaceholder(i);
            }
        }
    }

    writeBuffer() {
        this.input.value = this.buffer.join('');
    }

    checkVal(allow?: boolean) {
        // try to place characters where they belong
        let test = this.input.value,
            lastMatch = -1,
            i,
            c,
            pos;

        for (i = 0, pos = 0; i < this.len; i++) {
            if (this.tests[i]) {
                this.buffer[i] = this.getPlaceholder(i);
                while (pos++ < test.length) {
                    c = test.charAt(pos - 1);
                    if (this.tests[i].test(c)) {
                        this.buffer[i] = c;
                        lastMatch = i;
                        break;
                    }
                }
                if (pos > test.length) {
                    this.clearBuffer(i + 1, this.len);
                    break;
                }
            } else {
                if (this.buffer[i] === test.charAt(pos)) {
                    pos++;
                }
                if ( i < this.partialPosition){
                    lastMatch = i;
                }
            }
        }
        if (allow) {
            this.writeBuffer();
        } else if (lastMatch + 1 < this.partialPosition) {
            if (this.autoClear || this.buffer.join('') === this.defaultBuffer) {
                // Invalid value. Remove it and replace it with the
                // mask, which is the default behavior.
                if (this.input.value) {
                    this.input.value = '';
                }
                this.clearBuffer(0, this.len);
            } else {
                // Invalid value, but we opt to show the value to the
                // user and allow them to correct their mistake.
                this.writeBuffer();
            }
        } else {
            this.writeBuffer();
            this.input.value = this.input.value.substring(0, lastMatch + 1);
        }
        return (this.partialPosition ? i : this.firstNonMaskPos);
    }

    onFocus(event) {
        if (this.readonly){
            return;
        }

        clearTimeout(this.caretTimeoutId);
        let pos;

        this.focusText = this.input.value;

        pos = this.checkVal();

        this.caretTimeoutId = setTimeout(() => {
            if (this.input !== document.activeElement){
                return;
            }
            this.writeBuffer();
            if (pos === this.mask.replace('?','').length) {
                this.caret(0, pos);
            } else {
                this.caret(pos);
            }
        }, 10);
    }

    onInput(event) {
        if (this.androidChrome) {
            this.handleAndroidInput(event);
        } else {
            this.handleInputChange(event);
        }
    }

    handleInputChange(event) {
        if (this.readonly){
            return;
        }

        setTimeout(() => {
            let pos = this.checkVal(true);
            this.caret(pos);
            this.updateModel(event);
            if (this.isCompleted()) {
                this.onComplete.emit();
            }
        }, 0);
    }

    getUnmaskedValue() {
        let unmaskedBuffer = [];
        for (let i = 0; i < this.buffer.length; i++) {
            let c = this.buffer[i];
            if (this.tests[i] && c !== this.getPlaceholder(i)) {
                unmaskedBuffer.push(c);
            }
        }

        return unmaskedBuffer.join('');
    }

    updateModel(e) {
        this.onModelChange(this.unmask ? this.getUnmaskedValue() : e.target.value);
    }


    ngOnDestroy() {

    }
}

/* tslint:enable */
