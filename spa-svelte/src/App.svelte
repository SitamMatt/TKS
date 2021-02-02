<script lang="ts">
    import { Router, Link, Route } from "svelte-navigator";
    import Login from "./pages/Login.svelte";
    import DashboardView from "./pages/DashboardView.svelte";
    import { tokenRefreshed } from "./stores/auth-store";
    import { Notifications, notifier } from "smelte/src/components/Snackbar";
    import UserFormView from "./pages/UserFormView.svelte";

    tokenRefreshed.subscribe((val) => {
        console.log(val);
        if (val) notifier.notify("Token has been refreshed");
    });
</script>

<Router>
    <Route path="/" component={Login} />
    <Route path="/dashboard/*" component={DashboardView} />
    <Notifications />
</Router>
