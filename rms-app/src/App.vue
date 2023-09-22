<template>
  <Toast/>
  <div v-if="isAuthenticated || (!$route.meta.requiresAuth && $route.path != '/login')" class="h-full">        
    <SdieNavigation>
      <template v-slot:content>
        <router-view></router-view>
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
  import Toast from 'primevue/toast';
  const {isAuthenticated} = useSessionLogin();
</script>

<style scoped>
  .component-fade-enter-active, .component-fade-leave-active {
    transition: opacity .3s ease;
  }
  .component-fade-enter, .component-fade-leave-to
  /* .component-fade-leave-active below version 2.1.8 */ {
    opacity: 0;
  }
</style>