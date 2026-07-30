<template>
  <div class="space-y-6">
    <!-- Header -->
    <div class="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4">
      <div>
        <h1 class="font-display text-2xl font-bold text-[var(--color-text-dark)]">User Management</h1>
        <p class="text-sm text-[var(--color-text-muted)] mt-1">Manage platform users and their roles</p>
      </div>
      <button class="btn-primary text-sm inline-flex items-center">
        <svg class="w-4 h-4 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M18 9v3m0 0v3m0-3h3m-3 0h-3m-2-5a4 4 0 11-8 0 4 4 0 018 0zM3 20a6 6 0 0112 0v1H3v-1z" />
        </svg>
        Invite User
      </button>
    </div>

    <!-- Filters -->
    <div class="bg-white rounded-2xl border border-[var(--color-border)] p-4">
      <div class="flex flex-col md:flex-row gap-3">
        <div class="flex-1">
          <input
            v-model="searchQuery"
            type="text"
            placeholder="Search users by name or email..."
            class="block w-full h-10 rounded-xl border border-[var(--color-border)] bg-white px-4 text-sm text-[var(--color-text-dark)] placeholder:text-[var(--color-text-muted)] transition-all focus:border-[var(--color-primary)] focus:outline-none focus:ring-2 focus:ring-[var(--color-primary)]/20"
            @input="onSearch"
          />
        </div>
        <div class="w-full md:w-36">
          <select
            v-model="roleFilter"
            class="block w-full h-10 rounded-xl border border-[var(--color-border)] bg-white px-3 text-sm text-[var(--color-text-dark)] transition-all focus:border-[var(--color-primary)] focus:outline-none focus:ring-2 focus:ring-[var(--color-primary)]/20"
            @change="fetchUsers"
          >
            <option value="">All Roles</option>
            <option value="ADMIN">Admin</option>
            <option value="DONOR">Donor</option>
            <option value="RECEIVER">Receiver</option>
          </select>
        </div>
        <div class="w-full md:w-36">
          <select
            v-model="statusFilter"
            class="block w-full h-10 rounded-xl border border-[var(--color-border)] bg-white px-3 text-sm text-[var(--color-text-dark)] transition-all focus:border-[var(--color-primary)] focus:outline-none focus:ring-2 focus:ring-[var(--color-primary)]/20"
            @change="fetchUsers"
          >
            <option value="">All Status</option>
            <option value="active">Active</option>
            <option value="inactive">Inactive</option>
            <option value="locked">Locked</option>
          </select>
        </div>
      </div>
    </div>

    <!-- Users Table -->
    <div class="bg-white rounded-2xl border border-[var(--color-border)] overflow-hidden">
      <div class="overflow-x-auto">
        <table class="min-w-full divide-y divide-[var(--color-border)]">
          <thead>
            <tr class="bg-[var(--color-bg-warm)]">
              <th class="px-6 py-3 text-left text-[11px] font-semibold text-[var(--color-text-muted)] uppercase tracking-wider">User</th>
              <th class="px-6 py-3 text-left text-[11px] font-semibold text-[var(--color-text-muted)] uppercase tracking-wider">Role</th>
              <th class="px-6 py-3 text-left text-[11px] font-semibold text-[var(--color-text-muted)] uppercase tracking-wider">Status</th>
              <th class="px-6 py-3 text-left text-[11px] font-semibold text-[var(--color-text-muted)] uppercase tracking-wider">Joined</th>
              <th class="px-6 py-3 text-left text-[11px] font-semibold text-[var(--color-text-muted)] uppercase tracking-wider">Actions</th>
            </tr>
          </thead>
          <tbody class="divide-y divide-[var(--color-border)]">
            <tr v-if="loading">
              <td colspan="5" class="px-6 py-12 text-center">
                <div class="w-6 h-6 border-2 border-[var(--color-primary)] border-t-transparent rounded-full animate-spin mx-auto" />
              </td>
            </tr>
            <tr v-else-if="paginatedUsers.length === 0">
              <td colspan="5" class="px-6 py-12 text-center text-sm text-[var(--color-text-muted)]">
                No users found
              </td>
            </tr>
            <tr v-for="user in paginatedUsers" :key="user.id" class="hover:bg-[var(--color-bg-warm)]/50 transition-colors">
              <td class="px-6 py-4 whitespace-nowrap">
                <div class="flex items-center gap-3">
                  <div class="w-8 h-8 rounded-full bg-[var(--color-primary)]/15 flex items-center justify-center text-xs font-bold text-[var(--color-primary)] flex-shrink-0">
                    {{ (user.name || '?').charAt(0) }}
                  </div>
                  <div>
                    <div class="text-sm font-medium text-[var(--color-text-dark)]">{{ user.name }}</div>
                    <div class="text-xs text-[var(--color-text-muted)]">{{ user.email }}</div>
                  </div>
                </div>
              </td>
              <td class="px-6 py-4 whitespace-nowrap">
                <RoleBadge :role="user.role" />
              </td>
              <td class="px-6 py-4 whitespace-nowrap">
                <span
                  :class="[
                    'px-2.5 py-0.5 text-[11px] font-semibold rounded-full',
                    user.active ? 'bg-[var(--color-secondary)]/10 text-[var(--color-secondary)]' :
                    user.locked ? 'bg-[var(--color-urgent)]/10 text-[var(--color-urgent)]' : 'bg-[var(--color-primary)]/12 text-[#A6732A]'
                  ]"
                >
                  {{ user.active ? 'Active' : user.locked ? 'Locked' : 'Inactive' }}
                </span>
              </td>
              <td class="px-6 py-4 whitespace-nowrap text-sm text-[var(--color-text-muted)]">
                {{ user.createdAt }}
              </td>
              <td class="px-6 py-4 whitespace-nowrap">
                <div class="flex items-center gap-1">
                  <button
                    @click="toggleUserStatus(user)"
                    class="p-1.5 rounded-lg text-[var(--color-text-muted)] hover:text-[var(--color-primary)] hover:bg-[var(--color-primary)]/8 transition-all"
                    :title="user.active ? 'Deactivate' : 'Activate'"
                  >
                    <svg v-if="user.active" class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M18.364 18.364A9 9 0 005.636 5.636m12.728 12.728A9 9 0 015.636 5.636m12.728 12.728L5.636 5.636" />
                    </svg>
                    <svg v-else class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z" />
                    </svg>
                  </button>
                  <button
                    @click="editUser(user)"
                    class="p-1.5 rounded-lg text-[var(--color-text-muted)] hover:text-[var(--color-primary)] hover:bg-[var(--color-primary)]/8 transition-all"
                    title="Edit user"
                  >
                    <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z" />
                    </svg>
                  </button>
                  <button
                    @click="confirmDeleteUser(user)"
                    class="p-1.5 rounded-lg text-[var(--color-text-muted)] hover:text-[var(--color-urgent)] hover:bg-[var(--color-urgent)]/8 transition-all"
                    title="Delete user"
                  >
                    <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
                    </svg>
                  </button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- Pagination -->
    <div v-if="totalPages > 1" class="flex items-center justify-between">
      <p class="text-sm text-[var(--color-text-muted)]">
        Showing {{ (currentPage - 1) * perPage + 1 }}–{{ Math.min(currentPage * perPage, totalUsers) }} of {{ totalUsers }}
      </p>
      <div class="flex items-center gap-2">
        <button
          @click="changePage(currentPage - 1)"
          :disabled="currentPage === 1"
          class="p-2 rounded-xl border border-[var(--color-border)] text-[var(--color-text-muted)] hover:text-[var(--color-text-dark)] hover:bg-[var(--color-bg-warm)] disabled:opacity-40 disabled:cursor-not-allowed transition-all"
        >
          <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7" />
          </svg>
        </button>
        <span class="text-sm text-[var(--color-text-muted)] px-1">{{ currentPage }} / {{ totalPages }}</span>
        <button
          @click="changePage(currentPage + 1)"
          :disabled="currentPage === totalPages"
          class="p-2 rounded-xl border border-[var(--color-border)] text-[var(--color-text-muted)] hover:text-[var(--color-text-dark)] hover:bg-[var(--color-bg-warm)] disabled:opacity-40 disabled:cursor-not-allowed transition-all"
        >
          <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
          </svg>
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import RoleBadge from '@/components/RoleBadge.vue'
import { debounce } from '@/utils/helpers'

