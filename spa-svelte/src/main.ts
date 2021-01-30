import App from './App.svelte';
import Login from './Login.svelte';

// const app = new App({
// 	target: document.body,
// 	props: {
// 		name: 'world'
// 	}
// });
const app = new Login({
	target: document.body
})

export default app;