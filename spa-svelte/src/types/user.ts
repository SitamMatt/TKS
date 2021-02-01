export interface UserBase {
    active: boolean;
    firstname: string;
    lastname: string;
    login: string;
    role: string;
}

export interface User {
    guid: string;
}

export interface UserEdit extends UserBase {
    password: string;
}