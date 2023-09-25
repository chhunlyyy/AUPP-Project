import './assets/tailwind.css';
import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import PrimeVue from "primevue/config";
import ToastService from 'primevue/toastservice';

import Button from 'primevue/button';
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import ColumnGroup from 'primevue/columngroup';   
import Row from 'primevue/row';                  
import InputText from 'primevue/inputtext';
import InputNumber from 'primevue/inputnumber';
import Calendar from 'primevue/calendar';
import Textarea from 'primevue/textarea';
import axios from 'axios';
import Dialog from 'primevue/dialog';
import Divider from 'primevue/divider';
import Dropdown from 'primevue/dropdown';
import ConfirmDelete from '@/components/confirm-delete.vue';

//theme
import 'primevue/resources/themes/saga-blue/theme.css';
import 'primeicons/primeicons.css';
import 'primevue/resources/primevue.min.css';


axios.defaults.baseURL = 'http://localhost:8080';

const app = createApp(App)

app.use(router)
app.use(PrimeVue);
app.use(ToastService);

app.component("Button", Button);
app.component("DataTable", DataTable);
app.component("Column", Column);
app.component("ColumnGroup", ColumnGroup);
app.component("Row", Row);
app.component("InputText", InputText);
app.component("Calendar", Calendar);
app.component("InputNumber", InputNumber);
app.component("Textarea", Textarea);
app.component("Dialog", Dialog);
app.component("Divider", Divider);
app.component("Dropdown", Dropdown);
app.component("ConfirmDelete", ConfirmDelete);

app.mount('#app')
