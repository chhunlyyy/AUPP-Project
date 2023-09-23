<template>
  <Toast/>
  <Toast position="bottom-right" group="br" />
  <router-view name="dashboard"></router-view>
  <router-view></router-view>
</template>

<script setup>
  import { RouterView } from 'vue-router'
  import Toast from 'primevue/toast';
</script>

<style>
.p-button.p-component{
  padding-left: 20px!important;
  padding-right: 20px!important;
  height: 38px!important;
}

</style>