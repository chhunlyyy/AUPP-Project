<template>
  <div class="p-3 bg-gray-100 rounded">
    <toolbar :actions="[]"></toolbar>
  </div>
  <div class="mt-3 flex flex-col lg:flex-row gap-3">
    <div class="w-full lg:w-96">
      <div class="bg-gray-100 rounded p-3">
        <div class="mt-3">
          <label for="name">Name</label>
          <InputText id="name" :value="lecturerForm.name" v-model="lecturerForm.name" class="w-full mt-1" />
        </div>
        <div class="mt-3">
          <label for="email">Email</label>
          <InputText type="email" id="email" :value="lecturerForm.email" v-model="lecturerForm.email" class="w-full mt-1" />
        </div>
        <div class="mt-3">
          <label for="phone">Phone</label>
          <InputText id="phone" :value="lecturerForm.phone" v-model="lecturerForm.phone" class="w-full mt-1" />
        </div>
        <div class="mt-3">
          <label for="password">Password</label>
          <InputText id="password" :value="lecturerForm.password" v-model="lecturerForm.password" class="w-full mt-1" />
        </div>
        <div class="mt-3">
          <label for="confirm_password">Confirm Password</label>
          <InputText id="confirm_password" :value="lecturerForm.confirm_password" v-model="lecturerForm.confirm_password" class="w-full mt-1" />
        </div>
        <div class="mt-3">
          <label for="address">Address</label>
          <InputText id="address" :value="lecturerForm.address" v-model="lecturerForm.address" class="w-full mt-1" />
        </div>
        <div class="mt-3 flex gap-3 justify-end">
          <Button @click="lecturerForm.id ? handleUpdate() : handleCreate()" :label="lecturerForm.id ? 'Update' : 'Create'" :disabled="!(lecturerForm.name && lecturerForm.phone && lecturerForm.email)"></Button>
          <Button @click="handleClear()" label="Clear" severity="danger" v-if="lecturerForm.name || lecturerForm.description" />
        </div>
      </div>
    </div>
    <div class="flex-1">
      <Datatable ref="datatableRef" class="p-3 bg-gray-100 rounded" :list="lecturerList" :columns="columns"></Datatable>
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
const lecturerList = ref([]);
const selectedData = ref(null);

const lecturerForm = reactive({
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
  lecturerForm.id = selectedData.value?.id;
  lecturerForm.name = selectedData.value?.name;
  lecturerForm.email = selectedData.value?.email;
  lecturerForm.phone = selectedData.value?.phone;
  lecturerForm.address = selectedData.value?.address;
});

const handleUpdate = () => {
  toast.add({ severity: 'info', summary: 'Available soon.', life: 3000 });
}

const handleCreate = () => {

  if(lecturerForm.confirm_password != lecturerForm.password)
    toast.add({ severity: 'warn', summary: "The password doesn't match.", life: 3000 });
  else
    axios.create({
      method: 'post',
      headers: {
        'Authorization': `Bearer ${token.value}`
      }
    })
    .post("/auth/register", {
      ...lecturerForm,
      role_id: 2
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

  lecturerForm.id= null
  lecturerForm.name= null
  lecturerForm.phone= null
  lecturerForm.password= null
  lecturerForm.address= null
  lecturerForm.confirm_password= null

  datatableRef.value.clearSelectedData()
}

const fetchData = () => {
  axios.create({
    method: 'get',
    timeout: 1000,
    headers: {
      'Authorization': `Bearer ${token.value}`
    }
  }).get("/teacher/get-lecturer").then(response => {
    if (response.data.status == 200) {
      lecturerList.value = response.data.data;
    }
  });
}

onMounted(() => {
  fetchData()
});

</script>