<template>
  <div class="space-y-6">
    <!-- Header -->
    <div class="flex items-center justify-between">
      <div>
        <h1 class="text-2xl font-bold text-secondary-900">Admin Dashboard</h1>
        <p class="text-secondary-500 mt-1">Platform overview and management</p>
      </div>
      <router-link to="/dashboard/analytics" class="btn-primary">
        View Analytics
      </router-link>
    </div>
    
    <!-- Stats Grid -->
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
      <StatCard 
        title="Total Users" 
        :value="stats.totalUsers" 
        icon="users"
        trend="+12%"
        trend-up
      />
      <StatCard 
        title="Total Donations" 
        :value="stats.totalDonations" 
        icon="donation"
        trend="+8%"
        trend-up
      />
      <StatCard 
        title="Pending Requests" 
        :value="stats.pendingRequests" 
        icon="request"
        color="orange"
      />
      <StatCard 
        title="Food Saved (kg)" 
        :value="stats.foodSaved" 
        icon="scale"
        trend="+15%"
        trend-up
        color="green"
      />
    </div>
    
    <!-- Charts & Tables -->
    <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
      <!-- Donation Analytics Chart -->
      <div class="lg:col-span-2 card p-6">
        <h3 class="text-lg font-semibold text-secondary-900 mb-4">Donation Trends</h3>
        <div class="h-80">
          <Line :data="chartData" :options="chartOptions" />
        </div>
      </div>
      
      <!-- Recent Activity -->
      <div class="card p-6">
        <h3 class="text-lg font-semibold text-secondary-900 mb-4">Recent Activity</h3>
        <div class="space-y-4">
          <div 
            v-for="activity in recentActivity" 
            :key="activity.id"
            class="flex items-start space-x-3"
          >
            <div class="w-8 h-8 rounded-full bg-primary-100 flex items-center justify-center flex-shrink-0">
              <span class="text-sm">{{ activity.icon }}</span>
            </div>
            <div>
              <p class="text-sm font-medium text-secondary-900">{{ activity.description }}</p>
              <p class="text-xs text-secondary-500">{{ activity.time }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
    
    <!-- User Management Preview -->
    <div class="card overflow-hidden">
      <div class="px-6 py-4 border-b border-secondary-200 flex items-center justify-between">
        <h3 class="text-lg font-semibold text-secondary-900">Recent Users</h3>
        <router-link to="/dashboard/users" class="text-sm text-primary-600 hover:text-primary-500">
          View all
        </router-link>
      </div>
      <div class="overflow-x-auto">
        <table class="min-w-full divide-y divide-secondary-200">
          <thead class="bg-secondary-50">
            <tr>
              <th class="px-6 py-3 text-left text-xs font-medium text-secondary-500 uppercase tracking-wider">User</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-secondary-500 uppercase tracking-wider">Role</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-secondary-500 uppercase tracking-wider">Status</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-secondary-500 uppercase tracking-wider">Joined</th>
            </tr>
          </thead>
          <tbody class="bg-white divide-y divide-secondary-200">
            <tr v-for="user in recentUsers" :key="user.id">
              <td class="px-6 py-4 whitespace-nowrap">
                <div class="flex items-center">
                  <img :src="user.avatar" class="w-8 h-8 rounded-full mr-3" />
                  <div>
                    <div class="text-sm font-medium text-secondary-900">{{ user.name }}</div>
                    <div class="text-sm text-secondary-500">{{ user.email }}</div>
                  </div>
                </div>
              </td>
              <td class="px-6 py-4 whitespace-nowrap">
                <RoleBadge :role="user.role" />
              </td>
              <td class="px-6 py-4 whitespace-nowrap">
                <span :class="[
                  'px-2 py-1 text-xs rounded-full',
                  user.active ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'
                ]">
                  {{ user.active ? 'Active' : 'Inactive' }}
                </span>
              </td>
              <td class="px-6 py-4 whitespace-nowrap text-sm text-secondary-500">
                {{ formatDate(user.createdAt) }}
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { Line } from 'vue-chartjs'
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
  Filler
} from 'chart.js'
import StatCard from '@/components/dashboard/StatCard.vue'
import RoleBadge from '@/components/common/RoleBadge.vue'
import { formatDate } from '@/utils/helpers'

ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend, Filler)

const stats = ref({
  totalUsers: 1248,
  totalDonations: 3856,
  pendingRequests: 42,
  foodSaved: 12580
})

const chartData = ref({
  labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],
  datasets: [{
    label: 'Donations',
    data: [320, 450, 520, 480, 600, 720],
    borderColor: '#16a34a',
    backgroundColor: 'rgba(22, 163, 74, 0.1)',
    fill: true,
    tension: 0.4
  }, {
    label: 'Requests',
    data: [280, 380, 450, 420, 550, 650],
    borderColor: '#3b82f6',
    backgroundColor: 'rgba(59, 130, 246, 0.1)',
    fill: true,
    tension: 0.4
  }]
})

const chartOptions = {
  responsive: true,
  maintainAspectRatio: false,
  plugins: {
    legend: { position: 'bottom' }
  },
  scales: {
    y: { beginAtZero: true, grid: { color: '#f1f5f9' } },
    x: { grid: { display: false } }
  }
}

const recentActivity = ref([
  { id: 1, icon: '🍎', description: 'New donation from Whole Foods Market', time: '2 min ago' },
  { id: 2, icon: '🤝', description: 'Request approved for Community Center', time: '15 min ago' },
  { id: 3, icon: '👤', description: 'New user registered as Donor', time: '1 hour ago' },
  { id: 4, icon: '✅', description: 'Pickup completed - 25kg food saved', time: '2 hours ago' }
])

const recentUsers = ref([
  { id: 1, name: 'Sarah Johnson', email: 'sarah@example.com', role: 'DONOR', active: true, createdAt: '2024-01-15', avatar: 'https://ui-avatars.com/api/?name=Sarah+Johnson&background=random' },
  { id: 2, name: 'Mike Chen', email: 'mike@ngo.org', role: 'RECEIVER', active: true, createdAt: '2024-01-14', avatar: 'https://ui-avatars.com/api/?name=Mike+Chen&background=random' },
  { id: 3, name: 'Emma Davis', email: 'emma@restaurant.com', role: 'DONOR', active: true, createdAt: '2024-01-13', avatar: 'https://ui-avatars.com/api/?name=Emma+Davis&background=random' }
])
</script>