<template>
  <div v-if="!isAuthenticated">
    <router-view :key="$route.path"></router-view>
  </div>
  <div v-else class="h-full">
    <SdieNavigation>
      <template v-slot:content>
        <RouterView/>
      </template>
    </SdieNavigation>
  </div>
</template>

<script setup>
  import { RouterView, useRoute } from 'vue-router'
  import SdieNavigation from '@/views/side-navigation.vue'
  import {useSessionLogin} from '@/composables/auth';

  const {isAuthenticated} = useSessionLogin();
</script>