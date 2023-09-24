<template>
  
  <!-- toolbar -->
  <div class="p-3 bg-gray-100 rounded">
    <toolbar :actions="toolbarActions"></toolbar>
  </div>

  <!-- delete dialog -->
  <div class="z-50">
    <ConfirmDelete :options="deleteOptions" @handleDelete="handleRemoveStudent()" @handleCancel="handleClear()" />
  </div>

  <div class="mt-3 flex flex-col lg:flex-row gap-3">
    <!-- form -->
    <div class="w-full lg:w-[450]">
      <div class="bg-gray-100 rounded p-3">
        <h3 class="text-lg font-medium">Enrollment Form</h3>
        <div class="mt-3">
          <label for="name">Enroll for student</label>
          <Dropdown v-model="enrollForm.student" :options="studentList" optionLabel="name" class="mt-1 w-full md:w-14rem capitalize">
            <template #value="slotProps">
              <div v-if="slotProps.value">
                <span :class="[`bg-${color[Math.floor(Math.random() * color.length)]}-500`, 'inline-flex h-6 w-6 items-center justify-center rounded-full']">
                  <span class="text-sm leading-none text-white">{{
                    slotProps.value?.name?.charAt(0)?.toUpperCase() }}
                    </span>
                </span>
                <span class="vertical-align-middle ml-2 font-bold line-height-3 capitalize">
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
              <span class="vertical-align-middle ml-2 font-bold line-height-3 capitalize">{{ slotProps.option.name }}</span>
            </template>
          </Dropdown>
          <small>Does the student not exist? <span class="font-medium text-blue-500 cursor-pointer" @click="toast.add({ severity: 'info', summary: 'Available soon!', life: 3000 })">Create New</span></small> 
        </div>

        <div class="mt-3">
          <label for="name">Choose Class</label>
          <Dropdown v-model="enrollForm.class" :options="groupTeacher" optionGroupLabel="label" optionGroupChildren="items" optionLabel="name" class="mt-1 w-full md:w-14rem"></Dropdown>
        </div>

        <div class="mt-3 flex gap-3 justify-end">
          <Button @click="selectedEnrollmentData ? handleUpdate() : handleCreate()" :label="selectedEnrollmentData ? 'Update' : 'Create'" :disabled="!(enrollForm.student && enrollForm.class)"></Button>
          <Button @click="handleClear" label="Clear" severity="danger" v-if="enrollForm.student || enrollForm.class" />
        </div>

      </div>      
    </div>

    <!-- datatable -->
    <div class="flex-1">
      <div class="bg-gray-100 rounded p-3 w-full">
        <div class="card">
          <DataTable 
            v-model:expandedRowGroups="expandedRowGroups" 
            v-model:selection="selectedEnrollmentData"
            expandableRowGroups
            selectionMode="single" 
            dataKey="id"
            rowGroupMode="subheader" 
            groupRowsBy="user_id" 
            sortMode="single"
            sortField="name" 
            tableStyle="min-width: 50rem" 
            :value="enrollmentList" 
            :sortOrder="1"
          >
            <template #groupheader="slotProps">
              <span :class="[`bg-${color[Math.floor(Math.random() * color.length)]}-500`, 'inline-flex h-9 w-9 items-center justify-center rounded-full']">
                <span class="text-lg font-medium leading-none text-white capitalize">
                  {{ slotProps.data?.name?.charAt(0)?.toUpperCase() }}
                </span>
              </span>
              <span class="vertical-align-middle ml-2 font-bold line-height-3 capitalize">
                {{ slotProps?.data?.name }}
              </span>
              <span  class="ml-5 inline-flex items-center gap-x-1.5 rounded-md px-2 py-1 text-xs font-medium text-gray-900 ring-1 ring-inset ring-gray-200">
                <svg class="h-1.5 w-1.5 fill-green-500" viewBox="0 0 6 6" aria-hidden="true">
                  <circle cx="3" cy="3" r="3" />
                </svg>
                {{ slotProps?.data?.phone }}
              </span>
              <span  class="ml-5 inline-flex items-center gap-x-1.5 rounded-md px-2 py-1 text-xs font-medium text-gray-900 ring-1 ring-inset ring-gray-200">
                <svg class="h-1.5 w-1.5 fill-pink-500" viewBox="0 0 6 6" aria-hidden="true">
                  <circle cx="3" cy="3" r="3" />
                </svg>
                {{ slotProps?.data?.email }}
              </span>
            </template>
            <Column field="user_id" header="Enrollment"></Column>          
            <Column field="class_name" header="Class" style="width: 40%"></Column>
            <Column field="subject_name" header="Subject" style="width: 30%"></Column>          
            <Column field="teacher_name" header="Teacher Name"></Column>
            <template #groupfooter="slotProps">
              <span>Total class: </span><span class="inline-flex items-center rounded-md bg-green-100 px-1.5 py-0.5 text-xs font-medium text-green-800">{{ groupClass(slotProps.data.user_id) }}</span>
            </template>
          </DataTable>
        </div>
      </div>
    </div>
  </div>

