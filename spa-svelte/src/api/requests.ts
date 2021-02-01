import type { Token } from "../types/token";
import type {User, UserEdit} from "../types/user";

const authUrl = "https://localhost:8181/pas-rest-1.0-SNAPSHOT/api/auth/login"
const refreshUrl = "https://localhost:8181/pas-rest-1.0-SNAPSHOT/api/auth/refresh"
const usersUrl = "https://localhost:8181/pas-rest-1.0-SNAPSHOT/api/users"
const saveUrl = "https://localhost:8181/pas-rest-1.0-SNAPSHOT/api/users"


export const requestToken = async (login: string, password: string) => {
    let response = await fetch(authUrl, {
        body: JSON.stringify({
            login,
            password
        }),
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        mode: 'cors',
        referrerPolicy: 'unsafe-url'
    })
    let token = await response.json() as Token
    return token;
}

export const requestTokenRefresh = async (refreshToken: string) => {
    let response = await fetch(refreshUrl, {
        method: 'GET',
        headers:{
            "Authorization": "Bearer " + refreshToken
        },
        mode: 'cors',
        referrerPolicy: 'unsafe-url'
    })
    let token = await response.json() as Token
    return token;
}

export const requestUsers = async (token: string) => {
    let response = await fetch(usersUrl, {
        method: 'GET',
        mode: 'cors',
        headers:{
            "Authorization": "Bearer " + token
        },
        referrerPolicy: 'unsafe-url'
    })
    let data = await response.json() as User[];
    return data;
}

export const requestUserAdd = async (token: string, user: UserEdit) => {
    let response = await fetch(saveUrl, {
        method: 'POST',
        mode: 'cors',
        headers: {
            "Authorization": "Bearer " + token,
            'Content-Type': 'application/json'
        },
        referrerPolicy: 'unsafe-url',
        body: JSON.stringify(user)
    });
    console.log("userAdd | Response status: " + response.status);
}

export const requestUserUpdate = async (token: string, etag: string, user: User) => {
    console.log("etag: " + etag);
    let response = await fetch(saveUrl + "/" + user.guid, {
        method: 'PUT',
        mode: 'cors',
        headers: {
            "Authorization": "Bearer " + token,
            'Content-Type': 'application/json',
            'If-Match': etag,
        },
        referrerPolicy: 'unsafe-url',
        body: JSON.stringify(user)
    });
    console.log("userUpdate | Response status: " + response.status);
}

export const requestUser = async (token: string, guid: string) => {
    let response = await fetch(usersUrl + "/" + guid, {
        method: 'GET',
        mode: 'cors',
        headers:{
            "Authorization": "Bearer " + token,
        },
        referrerPolicy: 'unsafe-url'
    })
    let data = await response.json() as User;
    let etag = response.headers.get("ETag");
    console.log("requestUser | response: " + response.status + ", Etag: " + etag);
    etag = etag.substring(1, etag.length - 1)
    return [data, etag];
}