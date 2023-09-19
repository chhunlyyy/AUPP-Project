<template>
  <Menu as="div" class="relative">
    <MenuButton class="-m-1.5 flex items-center p-1.5">
      <span class="sr-only">Open user menu</span>
      <img class="h-8 w-8 rounded-full bg-gray-50" src="../assets/images/avatar.jpg" alt="Avatar" />
      <span class="hidden lg:flex lg:items-center">
        <ChevronDownIcon class="ml-2 h-5 w-5 text-gray-400" aria-hidden="true" />
      </span>
    </MenuButton>
    <transition enter-active-class="transition ease-out duration-100" enter-from-class="transform opacity-0 scale-95"
      enter-to-class="transform opacity-100 scale-100" leave-active-class="transition ease-in duration-75"
      leave-from-class="transform opacity-100 scale-100" leave-to-class="transform opacity-0 scale-95">
      <MenuItems
        class="absolute right-0 z-10 mt-2.5 w-32 origin-top-right rounded-md bg-white py-2 shadow-lg ring-1 ring-gray-900/5 focus:outline-none">
        <MenuItem v-for="item in userNavigation" :key="item.name" v-slot="{ active }">
          <router-link :to="{ name: item.name }" :class="[active ? 'bg-gray-50' : '', 'block px-3 py-1 text-sm leading-6 text-gray-900']">
            {{  item.title }}
          </router-link>
        </MenuItem>
        <MenuItem>
          <a @click="handleLogout()" class="cursor-pointer block px-3 py-1 text-sm leading-6 text-gray-900">Logout</a>
        </MenuItem>
      </MenuItems>
    </transition>
  </Menu>
</template>

<script setup>
  import {
    Menu,
    MenuButton,
    MenuItem,
    MenuItems
  } from '@headlessui/vue';

  import { ChevronDownIcon } from '@heroicons/vue/20/solid'
  import { useSessionLogin } from '@/composables/auth';
  import { useRouter, useRoute } from 'vue-router';
  
  const userNavigation = [
    { 
      title: 'Your profile', 
      name: 'profile'
    }
  ];

  const router = useRouter();
  const {user, logout} = useSessionLogin();

  const handleLogout = () => {
    logout();
    router.go();
  }

</script>