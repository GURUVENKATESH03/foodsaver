<template>
  <main
    class="flex min-h-screen items-center justify-center bg-[#FAFAFA] px-4 font-sans antialiased text-zinc-900 selection:bg-emerald-100">
    <div class="w-full max-w-sm">

      <div class="flex flex-col items-center text-center">
        <div class="flex h-10 w-10 items-center justify-center rounded-xl bg-zinc-900 shadow-sm">
          <div class="h-3.5 w-3.5 rounded-xs bg-emerald-400"></div>
        </div>

        <h1 class="mt-6 text-2xl font-semibold tracking-tight text-zinc-900">
          Welcome back
        </h1>
        <p class="mt-2 text-sm text-zinc-500">
          Enter your details to access your Meal Share account
        </p>
      </div>

      <form @submit.prevent="handleLogin" class="mt-8 space-y-4">
        <div class="space-y-1.5">
          <label for="email" class="text-xs font-medium text-zinc-700">Email address</label>
          <input id="email" v-model="email" type="email" required placeholder="name@example.com"
            class="block w-full h-10 rounded-lg border border-zinc-200 bg-white px-3 text-sm text-zinc-900 placeholder-zinc-400 transition-all focus:border-zinc-900 focus:outline-none focus:ring-1 focus:ring-zinc-900" />
        </div>

        <div class="space-y-1.5">
          <div class="flex items-center justify-between">
            <label for="password" class="text-xs font-medium text-zinc-700">Password</label>
            <a href="#" class="text-xs font-medium text-emerald-700 hover:text-emerald-600 transition-colors">
              Forgot password?
            </a>
          </div>
          <input id="password" v-model="password" type="password" required placeholder="••••••••"
            class="block w-full h-10 rounded-lg border border-zinc-200 bg-white px-3 text-sm text-zinc-900 placeholder-zinc-400 transition-all focus:border-zinc-900 focus:outline-none focus:ring-1 focus:ring-zinc-900" />
        </div>

        <button type="submit"
          class="inline-flex h-10 w-full items-center justify-center rounded-lg bg-zinc-900 text-sm font-medium text-zinc-50 shadow-sm transition-colors hover:bg-zinc-800 focus:outline-none focus:ring-2 focus:ring-zinc-950 focus:ring-offset-2">
          Sign in
        </button>
      </form>

      <p class="mt-6 text-center text-xs text-zinc-500">
        Don't have an account?
        <router-link to="/auth/signup"
          class="font-medium text-zinc-900 underline underline-offset-4 hover:text-zinc-700">
          Sign up
        </router-link>
      </p>

      <div class="mt-8 flex justify-center border-t border-zinc-200/60 pt-4">
        <router-link to="/"
          class="inline-flex items-center gap-1.5 text-xs font-medium text-zinc-500 hover:text-zinc-900 transition-colors">
          <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none"
            stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round">
            <path d="m12 19-7-7 7-7" />
            <path d="M19 12H5" />
          </svg>
          Back to home
        </router-link>
      </div>

    </div>
  </main>
</template>

<script setup>

import { ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'


const router = useRouter()


// form data
const email = ref('')
const password = ref('')


// ui state
const isLoading = ref(false)
const errorMessage = ref('')



const handleLogin = async () => {

  isLoading.value = true
  errorMessage.value = ''


  try {


    const baseApiUrl = import.meta.env.VITE_API_URL


    const response = await axios.post(
      `${baseApiUrl}/auth/login`,
      {

        email: email.value,

        password: password.value

      }
    )



    if (response.data.token) {

      localStorage.setItem(
        'auth_token',
        response.data.token
      )


      router.push('/')

    }


  }
  catch (error) {

    errorMessage.value =
      error.response?.data?.message
      || 'Failed to connect.'

  }
  finally {

    isLoading.value = false

  }

}


</script>