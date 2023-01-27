export class Event {
    title: string;
    description: string;
    start: string;
    end: string;
    notify: string;
    allDay = true;
    participants: Object[];
    createdUser: any;
    id: number;
}
