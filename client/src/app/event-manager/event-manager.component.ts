import { Component, OnInit, ViewChild } from '@angular/core';

import { UserService, EventService } from '../shared/services';
import { Event } from '../shared/models/event.modal';
import { ManageEventModalComponent } from '../shared/ui-elements/manage-event-popup/manage-event-modal.component';

declare var moment: any;

@Component({
    selector: 'cbs-event-manager',
    templateUrl: 'event-manager.component.html',
    styleUrls: ['event-manager.component.scss']
})
export class EventManagerComponent implements OnInit {
    public events: any[] = [];
    public scheduleConfigHeader: any;
    @ViewChild(ManageEventModalComponent, {static: false}) private manageEventComponent: ManageEventModalComponent;
    public calendarExtraOptions = {
        views: {
            agenda: {
                timeFormat: 'HH:mm',
                slotLabelFormat: 'HH:mm'
            }
        }
    };

    private currentUser: any;
    private monthsArray = [];
    private currentMonth: any;
    private isFirstGetEvents = true;

    constructor(private userService: UserService,
                private eventService: EventService) {
    }

    ngOnInit() {
        this.scheduleConfigHeader = {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay, listMonth'
        };

        this.currentUser = this.userService.getCurrentUser();
    }

    getEvents(date, renew?, monthNum = 1) {
        if (this.monthsArray.indexOf(date) === -1 || renew) {
            this.eventService.getEvents(`${date}-01`, this.currentUser.id, monthNum).subscribe(
                resp => {
                    if (renew) {
                        this.events = [];
                        this.monthsArray = [];
                    }

                    if (resp.data) {
                        this.monthsArray.push(date);
                        if (monthNum === 3) {
                            const prevMonth = moment(`${date}-01`).subtract(1, 'months').format('YYYY-MM');
                            const nextMonth = moment(`${date}-01`).add(1, 'months').format('YYYY-MM');
                            this.monthsArray.push(prevMonth, nextMonth);
                        }

                        if (resp.data.length) {
                            resp.data.map(item => {
                                this.events.push(this.formatEvent(item));
                            });
                        }
                    }
                },
                err => {
                    console.log(err);
                });
        }
    }

    formatEvent(event: Event) {
        const obj = new Event();
        obj.title = event.title;
        obj.description = event.description;
        obj.start = moment(event.start).format();
        obj.end = moment(event.end).format();
        obj.allDay = event.allDay;
        obj.participants = event.participants;
        obj.createdUser = event.createdUser;
        obj.id = event.id;
        obj.notify = moment(event.notify).format();
        return obj;
    }

    handleDayClick(event) {
        this.manageEventComponent.createEvent(event.date, this.shouldShowTime(event));
    }

    handleEventClick(e) {
        this.manageEventComponent.viewEvent(e);
    }

    onViewRender(data) {
        if (data && data.view) {
            console.log(data);
            const currentMonth = this.getDate(data.view.start);

            this.currentMonth = data.view.start;
            if (this.isFirstGetEvents) {
                this.getEvents(currentMonth.slice(0, -3), true, 3);
                this.isFirstGetEvents = false;
            } else {
                this.getEvents(currentMonth.slice(0, -3));
            }
        }
    }

    private getDate(date) {
        return date.format('YYYY-MM-DD');
    }
    onEventChanged(data) {
        this.getEvents(data, true, 3);
    }


    shouldShowTime(event): boolean {
        return event.view && event.view.type === 'agendaWeek' ||
            event.view && event.view.type === 'agendaDay';
    }
}

