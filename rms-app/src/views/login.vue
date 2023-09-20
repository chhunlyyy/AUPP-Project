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
            <div class="text-sm">
              <a href="#" class="font-semibold text-indigo-600 hover:text-indigo-500">Forgot password?</a>
            </div>
          </div>
          <div class="mt-2">
            <input id="password" v-model="password" name="password" type="password" autocomplete="current-password"
              class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">
          </div>
        </div>
        <p v-if="isShowError">Failed login</p>
        <div class="mt-5">
          <button @click="handleLogin()" :disabled="email == null || password == null" class="flex w-full justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">Signin</button>
        </div>
    </div>
  </div>
</template>  

<script setup>
  import { useSessionLogin } from '@/composables/auth';
  import { ref, onMounted } from 'vue'
  import { useRouter, useRoute } from 'vue-router';

  const { login, logout, isAuthenticated } = useSessionLogin();

  const email = ref(null);
  const password = ref(null);
  const router = useRouter();
  const route = useRoute();
  const isShowError = ref(false);

  const handleLogin = async () => {
    await login(email.value, password.value);
    
    if(!isAuthenticated.value)
      isShowError.value = true;
    else
      intendedRedirect()    
  }

  const intendedRedirect = () => {
    if(isAuthenticated.value)
      router.push(route.redirectedFrom ? route.redirectedFrom.fullPath : "/").then(() => router.go());
  }

  onMounted(() => {    
    intendedRedirect();
  })
</script>
