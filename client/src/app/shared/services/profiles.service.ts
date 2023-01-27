import { map, delay } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';


@Injectable()
export class ProfileService {

  constructor(private apiService: ApiService) {
  }

  getPeople(query?): Observable<any> {
    let url = '?';
    if (query) {
      for (let key in query) {
        if (key === 'page') {
          let page = query[key];
          url = url + key + '=' + --page + '&';
        } else {
          url = url + key + '=' + query[key] + '&';
        }
      }
    }
    return this.apiService.get(`profiles/${url.slice(0, -1)}`).pipe(
      delay(200),
      map(
        data => data
      )
    );
  }

  getProfileFields(): Observable<any> {
    return this.apiService.get(`profiles/fields`).pipe(delay(200));
  }

  getProfileInfo(id): Observable<any> {
    return this.apiService.get(`profiles/${id}`).pipe(delay(200));
  }

  createProfile(data) {
    return this.apiService.post('profiles', data).pipe(delay(200));
  }

  updateProfile(data, id) {
    return this.apiService.put(`profiles/${id}`, data).pipe(delay(200));
  }

  deleteProfile(id) {
    return this.apiService.delete(`profiles/${id}`).pipe(delay(200));
  }

  getAttachment(data) {
    let url = `profiles/${data.profileId}/${data.value.sectionCode}/${data.value.code}/attachment`;
    return this.apiService.getAttachmentFile(url).pipe(delay(200));
  }
}
