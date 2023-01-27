import { throwError as observableThrowError, Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { AppSettings } from '../../app.settings';
import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';


import { JwtService } from './jwt.service';

@Injectable()
export class ApiService {
  constructor(private httpClient: HttpClient,
              private jwtService: JwtService) {
  }

  private setHeaders(): HttpHeaders {
    const headersConfig = {
      'Content-Type': 'application/json',
      Accept: 'application/json'
    };

    if (this.jwtService.getToken()) {
      headersConfig['Authorization'] = `Bearer ${this.jwtService.getToken()}`;
    }
    return new HttpHeaders(headersConfig);
  }

  private formatErrors(error: any, caught: Observable<any>) {
    console.log('this is error', caught);
    return observableThrowError(error.json());
  }

  get(path: string, params: HttpParams = new HttpParams()): Observable<any> {
    return this.httpClient.get(
      `${AppSettings.API_ENDPOINT}${path}`,
      {responseType: 'json', headers: this.setHeaders(), params}
    );
  }

  getAttachmentFile(path: string, params: HttpParams = new HttpParams()): Observable<any> {
    return this.httpClient.get(
      `${AppSettings.API_ENDPOINT}${path}`,
      {responseType: 'blob', headers: this.setHeaders(), params}
    );
  }


  getBlob(path: string, params: HttpParams = new HttpParams()): Observable<any> {
    return this.httpClient.get(`${AppSettings.API_ENDPOINT}${path}`,
      {
        headers: this.setHeaders(),
        params,
        responseType: 'blob'
      });
  }

  put(path: string, body: object = {}): Observable<any> {
    return this.httpClient.put(
      `${AppSettings.API_ENDPOINT}${path}`,
      JSON.stringify(body),
      {headers: this.setHeaders(), responseType: 'json'}
    );
  }

  post(path: string, body: object = {}): Observable<any> {
    return this.httpClient.post(
      `${AppSettings.API_ENDPOINT}${path}`,
      JSON.stringify(body),
      {headers: this.setHeaders(), responseType: 'json'});
  }

  delete(path: string, body: any = {}): Observable<any> {
    return this.httpClient.request('delete',
      `${AppSettings.API_ENDPOINT}${path}`,
      {headers: this.setHeaders(), body: body}
    );
  }
}
