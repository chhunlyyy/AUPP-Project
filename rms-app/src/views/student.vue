<template>
  <div class="p-3 bg-gray-100 rounded">
    <toolbar :actions="[]"></toolbar>
  </div>
  <div class="mt-3 flex flex-col lg:flex-row gap-3">
    <div class="w-full lg:w-96">
      <div class="bg-gray-100 rounded p-3">
        <div class="mt-3">
          <label for="name">Name</label>
          <InputText id="name" :value="studentForm.name" v-model="studentForm.name" class="w-full mt-1" />
        </div>
        <div class="mt-3">
          <label for="email">Email</label>
          <InputText type="email" id="email" :value="studentForm.email" v-model="studentForm.email" class="w-full mt-1" />
        </div>
        <div class="mt-3">
          <label for="phone">Phone</label>
          <InputText id="phone" :value="studentForm.phone" v-model="studentForm.phone" class="w-full mt-1" />
        </div>
        <div class="mt-3">
          <label for="password">Password</label>
          <InputText id="password" :value="studentForm.password" v-model="studentForm.password" class="w-full mt-1" />
        </div>
        <div class="mt-3">
          <label for="confirm_password">Confirm Password</label>
          <InputText id="confirm_password" :value="studentForm.confirm_password" v-model="studentForm.confirm_password" class="w-full mt-1" />
        </div>
        <div class="mt-3">
          <label for="address">Address</label>
          <InputText id="address" :value="studentForm.address" v-model="studentForm.address" class="w-full mt-1" />
        </div>
        <div class="mt-3 flex gap-3 justify-end">
          <Button @click="studentForm.id ? handleUpdate() : handleCreate()" :label="studentForm.id ? 'Update' : 'Create'" :disabled="!(studentForm.name && studentForm.phone && studentForm.email)"></Button>
          <Button @click="handleClear()" label="Clear" severity="danger" v-if="studentForm.name || studentForm.description" />
        </div>
      </div>
    </div>
    <div class="flex-1">
      <Datatable ref="datatableRef" class="p-3 bg-gray-100 rounded" :list="studentList" :columns="columns"></Datatable>
    </div>
  </div>
</template>

<script setup>
import { computed, watchEffect, ref, onMounted, reactive } from 'vue';
import Datatable from '@/components/datatable.vue';
import axios from 'axios';
import { useSessionLogin } from '@/composables/auth';
import { useToast } from "primevue/usetoast";
import Toolbar from '@/components/toolbar.vue';
const { token } = useSessionLogin();

const toast = useToast();
const datatableRef = ref({});
const studentList = ref([]);
const selectedData = ref(null);

const studentForm = reactive({
  id: null,
  name: null,
  phone: null,
  email: null,
  address: null,
  password: null,
  confirm_password: null
});

const columns = [
  {
    header: "ID",
    field: 'id',
    sortable: true
  },
  {
    header: "Name",
    field: 'name',
    sortable: true
  },
  {
    header: "Phone",
    field: 'phone'
  },
  {
    header: "Email",
    field: 'email'
  },
  {
    header: "Address",
    field: 'address'
  }
];

watchEffect(() => {
  selectedData.value = datatableRef.value.selectedData;
  studentForm.id = selectedData.value?.id;
  studentForm.name = selectedData.value?.name;
  studentForm.email = selectedData.value?.email;
  studentForm.phone = selectedData.value?.phone;
  studentForm.address = selectedData.value?.address;
});

const handleUpdate = () => {
  toast.add({ severity: 'info', summary: 'Available soon.', life: 3000 });
}

const handleCreate = () => {

  if(studentForm.confirm_password != studentForm.password)
    toast.add({ severity: 'warn', summary: "The password doesn't match.", life: 3000 });
  else
    axios.create({
      method: 'post',
      headers: {
        'Authorization': `Bearer ${token.value}`
      }
    })
    .post("/auth/register", {
      ...studentForm,
      role_id: 3
    })
    .then(response => {
      if (response.data.status == 200) {
        toast.add({ severity: 'success', summary: 'Create successfully.', life: 3000 });
        fetchData();
        handleClear();
      }
    })
    .catch(err => {
      toast.add({ severity: 'error', summary: "Something went wrong.", life: 3000 });
    })
}

const handleClear = () => {

  studentForm.id= null
  studentForm.name= null
  studentForm.phone= null
  studentForm.password= null
  studentForm.address= null
  studentForm.confirm_password= null

  datatableRef.value.clearSelectedData()
}

const fetchData = () => {
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

onMounted(() => {
  fetchData()
});

</script>