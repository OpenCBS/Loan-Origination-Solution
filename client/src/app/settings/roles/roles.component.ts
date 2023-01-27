
import {map} from 'rxjs/operators';
import { Component, OnInit, ViewChild } from '@angular/core';
import { Router, ActivatedRoute, NavigationExtras } from '@angular/router';

import { RolesService, RoleFormModalComponent } from '../../shared';
import { Role } from '../../shared/models';

import { AppSettings } from '../../app.settings';

@Component({
    selector: 'cbs-roles',
    templateUrl: 'roles.component.html'
})
export class RolesComponent implements OnInit {
    @ViewChild(RoleFormModalComponent, {static: false}) private roleFormModal: RoleFormModalComponent;

    public rolesData: Object[] = [];
    public queryPageObject: any;
    public queryObject: any = {};
    public pagerData: any = {};
    public currentPage = 0;
    public isLoading = true;
    public noData = true;

    public permissionCreateRole: string = AppSettings.PERMISSION_NAMES['CREATE_ROLE'];
    public permissionEditRole: string = AppSettings.PERMISSION_NAMES['EDIT_ROLE'];


    constructor(
        private roleService: RolesService,
        private route: ActivatedRoute,
        private router: Router) { }

    ngOnInit() {
        this.queryPageObject = this.route
            .queryParams.pipe(
            map(params => params || null));
        this.queryPageObject.subscribe((obj: any) => {
            // This is a hack for cloning object without the getters and setters,
            // here the obj keys are non-writable and non-configurable
            this.queryObject = JSON.parse(JSON.stringify(obj));

            this.refreshPage();
        });
    }

    refreshPage() {
        if (this.queryObject.hasOwnProperty('page')) {
            this.populate(+this.queryObject.page);
        } else {
            this.populate(1);
        }
    }

    populate(page: number) {
        this.roleService.getRoles(page).subscribe(
            resp => {
                if (resp.content.length) {
                    this.noData = false;
                    this.isLoading = false;
                    this.rolesData = resp.content;

                    this.pagerData.size = resp.size;
                    this.pagerData.total = resp.totalElements;
                    this.currentPage = resp.number + 1;
                } else {
                    console.log('No data');
                    this.isLoading = false;
                    this.noData = true;
                }
            },
            err => {
                console.log(err);
            }
        );
    }

    saveRole(role) {
        if (role.isNew) {
            this.roleService.createRole(role.data).subscribe(
                resp => {
                    this.roleService.announceRoleDataChange({status: 'success', isNew: true});
                    this.refreshPage();
                },
                err => {
                    this.roleService.announceRoleDataChange({status: 'error', isNew: true});
                });
        } else {
            this.roleService.updateRole(role.data, role.id).subscribe(
                resp => {
                    this.roleService.announceRoleDataChange({status: 'success', isNew: false});
                    this.refreshPage();
                },
                err => {
                    this.roleService.announceRoleDataChange({status: 'error', isNew: false});
                });
        }
    }

    addNewRole() {
        this.roleFormModal.openModal(new Role(), true);
    }

    editRole(role: Role) {
        this.roleFormModal.openModal(role, false);
    }

    gotoPage(page: number) {
        this.queryObject.page = page;
        this.isLoading = true;

        let navigationExtras: NavigationExtras = {
            queryParams: this.queryObject
        };
        this.router.navigate(['settings', 'roles'], navigationExtras);
    }
}


