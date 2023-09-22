import { createRouter, createWebHistory } from 'vue-router';
import { useSessionLogin } from '@/composables/auth';
import Login from '@/views/login.vue';
import Lecturer from '@/views/lecturer.vue';
import Error404 from '@/components/404.vue';
import Profile from '@/views/profile-page.vue';
import SdieNavigation from '@/components/side-navigation.vue';
import Home from '@/views/home.vue';
import Subject from '@/views/subject.vue';
import { HomeIcon } from "@heroicons/vue/24/outline";
// import { markRaw } from 'vue';


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  linkActiveClass: "bg-gray-800 text-white",
  linkExactActiveClass: "bg-gray-800 text-white",
  routes: [
    {
      path: '/',
      components: {
        dashboard: SdieNavigation
      },
      meta: {
        breadcrumb: {name: '', icon: HomeIcon}
      },
      children: [
        {
          path: '/',
          name: 'home',
          component: Home,
          meta: {
            requiresAuth: true,
            class: 'p-0',
          }
        },
        {
          path: '/lecturers',
          name: 'lecturer',
          component: Lecturer,
          meta: {
            requiresAuth: true,
            breadcrumb: {name: 'Lecturer'}
          }
        },        
        {
          path: '/subjects',
          name: 'subject',
          component: Subject,
          meta: {
            requiresAuth: true,
            breadcrumb: {name: 'Subject'}
          }
        },        
        {
          path: '/profile',
          name: 'profile',
          component: Profile,
          meta: {
            requiresAuth: true,
            breadcrumb: {name: 'Subject'}
          }
        }
      ]
    },
    {
      path: '/login',
      name: 'login',
      //component: Login,
      components: {
        default: Login
      }
    },
    {
      path: '/:pathMatch(.*)*',
      name: '404',
      components: {
        dashboard: SdieNavigation
      },
      children: [
        {
          path:'',
          name:'404-page',
          component: Error404,
          meta: {
            requiresAuth: true
          }
        }
      ]
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
    next();
  }
});

export default router;
