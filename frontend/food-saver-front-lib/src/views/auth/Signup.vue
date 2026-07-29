<template>
    <main
        class="flex min-h-screen items-center justify-center bg-[#FAFAFA] px-4 font-sans antialiased text-zinc-900 selection:bg-emerald-100">
        <div class="w-full max-w-sm">

            <div class="flex flex-col items-center text-center">
                <div class="flex h-10 w-10 items-center justify-center rounded-xl bg-zinc-900 shadow-sm">
                    <div class="h-3.5 w-3.5 rounded-xs bg-emerald-400"></div>
                </div>

                <h1 class="mt-6 text-2xl font-semibold tracking-tight text-zinc-900">
                    Create your account
                </h1>
                <p class="mt-2 text-sm text-zinc-500">
                    Join Meal Share to start routing surplus food to those in need
                </p>
            </div>

            <form @submit.prevent="handleSignup" class="mt-8 space-y-4">
                <div class="space-y-1.5">
                    <label for="name" class="text-xs font-medium text-zinc-700">Full name</label>
                    <input id="name" v-model="name" type="text" required placeholder="Alex Carter"
                        class="block w-full h-10 rounded-lg border border-zinc-200 bg-white px-3 text-sm text-zinc-900 placeholder-zinc-400 transition-all focus:border-zinc-900 focus:outline-none focus:ring-1 focus:ring-zinc-900" />
                </div>

                <div class="space-y-1.5">
                    <label for="email" class="text-xs font-medium text-zinc-700">Email address</label>
                    <input id="email" v-model="email" type="email" required placeholder="name@example.com"
                        class="block w-full h-10 rounded-lg border border-zinc-200 bg-white px-3 text-sm text-zinc-900 placeholder-zinc-400 transition-all focus:border-zinc-900 focus:outline-none focus:ring-1 focus:ring-zinc-900" />
                </div>

                <div class="space-y-1.5">
                    <label class="text-xs font-medium text-zinc-700">I want to</label>
                    <div class="grid grid-cols-2 gap-2">
                        <label
                            :class="[
                                'flex items-center justify-center p-2.5 border rounded-lg cursor-pointer transition-all text-center',
                                role === 'DONOR'
                                    ? 'border-emerald-500 bg-emerald-50 text-emerald-700'
                                    : 'border-zinc-200 bg-white text-zinc-600 hover:border-zinc-300'
                            ]"
                        >
                            <input type="radio" v-model="role" value="DONOR" class="sr-only" />
                            <div>
                                <div class="text-lg">🍎</div>
                                <div class="text-xs font-medium mt-0.5">Donate Food</div>
                            </div>
                        </label>
                        <label
                            :class="[
                                'flex items-center justify-center p-2.5 border rounded-lg cursor-pointer transition-all text-center',
                                role === 'RECEIVER'
                                    ? 'border-emerald-500 bg-emerald-50 text-emerald-700'
                                    : 'border-zinc-200 bg-white text-zinc-600 hover:border-zinc-300'
                            ]"
                        >
                            <input type="radio" v-model="role" value="RECEIVER" class="sr-only" />
                            <div>
                                <div class="text-lg">🤝</div>
                                <div class="text-xs font-medium mt-0.5">Receive Food</div>
                            </div>
                        </label>
                    </div>
                </div>

                <div class="space-y-1.5">
                    <label for="password" class="text-xs font-medium text-zinc-700">Password</label>
                    <input id="password" v-model="password" type="password" required
                        placeholder="Create a secure password"
                        class="block w-full h-10 rounded-lg border border-zinc-200 bg-white px-3 text-sm text-zinc-900 placeholder-zinc-400 transition-all focus:border-zinc-900 focus:outline-none focus:ring-1 focus:ring-zinc-900" />
                </div>

                <p class="text-[11px] leading-normal text-zinc-500">
                    By clicking sign up, you agree to our
                    <a href="#" class="underline underline-offset-2 hover:text-zinc-900">Terms of Service</a>
                    and
                    <a href="#" class="underline underline-offset-2 hover:text-zinc-900">Privacy Policy</a>.
                </p>

                <div v-if="errorMessage" class="p-3 rounded-lg bg-red-50 border border-red-200 text-sm text-red-700">
                    {{ errorMessage }}
                </div>

                <button type="submit"
                    class="inline-flex h-10 w-full items-center justify-center rounded-lg bg-zinc-900 text-sm font-medium text-zinc-50 shadow-sm transition-colors hover:bg-zinc-800 focus:outline-none focus:ring-2 focus:ring-zinc-950 focus:ring-offset-2 disabled:opacity-50 disabled:cursor-not-allowed"
                    :disabled="isLoading"
                >
                    <svg v-if="isLoading" class="animate-spin -ml-1 mr-2 h-4 w-4 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                        <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4" />
                        <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z" />
                    </svg>
                    {{ isLoading ? 'Creating account...' : 'Get started' }}
                </button>
            </form>

            <p class="mt-6 text-center text-xs text-zinc-500">
                Already have an account?
                <router-link to="/auth/login"
                    class="font-medium text-zinc-900 underline underline-offset-4 hover:text-zinc-700">
                    Sign in
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

// Reactive Form Inputs
const name = ref('')
const email = ref('')
const password = ref('')
const role = ref('DONOR')

// Reactive UX Flow Variables
const isLoading = ref(false)
const errorMessage = ref('')

const handleSignup = async () => {
    isLoading.value = true
    errorMessage.value = ''

    try {
        const baseApiUrl = import.meta.env.VITE_API_URL

        const response = await axios.post(`${baseApiUrl}/auth/signup`, {
            name: name.value,
            email: email.value,
            password: password.value,
            role: role.value
        })

        if (response.data.token) {
            localStorage.setItem('auth_token', response.data.token)
            router.push('/')
        } else {
            router.push('/auth/login')
        }
    } catch (error) {
        errorMessage.value = error.response?.data?.message || 'An error occurred during registration. Please try again.'
        console.error('Registration processing failure:', error)
    } finally {
        isLoading.value = false
    }
}
</script>