const searchQuery = ref('')
const roleFilter = ref('')
const statusFilter = ref('')
const currentPage = ref(1)
const perPage = ref(10)
const loading = ref(false)

const users = ref([])

onMounted(() => {
  loading.value = true
  setTimeout(() => {
    users.value = [
      { id: 1, name: 'Sarah Johnson', email: 'sarah@example.com', role: 'ADMIN', active: true, locked: false, createdAt: '2024-01-15' },
      { id: 2, name: 'Mike Chen', email: 'mike@ngo.org', role: 'RECEIVER', active: true, locked: false, createdAt: '2024-01-14' },
      { id: 3, name: 'Emma Davis', email: 'emma@restaurant.com', role: 'DONOR', active: true, locked: false, createdAt: '2024-01-13' },
      { id: 4, name: 'John Smith', email: 'john@example.com', role: 'DONOR', active: false, locked: false, createdAt: '2024-01-10' },
      { id: 5, name: 'Lisa Wong', email: 'lisa@community.org', role: 'RECEIVER', active: true, locked: true, createdAt: '2024-01-08' },
      { id: 6, name: 'Alex Turner', email: 'alex@bakery.com', role: 'DONOR', active: true, locked: false, createdAt: '2024-01-05' },
      { id: 7, name: 'Maria Garcia', email: 'maria@foodbank.org', role: 'RECEIVER', active: true, locked: false, createdAt: '2024-01-03' },
      { id: 8, name: 'David Kim', email: 'david@example.com', role: 'ADMIN', active: true, locked: false, createdAt: '2023-12-28' }
    ]
    loading.value = false
  }, 500)
})

const filteredUsers = computed(() => {
  let result = [...users.value]
  if (searchQuery.value) {
    const q = searchQuery.value.toLowerCase()
    result = result.filter(u => u.name.toLowerCase().includes(q) || u.email.toLowerCase().includes(q))
  }
  if (roleFilter.value) result = result.filter(u => u.role === roleFilter.value)
  if (statusFilter.value) {
    result = result.filter(u => {
      if (statusFilter.value === 'active') return u.active
      if (statusFilter.value === 'inactive') return !u.active && !u.locked
      if (statusFilter.value === 'locked') return u.locked
      return true
    })
  }
  return result
})

const paginatedUsers = computed(() => {
  const start = (currentPage.value - 1) * perPage.value
  return filteredUsers.value.slice(start, start + perPage.value)
})

const totalUsers = computed(() => filteredUsers.value.length)
const totalPages = computed(() => Math.ceil(totalUsers.value / perPage.value))

const onSearch = debounce(() => { currentPage.value = 1 }, 300)

function fetchUsers() { currentPage.value = 1 }
function changePage(page) { if (page >= 1 && page <= totalPages.value) currentPage.value = page }
function toggleUserStatus(user) { user.active = !user.active }
function editUser(user) { console.log('Edit user:', user.id) }
function confirmDeleteUser(user) {
  if (confirm(`Delete ${user.name}?`)) {
    users.value = users.value.filter(u => u.id !== user.id)
  }
}
</script>
