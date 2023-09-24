<template>

  <!-- class form dialog -->
  <Dialog 
    v-model:visible="showDialog" 
    modal 
    position="top"
    :header="classForm.id ? 'Edit Class Information' : 'Create Class'"
    :draggable="false"
    :style="{ width: '40vw' }"
    @hide="handleClear()"
  >
    <div class="mt-3">
      <div>
        <label for="name">Title</label>
        <InputText id="name" :value="classForm.name" v-model="classForm.name" class="w-full mt-1" />
      </div>
      <div class="mt-3">
        <label for="name">Assign to lecturer</label>
        <Dropdown v-model="classForm.teacher" :options="teacherList" optionLabel="name" class="mt-1 w-full md:w-14rem">
          <template #value="slotProps">
            <div v-if="slotProps.value">
              <span :class="[`bg-${color[Math.floor(Math.random() * color.length)]}-500`, 'inline-flex h-6 w-6 items-center justify-center rounded-full']">
                <span class="text-sm leading-none text-white">{{
                  slotProps.value?.name?.charAt(0)?.toUpperCase() }}
                  </span>
              </span>
              <span class="vertical-align-middle ml-2 font-bold line-height-3">
                {{ slotProps.value.name }}
              </span>
            </div>
          </template>
          <template #option="slotProps">
            <span :class="[`bg-${color[Math.floor(Math.random() * color.length)]}-500`, 'inline-flex h-9 w-9 items-center justify-center rounded-full']">
                <span class="text-lg font-medium leading-none text-white">{{
                  slotProps.option?.name?.charAt(0)?.toUpperCase() }}
                </span>
            </span>
            <span class="vertical-align-middle ml-2 font-bold line-height-3">{{ slotProps.option.name }}</span>
          </template>
        </Dropdown>
      </div>
      <div class="mt-3">
        <label for="name">Choose Subject</label>
        <Dropdown v-model="classForm.subject" optionLabel="name" :options="subjectList" class="mt-1 w-full md:w-14rem"></Dropdown>
      </div>
      <div class="mt-3">
        <label for="name">Description</label>
        <Textarea rows="3" id="name" :value="classForm.description" v-model="classForm.description"
          class="w-full mt-1" />
      </div>
    </div>
    <template #footer>
      <Button label="Cancel" icon="pi pi-times" @click="handleClear()" text />
      <Button :disabled="!classForm.name" label="Save" icon="pi pi-check" @click="classForm.id ? handleUpdate() : handleCreate()" autofocus />
    </template>
  </Dialog>

  <!-- toolbar -->
  <div class="p-3 bg-gray-100 rounded">
    <toolbar :actions="toolbarActions"></toolbar>
  </div>

  <!-- datatable -->
  <div class="mt-3 bg-gray-100 rounded p-3 w-full">
    <div class="card">
      <DataTable 
        v-model:expandedRowGroups="expandedRowGroups" 
        v-model:selection="selectedClassData"
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
          <span class="vertical-align-middle ml-2 font-bold line-height-3 capitalize">
            {{ slotProps?.data?.teacher?.name}}
          </span>
          <span v-for="(classItem, index) in classList.filter(item => item?.teacher?.id == slotProps?.data?.teacher?.id)" :key="index"
            class="ml-5 inline-flex items-center gap-x-1.5 rounded-md px-2 py-1 text-xs font-medium text-gray-900 ring-1 ring-inset ring-gray-200">
            <svg class="h-1.5 w-1.5 fill-green-500" viewBox="0 0 6 6" aria-hidden="true">
              <circle cx="3" cy="3" r="3" />
            </svg>
            {{ classItem.name }}
          </span>
        </template>
        <Column expander style="width: 5rem"/>
        <Column field="teacher.id" header="Teacher"></Column>
        <Column field="id" header="ID" style="width: 5%"></Column>
        <Column field="name" header="Name" style="width: 30%"></Column>
        <Column field="students" header="No." style="width: 10%">
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
        <Column field="description" class="text-ellipsis overflow-hidden" header="Description"></Column>
        <template #groupfooter="slotProps">
          <span>Total class: <span class="inline-flex items-center rounded-md bg-green-100 px-1.5 py-0.5 text-xs font-medium text-green-800">{{ classList.filter(item => item?.teacher?.id == slotProps?.data?.teacher?.id)?.length }}</span></span>
        </template>   
        <!-- sub datatable -->
        <template #expansion="slotProps">
          <p class="font-medium">Student list</p>
          <div class="p-3">
            <DataTable 
              :value="slotProps.data?.students"
              selectionMode="single" 
              dataKey="id"
            >
            <Column field="detail.id" header="ID" style="width: 10%"></Column>
            <Column field="id" header="Enroll No." style="width: 10%"></Column>
              <Column field="detail.name" header="Name" style="width: 20%"></Column>
              <Column field="detail.email" header="Email" style="width: 15%"></Column>
              <Column field="detail.phone" header="Phone" style="width: 15%"></Column>
              <Column field="detail.address" header="Address"></Column>
              <Column>
                <template #body="slotProps">
                  <Button icon="pi pi-trash" class="h-20" outlined rounded severity="danger" @click="handleRemoveStudent(slotProps.data)" />
                </template>
              </Column>
            </DataTable>
          </div>
        </template>
      </DataTable>
    </div>
  </div>

  <!-- delete dialog -->
  <div class="z-50">
    <ConfirmDelete :options="deleteOptions" @handleDelete="handleDelete()" @handleCancel="deleteOptions.show = false" />
  </div>
