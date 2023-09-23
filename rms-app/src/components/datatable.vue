<template>
  <div class="card">
    <DataTable       
      v-model:selection="selectedData" 
      selectionMode="single" 
      dataKey="id" 
      tableStyle="min-width: 50rem"
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
      </Column>
    </DataTable>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
const props = defineProps({
  list: Array,
  columns: Array
});

const selectedData = ref();
const metaKey = ref(true);

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