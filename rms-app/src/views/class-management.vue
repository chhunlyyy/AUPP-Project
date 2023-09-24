<template>
    <Dialog 
      v-model:visible="showDialog" 
      modal 
      position="top"
      header="Create class"
      :style="{ width: '40vw' }"
    >
      <div class="mt-3">
        <div>
          <label for="name">Title</label>
          <InputText id="name" :value="classForm.name" v-model="classForm.name" class="w-full mt-1" />
        </div>
        <div class="mt-3">
          <label for="name">Description</label>
          <Textarea rows="3" id="name" :value="classForm.description" v-model="classForm.description"
            class="w-full mt-1" />
        </div>
      </div>
      <template #footer>
        <Button label="Cancel" icon="pi pi-times" @click="handleCancel()" text />
        <Button label="Save" icon="pi pi-check" @click="visible = false" autofocus />
      </template>
    </Dialog>
  <div class="p-3 bg-gray-100 rounded">
    <toolbar :actions="toolbarActions"></toolbar>
  </div>
  <div class="mt-3 bg-gray-100 rounded p-3 w-full">
    <div class="card">
      <DataTable 
        v-model:expandedRowGroups="expandedRowGroups" 
        v-model:selection="selectedData"
        v-model:expandedRows="expandedRows"
        expandableRowGroups
        selectionMode="single" 
        dataKey="id"
        rowGroupMode="subheader" 
        groupRowsBy="teacher.id" 
        sortMode="single"
        sortField="teacher.name" 
        tableStyle="min-width: 50rem" 
        :value="classList" 
        :sortOrder="1"
        @rowgroup-collapse="expandedRows=null"
      >
        <template #groupheader="slotProps">
          <span
            :class="[`bg-${color[Math.floor(Math.random() * color.length)]}-500`, 'inline-flex h-10 w-10 items-center justify-center rounded-full']">
            <span class="text-lg font-medium leading-none text-white">{{
              slotProps.data?.teacher?.name?.charAt(0)?.toUpperCase() }}</span>
          </span>
          <span class="vertical-align-middle ml-2 font-bold line-height-3 capitalize">{{ slotProps?.data?.teacher?.name
          }}</span>
          <span
            class="ml-5 inline-flex items-center gap-x-1.5 rounded-md px-2 py-1 text-xs font-medium text-gray-900 ring-1 ring-inset ring-gray-200">
            <svg class="h-1.5 w-1.5 fill-green-500" viewBox="0 0 6 6" aria-hidden="true">
              <circle cx="3" cy="3" r="3" />
            </svg>
            {{ slotProps?.data?.name }}
          </span>
        </template>
        <Column field="students.detail.id" expander style="width: 5rem" />
        <Column field="teacher.id" header="Teacher"></Column>
        <Column field="name" header="Name" style="width: 30%"></Column>
        <Column field="students" header="Student No." style="width: 10%">
          <template #body="slotProps">
            <div class="flex justify-content-end font-bold w-full">
              <span
                class="inline-flex items-center rounded-md bg-purple-400/10 px-2 py-1 text-xs font-medium text-purple-400 ring-1 ring-inset ring-purple-400/30">
                <UsersIcon class="h-4 w-4 shrink-0 text-purple-400 mr-2" />
                {{ slotProps.data.students.length }}
              </span>
            </div>
          </template>
        </Column>
        <Column field="subject.name" header="Subject" style="width: 20%"></Column>
        <Column field="description" header="Description"></Column>
        <template #groupfooter="slotProps">
          <div class="flex justify-content-end font-bold w-full">Total class: {{ slotProps.index + 1 }}</div>
        </template>        
        <template #expansion="slotProps">
          <div class="p-3">
            <DataTable 
              :value="slotProps.data?.students"
              selectionMode="single" 
              dataKey="class_id" 
            >
              <Column field="detail.id" header="ID"></Column>
              <Column field="detail.name" header="Name" style="width: 20%"></Column>
              <Column field="detail.email" header="Email" style="width: 15%"></Column>
              <Column field="detail.phone" header="Phone" style="width: 15%"></Column>
              <Column field="detail.address" header="Address"></Column>
            </DataTable>
          </div>
        </template>
      </DataTable>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref, computed, reactive } from 'vue';
import Toolbar from '@/components/toolbar.vue';
import axios from 'axios';
import { useSessionLogin } from '@/composables/auth';
import { useToast } from "primevue/usetoast";
import { UsersIcon } from "@heroicons/vue/24/outline";

const showDialog = ref(false)

const toolbarActions = computed(() => ([
  {
    label: "Create",
    style: "",
    icon: "pi pi-plus",
    severity: "primary",
    procced: (event) => {
      showDialog.value = true
    }
  }
]));

const classForm = reactive({
  name: null,
  description: null,
  teacher_id: null,
  subject_id: null
});

const selectedData = ref();
const toast = useToast();
const { token } = useSessionLogin();
const expandedRowGroups = ref();
const expandedRows = ref();
const classList = ref();
const color = [
  'green',
  'blue',
  'gray'
]

const fetchData = () => {
  axios.create({
    method: 'get',
    timeout: 1000,
    headers: {
      'Authorization': `Bearer ${token.value}`
    }
  }).get("/admin/class/list").then(response => {
    if (response.data.status == 200) {
      classList.value = response.data.data;
    }
  });
}

const handleCancel = () => {
  showDialog.value = false
  classForm.name = null
  classForm.description = null
  classForm.subject_id = null
  classForm.teacher_id = null
}

onMounted(() => {
  fetchData();
});

</script>