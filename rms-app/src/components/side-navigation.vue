<template>
  <div>
    <TransitionRoot as="template" :show="sidebarOpen">
      <Dialog as="div" class="relative z-40 lg:hidden" @close="sidebarOpen = false">
        <TransitionChild as="template" enter="transition-opacity ease-linear duration-300" enter-from="opacity-0"
          enter-to="opacity-100" leave="transition-opacity ease-linear duration-300" leave-from="opacity-100"
          leave-to="opacity-0">
          <div class="fixed inset-0 bg-gray-900/80" />
        </TransitionChild>

        <div class="fixed inset-0 flex">
          <TransitionChild as="template" enter="transition ease-in-out duration-300 transform"
            enter-from="-translate-x-full" enter-to="translate-x-0" leave="transition ease-in-out duration-300 transform"
            leave-from="translate-x-0" leave-to="-translate-x-full">
            <DialogPanel class="relative mr-16 flex w-full max-w-xs flex-1">
              <TransitionChild as="template" enter="ease-in-out duration-300" enter-from="opacity-0"
                enter-to="opacity-100" leave="ease-in-out duration-300" leave-from="opacity-100" leave-to="opacity-0">
                <div class="absolute left-full top-0 flex w-16 justify-center pt-5">
                  <button type="button" class="-m-2.5 p-2.5" @click="sidebarOpen = false">
                    <span class="sr-only">Close sidebar</span>
                    <XMarkIcon class="h-6 w-6 text-white" aria-hidden="true" />
                  </button>
                </div>
              </TransitionChild>
              <div class="flex grow flex-col gap-y-5 overflow-y-auto bg-gray-900 px-6 pb-4 ring-1 ring-white/10">
                <div class="flex h-16 shrink-0 items-center mt-2">
                  <img class="h-12 w-auto" src="../assets/images/aupp-logo.webp" alt="Your Company" />
                </div>
                <nav class="flex flex-1 flex-col">
                  <ul role="list" class="flex flex-1 flex-col gap-y-7">
                    <li>
                      <ul role="list" class="-mx-2 space-y-1">
                        <li>
                          <ul role="list" class="-mx-2 space-y-1">
                            <li v-for="item in navigation" :key="item.name">
                              <router-link v-if="!item.children" :to="item.href"
                                :class="[item.current ? 'bg-gray-800 text-white' : 'text-gray-400 hover:text-white hover:bg-gray-800', 'group flex gap-x-3 rounded-md p-2 text-sm leading-6 font-semibold']">
                                <component :is="item.icon" class="h-6 w-6 shrink-0 text-gray-400" aria-hidden="true" />
                                {{ item.name }}
                              </router-link>
                              <Disclosure as="div" v-else v-slot="{ open }">
                                <DisclosureButton :class="[item.current ? 'bg-gray-800 text-white' : 'text-gray-400 hover:text-white hover:bg-gray-800', 'w-full group flex gap-x-3 rounded-md p-2 text-sm leading-6 font-semibold']">
                                  <component :is="item.icon" class="h-6 w-6 shrink-0 text-gray-400" aria-hidden="true" />
                                  {{ item.name }}
                                  <ChevronRightIcon :class="[open ? 'rotate-90 text-gray-500' : 'text-gray-400', 'ml-auto h-5 w-5 shrink-0']" aria-hidden="true" />
                                </DisclosureButton>
                                <DisclosurePanel as="ul" class="mt-1 px-2">
                                  <li v-for="subItem in item.children" :key="subItem.name">
                                    <router-link as="router-link" :to="`${item.href}${subItem.href}`" class="text-gray-400 w-full group flex gap-x-3 rounded-md p-2 text-sm leading-6 font-semibold">
                                      {{ subItem.name }}
                                    </router-link>
                                  </li>
                                </DisclosurePanel>
                              </Disclosure>
                            </li>
                          </ul>
                        </li>
                      </ul>
                    </li>
                    <li class="mt-auto">
                      <router-link :to="{ name: 'profile' }"
                        class="group -mx-2 flex gap-x-3 rounded-md p-2 text-sm font-semibold leading-6 text-gray-400 hover:bg-gray-800 hover:text-white">
                        <Cog6ToothIcon class="h-6 w-6 shrink-0" aria-hidden="true" />
                        Profile
                      </router-link>
                    </li>
                  </ul>
                </nav>
              </div>
            </DialogPanel>
          </TransitionChild>
        </div>
      </Dialog>
    </TransitionRoot>

    <!-- Static sidebar for desktop -->
    <div class="hidden lg:fixed lg:inset-y-0 lg:z-40 lg:flex lg:w-72 lg:flex-col">
      <!-- Sidebar component, swap this element with another sidebar if you like -->
      <div class="flex grow flex-col gap-y-5 overflow-y-auto bg-gray-900 px-6 pb-4">
        <div class="flex h-16 shrink-0 items-center mt-2">
          <img class="h-12 w-auto" src="../assets/images/aupp-logo.webp" alt="aupp" />
        </div>
        <nav class="flex flex-1 flex-col">
          <ul role="list" class="flex flex-1 flex-col gap-y-7">
            <li>
              <ul role="list" class="-mx-2 space-y-1">
                <li>
                  <ul role="list" class="-mx-2 space-y-1">
                    <li v-for="item in navigation" :key="item.name">
                      <router-link v-if="!item.children" :to="item.href"
                        :class="[item.current ? 'bg-gray-800 text-white' : 'text-gray-400 hover:text-white hover:bg-gray-800', 'group flex gap-x-3 rounded-md p-2 text-sm leading-6 font-semibold']">
                        <component :is="item.icon" class="h-6 w-6 shrink-0 text-gray-400" aria-hidden="true" />
                        {{ item.name }}
                      </router-link>
                      <Disclosure as="div" v-else v-slot="{ open }">
                        <DisclosureButton :class="[item.current ? 'bg-gray-800 text-white' : 'text-gray-400 hover:text-white hover:bg-gray-800', 'w-full group flex gap-x-3 rounded-md p-2 text-sm leading-6 font-semibold']">
                          <component :is="item.icon" class="h-6 w-6 shrink-0 text-gray-400" aria-hidden="true" />
                          {{ item.name }}
                          <ChevronRightIcon :class="[open ? 'rotate-90 text-gray-500' : 'text-gray-400', 'ml-auto h-5 w-5 shrink-0']" aria-hidden="true" />
                        </DisclosureButton>
                        <DisclosurePanel as="ul" class="mt-1 px-2">
                          <li v-for="subItem in item.children" :key="subItem.name">
                            <router-link as="router-link" :to="`${item.href}${subItem.href}`" class="text-gray-400 w-full group flex gap-x-3 rounded-md p-2 text-sm leading-6 font-semibold">
                              {{ subItem.name }}
                            </router-link>
                          </li>
                        </DisclosurePanel>
                      </Disclosure>
                    </li>
                  </ul>
                </li>
              </ul>
            </li>
            <li class="mt-auto">
              <router-link :to="{ name: 'profile' }"
                class="group -mx-2 flex gap-x-3 rounded-md p-2 text-sm font-semibold leading-6 text-gray-400 hover:bg-gray-800 hover:text-white">
                <Cog6ToothIcon class="h-6 w-6 shrink-0" aria-hidden="true" />
                Profile
              </router-link>
            </li>
          </ul>
        </nav>
      </div>
    </div>

    <div class="lg:pl-72  flex flex-col" style="height: 100vh;">
      <div
        class="sticky z-40 flex h-16 shrink-0 items-center gap-x-4 border-b border-gray-200 bg-white px-4 shadow-sm sm:gap-x-6 sm:px-6 lg:px-8">
        <button type="button" class="-m-2.5 p-2.5 text-gray-700 lg:hidden" @click="sidebarOpen = true">
          <span class="sr-only">Open sidebar</span>
          <Bars3Icon class="h-6 w-6" aria-hidden="true" />
        </button>
        <div class="h-6 w-px bg-gray-900/10 lg:hidden" aria-hidden="true" />
        <div class="flex items-center gap-x-4 lg:gap-x-6 flex-1 justify-end">
          <UserProfile></UserProfile>
        </div>
      </div>

      <main class="flex-grow overflow-auto h-full">
        <router-view v-slot="{ Component, route }">
          <!-- <transition name="fade" mode="out-in"> -->
            <div :key="route.name" :class="[route.meta?.class ? route.meta.class : 'pl-3 pt-3 pr-3', 'h-full']">
              <component :is='Component'></component>
            </div>
          <!-- </transition> -->
        </router-view>
      </main>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import {
  Dialog,
  DialogPanel,
  TransitionChild,
  TransitionRoot,
  Disclosure,
  DisclosureButton,
  DisclosurePanel
} from '@headlessui/vue'
import {
  Bars3Icon,
  Cog6ToothIcon,
  HomeIcon,
  UsersIcon,
  BriefcaseIcon,
  XMarkIcon,
  RectangleGroupIcon,
} from '@heroicons/vue/24/outline';

import { ChevronRightIcon } from '@heroicons/vue/20/solid'

import UserProfile from '@/components/user-profile.vue';
const route = useRoute();
const sidebarOpen = ref(false);

const currentUrl = computed(() => {
  return route.fullPath.startsWith("/class-management");
})

const navigation = computed(() => ([
    { name: 'Dashboard', href: '/', icon: HomeIcon },
    { name: 'Lecturers', href: 'lecturers', icon: UsersIcon },
    { name: 'Subjects', href: '/subjects', icon: BriefcaseIcon },
    {
      name: 'Class Management',
      href: '/class-management',
      icon: RectangleGroupIcon,
      current: currentUrl.value,
      children: [
        { name: 'Class Control', href: ''},
        { name: 'Enrollment', href: '/enrollment'}
      ],
    }
  ]));

</script>

<style>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.08s;
}

.fade-enter,
.fade-leave-to {
  opacity: 0;
}</style>