</template>

<script setup>
import { watchEffect, ref, onMounted, computed, reactive } from 'vue';
import axios from 'axios';
import { useSessionLogin } from '@/composables/auth';
import { useToast } from "primevue/usetoast";
import Toolbar from '@/components/toolbar.vue';
const { token } = useSessionLogin();

const toast = useToast();
const expandedRowGroups = ref();
const studentList = ref([]);
const classList = ref([]);
const enrollmentList = ref([]);
const selectedEnrollmentData = ref(null);

const color = [
  'green',
  'blue',
  'gray'
];

const enrollForm = reactive({
  student: null,
  class: null
});

const deleteOptions = reactive({
  show: false,
  loading: false,
  disabled: false
});

const toolbarActions = computed(() => ([
  {
    label: "Remove",
    style: "",
    icon: "pi pi-trash",
    severity: "danger",
    disabled: selectedEnrollmentData.value != null ? false: true,
    procced: (event) => {
      deleteOptions.show = true
    }
  }
]));

watchEffect(() => {
  enrollForm.student = studentList.value.filter(student => student.id == selectedEnrollmentData.value?.user_id)[0];
  enrollForm.class = classList.value.filter(_class => _class.id == selectedEnrollmentData.value?.class_id)[0];
});

const groupTeacher = computed(() => {
  if (classList.value?.length > 0) {
    return classList.value?.reduce((group, item) => {
      if(!group?.length) {
        group = [
          {
            id: item?.teacher_id,
            label: item?.teacher?.name,
            items: classList.value.filter(c => c.teacher_id == item?.teacher_id)
          }
        ];
      }
      else if (group.indexOf(g => g?.id == item?.teacher_id) == -1) {
        group.push({
          id: item?.teacher_id,
          label: item?.teacher.name,
          items: classList.value.filter(c => c.teacher_id == item?.teacher_id)
        })
      }
      return group;
    })    
  }
  else return []
})

const handleUpdate = () => {
  toast.add({ severity: 'info', summary: 'Available soon!', life: 3000 });
}

const handleCreate = () => {
  axios.create({
    method: 'post',
    headers: {
      'Authorization': `Bearer ${token.value}`
    }
  })
    .post("/admin/class/enroll-student", [
      {
        user_id: enrollForm.student.id,
        class_id: enrollForm.class.id
      }
    ])
    .then(response => {
      if (response.data.status == 200) {
        toast.add({ severity: 'success', summary: 'Create successfully.', life: 3000 });
        fetchData();
        handleClear();
      }
      else {
        toast.add({ severity: 'error', summary: 'Something went wrong.', life: 3000 });
      }
    })
    .catch((err) => {
      toast.add({ severity: 'error', summary: 'Enrollment already exist.', life: 3000 });
    })
}

const handleClear = () => {
  enrollForm.student = null;
  enrollForm.class = null;
  selectedEnrollmentData.value = null;
  deleteOptions.show = false;
}

const fetchData = () => {
  axios.create({
    method: 'get',
    timeout: 1000,
    headers: {
      'Authorization': `Bearer ${token.value}`
    }
  }).get("/admin/class/get-enrollment").then(response => {
    if (response.data.status == 200) {
      enrollmentList.value = response.data.data;
    }
  });

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
  }).get("/admin/student/list").then(response => {
    if (response.data.status == 200) {
      studentList.value = response.data.data;
    }
  });
}

const groupClass = (userId) => {
  return enrollmentList.value.filter(item => item.user_id == userId).length;
}

const handleRemoveStudent = () => {
  axios.create({
    headers: {
      'Authorization': `Bearer ${token.value}`
    }
  })
  .delete(`/admin/class/remove-student?id=${selectedEnrollmentData.value?.id}`)
  .then(response => {
    if (response.data.status == 200) {
      toast.add({ severity: 'success', summary: 'Delete successfully.', life: 3000 });
      fetchData();
      handleClear();
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

onMounted(() => {
  fetchData()
});

</script>