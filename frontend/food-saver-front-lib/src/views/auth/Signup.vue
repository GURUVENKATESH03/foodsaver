<template>
  <AuthLayout>
    <!-- Header -->
    <div class="text-center mb-7">
      <h1 class="font-display text-2xl font-bold text-[var(--color-text-dark)]">
        Create your account
      </h1>
      <p class="mt-1.5 text-sm text-[var(--color-text-muted)]">
        Join Meal Share and help reduce food waste
      </p>
    </div>

    <!-- Error message -->
    <div
      v-if="errorMessage"
      class="mb-5 p-3 rounded-xl bg-[var(--color-urgent)]/8 border border-[var(--color-urgent)]/20 text-sm text-[var(--color-urgent)]"
      role="alert"
    >
      {{ errorMessage }}
    </div>

    <!-- Form -->
    <form @submit.prevent="handleSignup" class="space-y-5">
      <!-- Full name -->
      <div class="space-y-1.5">
        <label for="name" class="text-xs font-semibold text-[var(--color-text-dark)] tracking-wide uppercase">
          Full name
        </label>
        <input
          id="name"
          v-model="name"
          type="text"
          required
          placeholder="Alex Carter"
          class="block w-full h-11 rounded-xl border border-[var(--color-border)] bg-white px-4 text-sm text-[var(--color-text-dark)] placeholder:text-[var(--color-text-muted)] transition-all focus:border-[var(--color-primary)] focus:outline-none focus:ring-2 focus:ring-[var(--color-primary)]/20"
        />
      </div>

      <!-- Email -->
      <div class="space-y-1.5">
        <label for="email" class="text-xs font-semibold text-[var(--color-text-dark)] tracking-wide uppercase">
          Email address
        </label>
        <input
          id="email"
          v-model="email"
          type="email"
          required
          autocomplete="email"
          placeholder="name@example.com"
          class="block w-full h-11 rounded-xl border border-[var(--color-border)] bg-white px-4 text-sm text-[var(--color-text-dark)] placeholder:text-[var(--color-text-muted)] transition-all focus:border-[var(--color-primary)] focus:outline-none focus:ring-2 focus:ring-[var(--color-primary)]/20"
        />
      </div>

      <!-- Password -->
      <div class="space-y-1.5">
        <label for="password" class="text-xs font-semibold text-[var(--color-text-dark)] tracking-wide uppercase">
          Password
        </label>
        <input
          id="password"
          v-model="password"
          type="password"
          required
          autocomplete="new-password"
          placeholder="Create a secure password"
          class="block w-full h-11 rounded-xl border border-[var(--color-border)] bg-white px-4 text-sm text-[var(--color-text-dark)] placeholder:text-[var(--color-text-muted)] transition-all focus:border-[var(--color-primary)] focus:outline-none focus:ring-2 focus:ring-[var(--color-primary)]/20"
        />
      </div>

      <!-- Role selection -->
      <div class="space-y-2">
        <label class="text-xs font-semibold text-[var(--color-text-dark)] tracking-wide uppercase block">
          I want to
        </label>
        <div class="grid grid-cols-2 gap-3">
          <label
            :class="[
              'flex flex-col items-center gap-1.5 p-4 border-2 rounded-xl cursor-pointer transition-all text-center',
              role === 'DONOR'
                ? 'border-[var(--color-primary)] bg-[var(--color-primary)]/8 text-[var(--color-text-dark)]'
                : 'border-[var(--color-border)] bg-white text-[var(--color-text-muted)] hover:border-[var(--color-primary)]/40 hover:text-[var(--color-text-dark)]'
            ]"
          >
            <input type="radio" v-model="role" value="DONOR" class="sr-only" />
            <span class="text-2xl">🍎</span>
            <span class="text-xs font-semibold">Donate Food</span>
            <span class="text-[10px] leading-tight text-[var(--color-text-muted)] hidden sm:block">
              Share surplus meals
            </span>
          </label>
          <label
            :class="[
              'flex flex-col items-center gap-1.5 p-4 border-2 rounded-xl cursor-pointer transition-all text-center',
              role === 'RECEIVER'
                ? 'border-[var(--color-primary)] bg-[var(--color-primary)]/8 text-[var(--color-text-dark)]'
                : 'border-[var(--color-border)] bg-white text-[var(--color-text-muted)] hover:border-[var(--color-primary)]/40 hover:text-[var(--color-text-dark)]'
            ]"
          >
            <input type="radio" v-model="role" value="RECEIVER" class="sr-only" />
            <span class="text-2xl">🤝</span>
            <span class="text-xs font-semibold">Receive Food</span>
            <span class="text-[10px] leading-tight text-[var(--color-text-muted)] hidden sm:block">
              Find meals nearby
            </span>
          </label>
        </div>
      </div>

      <!-- Terms -->
      <p class="text-[11px] leading-normal text-[var(--color-text-muted)]">
        By clicking sign up, you agree to our
        <a href="#" class="font-medium text-[var(--color-secondary)] hover:text-[var(--color-secondary-hover)] transition-colors underline underline-offset-2">
          Terms of Service
        </a>
        and
        <a href="#" class="font-medium text-[var(--color-secondary)] hover:text-[var(--color-secondary-hover)] transition-colors underline underline-offset-2">
          Privacy Policy
        </a>.
      </p>

      <!-- Submit -->
      <button
        type="submit"
        class="btn-primary w-full h-11 text-sm"
        :disabled="isLoading"
      >
        <svg
          v-if="isLoading"
          class="animate-spin -ml-1 mr-2 h-4 w-4 text-[var(--color-text-dark)]"
          xmlns="http://www.w3.org/2000/svg"
          fill="none"
          viewBox="0 0 24 24"
        >
          <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4" />
          <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z" />
        </svg>
        {{ isLoading ? 'Creating account...' : 'Get started' }}
      </button>
    </form>

    <!-- Sign in link -->
    <p class="mt-7 text-center text-xs text-[var(--color-text-muted)]">
      Already have an account?
      <router-link
        to="/auth/login"
        class="font-semibold text-[var(--color-secondary)] hover:text-[var(--color-secondary-hover)] transition-colors"
      >
        Sign in
      </router-link>
    </p>

    <!-- Back link -->
    <div class="mt-5 flex justify-center">
      <router-link
        to="/"
        class="inline-flex items-center gap-1.5 text-xs font-medium text-[var(--color-text-muted)] hover:text-[var(--color-text-dark)] transition-colors"
      >
        <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round">
          <path d="m12 19-7-7 7-7" />
          <path d="M19 12H5" />
        </svg>
        Back to home
      </router-link>
    </div>
  </AuthLayout>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuth } from '@/composables/useAuth'
import AuthLayout from '@/layouts/AuthLayout.vue'

const router = useRouter()
const { register } = useAuth()

const name = ref('')
const email = ref('')
const password = ref('')
const role = ref('DONOR')
const isLoading = ref(false)
const errorMessage = ref('')

async function handleSignup() {
  isLoading.value = true
  errorMessage.value = ''

  try {
    const result = await register({
      name: name.value,
      email: email.value,
      password: password.value,
      role: role.value
    })

    if (result.success) {
      router.push('/dashboard')
    } else {
      errorMessage.value = result.error || 'Registration failed. Please try again.'
    }
  } catch (error) {
    errorMessage.value = 'Unable to connect. Please try again.'
  } finally {
    isLoading.value = false
  }
}
</script>
