<template>
  <div class="p-3 bg-gray-100 rounded">
    <toolbar :actions="actions"></toolbar>
  </div>
  <div class="mt-3 flex flex-col lg:flex-row gap-3">
    <div class="w-full lg:w-96">
      <div class="bg-gray-100 rounded p-3">
        <h3 class="text-lg font-medium">Subject Form</h3>
        <div class="mt-3">
          <label for="name">Title</label>
          <InputText id="name" :value="subjectForm.name" v-model="subjectForm.name" class="w-full mt-1"/>
        </div>
        <div class="mt-3">
          <label for="name">Description</label>
          <Textarea rows="5" id="name" :value="subjectForm.description" v-model="subjectForm.description" class="w-full mt-1"/>
        </div>
        <div class="mt-3 flex gap-3 justify-end">
          <Button @click="subjectForm.id ? handleUpdate() : handleCreate()" :label="subjectForm.id ? 'Update' : 'Create'"></Button>      
          <Button @click="handleClear" label="Clear" severity="danger" v-if="subjectForm.name || subjectForm.description" />
        </div>
      </div>
    </div>
    <div class="flex-1">
      <Datatable ref="datatableRef" class="p-3 bg-gray-100 rounded" :list="subjects" :columns="columns"></Datatable>      
    </div>
  </div>
</template>

<script setup>
  import Toolbar from '@/components/toolbar.vue';
  import { computed, watchEffect, ref, onMounted, reactive } from 'vue';
  import Datatable from '@/components/datatable.vue';
  import axios from 'axios';
  import { useSessionLogin } from '@/composables/auth';
  import { useToast } from "primevue/usetoast";

  const toast = useToast();
  const datatableRef = ref({});
  const subjects = ref([]);
  const selectedData = ref(null);
  const { token } = useSessionLogin();
  const subjectForm = reactive({
    id: null,
    name: null,
    description: null
  });

  const columns = [
    {
      header: "ID",
      field: 'id',
      sortable: true  
    },
    {
      header: "Title",
      field: 'name',
      sortable: true  
    },
    {
      header: "Description",
      field: 'description',
      sortable: true  
    }
  ];

  watchEffect(() => {
    selectedData.value = datatableRef.value.selectedData;
    subjectForm.id = selectedData.value?.id;
    subjectForm.name = selectedData.value?.name;
    subjectForm.description = selectedData.value?.description;
  });

  const actions = computed(() => ([
    {
      label: "Delete",
      style: "",
      icon: "pi pi-trash",
      severity: "danger",
      disabled: !selectedData.value?.id,
      procced: (event) => {
        handleDelete();
      }
    }
  ]));

  const handleDelete = () => {
    axios.create({
      headers: {
        'Authorization': `Bearer ${token.value}`
      }
    })
    .delete(`/admin/subject/delete?id=${selectedData.value?.id}`)
    .then(response => {
      if(response.data.status == 200) {
        toast.add({ severity: 'success', summary: 'Delete successfully.', life: 3000});
        fetchData();
      }
    })
  }
  
  const handleUpdate = () => {
    axios.create({
      headers: {
        'Authorization': `Bearer ${token.value}`
      }
    })
    .patch("admin/subject/update", subjectForm)
    .then(response => {
      if(response.data.status == 200) {
        toast.add({ severity: 'success', summary: 'Update successfully.', life: 3000});
        fetchData();
      }
    })
  }

  const handleCreate = () => {
    axios.create({      
      method: 'post',
      headers: {
        'Authorization': `Bearer ${token.value}`
      }
    })
    .post("/admin/subject/add", {
      name: subjectForm.name,
      description: subjectForm.description
    }).then(response => {
      if(response.data.status == 200) {
        toast.add({ severity: 'success', summary: 'Create successfully.', life: 3000});
        fetchData();
      }
    });
  }

  const handleClear = () => {
    subjectForm.id = null;
    subjectForm.name = null;
    subjectForm.description = null;
  }

  const fetchData = () => {
    axios.create({      
      method: 'get',
      timeout: 1000,
      headers: {
        'Authorization': `Bearer ${token.value}`
      }
    }).get("/admin/subject/list").then(response => {
      if(response.data.status == 200) {
        subjects.value = response.data.data;
      }
    });
  }

  onMounted(() => {
    fetchData()
  });

</script>