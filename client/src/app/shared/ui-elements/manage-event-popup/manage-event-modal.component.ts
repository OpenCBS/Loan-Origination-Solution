import { AfterViewInit, Component, ElementRef, EventEmitter, OnInit, Output, Renderer2, ViewChild } from '@angular/core';
import { FormLookupComponent } from '../../../sharedModules/cbs-dynamic-form/components/form-lookup/form-lookup.component';
import { AppSettings } from '../../../app.settings';
import { FormMode } from '../../models/formMode';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { EventService } from '../../services/event.service';
import { Event } from '../../models/event.modal';

declare var moment: any;

@Component({
    selector: 'cbs-manage-event-modal',
    templateUrl: 'manage-event-modal.component.html',
    styleUrls: ['manage-event-modal.component.scss']
})
export class ManageEventModalComponent implements OnInit, AfterViewInit {
    @ViewChild(FormLookupComponent, {static: false}) lookup: FormLookupComponent;
    @ViewChild('submitBtn', {static: false}) submitBtn: ElementRef;
    public isDialogVisible = false;
    public eventForm: FormGroup;
    public mode: FormMode = FormMode.create;
    private currentUser: any;
    public event: Event;
    public tempParticipants: any[] = [];
    public isResponseStatusOk = null;
    public formConfig = [
        {
            disabled: false,
            placeholder: 'Event title',
            name: 'title',
            caption: 'Title',
            fieldType: 'TEXT',
            required: true,
            value: ''
        },
        {
            disabled: false,
            placeholder: 'Description',
            name: 'description',
            caption: 'Description',
            fieldType: 'TEXT_AREA',
            required: true,
            value: ''
        },
        {
            disabled: false,
            placeholder: '',
            name: 'allDay',
            caption: 'All day event',
            fieldType: 'CHECKBOX',
            required: false,
            value: ''
        },
        {
            disabled: false,
            placeholder: '',
            name: 'startDate',
            caption: 'Start date',
            fieldType: 'DATE',
            required: false,
            value: ''
        },
        {
            disabled: false,
            placeholder: '',
            name: 'startTime',
            caption: 'Time',
            fieldType: 'TIME',
            required: false,
            value: ''
        },
        {
            disabled: false,
            placeholder: '',
            name: 'endDate',
            caption: 'End date',
            fieldType: 'DATE',
            required: false,
            value: ''
        },
        {
            disabled: false,
            placeholder: '',
            name: 'endTime',
            caption: 'Time',
            fieldType: 'TIME',
            required: false,
            value: ''
        },
        {
            disabled: false,
            placeholder: '',
            name: 'notifyDate',
            caption: 'Notify Date',
            fieldType: 'DATE',
            required: false,
            value: ''
        },
        {
            disabled: false,
            placeholder: '',
            name: 'notifyTime',
            caption: 'Time',
            fieldType: 'TIME',
            required: false,
            value: ''
        },
        {
            disabled: false,
            placeholder: '',
            name: 'participants',
            caption: 'Participants',
            fieldType: 'LOOKUP',
            required: true,
            value: '',
            extra: {
                url: `${AppSettings.API_ENDPOINT}events/participants`,
                scope: 'data'
            }
        }
    ];

    @Output() eventChanged = new EventEmitter();

    constructor(private userService: UserService,
                private eventService: EventService,
                private renderer: Renderer2) {
    }

    ngOnInit() {
        this.eventForm = new FormGroup({
            title: new FormControl('', [Validators.required]),
            description: new FormControl('', [Validators.required]),
            allDay: new FormControl(''),
            startDate: new FormControl('', [Validators.required]),
            startTime: new FormControl(''),
            endDate: new FormControl(''),
            endTime: new FormControl(''),
            notifyDate: new FormControl(''),
            notifyTime: new FormControl(''),
            participants: new FormControl('')
        });

        this.currentUser = this.userService.getCurrentUser();
    }

