import axios from "axios";
import { get, writable } from "svelte/store";
import { requestToken, requestTokenRefresh } from "../api/requests";
import type { Token } from "../types/token";

export const tokenStore = writable<Token>(null)

export const tokenRefreshed = writable<boolean>(false)

export const acquireToken = async (login: string, password: string) => {
    let token = await requestToken(login, password);
    tokenStore.set(token)
    localStorage.setItem("token", JSON.stringify(token))
}

export const logout = async () => {
    tokenStore.set(null);
    localStorage.removeItem("token")
}



export const getToken = async () => {
    let tokenObj = get(tokenStore);
    console.log("Token: "+tokenObj)
    if(tokenObj === null){
        // force to acquire token
        tokenObj = JSON.parse(localStorage.getItem("token")) as Token
            console.log("Token in store: "+tokenObj)

        if(tokenObj == null){
            console.log("Token is null");
            return;
        }
    }
    // check if token expired
    let date = new Date();
    let now = new Date()
    date.setSeconds(date.getSeconds() + 30)
    let tokenRefreshExpirationDate = new Date(tokenObj.expires)
    console.log(date)
    console.log(tokenRefreshExpirationDate)
    console.log(now)
    if(tokenRefreshExpirationDate < now){
        console.log("token expired")
        return;
    }
    else if(tokenRefreshExpirationDate < date){
        console.log("refreshing token")
        tokenRefreshed.set(true)
        let token = await requestTokenRefresh(tokenObj.token)
        tokenStore.set(token)
        tokenObj = get(tokenStore)
        tokenRefreshed.set(false)
    }
    return tokenObj.token;
}