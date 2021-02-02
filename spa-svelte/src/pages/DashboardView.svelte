<script lang="ts">
    import { Route, Router } from "svelte-navigator";
    import UsersView from "./UsersView.svelte";
    import UserFormView from "./UserFormView.svelte";
    import { getAccountInfo } from "../stores/user-store";
    import { acquireToken } from "../stores/auth-store";

    let username = "Samurai";

    getAccountInfo().then((u) => (username = u.firstname));
</script>

<Router>
    <h3>Welcome, {username}</h3>
    <Route path="/" component={UsersView} />
    <Route path="/users" component={UsersView} />
    <Route path="/users/new" component={UserFormView} />
    <Route path="/users/:id" let:params>
        <UserFormView guid={params.id} />
    </Route>
</Router>
