<script lang="ts">
    import { Router, Link, Route, navigate } from "svelte-navigator";
    import Login from "./pages/Login.svelte";
    import DashboardView from "./pages/DashboardView.svelte";
    import { getToken, tokenRefreshed } from "./stores/auth-store";
    import { Notifications, notifier } from "smelte/src/components/Snackbar";
    import UserFormView from "./pages/UserFormView.svelte";
    import { onMount } from "svelte";

    tokenRefreshed.subscribe((val) => {
        console.log(val);
        if (val) notifier.notify("Token has been refreshed");
    });

    onMount(async () => {
        let token = await getToken();
        if (token !== undefined) {
            navigate("/dashboard");
        }
    });
</script>

<Router>
    <Route path="/" component={Login} />
    <Route path="/dashboard/*" component={DashboardView} />
    <Notifications />
</Router>
