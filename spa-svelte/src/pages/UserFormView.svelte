<script lang="ts">
    import TextField from "smelte/src/components/TextField";
    import { Button, Checkbox, Select } from "smelte";
    import { acquireToken } from "../stores/auth-store";
    import { navigate } from "svelte-navigator";
    import {editUser, getUser, loadUsers, saveUser} from "../stores/user-store";
    import type {User, UserEdit} from "../types/user";

    const label = "User Type";
    const items = [
        { value: 1, text: "CLIENT" },
        { value: 2, text: "ADMIN" },
        { value: 3, text: "WORKER" },
    ];

    let login = "";
    let password = "";
    let firstname = "";
    let lastname = "";
    let role = "CLIENT";
    let active = true;

    export let guid = null;

    let onlyEdit = false;
    if (guid != null) {
        onlyEdit = true;
        acquireToken("admin", "admin0").then(async () => {
            let user: User = await getUser(guid);
            login = user.login;
            firstname = user.firstname;
            lastname = user.lastname;
            role = user.role;
            active = user.active;
        });
    }



    const save = async () => {
        acquireToken("admin", "admin0").then(() => {
            let user: UserEdit = {
                active: active,
                firstname: firstname,
                lastname: lastname,
                login: login,
                password: password,
                role: role,
            };
            if(onlyEdit) {
                editUser(guid, user);
            } else {
                saveUser(user);
            }
        });
        await loadUsers();
        navigate(-1);
    };
</script>

<main>
    <h1>New User Form</h1>
    <div>Dane oznaczone znakiem '*' są obowiązkowe.</div>
    <TextField label="Login*" bind:value={login}/>
    <TextField label="Password*" bind:value={password} type="password" />
    <TextField label="First name" bind:value={firstname} />
    <TextField label="Last name" bind:value={lastname} />
    <Select
        {label}
        {items}
        on:change={(v) => (role = items[v.detail - 1].text)}/>
    <Checkbox label="Active" bind:value={active} checked="{active}" />
    <Button on:click={save}>Save</Button>
</main>
