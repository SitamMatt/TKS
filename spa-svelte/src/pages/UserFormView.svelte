<script lang="ts">
    import TextField from "smelte/src/components/TextField";
    import Button from "smelte/src/components/Button";
    import Checkbox from "smelte/src/components/Checkbox";
    import Select from "smelte/src/components/Select";
    import { acquireToken } from "../stores/auth-store";
    import { navigate } from "svelte-navigator";
    import {
        editUser,
        getUser,
        loadUsers,
        saveUser,
    } from "../stores/user-store";
    import type { User } from "../types/user";
    import { onMount } from "svelte";

    const label = "User Type";
    const items = [
        { value: "CLIENT", text: "CLIENT" },
        { value: "ADMIN", text: "ADMIN" },
        { value: "WORKER", text: "WORKER" },
    ];

    let login = "";
    let password = "";
    let firstname = "";
    let lastname = "";
    let role = "";
    let active = false;
    let onlyEdit = false;

    export let guid = null;

    onMount(async () => {
        console.log("Mounted");
        if (guid != null) {
            onlyEdit = true;
            let user: User = await getUser(guid);
            login = user.login;
            firstname = user.firstname;
            lastname = user.lastname;
            role = user.role;
            active = user.active;
        }
    });

    const save = async () => {
        if (onlyEdit) {
            editUser({
                active: active,
                firstname: firstname,
                lastname: lastname,
                login: login,
                guid: guid.toString(),
                password: password,
                role: role,
            });
        } else {
            saveUser({
                guid: null,
                active: active,
                firstname: firstname,
                lastname: lastname,
                login: login,
                password: password,
                role: role,
            });
        }
        await loadUsers();
        navigate("/dashboard/users");
    };
</script>

<main>
    <div>Dane oznaczone znakiem '*' są obowiązkowe.</div>
    <TextField label="Login*" bind:value={login} disabled={onlyEdit} />
    <TextField label="Password*" bind:value={password} type="password" />
    <TextField label="First name" bind:value={firstname} />
    <TextField label="Last name" bind:value={lastname} />
    <Select {label} {items} bind:value={role} />
    <Checkbox label="Active" bind:checked={active} />
    <Button on:click={save}>Save</Button>
</main>
