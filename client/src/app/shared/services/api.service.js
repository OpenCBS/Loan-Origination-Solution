"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require('@angular/core');
var app_settings_1 = require('../../app.settings');
var http_1 = require('@angular/http');
var Rx_1 = require('rxjs/Rx');
require('rxjs/add/operator/map');
require('rxjs/add/operator/catch');
var ApiService = (function () {
    function ApiService(http, jwtService) {
        this.http = http;
        this.jwtService = jwtService;
    }
    ApiService.prototype.setHeaders = function () {
        var headersConfig = {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        };
        if (this.jwtService.getToken()) {
            headersConfig['Authorization'] = "Bearer " + this.jwtService.getToken();
        }
        return new http_1.Headers(headersConfig);
    };
    ApiService.prototype.formatErrors = function (error, caught) {
        console.log('this is error', caught);
        return Rx_1.Observable.throw(error.json());
    };
    ApiService.prototype.get = function (path, params) {
        if (params === void 0) { params = new http_1.URLSearchParams(); }
        return this.http.get("" + app_settings_1.AppSettings.API_ENDPOINT + path, { headers: this.setHeaders(), search: params })
            .catch(this.formatErrors)
            .map(function (res) { return res.json(); });
    };
    ApiService.prototype.getBlob = function (path, params) {
        if (params === void 0) { params = new http_1.URLSearchParams(); }
        return this.http.get("" + app_settings_1.AppSettings.API_ENDPOINT + path, {
            headers: this.setHeaders(),
            search: params,
            responseType: http_1.ResponseContentType.Blob
        });
    };
    ApiService.prototype.put = function (path, body) {
        if (body === void 0) { body = {}; }
        return this.http.put("" + app_settings_1.AppSettings.API_ENDPOINT + path, JSON.stringify(body), { headers: this.setHeaders() })
            .catch(this.formatErrors)
            .map(function (res) { return res.json(); });
    };
    ApiService.prototype.post = function (path, body) {
        if (body === void 0) { body = {}; }
        return this.http.post("" + app_settings_1.AppSettings.API_ENDPOINT + path, JSON.stringify(body), { headers: this.setHeaders() })
            .catch(this.formatErrors)
            .map(function (res) { return res.json(); });
    };
    ApiService.prototype.delete = function (path) {
        return this.http.delete("" + app_settings_1.AppSettings.API_ENDPOINT + path, { headers: this.setHeaders() })
            .catch(this.formatErrors)
            .map(function (res) { return res.json(); });
    };
    ApiService = __decorate([
        core_1.Injectable()
    ], ApiService);
    return ApiService;
}());
exports.ApiService = ApiService;
