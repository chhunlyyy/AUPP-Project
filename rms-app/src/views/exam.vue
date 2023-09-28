<template>
  <div class="p-3 bg-gray-100 rounded">
    <toolbar :actions="toolbarActions"></toolbar>
  </div>
  <div class="mt-3 flex flex-col lg:flex-row gap-3">
    <div class="w-full lg:w-96">
      <div class="bg-gray-100 rounded p-3">
        <h3 class="text-lg font-medium">Subject Form</h3>
        <div class="mt-3">
          <label for="name">Class</label>
          <Dropdown
            v-model="examForm.selectedClass"
            :options="examForm.classes"
            optionLabel="name"
            placeholder
            class="w-full mt-1"
          />
        </div>
        <div class="mt-3">
          <label for="name">Title</label>
          <InputText id="name" :value="examForm.name" v-model="examForm.name" class="w-full mt-1" />
        </div>
        <div class="mt-3">
          <label for="name">Exam Date</label>
          <br />
          <Calendar
            :value="examForm.date"
            v-model="examForm.date"
            inputId="birth_date"
            class="w-full mt-1"
          />
        </div>

        <div class="mt-3">
          <label for="name">Description</label>
          <Textarea
            rows="5"
            id="name"
            :value="examForm.description"
            v-model="examForm.description"
            class="w-full mt-1"
          />
        </div>
        <div class="mt-3 flex gap-3 justify-end">
          <Button
            @click="examForm.id ? handleUpdate() : handleCreate()"
            :label="examForm.id ? 'Update' : 'Create'"
            :disabled="!examForm.name"
          ></Button>
          <Button
            @click="handleClear"
            label="Clear"
            severity="danger"
            v-if="examForm.name || examForm.description"
          />
        </div>
      </div>
    </div>
    <div class="flex-1">
      <Datatable ref="datatableRef" class="p-3 bg-gray-100 rounded" :list="exam" :columns="columns"></Datatable>
    </div>
  </div>
  <div class="z-50">
    <ConfirmDelete
      :options="deleteOptions"
      @handleDelete="submitDelete"
      @handleCancel="deleteOptions.show = false"
    />
  </div>
</template>
  
  <script setup>
import Toolbar from '@/components/toolbar.vue'
import { computed, watchEffect, ref, onMounted, reactive } from 'vue'
import Datatable from '@/components/datatable.vue'
import axios from 'axios'
import { useSessionLogin } from '@/composables/auth'
import { useToast } from 'primevue/usetoast'

const toast = useToast()
const datatableRef = ref({})
const exam = ref([])
const selectedData = ref(null)

const { token } = useSessionLogin()

const examForm = reactive({
  id: null,
  name: null,
  description: null,
  date: null,
  classes: null,
  selectedClass: null
})

const submitDelete = () => {
  handleDelete()
    .then((response) => {
      if (response.data.status == 200) {
        toast.add({ severity: 'success', summary: 'Delete successfully.', life: 3000 })
        fetchData()
        handleClear()
      }
    })
    .finally(() => {
      deleteOptions.show = false
      deleteOptions.loading = false
    })
    .catch((err) => {
      toast.add({
        severity: 'error',
        summary: 'Server Error',
        detail: 'Something when wrong!',
        life: 3000
      })
    })
}

const deleteOptions = reactive({
  show: false,
  loading: false,
  disabled: false
})

const columns = [
  {
    header: 'ID',
    field: 'id',
    sortable: true
  },
  {
    header: 'Title',
    field: 'name',
    sortable: true
  },
  {
    header: 'Description',
    field: 'description',
    sortable: true
  },
  {
    header: 'Date',
    field: 'date',
    sortable: true
  }
  
]

watchEffect(() => {
  selectedData.value = datatableRef.value.selectedData
  examForm.id = selectedData.value?.id
  examForm.name = selectedData.value?.name
  examForm.description = selectedData.value?.description
  examForm.date = selectedData.value?.date
  if(Array.isArray(examForm.classes)) {
    let index = examForm.classes.findIndex(item => item.id == selectedData.value?.class_id)
    examForm.selectedClass = examForm.classes[index]
  }

  
  // if (examForm.classes != null) {
  //   examForm.classes.forEach((element) => {
  //     if (element.id == examForm.id) {
  //       examForm.selectedClass = element
  //     }
  //   })
  // }
})

const toolbarActions = computed(() => [
  {
    label: 'Delete',
    style: '',
    icon: 'pi pi-trash',
    severity: 'danger',
    disabled: !selectedData.value?.id,
    procced: (event) => {
      deleteOptions.show = true
    }
  }
])

const handleDelete = () => {
  return axios
    .create({
      headers: {
        Authorization: `Bearer ${token.value}`
      }
    })
    .delete(`/admin/exam/delete?id=${selectedData.value?.id}`)
}

const handleUpdate = () => {
  if (
    examForm.selectedClass == null ||
    examForm.name == null ||
    examForm.date == null
  ) {
    toast.add({
      severity: 'error',
      summary: 'Error Validation',
      detail: 'Please Input All Information!',
      life: 3000
    })
  } else {
    axios
      .create({
        headers: {
          Authorization: `Bearer ${token.value}`
        }
      })
      .patch('admin/exam/update', {
        id: examForm.id,
        name: examForm.name,
        description: examForm.description,
        date: examForm.date,
        class_id: examForm.selectedClass.id
      })
      .then((response) => {
        if (response.data.status == 200) {
          toast.add({ severity: 'success', summary: 'Update successfully.', life: 3000 })
          fetchData()
          handleClear()
        }
      })
  }
}

const handleCreate = () => {
  if (
    examForm.selectedClass == null ||
    examForm.name == null ||
    examForm.date == null
  ) {
    toast.add({
      severity: 'error',
      summary: 'Error Validation',
      detail: 'Please Input All Information!',
      life: 3000
    })
  } else {
    axios
      .create({
        method: 'post',
        headers: {
          Authorization: `Bearer ${token.value}`
        }
      })
      .post('/admin/exam/add', {
        name: examForm.name,
        description: examForm.description,
        date: examForm.date,
        class_id: examForm.selectedClass.id
      })
      .then((response) => {
        if (response.data.status == 200) {
          toast.add({ severity: 'success', summary: 'Create successfully.', life: 3000 })
          fetchData()
          handleClear()
        }
      })
  }
}

const handleClear = () => {
  examForm.id = null
  examForm.name = null
  examForm.description = null

  datatableRef.value.clearSelectedData()
}
const fecthClasses = () => {
  axios
    .create({
      method: 'get',
      timeout: 1000,
      headers: {
        Authorization: `Bearer ${token.value}`
      }
    })
    .get('/admin/class/list')
    .then((response) => {
      if (response.data.status == 200) {
        examForm.classes = response.data.data
        console.log(examForm.classes)
      }
    })
}

const fetchData = () => {
  axios
    .create({
      method: 'get',
      timeout: 1000,
      headers: {
        Authorization: `Bearer ${token.value}`
      }
    })
    .get('/admin/exam/list')
    .then((response) => {
      if (response.data.status == 200) {
        exam.value = response.data.data
      }
    })
}

onMounted(() => {
  fetchData(), fecthClasses()
})
</script>