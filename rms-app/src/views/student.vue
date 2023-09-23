<template>
    <div class="p-3 bg-gray-100 rounded">
      <toolbar :actions="toolbarActions"></toolbar>
    </div>
    <div class="mt-3 flex flex-col lg:flex-row gap-3">
      <div class="w-full lg:w-96">
        <div class="bg-gray-100 rounded p-3">
          Welcome to class management
        </div>
      </div>
    </div>
</template>

<script setup>
  import { onMounted, ref, computed } from 'vue';
  import Toolbar from '@/components/toolbar.vue';

  const toolbarActions = computed(() => ([
    // {
    //   label: "Delete",
    //   style: "",
    //   icon: "pi pi-trash",
    //   severity: "danger",
    //   procced: (event) => {
        
    //   }
    // }
  ]));
</script>