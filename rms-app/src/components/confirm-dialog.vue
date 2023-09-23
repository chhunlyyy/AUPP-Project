<template>
    <TransitionRoot as="template" :show="confirm.show">
      <Dialog as="div" class="relative z-50" :initialFocus="autoFocus">
        <TransitionChild
          as="template"
          enter="ease-out duration-300"
          enter-from="opacity-0"
          enter-to="opacity-100"
          leave="ease-in duration-200"
          leave-from="opacity-100"
          leave-to="opacity-0"
        >
          <div class="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity" />
        </TransitionChild>
  
        <div class="fixed z-50 inset-0 overflow-y-auto">
          <div
            class="flex items-start justify-center min-h-full p-4 text-center sm:p-0"
          >
            <TransitionChild
              as="template"
              enter="ease-out duration-300"
              enter-from="opacity-0 translate-y-4 sm:translate-y-0 sm:scale-95"
              enter-to="opacity-100 translate-y-0 sm:scale-100"
              leave="ease-in duration-200"
              leave-from="opacity-100 translate-y-0 sm:scale-100"
              leave-to="opacity-0 translate-y-4 sm:translate-y-0 sm:scale-95"
            >
              <DialogPanel
                :class="[
                  width,
                  'relative bg-white rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:w-full',
                ]"
              >
                <!-- <div class="hidden sm:block absolute top-0 right-0 pt-4 pr-4">
                  <button
                    type="button"
                    class="bg-white rounded-md text-gray-400 hover:text-gray-500 focus:outline-none outline-none focus:none"
                    @click="$emit('close')"
                  >
                    <span class="sr-only">Close</span>
                    <XIcon class="h-6 w-6" aria-hidden="true" />
                  </button>
                </div> -->
                <div class="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
                  <div class="sm:flex sm:items-start">
                    <div
                      :class="confirm.icon.backgroudClass"
                      class="mx-auto flex-shrink-0 flex items-center justify-center h-12 w-12 rounded-full sm:mx-0 sm:h-10 sm:w-10"
                    >
                      <component
                        :is="confirm.icon.component"
                        :class="confirm.icon.class"
                      ></component>
                    </div>
                    <div class="mt-3 text-center sm:mt-0 sm:ml-4 sm:text-left w-full">
                      <DialogTitle
                        as="h3"
                        class="text-xl font-medium text-gray-900"
                        >{{ confirm.title }}</DialogTitle
                      >
                      <slot name="body" :confirm="confirm">
                        <div class="mt-2">
                          {{ confirm.message }}
                        </div>
                      </slot>
                    </div>
                  </div>
                </div>
                <footer>
                  <slot name="footer">
                    <div
                      class="bg-gray-50 px-4 py-3 sm:px-6 sm:flex sm:flex-row-reverse gap-2"
                    >
                      <button
                        class="rounded-md mt-3 w-full inline-flex justify-center items-center shadow-sm sm:mt-0 h-9 px-2 sm:w-auto"
                        v-bind="action"
                        type="button"
                        v-for="(action, index) in confirm.actions"
                        :key="index"
                        :disabled="action.disabled || confirm.disabledAll"
                        @click="action.command"
                      >
                        <i class="pi pi-fw pi-spin pi-spinner mt-[2px] mr-1" v-if="action.loading"></i>
                        <i :class="[action.icon, 'mt-[2px] mr-1']" v-else-if="action.icon"></i>
                        {{ action.label }}
                      </button>
                      <button
                        :class="{'text-gray-700 border border-gray-300 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2': true}"
                        class="rounded-md mt-3 w-full inline-flex justify-center items-center shadow-sm sm:mt-0 h-9 px-4 sm:w-auto"
                        type="button"
                        @click="$emit('close')"
                        ref="autoFocus"
                        >
                          Cancel
                      </button>
                    </div>
                  </slot>
                </footer>
              </DialogPanel>
            </TransitionChild>
          </div>
        </div>
      </Dialog>
    </TransitionRoot>
  </template>
  
  <script setup>
  import {
    Dialog,
    DialogPanel,
    DialogTitle,
    TransitionChild,
    TransitionRoot,
  } from "@headlessui/vue";
  import { ref } from 'vue';
  import { InformationCircleIcon } from "@heroicons/vue/24/outline";
  const autoFocus = ref(null);
  const props = defineProps({
    confirm: {
      type: Object,
      default: () => ({
        show: false,
        title: "Title",
        message: "Message",
        icon: {
          component: InformationCircleIcon,
          class: "h-6 w-6 text-blue-400",
        },
        disabledAll: false,
        actions: [
          {
            label: "Cancel",
            class: "text-gray-700 hover:bg-gray-50 border border-gray-300 rounded-md sm:w-auto",
            icon: "",
            loading: false,
            disabled: false,
            command: () => {
  
            },
          },
        ],
      }),
    },
    width: {
      type: String,
      default: "sm:max-w-lg",
    },
  });
  </script>
  