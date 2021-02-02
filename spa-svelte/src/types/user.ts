export interface User {
    guid: string;
    active: boolean;
    firstname: string;
    lastname: string;
    login: string;
    role: string;
}

export interface UserEdit extends User {
    password: string;
}