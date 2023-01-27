import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ApplicationService } from '../../shared';
import { ManageEventModalComponent } from '../../shared/ui-elements/manage-event-popup/manage-event-modal.component';
import { UserService } from '../../shared/services/user.service';

declare var moment: any;

@Component({
    selector: 'cbs-application-events',
    templateUrl: 'application-events.component.html'
})
export class ApplicationEventsComponent implements OnInit, OnDestroy {
    public eventsData: any[] = [];
    private sub: any;
    private applicationId: number;
    public isLoading = true;
    public noData = false;
    public pagerData: any = {};
    public currentPage: any = {};
    public isEventSelected = false;
    public selectedEvent: any = null;
    public currentUser = null;
    public applicationData = null;
    @ViewChild(ManageEventModalComponent, {static: false}) private manageEventComponent: ManageEventModalComponent;

    constructor(private route: ActivatedRoute,
                private applicationService: ApplicationService,
                private userService: UserService) {
    }

    ngOnInit() {
        this.currentUser = this.userService.getCurrentUser();

        this.sub = this.route.parent.params.subscribe(params => {
            this.applicationId = +params['id'];
            this.getApplicationInfo();
            this.getEvents();
        });
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }

    private getDate(event) {
        return event.start + ' - ' + event.end;
    }

    public displayEventInfo(data) {
        let event = data.data;

        event.start = moment(event.start);
        event.end = moment(event.end);
        event.notify = moment(event.notify);
        this.manageEventComponent.displayEvent(event);
    }

    private getEvents() {
        this.applicationService.getApplicationEvents(this.applicationId).subscribe(
            data => {
                if (data.content.length) {
                    this.noData = false;
                    this.isLoading = false;
                    this.eventsData = data.content.map(event => {
                        return {
                            title: event.title,
                            participants: event.participants.map(participant => participant.name).join(', '),
                            date: this.getDate(event),
                            description: event.description,
                            data: event
                        };
                        });

                    this.pagerData.size = data.size;
                    this.pagerData.total = data.totalElements;
                    this.currentPage = data.number + 1;
                } else {
                    this.isLoading = false;
                    this.noData = true;
                }
            },
            err => {
                this.isLoading = false;
                this.noData = true;
            }
        );
    }

    onEventChanged() {
        this.getEvents();
    }

    addEvent() {
        let date = moment();
        let shouldShowTime = false;

        this.manageEventComponent.createEvent(date, shouldShowTime);
        let profile = this.applicationData.profile;

        let selectedUser = {
                id: profile.id,
                name: profile.fullName,
                isUser: false,
                email: profile.email,
                applicationId: this.applicationData.id
            };

        this.manageEventComponent.addParticipant(selectedUser, true);
    }

    getApplicationInfo() {
        this.applicationService.getApplicationDetails(this.applicationId).subscribe(
            resp => {
                this.applicationData = resp.data;
            },
            err => {
            }
        );
    }

}

