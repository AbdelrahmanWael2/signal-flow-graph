import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Edge, result} from "./myFactory";

@Injectable({
    providedIn: 'root'
})
export class BackServiceService {

    constructor(private http: HttpClient) { }
    request(listOfLists: Edge[][]): Observable<result> {
        return this.http.post<result>("http://localhost:8080/request", listOfLists);
    }

    receiveResult(impression: string): Observable<result>{
        return this.http.post<result>("http://localhost:8080/sendResult", impression);
    }
}
