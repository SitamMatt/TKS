<script lang="ts">
    import { Route, Router } from "svelte-navigator";
    import UsersView from "./UsersView.svelte";
    import UserFormView from "./UserFormView.svelte";
</script>

<Router>
    <h3>Welcome Samurai</h3>
    <Route path="/" component={UsersView} />
    <Route path="/users/:id" let:params>
        <UserFormView guid={params.id} />
    </Route>
</Router>
