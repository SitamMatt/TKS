<script lang="ts">
    import Button from "smelte/src/components/Button";
    import { acquireToken } from "../stores/auth-store";
    import { loadUsers, usersStore } from "../stores/user-store";
    import type { User } from "../types/user";
    import { Link, navigate } from "svelte-navigator";
    import SvelteTable from "svelte-table";

    let data: User[] = [];
    let subcription = usersStore.subscribe((value) => {
        data = value;
    });
    let columns = [
        {
            title: "Guid",
            key: "Guid",
            value: (v) => v.guid,
        },
        {
            title: "firstname",
            key: "firstname",
            value: (v) => v.firstname,
        },
        {
            title: "lastname",
            key: "lastname",
            value: (v) => v.lastname,
        },
        {
            title: "role",
            key: "role",
            value: (v) => v.role,
        },
        {
            title: "active",
            key: "active",
            value: (v) => v.active,
        },
    ];
    acquireToken("admin", "admin0").then(() => loadUsers());

    const refresh = () => {
        loadUsers();
    };

    const editUser = (user) => {
        navigate(`/users/${user.guid}`);
    };
</script>

<main>
    <Button on:click={refresh}>Refresh</Button>
    <SvelteTable
        rows={data}
        {columns}
        on:clickRow={(v) => editUser(v.detail.row)}
    />
</main>
