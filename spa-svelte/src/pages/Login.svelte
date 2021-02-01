<script lang="ts">
    import Button from "smelte/src/components/Button";
    import TextField from "smelte/src/components/TextField";
    import { navigate } from "svelte-navigator";
    import { acquireToken } from "../stores/auth-store";

    let login: string = "";
    let password: string = "";

    const doLogin = async () => {
        console.log(login);
        await acquireToken(login, password);
        navigate("/dashboard");
    };
</script>

<main>
    <h1 class="text-center">Welcome</h1>
    <div class="flex flex-col container md:w-2/5 md:mx-auto sm:mx-2">
        <TextField label="Login" bind:value={login} />
        <TextField label="Password" type="password" bind:value={password} />
        <Button on:click={doLogin}>Login</Button>
    </div>
</main>

<style>
    /* .container {
        margin-left: 50px;
        margin-right: 50px;
    } */
</style>
