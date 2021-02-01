import type { Token } from "../types/token";
import type { User } from "../types/user";

const authUrl = "https://localhost:8181/pas-rest-1.0-SNAPSHOT/api/auth/login"
const refreshUrl = "https://localhost:8181/pas-rest-1.0-SNAPSHOT/api/auth/refresh"
const usersUrl = "https://localhost:8181/pas-rest-1.0-SNAPSHOT/api/users"


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