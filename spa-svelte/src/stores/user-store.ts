import { writable } from "svelte/store";
import {requestUserAdd, requestUsers} from "../api/requests";
import type {User, UserEdit} from "../types/user";
import { getToken } from "./auth-store";

export const usersStore = writable<User[]>([])

export const loadUsers = async () => {
    let token = await getToken()
    let data = await requestUsers(token)
    usersStore.set(data)
}

export const saveUsers = async (user: UserEdit) => {
    let token = await getToken()
    await requestUserAdd(token, user)
}