import { environment } from '../environments/environment';

export class AppSettings {
    public static get DOMAIN(): string {
        return environment.production ? '' : 'http://localhost:8080';
    }
    public static get API_ENDPOINT(): string {
        return `${this.DOMAIN}/api/`;
    }

    public static get DATE_FORMAT(): string {
        return 'yyyy-MM-dd';
    }

    public static get ERROR_DELAY(): number {
        return 5000;
    }

    public static get SUCCESS_DELAY(): number {
        return 2000;
    }

    public static get PERMISSION_NAMES(): object {
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
            READ_ALERTS: 'READ_ALERTS'
        };
    }
}

