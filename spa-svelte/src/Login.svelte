<script lang="ts">
    import Button from "smelte/src/components/Button";
    import TextField from "smelte/src/components/TextField";
</script>

<main>
    <h1 class="text-center">Welcome</h1>
    <div class="flex flex-col container">
        <TextField label="Login" />
        <TextField label="Password" type="password" />
        <Button>Login</Button>
    </div>
</main>

<style>
    .container {
        margin-left: 50px;
        margin-right: 50px;
    }
</style>
