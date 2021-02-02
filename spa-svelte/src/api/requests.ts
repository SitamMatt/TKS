import type { Token } from "../types/token";
import type {User, UserEdit} from "../types/user";

const authUrl = "https://localhost:8181/pas-rest-1.0-SNAPSHOT/api/auth/login"
const refreshUrl = "https://localhost:8181/pas-rest-1.0-SNAPSHOT/api/auth/refresh"
const usersUrl = "https://localhost:8181/pas-rest-1.0-SNAPSHOT/api/users"
const saveUrl = "https://localhost:8181/pas-rest-1.0-SNAPSHOT/api/users"

function prepareRequest(method: string, token?: string, body?: any){
    let options: RequestInit = {
        method: method,
        mode: 'cors',
        referrerPolicy: 'unsafe-url'
    }
    let headers: HeadersInit = {}
    if(token){
        headers["Authorization"] = `Bearer ${token}` 
    }
    if(method === 'POST' || method === 'PUT'){
        headers["Content-Type"] = 'application/json'
    }
    if(body){
        options.body = JSON.stringify(body)
    }
    options.headers = headers
    return options
}


export const requestToken = async (login: string, password: string) => {
    let options = prepareRequest('POST', null, {
        login,
        password
    })
    let response = await fetch(authUrl, options)
    let token = await response.json() as Token
    return token;
}

export const requestTokenRefresh = async (refreshToken: string) => {
    let options = prepareRequest('GET', refreshToken)
    let response = await fetch(refreshUrl, options)
    let token = await response.json() as Token
    return token;
}

export const requestUsers = async (token: string) => {
    let options = prepareRequest('GET', token)
    let response = await fetch(usersUrl, options)
    let data = await response.json() as User[];
    return data;
}

export const requestUserAdd = async (token: string, user: UserEdit) => {
    let options = prepareRequest('POST', token, user)
    let response = await fetch(saveUrl, options);
    console.log("userAdd | Response status: " + response.status);
}

export const requestUserUpdate = async (token: string, etag: string, user: User) => {
    console.log("etag: " + etag);
    let options = prepareRequest('PUT', token, user);
    options.headers["If-Match"] = etag
    let response = await fetch(saveUrl + "/" + user.guid, options);
    console.log("userUpdate | Response status: " + response.status);
}

export const requestUser = async (token: string, guid: string) => {
    let options = prepareRequest('GET', token)
    let response = await fetch(usersUrl + "/" + guid, options)
    let data = await response.json() as User;
    let etag = response.headers.get("ETag");
    console.log("requestUser | response: " + response.status + ", Etag: " + etag);
    etag = etag.substring(1, etag.length - 1)
    return [data, etag];
}

export const requestMyInfo = async (token: string) => {
    let options = prepareRequest('GET', token);
    let response = await fetch(usersUrl + "/me", options)
    let data = await response.json() as User;
    let etag = response.headers.get("ETag");
    console.log("requestUser | response: " + response.status + ", Etag: " + etag);
    etag = etag.substring(1, etag.length - 1)
    return [data, etag];
}