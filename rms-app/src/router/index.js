import { createRouter, createWebHistory } from 'vue-router';
import { useSessionLogin } from '@/composables/auth';
import Login from '@/views/login.vue';
import Lecturer from '@/views/lecturer.vue';
import Error404 from '@/components/404.vue';
import Profile from '@/views/profile-page.vue';
import SdieNavigation from '@/components/side-navigation.vue';
import Home from '@/views/home.vue';
import Exam from '@/views/exam.vue';
import Subject from '@/views/subject.vue';
import ClassManagement from '@/views/class-management.vue';
import Student from '@/views/student.vue';
import Enrollment from '@/views/enrollment.vue';
import { HomeIcon } from "@heroicons/vue/24/outline";


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  linkActiveClass: "bg-gray-800 text-white",
  linkExactActiveClass: "bg-gray-800 text-white",
  routes: [
    {
      path: '/',
      components: {
        dashboard: SdieNavigation,
      },
      meta: {
        breadcrumb: {name: '', icon: HomeIcon}
      },
      children: [
        {
          path: '',
          name: 'home',
          component: Home,
          meta: {
            requiresAuth: true,
            class: 'p-0',
          }
        },
        {
          path: 'lecturers',
          name: 'lecturer',
          component: Lecturer,
          meta: {
            requiresAuth: true,
            breadcrumb: {name: 'Lecturer'}
          }
        },   
        {
          path: 'exam',
          name: 'exam',
          component: Exam,
          meta: {
            requiresAuth: true,
            breadcrumb: {name: 'Exam'}
          }
        },     
        {
          path: 'students',
          name: 'student',
          component: Student,
          meta: {
            requiresAuth: true,
            breadcrumb: {name: 'Student'}
          }
        },        
        {
          path: 'subjects',
          name: 'subject',
          component: Subject,
          meta: {
            requiresAuth: true,
            breadcrumb: {name: 'Subject'}
          }
        },        
        {
          path: 'profile',
          name: 'profile',
          component: Profile,
          meta: {
            requiresAuth: true,
            breadcrumb: {name: 'Subject'}
          }
        },
        {
          path: 'class-management',
          meta: {
            requiresAuth: true,
            breadcrumb: { name: 'Class management' }
          },
          children: [
            {
              path: '',
              name: 'class',
              component: ClassManagement,
              meta: {
                requiresAuth: true,
                breadcrumb: { name: 'Class Control' }
              }
            },
            {
              path: 'enrollment',
              name: 'Enrollment',
              component: Enrollment,
              meta: {
                requiresAuth: true,
                breadcrumb: { name: 'Enrollment' }
              }
            }
          ]
        }
      ]
    },
    {
      path: '/login',
      name: 'login',
      component: Login
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
