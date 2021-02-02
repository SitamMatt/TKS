<script lang="ts">
    import Button from "smelte/src/components/Button";
    import { loadUsers, usersStore } from "../stores/user-store";
    import type { User } from "../types/user";
    import { navigate } from "svelte-navigator";
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
    loadUsers();

    const refresh = () => {
        loadUsers();
    };

    const editUser = (user) => {
        navigate(`/dashboard/users/${user.guid}`);
    };

    const createUser = () => {
        navigate("/dashboard/users/new");
    };
</script>

<main>
    <Button on:click={refresh}>Refresh</Button>
    <Button on:click={createUser}>Create New User</Button>
    <SvelteTable
        rows={data}
        {columns}
        on:clickRow={(v) => editUser(v.detail.row)}
    />
</main>
