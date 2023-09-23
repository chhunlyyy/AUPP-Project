<template>
  <nav v-if="crumbs?.length > 0" class="flex flex-row justify-between h-9">
    <ol role="list" class="flex items-center space-x-4">
      <li v-for="(crumb, index) in crumbs" :key="crumb.label">
        <div class="flex items-center">
          <router-link :to="crumb.to" class="bg-transparent">
            <component v-if="crumb.icon" :is="crumb.icon" class="h-6 w-6 shrink-0" /> <span
              class="text-black text-sm font-medium hover:text-gray-700">{{ crumb?.label }}</span>
          </router-link>
          <svg v-if="crumbs.length - 1 > index" class="h-5 w-5 flex-shrink-0 text-gray-300" fill="currentColor"
            viewBox="0 0 20 20" aria-hidden="true">
            <path d="M5.555 17.776l8-16 .894.448-8 16-.894-.448z" />
          </svg>
        </div>
      </li>
    </ol>    
    <div class="flex">
      <Button v-for="(action, index) in actions" :disabled="action.disabled" :key="index" @click="action.procced($event)" :severity="action?.severity" :label="action.label" :icon="action.icon" :class="['mr-2 h-9 px-5', action.style]"></Button>      
    </div>
  </nav>
</template>

<script setup>
  import { computed } from 'vue';
  import { useRouter, useRoute } from 'vue-router';

  const props = defineProps({
    actions: {
      default: []
    }
  })

  const route = useRoute();

  const crumbs = computed(() => {
  
    let matchedRoutes = route.matched.map(itemRoute => ({
      to: itemRoute.path,
      label: itemRoute.meta.breadcrumb?.name,
      icon: itemRoute.meta.breadcrumb?.icon
    }));

    return matchedRoutes.find((element, index) => element?.label == undefined) ? null : matchedRoutes;
  });
</script>