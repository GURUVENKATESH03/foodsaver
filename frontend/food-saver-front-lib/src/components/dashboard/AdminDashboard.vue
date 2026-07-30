<template>
  <div class="space-y-8">
    <!-- Header -->
    <div class="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4">
      <div>
        <h1 class="font-display text-2xl font-bold text-[var(--color-text-dark)]">Admin Dashboard</h1>
        <p class="text-sm text-[var(--color-text-muted)] mt-1">Platform overview and management</p>
      </div>
      <router-link to="/dashboard/analytics" class="btn-primary text-sm">
        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z" />
        </svg>
        View Analytics
      </router-link>
    </div>

    <!-- Stats Grid -->
    <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-5">
      <StatCard title="Total Users" :value="stats.totalUsers" icon="users" trend="+12%" :trend-up="true" color="green" />
      <StatCard title="Total Donations" :value="stats.totalDonations" icon="donation" trend="+8%" :trend-up="true" />
      <StatCard title="Pending Requests" :value="stats.pendingRequests" icon="request" color="orange" />
      <StatCard title="Food Saved" :value="stats.foodSaved" icon="default" trend="+15%" :trend-up="true" color="green" />
    </div>

    <!-- Charts & Activity -->
    <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
      <!-- Donation Trends Chart -->
      <div class="lg:col-span-2 bg-white rounded-2xl border border-[var(--color-border)] p-6">
        <h3 class="font-display text-lg font-bold text-[var(--color-text-dark)] mb-5">Donation Trends</h3>
        <div class="h-72">
          <div class="flex items-end justify-between gap-2 h-full pb-1">
            <div v-for="(item, i) in chartData" :key="i" class="flex-1 flex flex-col items-center gap-2 h-full justify-end">
              <span class="text-[10px] font-mono text-[var(--color-text-muted)]">{{ item.value }}</span>
              <div class="w-full rounded-t-lg transition-all duration-300 hover:opacity-80" :style="{ height: item.pct + '%' }" :class="item.color"></div>
              <span class="text-[10px] text-[var(--color-text-muted)]">{{ item.label }}</span>
            </div>
          </div>
        </div>
      </div>

      <!-- Recent Activity -->
      <div class="bg-white rounded-2xl border border-[var(--color-border)] p-6">
        <h3 class="font-display text-lg font-bold text-[var(--color-text-dark)] mb-5">Recent Activity</h3>
        <div class="space-y-4">
          <div
            v-for="activity in recentActivity"
            :key="activity.id"
            class="flex items-start gap-3 p-3 rounded-xl hover:bg-[var(--color-bg-warm)] transition-colors"
          >
            <div class="w-9 h-9 rounded-full bg-[var(--color-bg-warm)] flex items-center justify-center flex-shrink-0">
              <span class="text-base">{{ activity.icon }}</span>
            </div>
            <div class="min-w-0">
              <p class="text-sm font-medium text-[var(--color-text-dark)]">{{ activity.description }}</p>
              <p class="text-xs text-[var(--color-text-muted)] mt-0.5">{{ activity.time }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Recent Users Table -->
    <div class="bg-white rounded-2xl border border-[var(--color-border)] overflow-hidden">
      <div class="px-6 py-4 border-b border-[var(--color-border)] flex items-center justify-between">
        <h3 class="font-display text-lg font-bold text-[var(--color-text-dark)]">Recent Users</h3>
        <router-link to="/dashboard/users" class="text-sm font-medium text-[var(--color-secondary)] hover:text-[var(--color-secondary-hover)] transition-colors">
          View all
        </router-link>
      </div>
      <div class="overflow-x-auto">
        <table class="min-w-full divide-y divide-[var(--color-border)]">
          <thead>
            <tr class="bg-[var(--color-bg-warm)]">
              <th class="px-6 py-3 text-left text-[11px] font-semibold text-[var(--color-text-muted)] uppercase tracking-wider">User</th>
              <th class="px-6 py-3 text-left text-[11px] font-semibold text-[var(--color-text-muted)] uppercase tracking-wider">Role</th>
              <th class="px-6 py-3 text-left text-[11px] font-semibold text-[var(--color-text-muted)] uppercase tracking-wider">Status</th>
              <th class="px-6 py-3 text-left text-[11px] font-semibold text-[var(--color-text-muted)] uppercase tracking-wider">Joined</th>
            </tr>
          </thead>
          <tbody class="divide-y divide-[var(--color-border)]">
            <tr v-for="user in recentUsers" :key="user.id" class="hover:bg-[var(--color-bg-warm)]/50 transition-colors">
              <td class="px-6 py-4 whitespace-nowrap">
                <div class="flex items-center gap-3">
                  <div class="w-8 h-8 rounded-full bg-[var(--color-primary)]/15 flex items-center justify-center text-xs font-bold text-[var(--color-primary)] flex-shrink-0">
                    {{ user.name.charAt(0) }}
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
                    user.active ? 'bg-[var(--color-secondary)]/10 text-[var(--color-secondary)]' : 'bg-[var(--color-urgent)]/10 text-[var(--color-urgent)]'
                  ]"
                >
                  {{ user.active ? 'Active' : 'Inactive' }}
                </span>
              </td>
              <td class="px-6 py-4 whitespace-nowrap text-sm text-[var(--color-text-muted)]">
                {{ user.createdAt }}
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import StatCard from '@/components/dashboard/StatCard.vue'
import RoleBadge from '@/components/RoleBadge.vue'

const stats = ref({
  totalUsers: 1248,
  totalDonations: 3856,
  pendingRequests: 42,
  foodSaved: 12580
})

const chartData = ref([
  { label: 'Jan', value: 320, pct: 44, color: 'bg-[var(--color-primary)]' },
  { label: 'Feb', value: 450, pct: 62, color: 'bg-[var(--color-primary)]' },
  { label: 'Mar', value: 520, pct: 72, color: 'bg-[var(--color-primary)]' },
  { label: 'Apr', value: 480, pct: 66, color: 'bg-[var(--color-primary)]' },
  { label: 'May', value: 600, pct: 83, color: 'bg-[var(--color-primary)]' },
  { label: 'Jun', value: 720, pct: 100, color: 'bg-[var(--color-primary)]' }
])

const recentActivity = ref([
  { id: 1, icon: '🍎', description: 'New donation from Whole Foods Market', time: '2 min ago' },
  { id: 2, icon: '🤝', description: 'Request approved for Community Center', time: '15 min ago' },
  { id: 3, icon: '👤', description: 'New user registered as Donor', time: '1 hour ago' },
  { id: 4, icon: '✅', description: 'Pickup completed — 25kg food saved', time: '2 hours ago' }
])

const recentUsers = ref([
  { id: 1, name: 'Sarah Johnson', email: 'sarah@example.com', role: 'DONOR', active: true, createdAt: '2024-01-15' },
  { id: 2, name: 'Mike Chen', email: 'mike@ngo.org', role: 'RECEIVER', active: true, createdAt: '2024-01-14' },
  { id: 3, name: 'Emma Davis', email: 'emma@restaurant.com', role: 'DONOR', active: true, createdAt: '2024-01-13' }
])
</script>