    ngAfterViewInit() {
        let allDayEventStatus = false;
        this.eventForm.valueChanges.subscribe(data => {
            if (data.allDay && !allDayEventStatus) {
                this.formConfig.map(field => {
                    if (field.name === 'startTime' || field.name === 'endTime') {
                        field.disabled = true;
                        field.value = '00:00';
                    }
                });
                allDayEventStatus = true;
            } else if (!data.allDay && allDayEventStatus) {
                this.formConfig.map(field => {
                    if (field.name === 'startTime' || field.name === 'endTime') {
                        field.disabled = false;
                    }
                });
                allDayEventStatus = false;
            }
        });
    }

    cancel() {
        this.isDialogVisible = false;
        this.eventForm.reset();
    }

    public createEvent(date, showTime) {
        this.eventForm.reset();
        let startDate = date.format('YYYY-MM-DD'),
            startTime, endTime, notifyTime;

        this.eventForm.controls['startDate'].setValue(startDate);
        this.eventForm.controls['endDate'].setValue(startDate);
        this.eventForm.controls['notifyDate'].setValue(startDate);

        if (showTime) {
            startTime = date.format('HH:mm');
            endTime = date.add(1, 'hours').format('HH:mm');
            notifyTime = date.add(-1, 'hours').format('HH:mm');
            this.eventForm.controls['startTime'].setValue(startTime);
            this.eventForm.controls['endTime'].setValue(endTime);
            this.eventForm.controls['notifyTime'].setValue(notifyTime);

        } else {
            this.eventForm.controls['startTime'].setValue('00:00');
            this.eventForm.controls['endTime'].setValue('00:00');
            this.eventForm.controls['notifyTime'].setValue('00:00');
        }

        this.applyDateAndTime(startDate, startTime, startDate, endTime, startDate, startTime, showTime);

        this.tempParticipants = [];
        this.mode = FormMode.create;
        this.isDialogVisible = true;
    }

    applyDateAndTime(startDate, startTime, endDate, endTime, notifyDate, notifyTime, showTime) {
        this.formConfig.map(field => {
            if (field.name === 'startDate') {
                field.value = startDate;
            }
            if (field.name === 'endDate') {
                field.value = endDate;
            }
            if (field.name === 'notifyDate') {
                field.value = notifyDate;
            }
            if (showTime) {
                if (field.name === 'startTime' && startTime) {
                    field.value = startTime;
                }
                if (field.name === 'endTime' && endTime) {
                    field.value = endTime;
                }
                if (field.name === 'notifyTime' && notifyTime) {
                    field.value = notifyTime;
                }
            } else {
                if (field.name === 'startTime' || field.name === 'endTime' || field.name === 'notifyTime') {
                    field.value = '';
                }
            }
        });
    }

    viewEvent(e) {
        this.event = new Event();
        this.event.title = e.calEvent.title;
        this.event.description = e.calEvent.description;
        this.event.start = e.calEvent.start;
        this.event.end = e.calEvent.end ? e.calEvent.end : e.calEvent.start;
        this.event.notify = moment(e.calEvent.notify);
        this.event.allDay = e.calEvent.allDay;
        this.event.participants = e.calEvent.participants;
        this.event.createdUser = e.calEvent.createdUser;
        this.event.id = e.calEvent.id;

        this.showForm();
    }

    displayEvent(event: Event) {
        this.event = event;
        this.showForm();
    }

    private showForm() {
        this.eventForm.reset();

        if (this.currentUser.id === this.event.createdUser.id) {
            this.mode = FormMode.edit;
            this.displayInEditMode();
        } else {
            this.mode = FormMode.view;
        }

        this.isDialogVisible = true;
    }

    displayInEditMode() {
        this.eventForm.controls['title'].setValue(this.event.title);
        this.eventForm.controls['description'].setValue(this.event.description);
        this.eventForm.controls['allDay'].setValue(this.event.allDay);

        let startDate = this.getDate(this.event.start);
        let startTime = this.getTime(this.event.start);
        let endDate = this.getDate(this.event.end);
        let endTime = this.getTime(this.event.end);
        let notifyDate = this.getDate(this.event.notify);
        let notifyTime = this.getTime(this.event.notify);
        this.eventForm.controls['startDate'].setValue(startDate);
        this.eventForm.controls['startTime'].setValue(startTime);
        this.eventForm.controls['endDate'].setValue(endDate);
        this.eventForm.controls['endTime'].setValue(endTime);
        this.eventForm.controls['notifyDate'].setValue(notifyDate);
        this.eventForm.controls['notifyTime'].setValue(notifyTime);
        if (this.event.participants) {
            this.event.participants.forEach(participant => {
                this.addParticipant(participant, false);
            });
        }
        this.applyDateAndTime(startDate, startTime, endDate, endTime, notifyDate, notifyTime, true);
    }

