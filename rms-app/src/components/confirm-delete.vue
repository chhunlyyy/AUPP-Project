<template>
  <ConfirmDialog :confirm="confirmBuilder" @close="$emit('handleCancel')"/>
</template>
  
<script setup>
import { computed } from "vue";
import ConfirmDialog from "@/components/confirm-dialog.vue";
import { ICON, BUTTON_COLOR } from "@/config.js"

const emit = defineEmits(["handleCancel", "handleDelete"]);
const props = defineProps({options: {
  type: Object,
  default: {
    show: false,
    loading: false,
    disabled: false
  }
}});

const confirmBuilder = computed(() => {
  return {
    show: props.options?.show,
    title: 'Delete',
    message: 'Are you sure want to delete?',
    icon: ICON.DANGER,
    actions: [
      {
        label: "Delete",
        class: `px-5 ${BUTTON_COLOR.DANGER}`,
        loading: props.options?.loading,
        disabled: props.options?.disabled || props.options?.loading,
        command: () => { emit('handleDelete') }
      }      
    ],
  }
});
</script>
  