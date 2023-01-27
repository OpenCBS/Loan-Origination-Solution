"use strict";
var AppSettings = (function () {
    function AppSettings() {
    }
    Object.defineProperty(AppSettings, "DOMAIN", {
        get: function () {
            return process.env.ENV === 'build' ? '' : 'http://localhost:8080';
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(AppSettings, "API_ENDPOINT", {
        get: function () {
            return this.DOMAIN + "/api/";
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(AppSettings, "DATE_FORMAT", {
        get: function () {
            return 'yyyy-MM-dd';
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(AppSettings, "ERROR_DELAY", {
        get: function () {
            return 5000;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(AppSettings, "SUCCESS_DELAY", {
        get: function () {
            return 2000;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(AppSettings, "PERMISSION_NAMES", {
        get: function () {
            return {
                ALL: 'ALL',
                READ_PROFILE: 'READ_PROFILE',
                CREATE_PROFILE: 'CREATE_PROFILE',
                EDIT_PROFILE: 'EDIT_PROFILE',
                DELETE_PROFILE: 'DELETE_PROFILE',
                READ_APPLICATION: 'READ_APPLICATION',
                CREATE_APPLICATION: 'CREATE_APPLICATION',
                EDIT_APPLICATION: 'EDIT_APPLICATION',
                READ_APPLICATION_FIELDS: 'READ_APPLICATION_FIELDS',
                EDIT_APPLICATION_FIELDS: 'EDIT_APPLICATION_FIELDS',
                READ_APPLICATION_PERMISSIONS: 'READ_APPLICATION_PERMISSIONS',
                EDIT_APPLICATION_PERMISSIONS: 'EDIT_APPLICATION_PERMISSIONS',
                READ_APPLICATION_ATTACHMENTS: 'READ_APPLICATION_ATTACHMENTS',
                DELETE_APPLICATION_ATTACHMENTS: 'DELETE_APPLICATION_ATTACHMENTS',
                CREATE_APPLICATION_ATTACHMENTS: 'CREATE_APPLICATION_ATTACHMENTS',
                READ_APPLICATION_WORKLOGS: 'READ_APPLICATION_WORKLOGS',
                CREATE_APPLICATION_WORKLOGS: 'CREATE_APPLICATION_WORKLOGS',
                READ_USER: 'READ_USER',
                CREATE_USER: 'CREATE_USER',
                EDIT_USER: 'EDIT_USER',
                RESET_USER_PASSWORD: 'RESET_USER_PASSWORD',
                READ_ROLE: 'READ_ROLE',
                CREATE_ROLE: 'CREATE_ROLE',
                EDIT_ROLE: 'EDIT_ROLE',
            };
        },
        enumerable: true,
        configurable: true
    });
    return AppSettings;
}());
exports.AppSettings = AppSettings;
