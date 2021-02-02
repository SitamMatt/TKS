<script lang="ts">
    import { navigate, Route, Router } from "svelte-navigator";
    import UsersView from "./UsersView.svelte";
    import UserFormView from "./UserFormView.svelte";
    import { getAccountInfo } from "../stores/user-store";
    import { acquireToken, logout } from "../stores/auth-store";
    import Button from "smelte/src/components/Button";
    import type { User } from "../types/user";

    let user: User = null;
    let username = "Samurai";

    getAccountInfo().then((u) => {
        user = u;
        username = u.firstname;
    });

    const doLogout = () => {
        logout();
        navigate("/");
    };
</script>

<Router>
    <h3>Welcome, {username}</h3>
    <div class="m-2">
        <Button on:click={doLogout}>Logout</Button>
    </div>

    {#if user?.role == "ADMIN"}
        <Route path="/" component={UsersView} />
        <Route path="/users" component={UsersView} />
        <Route path="/users/new" component={UserFormView} />
        <Route path="/users/:id" let:params>
            <UserFormView guid={params.id} />
        </Route>
    {/if}
</Router>
