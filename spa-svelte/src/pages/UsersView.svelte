<script lang="ts">
    import DataTable from "smelte/src/components/DataTable";
    import Button from "smelte/src/components/Button";
    import Snackbar, { notifier } from "smelte/src/components/Snackbar";
    import { acquireToken } from "../stores/auth-store";
    import { loadUsers, usersStore } from "../stores/user-store";
    import type { User } from "../types/user";

    let data: User[] = [];
    let subcription = usersStore.subscribe((value) => {
        data = value;
    });
    let columns = [
        {
            field: "login",
            class: "md:w-10",
        },
        {
            field: "firstname",
        },
        {
            field: "lastname",
        },
        {
            field: "role",
        },
        {
            field: "active",
        },
        {
            field: "action",
            value: (v) => `<Link to="/users/51">Edit</Link>`,
        },
    ];
    acquireToken("admin", "admin0").then(() => loadUsers());

    const refresh = () => {
        loadUsers();
    };
</script>

<main>
    <Button on:click={refresh}>Refresh</Button>
    <DataTable class="w-full" {data} {columns} pagination={true} />
</main>
