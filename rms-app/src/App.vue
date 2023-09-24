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

.p-dialog .p-dialog-header {
    border-bottom: 0 none;
    background: #ffffff;
    color: #495057;
    padding: 1.5rem;
    padding-top: 1rem!important;
    padding-bottom: 1rem!important;
    border-bottom: 1px solid #3232327e !important;
}
</style>