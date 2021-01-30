import { writable } from "svelte/store";
import type { User } from "../types/user";
import { acquireToken } from "./auth-store";

const usersUrl = "https://localhost:8181/pas-rest-1.0-SNAPSHOT/api/users"

export const getUsers = async () => {
    let token = await acquireToken("admin", "admin0")
    let response = await fetch(usersUrl, {
        method: 'GET',
        mode: 'cors',
        headers:{
            "Authorization": "Bearer " + token.token
        },
        referrerPolicy: 'unsafe-url'
    })
    let data = await response.json() as User[];
    users.set(data)
}

export const users = writable<User[]>([])