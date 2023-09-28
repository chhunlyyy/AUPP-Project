<template>
  <div class="card">
    <DataTable       
      v-model:selection="selectedData" 
      @cell-edit-complete="onCellEditComplete"
      selectionMode="single" 
      dataKey="id" 
      tableStyle="min-width: 50rem"
      editMode="cell"
      tableClass="editable-cells-table"
      :value="list" 
      :metaKeySelection="metaKey"       
    >
      <Column
        v-for="(column, index) in columns" 
        :key="index"
        :header="column.header"
        :field="column.field"
        :sortable="column?.sortable"
      >
        <template v-if="editable" #editor="{ data, field }">
          <template v-if="field == 'score'">
            <input style="height: 25px!important;" type="number" min="0" max="100" v-model="data[field]" autofocus />
          </template>
          <template v-else>
            <p class="text-black">{{ data?.field }}</p>
          </template>
        </template>
      </Column>
    </DataTable>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
const props = defineProps({
  list: Array,
  columns: Array,
  editable: Boolean
});

const emit = defineEmits(["onCellEditComplete"])

const selectedData = ref();
const metaKey = ref(true);


const onCellEditComplete = (event) => {
  emit('onCellEditComplete', event);
}

defineExpose({ 
  selectedData,
  clearSelectedData: () => selectedData.value = null
})
</script>

<style>
.p-datatable .p-datatable-tbody > tr > td {
  padding-top: 9px!important;
  padding-bottom: 9px!important;
}
</style>