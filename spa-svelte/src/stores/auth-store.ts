import axios from "axios";
import { writable } from "svelte/store";

const authUrl = "https://localhost:8181/pas-rest-1.0-SNAPSHOT/api/auth/login"

export const acquireToken = async (login: string, password: string) => {
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
    let json = await response.json() as Token
    return json;
}

export const getToken = async () => {
    let tokenResponse = await acquireToken("admin", "admin0");
    token.set(tokenResponse);
}

interface Token{
    token: string;
    expires: Date;
}

export const token = writable<Token>(null)