import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
    selector: 'cbs-not-found',
    templateUrl: '404.component.html'
})
export class NotFoundComponent implements OnInit {
    constructor(private router: Router) { }

    ngOnInit() { }

    goHome() {
        this.router.navigateByUrl('/');
    }
}