</template>

<script setup>
import { onMounted, ref, computed, reactive,watchEffect } from 'vue';
import Toolbar from '@/components/toolbar.vue';
import axios from 'axios';
import { useSessionLogin } from '@/composables/auth';
import { useToast } from "primevue/usetoast";
import { UsersIcon } from "@heroicons/vue/24/outline";

const showDialog = ref(false)
const selectedClassData = ref();
const toast = useToast();
const { token } = useSessionLogin();
const expandedRowGroups = ref();
const expandedRows = ref();
const classList = ref([]);
const teacherList = ref([]);
const subjectList = ref([]);

const deleteOptions = reactive({
  show: false,
  loading: false,
  disabled: false,
  message: "Are you sure want to delete class?"
});

const toolbarActions = computed(() => ([
  {
    label: "Create",
    style: "",
    icon: "pi pi-plus",
    severity: "primary",
    procced: (event) => {
      handleClear()
      showDialog.value = true      
    }
  },
  {
    label: "Edit",
    style: "",
    icon: "pi pi-file-edit",
    severity: "primary",
    disabled: selectedClassData.value != null ? false: true,
    procced: (event) => {
      showDialog.value = true      
    }
  },
  {
    label: "Remove",
    style: "",
    icon: "pi pi-trash",
    severity: "danger",
    disabled: selectedClassData.value != null ? false: true,
    procced: (event) => {
      deleteOptions.show = true
    }
  }
]));

const classForm = reactive({
  id: null,
  name: null,
  description: null,
  teacher: null,
  subject: null
});

watchEffect(() => {
  classForm.id = selectedClassData.value?.id
  classForm.name = selectedClassData.value?.name
  classForm.description = selectedClassData.value?.description
  classForm.subject = selectedClassData.value?.subject
  classForm.teacher = selectedClassData.value?.teacher
});

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
  
  axios.create({
    method: 'get',
    timeout: 1000,
    headers: {
      'Authorization': `Bearer ${token.value}`
    }
  }).get("/teacher/get-lecturer").then(response => {
    if (response.data.status == 200) {
      teacherList.value = response.data.data;
    }
  });
  
  axios.create({
    method: 'get',
    timeout: 1000,
    headers: {
      'Authorization': `Bearer ${token.value}`
    }
  }).get("/admin/subject/list").then(response => {
    if (response.data.status == 200) {
      subjectList.value = response.data.data;
    }
  });
}

const handleClear = () => {  
  
  showDialog.value = false
  
  selectedClassData.value = null;

  classForm.id = null
  classForm.name = null
  classForm.description = null
  classForm.subject = null
  classForm.teacher = null
}

const handleCreate = () => {
  axios.create({
    method: 'post',
    headers: {
      'Authorization': `Bearer ${token.value}`
    }
  })
    .post("/admin/class/add", {
      name: classForm.name,
      description: classForm.description,
      teacher_id: classForm.teacher.id,
      subject_id: classForm.subject.id
    }).then(response => {
      if (response.data.status == 200) {
        toast.add({ severity: 'success', summary: 'Create successfully.', life: 3000 });
        fetchData();
        handleClear();
        showDialog.value = false;
      }
    });
}

const handleDelete = () => {
  axios.create({
    headers: {
      'Authorization': `Bearer ${token.value}`
    }
  })
  .delete(`/admin/class/delete?id=${selectedClassData.value?.id}`)
  .then(response => {
    if (response.data.status == 200) {
      toast.add({ severity: 'success', summary: 'Delete successfully.', life: 3000 });
      fetchData();
      selectedClassData.value = null;
    }
  })
  .finally(() => {
    deleteOptions.show = false;
  })
  .catch(err => {
    toast.add({
      severity: "error",
      summary: "Server Error",
      detail: 'Something when wrong!',
      life: 3000,
    });
  });
}

const handleRemoveStudent = (data) => {
  axios.create({
    headers: {
      'Authorization': `Bearer ${token.value}`
    }
  })
  .delete(`/admin/class/remove-student?id=${data.id}`)
  .then(response => {
    if (response.data.status == 200) {
      toast.add({ severity: 'success', summary: 'Delete successfully.', life: 3000 });
      fetchData();
    }
  })
  .catch(err => {
    toast.add({
      severity: "error",
      summary: "Server Error",
      detail: 'Something when wrong!',
      life: 3000,
    });
  });
}

const handleUpdate = () => {
  axios.create({
    headers: {
      'Authorization': `Bearer ${token.value}`
    }
  })
    .patch("admin/class/update", {
      id: classForm.id,
      name: classForm.name,
      description: classForm.description,
      teacher_id: classForm.teacher.id,
      subject_id: classForm.subject.id
    })
    .then(response => {
      if (response.data.status == 200) {
        toast.add({ severity: 'success', summary: 'Update successfully.', life: 3000 });
        fetchData();
        handleClear();
      }
    })
}

onMounted(() => {
  fetchData();
});

</script>