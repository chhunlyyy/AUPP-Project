import './assets/tailwind.css';
import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import PrimeVue from "primevue/config";
import ToastService from 'primevue/toastservice';

import Button from 'primevue/button';
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import ColumnGroup from 'primevue/columngroup';   // optional
import Row from 'primevue/row';                   // optional
import InputText from 'primevue/inputtext';
import Textarea from 'primevue/textarea';
import axios from 'axios'

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
app.component("Textarea", Textarea);

app.mount('#app')
