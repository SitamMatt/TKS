<script lang="ts">
	import DataTable from "smelte/src/components/DataTable";
	import { Pagination } from "smelte/src/components/DataTable";
	import Button from "smelte/src/components/Button";
	import { getUsers, users } from "./stores/user-store";
	import "smelte/src/tailwind.css";
	import type { User } from "./types/user";
	import { onMount } from "svelte";

	export let name: string;
	let table;
	onMount(() => {
		console.log(table);
	});
	let data: User[] = [];
	let usersS = users.subscribe((value) => {
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
	];
</script>

<main>
	<h1>Hello {name}!</h1>
	<p>
		Visit the <a href="https://svelte.dev/tutorial">Svelte tutorial</a> to learn
		how to build Svelte apps.
	</p>
	<Button on:click={() => getUsers()}>Login</Button>
	<DataTable
		bind:this={table}
		class="w-full"
		{data}
		{columns}
		pagination={true}
	>
		<!-- <Pagination
			{table}
			total={10}
			perPage={1}
			offset={1}
			page={1}
			slot="pagination"
		/> -->
	</DataTable>
</main>

<style>
	main {
		text-align: center;
		padding: 1em;
		max-width: 240px;
		margin: 0 auto;
	}

	h1 {
		color: #ff3e00;
		text-transform: uppercase;
		font-size: 4em;
		font-weight: 100;
	}

	@media (min-width: 640px) {
		main {
			max-width: none;
		}
	}
</style>