    disableSubmitBtn(bool) {
        this.renderer.setProperty(this.submitBtn.nativeElement, 'disabled', bool);
    }

    addParticipant(data, shouldClean?) {
        if (data && !this.tempParticipants.filter(item => item.name === data.name).length) {
            this.tempParticipants.push(data);
        }
        if (shouldClean) {
            this.lookup.clearLookupValue();
        }
        this.eventForm.controls['participants'].setValue(data.name);
    }

    removeParticipant(user) {
        this.tempParticipants.map((item, ind) => {
            if (item.id === user.id) {
                this.tempParticipants.splice(ind, 1);
            }
        });

        if (this.tempParticipants.length === 0) {
            this.lookup.clearLookupValue();
        }
    }

    submit({valid, value}) {
        if (!valid) {
            return;
        }
        this.disableSubmitBtn(true);

        let eventToSend = new Event();
        eventToSend.title = value.title;
        eventToSend.description = value.description;
        eventToSend.start = `${value.startDate} ${value.startTime ? value.startTime : '00:00'}:00`;
        eventToSend.end = `${value.endDate} ${value.endTime ? value.endTime : '00:00'}:00`;
        eventToSend.notify = `${value.notifyDate} ${value.notifyTime ? value.notifyTime : '00:00'}:00`;
        eventToSend.allDay = value.allDay || (value.startTime === '00:00' && value.endTime === '00:00');
        eventToSend.participants = [];

        this.tempParticipants.map(user => {
            eventToSend.participants.push({
                id: user.id,
                isUser: user.isUser,
                applicationId: user.applicationId
            });
        });

        let command;
        if (this.mode === FormMode.create) {
            command = this.eventService.createEvent;
            eventToSend.id = null;
        } else {
            command = this.eventService.updateEvent;
            eventToSend.id = this.event.id;
        }
        command.call(this.eventService, eventToSend).subscribe(
            res => {
                if (res.data) {
                    this.isResponseStatusOk = 'ok';
                    this.onEventSuccessfullyProcessed(value.startDate);
                    setTimeout(() => {
                        this.isDialogVisible = false;
                        this.isResponseStatusOk = 'null';
                        this.disableSubmitBtn(false);
                    }, AppSettings.SUCCESS_DELAY);
                } else if (res.status === 'error') {
                    this.isResponseStatusOk = 'error';
                    setTimeout(() => {
                        this.isResponseStatusOk = 'null';
                        this.disableSubmitBtn(false);
                    }, AppSettings.ERROR_DELAY);
                }
            },
            err => {
                this.isResponseStatusOk = 'error';
                setTimeout(() => {
                    this.isResponseStatusOk = 'null';
                }, AppSettings.ERROR_DELAY);
                this.disableSubmitBtn(false);
            }
        );
    }

    private getDate(date) {
        return date.format('YYYY-MM-DD');
    }

    private getTime(date) {
        return date.format('HH:mm');
    }

    onEventSuccessfullyProcessed(newDate) {
        let newDateRequest = newDate.slice(0, -3);

        if (this.mode === FormMode.edit) {
            let oldDateRequest = this.getDate(this.event.start).slice(0, -3);
            if (oldDateRequest !== newDateRequest) {
                this.eventChanged.emit(oldDateRequest);
                return;
            }
        }
        this.eventChanged.emit(newDateRequest);
    }

    public getEventHeader(): string {
        switch (this.mode) {
            case FormMode.edit:
                return 'Update event';
            case FormMode.view:
                return this.event.title;
            default:
                return 'Create new event';
        }
    }

    public canEditView(): boolean {
        return this.mode === FormMode.edit || this.mode === FormMode.create;
    }
}

