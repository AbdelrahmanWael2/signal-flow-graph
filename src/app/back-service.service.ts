import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Edge, givens, result} from "./myFactory";

@Injectable({
    providedIn: 'root'
})
export class BackServiceService {

    constructor(private http: HttpClient) { }
    request(myGivens: givens): Observable<result> {
        return this.http.post<result>("http://localhost:8080/request", myGivens);
    }

    receiveResult(impression: string): Observable<result>{
        return this.http.post<result>("http://localhost:8080/sendResult", impression);
    }
}
