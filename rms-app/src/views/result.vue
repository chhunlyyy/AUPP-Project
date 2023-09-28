<template>
  <div class="p-3 bg-gray-100 rounded">
    <toolbar :actions="toolbarActions"></toolbar>
  </div>
  <div class="p-3 mt-3 bg-gray-100 rounded flex gap-3 items-center">
      <Dropdown v-model="filterForm.class" :options="groupTeacher" optionGroupLabel="label" optionGroupChildren="items" placeholder="Choose class" optionLabel="name" class="mt-1 w-64 md:w-14rem"></Dropdown>
      <Dropdown v-model="filterForm.examination" :options="examOptions" :disabled="!filterForm.class" placeholder="Choose examinations" optionLabel="name" class="mt-1 w-64 md:w-14rem"></Dropdown>
      <Button :disabled="!(filterForm.examination?.class_id == filterForm.class?.id)" style="height: 41px!important;margin-top: 2px!important;" @click="handleApply()" severity="primary" label="Apply" icon=""></Button>      
  </div>
  <div class="mt-3 flex flex-col lg:flex-row gap-3">
    <div class="flex-1">
      <Datatable ref="datatableRef" @onCellEditComplete="handleCellEdit" class="p-3 bg-gray-100 rounded" :list="studentList" :columns="columns" :editable="true"></Datatable>
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
const { token, roleId, userId } = useSessionLogin();

const toast = useToast();
const datatableRef = ref({});
const studentList = ref([]);
const resultList = ref([]);
const examinations = ref([]);
const classList = ref([]);
const newData = ref({})
const disable = ref(true);

const filterForm = reactive({
  class: null,
  examination: null
});

const columns = [
  {
    header: "ID",
    field: 'id',
    sortable: true
  },
  {
    header: "Name",
    field: 'detail.name',
    sortable: true
  },
  {
    header: "Email",
    field: 'detail.email'
  },
  {
    header: "Phone No.",
    field: 'detail.phone'
  },
  {
    header: "Score",
    field: 'score'
  }
];

watchEffect(() => {
  studentList.value.forEach((item, index) => {
    if(item.id == newData.value.id) {
      studentList.value[index] = newData.value
    }    
  })

  if(studentList.value.length > 0)
    disable.value = studentList.value.filter(item => !item.score).length > 0
})

const toolbarActions = computed(() => ([
  {
    label: "Submit",
    style: "",
    icon: "pi pi-trash",
    severity: "primary",
    disabled: disable.value,
    procced: (event) => {
      handleCreate()
    }
  }
]));

const groupTeacher = computed(() => {
  if (Array.isArray(classList.value)) {
    let group = classList.value.reduce((group, item) => {
      if(group.length == 0) {
        group = [
          {
            id: item?.teacher?.id,
            label: item?.teacher?.name,
            items: classList.value.filter(c => c.teacher_id == item?.teacher_id)
          }
        ];
      }
      else {
        let index = group.findIndex(g => g.id == item?.teacher_id);
        if(index > -1) {
          group[index] = {
            ...group[index],
            items: classList.value.filter(c => c.teacher_id == item?.teacher_id)
          };
        }
        else {
          group.push({
            id: item?.teacher?.id,
            label: item?.teacher?.name,
            items: classList.value.filter(c => c.teacher_id == item?.teacher_id)
          })
        }
      }
      return group;
    }, []);

    if(roleId?.value == 1) {
      
      return group;
    }
    else {
      console.log(userId?.value);
      return group.filter(item => item.id == userId?.value)
    }
  }
  else {
    return []
  }
})

const examOptions = computed(() => examinations.value.filter(item => item.class_id == filterForm.class?.id))

const handleCreate = () => {

  let form = studentList.value.map(student => ({
    class_id: student.class_id,
    student_id: student.id,
    score: student.score,
    exam_id: filterForm.value.examination.id,
    status: 2
  }))

  axios.create({
    method: 'post',
    headers: {
      'Authorization': `Bearer ${token.value}`
    }
  })
    .post("/teacher/result/add", [
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
  filterForm.class = null;
  filterForm.examination = null;

  datatableRef.value.clearSelectedData()
}

const handleCellEdit = (event) => {
  newData.value = event.newData;
}

const handleApply = async () => {
  let filteredClass = classList.value.filter(item => item.id == filterForm.examination?.class_id);
  
  await axios.create({
    method: 'get',
    timeout: 1000,
    headers: {
      'Authorization': `Bearer ${token.value}`
    }
  })
  .get(`/teacher/result/get?examId=${filterForm.examination?.id}&classId=${filterForm.class?.id}`).then(response => {
    if (response.data.status == 200) {
      resultList.value = response.data.data;
    }
  })
  .catch((e) => {
    resultList.value = []
    toast.add({ severity: 'info', summary: "Exam results have not been input yet.", life: 5000, group:"br" });
  })

  if(Array.isArray(filteredClass)) {
    studentList.value = filteredClass[0]?.students.map(item => ({
      ...item,
      score: resultList.value.filter(scoreItem => scoreItem.student_id == item.id)[0]?.score
    }))
  }
}

const fetchData = () => {
  axios.create({
    method: 'get',
    timeout: 1000,
    headers: {
      'Authorization': `Bearer ${token.value}`
    }
  }).get("/admin/exam/list").then(response => {
    if (response.data.status == 200) {
      examinations.value = response.data.data;
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
}

onMounted(() => {
  fetchData()
});

</script>