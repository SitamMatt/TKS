import { writable } from "svelte/store";
import {requestUser, requestUserAdd, requestUsers, requestUserUpdate} from "../api/requests";
import type {User, UserEdit} from "../types/user";
import { getToken } from "./auth-store";

export const usersStore = writable<User[]>([])

export const loadUsers = async () => {
    let token = await getToken()
    let data = await requestUsers(token)
    usersStore.set(data)
}

export const saveUser = async (user: UserEdit) => {
    let token = await getToken()
    await requestUserAdd(token, user)
}

export const getUser = async (guid: string) => {
    let token = await getToken();
    let data = await requestUser(token, guid);
    return data[0] as User;
}

export const editUser = async (guid: string, user: UserEdit) => {
    let token = await getToken()
    let data = await requestUser(token, guid);
    await requestUserUpdate(token, data[1] as string, guid, user);
}