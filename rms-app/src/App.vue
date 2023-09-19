<template>
  <div v-if="isAuthenticated" class="h-full">
    <SdieNavigation>
      <template v-slot:content>
        <RouterView/>
      </template>
    </SdieNavigation>
  </div>
  <div v-else>
    <router-view :key="$route.path"></router-view>
  </div>
</template>

<script setup>
  import { RouterView, useRoute } from 'vue-router'
  import SdieNavigation from '@/components/side-navigation.vue'
  import {useSessionLogin} from '@/composables/auth';

  const {isAuthenticated} = useSessionLogin();
</script>