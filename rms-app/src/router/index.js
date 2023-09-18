import { createRouter, createWebHistory } from 'vue-router';
import { useSessionLogin } from '@/composables/auth';
import Login from '@/views/login.vue';
import Lecturer from '@/views/lecturer.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('../views/home.vue')
    },
    {
      path: '/lecturers',
      name: 'lecturer',
      component: Lecturer,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/login',
      name: 'login',
      component: Login
    }
  ]
});

router.beforeEach((to, from, next) => {
  const { isAuthenticated } = useSessionLogin();
  const requiresAuth = to.matched.some(record => record.meta.requiresAuth);

  if (requiresAuth && !isAuthenticated.value) {
    next('/login');
  }
  else {
    sessionStorage.removeItem('previousUrl')
    next();
  }
});

export default router;
