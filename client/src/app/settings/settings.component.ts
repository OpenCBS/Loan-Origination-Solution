import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
    selector: 'cbs-settings',
    templateUrl: 'settings.component.html',
    styleUrls: ['./settings.component.scss']
})
export class SettingsComponent implements OnInit {
    constructor(private router: Router) { }

    ngOnInit() { }

    goTo(route: string) {
        this.router.navigate(['settings', `${route}`]);
    }

    goHome() {
        this.router.navigate(['settings']);
    }
}

