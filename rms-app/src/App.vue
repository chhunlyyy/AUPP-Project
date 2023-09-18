<template>
  <div v-if="!isAuthenticated">
    <router-view :key="$route.path"></router-view>
  </div>
  <div v-else>
    <TopNavigation/>
    <header class="bg-white shadow-sm">
      <div class="mx-auto max-w-7xl px-4 py-4 sm:px-6 lg:px-8">
        <h1 class="text-lg font-semibold leading-6 text-gray-900">Dashboard</h1>
      </div>
    </header>
    <main>
      <div class="mx-auto max-w-7xl py-6 sm:px-6 lg:px-8">
        <RouterView/>
      </div>
    </main>
  </div>
</template>

<script setup>
  import { RouterView, useRoute } from 'vue-router'
  import TopNavigation from '@/views/top-navigation.vue'
  import {useSessionLogin} from '@/composables/auth';

  const {isAuthenticated} = useSessionLogin();
</script>