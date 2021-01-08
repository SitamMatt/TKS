import { RouteConfig } from 'vue-router';

const routes: RouteConfig[] = [
  {
    path: '/main',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      { path: '', component: () => import('pages/Index.vue') },
      { path: 'login', component: () => import('pages/Login.vue') },
      { path: 'rents', component: () => import('pages/Rents.vue') },
      { path: 'form', component: () => import('pages/ExampleForm.vue') },
    ],
  },
  {
    path: '/welcome',
    component: () => import('layouts/WelcomeLayout.vue'),
    children: [
      { path: 'login', component: () => import('pages/Login.vue') },
    ],
  },

  // Always leave this as last one,
  // but you can also remove it
  {
    path: '*',
    component: () => import('pages/Error404.vue'),
  },
];

export default routes;
