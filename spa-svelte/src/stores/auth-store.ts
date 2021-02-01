import axios from "axios";
import { get, writable } from "svelte/store";
import { requestToken, requestTokenRefresh } from "../api/requests";
import type { Token } from "../types/token";

export const tokenStore = writable<Token>(null)

export const tokenRefreshed = writable<boolean>(false)

export const acquireToken = async (login: string, password: string) => {
    let token = await requestToken(login, password);
    tokenStore.set(token)
}



export const getToken = async () => {
    let tokenObj = get(tokenStore);
    if(tokenObj == null){
        // force to acquire token
        console.log("Token is null");
        return;
    }
    // check if token expired
    let date = new Date();
    date.setSeconds(date.getSeconds() + 30)
    let tokenExpirationDate = new Date(tokenObj.expires)
    console.log(date)
    console.log(tokenExpirationDate)
    if(tokenExpirationDate < date){
        console.log("refreshing token")
        tokenRefreshed.set(true)
        let token = await requestTokenRefresh(tokenObj.token)
        tokenStore.set(token)
        tokenObj = get(tokenStore)
        tokenRefreshed.set(false)

        // setTimeout(() => {
        //     tokenRefreshed.set(false)
        // }, 2000);
    }
    return tokenObj.token;
}