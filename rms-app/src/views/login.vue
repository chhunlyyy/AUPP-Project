<template>  
  <div class="flex min-h-full flex-1 flex-col justify-center px-6 py-16 lg:px-8">
    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <img class="mx-auto h-20 w-auto" src="../assets/images/aupp-logo.webp" alt="Your Company" />
      <!-- <h2 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">Sign in to your account</h2> -->
    </div>

    <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
      <div>
          <label for="email" class="block text-sm font-medium leading-6 text-gray-900">Email address</label>
          <div class="mt-2">
            <input id="email" v-model="email" name="email" type="email" autocomplete="email" required
              class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
          </div>
        </div>

        <div class="mt-5">
          <div class="flex items-center justify-between">
            <label for="password" class="block text-sm font-medium leading-6 text-gray-900">Password</label>
            <!-- <div class="text-sm">
              <a href="#" class="font-semibold text-indigo-600 hover:text-indigo-500">Forgot password?</a>
            </div> -->
          </div>
          <div class="mt-2">
            <input id="password" v-model="password" name="password" type="password" autocomplete="current-password"
              class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">
          </div>
        </div>        
        <div class="mt-5 flex items-center justify-center">
          <button @click="handleLogin()" type="button" :disabled="isDisableLogin" class="bg-violet-500 w-full hover:bg-violet-600 focus:outline-none focus:ring focus:ring-violet-300 active:bg-violet-700 px-5 py-2 text-sm leading-5 rounded font-semibold text-white">
            <div class="inline-flex items-center" v-if="isLoading">
              <svg class="motion-reduce:hidden animate-spin -ml-1 mr-3 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
              </svg>
              Processing...
            </div>
            <p v-else>Sign In</p>
          </button>
        </div>
    </div>
  </div>
</template>  

<script setup>
  import { useSessionLogin } from '@/composables/auth';
  import { ref, onMounted, computed } from 'vue'
  import { useRouter, useRoute } from 'vue-router';
  import { useToast } from "primevue/usetoast";

  const { login, logout, isAuthenticated } = useSessionLogin();

  const email = ref(null);
  const password = ref(null);
  const router = useRouter();
  const route = useRoute();
  const toast = useToast();
  const isLoading = ref(false);
  
  const isDisableLogin = computed(() => {
    return email.value?.length > 0 && password.value?.length > 0 ? false : true
  });

  const sleep = (delay) => new Promise((resolve) => setTimeout(resolve, delay))

  const handleLogin = async () => {
    isLoading.value = true;

    await login(email.value, password.value);
    await sleep(900);
    
    isLoading.value = false;
    if(!isAuthenticated.value) {
      showError();
    }
    else {
      setTimeout(() => {
        showSuccess();
      }, 500);
      intendedRedirect();
    }
  }

  const showError = () => {
    toast.add({ severity: 'error', summary: 'Email or password incorrect.', life: 3000 });
  };
  
  const showSuccess = () => {
    toast.add({ severity: 'success', summary: 'Welcome! You have successfully logged in.', life: 3000, group: 'br' });
  };

  const intendedRedirect = () => {
    if(isAuthenticated.value) {
      router.push(route.redirectedFrom ? route.redirectedFrom.fullPath : "/");
    }
  }

  onMounted(() => {    
    intendedRedirect();
  })
</script>
