import { Injectable, EventEmitter } from '@angular/core';

@Injectable()
export class NglTreeRegistrationService {
  registerEvent = new EventEmitter(false);
  unregisterEvent = new EventEmitter(false);

  register(child: any) {
    this.registerEvent.emit(child);
  }

  unregister(child: any) {
    this.unregisterEvent.emit(child);
  }
}
