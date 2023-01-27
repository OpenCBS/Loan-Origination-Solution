/**
 * Created by Chyngyz on 4/20/2017.
 */
import { Injectable } from '@angular/core';
import { ApiService } from './api.service';

@Injectable()
export class EventService {

    constructor(private apiService: ApiService) {
    }

    getEvents(date: string, userId: number, monthNum?: number) {
        return this.apiService.get(`events/participant/${userId}/date/${date}${monthNum ? '?monthNum=' + monthNum : ''}`);
    }

    createEvent(event) {
        return this.apiService.post('events', event);
    }

    updateEvent(event) {
        return this.apiService.put('events', event);
    }
}
