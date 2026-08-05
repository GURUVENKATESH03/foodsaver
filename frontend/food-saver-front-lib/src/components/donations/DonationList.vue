<template>
  <div class="space-y-8 py-4">
    <!-- Hero Banner Header -->
    <div class="bg-[#0F172A] rounded-3xl p-8 md:p-10 text-white relative overflow-hidden shadow-lg">
      <div class="absolute -top-24 -right-24 w-80 h-80 bg-teal-500/10 rounded-full blur-3xl pointer-events-none" />
      
      <div class="flex flex-col md:flex-row md:items-center justify-between gap-6 relative z-10">
        <div class="max-w-2xl">
          <span class="inline-flex items-center gap-2 px-3.5 py-1 rounded-full bg-teal-500/15 border border-teal-500/30 text-teal-400 text-xs font-semibold font-mono uppercase tracking-wider mb-4">
            <span class="w-1.5 h-1.5 rounded-full bg-teal-400 animate-pulse" />
            Live Food Sharing Feed
          </span>
          <h1 class="font-display text-3xl sm:text-4xl font-bold tracking-tight text-white mb-3">
            Available Meals Near You
          </h1>
          <p class="text-slate-300 text-sm md:text-base leading-relaxed font-body">
            Browse fresh home-cooked dishes, surplus produce, and bakery items shared by neighbors in your local community.
          </p>
        </div>

        <router-link
          to="/donations/create"
          class="inline-flex items-center justify-center gap-2.5 px-6 py-3.5 bg-teal-600 hover:bg-teal-500 text-white font-semibold text-sm rounded-xl border border-teal-500/30 shadow-md hover:shadow-lg transition-all duration-200 shrink-0 font-body"
        >
          <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4" />
          </svg>
          Share a Meal
        </router-link>
      </div>

      <!-- Quick Metrics Bar -->
      <div class="mt-8 pt-6 border-t border-slate-800 flex flex-wrap items-center gap-6 text-xs font-mono text-slate-300">
        <div class="flex items-center gap-2">
          <span class="w-2 h-2 rounded-full bg-emerald-400" />
          <span>{{ displayedDonations.length }} Meals Available Today</span>
        </div>
        <div class="flex items-center gap-2">
          <span class="w-2 h-2 rounded-full bg-teal-400" />
          <span>Average 1.2 km Nearby</span>
        </div>
        <div class="flex items-center gap-2">
          <span class="w-2 h-2 rounded-full bg-amber-400" />
          <span>100% Free Community Sharing</span>
        </div>
      </div>
    </div>

    <!-- Category Quick Chips -->
    <div class="flex items-center gap-2 overflow-x-auto pb-2 scrollbar-none">
      <button
        v-for="cat in categoryChips"
        :key="cat.id"
        @click="selectCategoryChip(cat.id)"
        :class="[
          'px-4 py-2 rounded-xl text-xs font-semibold whitespace-nowrap transition-all font-body cursor-pointer',
          activeCategory === cat.id
            ? 'bg-teal-600 text-white shadow-sm'
            : 'bg-white text-slate-700 border border-slate-200 hover:bg-slate-50'
        ]"
      >
        {{ cat.icon }} {{ cat.label }}
      </button>
    </div>

    <!-- Search & Filters Container -->
    <DonationFilter v-model="filters" @apply="applyFilters" />

    <!-- Loading State -->
    <div v-if="loading" class="flex justify-center py-20">
      <div class="flex flex-col items-center gap-3">
        <div class="w-10 h-10 border-3 border-teal-600 border-t-transparent rounded-full animate-spin" />
        <span class="text-xs font-mono text-slate-500">Loading nearby meals...</span>
      </div>
    </div>

    <!-- Empty State -->
    <div v-else-if="displayedDonations.length === 0" class="bg-white rounded-3xl border border-slate-200 p-12 text-center max-w-md mx-auto shadow-sm">
      <div class="w-16 h-16 rounded-2xl bg-teal-50 text-teal-600 flex items-center justify-center mx-auto mb-4 text-3xl">
        🍲
      </div>
      <h3 class="font-display text-xl font-bold text-slate-900 mb-2">No meals match your criteria</h3>
      <p class="text-sm text-slate-500 font-body mb-6">Try clearing your search query or selecting another food category.</p>
      <button @click="resetAllFilters" class="px-5 py-2.5 bg-slate-900 text-white font-semibold text-xs rounded-xl hover:bg-slate-800 transition-all font-body">
        Reset All Filters
      </button>
    </div>

    <!-- Donations Grid -->
    <div v-else class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
      <FoodCard
        v-for="donation in displayedDonations"
        :key="donation.id"
        :donation="donation"
        @request="handleRequest"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useDonation } from '@/composables/useDonation'
import { useAuth } from '@/composables/useAuth'
import FoodCard from '@/components/donations/FoodCard.vue'
import DonationFilter from '@/components/donations/DonationFilter.vue'

const { donations, loading, loadDonations, setFilters } = useDonation()
const { isDonor, isReceiver, isAuthenticated } = useAuth()

const activeCategory = ref('')

const filters = ref({
  category: '',
  status: 'AVAILABLE',
  search: ''
})

const categoryChips = [
  { id: '', label: 'All Meals', icon: '🍽️' },
  { id: 'prepared', label: 'Prepared Dishes', icon: '🍲' },
  { id: 'bakery', label: 'Bakery & Bread', icon: '🥖' },
  { id: 'produce', label: 'Fresh Produce', icon: '🥗' },
  { id: 'pantry', label: 'Pantry Goods', icon: '🥫' }
]

// Fallback sample mock meals if backend API is not running or returns empty array
const mockMeals = [
  {
    id: 101,
    title: 'Homemade Vegetable Biryani',
    description: 'Fragrant basmati rice with seasonal vegetables, saffron, and fried onions. Prepared fresh today.',
    quantity: 4,
    unit: 'portions',
    distance: '0.4',
    category: 'prepared',
    status: 'AVAILABLE',
    donorName: 'Priya Sharma',
    pickupBy: 'Today 7:30 PM'
  },
  {
    id: 102,
    title: 'Freshly Baked Artisanal Sourdough',
    description: 'Crusty 24-hour slow fermentation sourdough loaf baked this afternoon at home.',
    quantity: 2,
    unit: 'loaves',
    distance: '0.8',
    category: 'bakery',
    status: 'AVAILABLE',
    donorName: 'Sarah Jenkins',
    pickupBy: 'Tomorrow 12 PM'
  },
  {
    id: 103,
    title: 'Hearty Minestrone Soup',
    description: 'Italian vegetable soup with rich tomato broth, beans, and fresh basil.',
    quantity: 3,
    unit: 'portions',
    distance: '1.1',
    category: 'prepared',
    status: 'AVAILABLE',
    donorName: 'Marcus Cole',
    pickupBy: 'Today 8 PM'
  },
  {
    id: 104,
    title: 'Organic Apple & Citrus Crate',
    description: 'Fresh organic apples and oranges from local orchard surplus.',
    quantity: 5,
    unit: 'kg',
    distance: '1.5',
    category: 'produce',
    status: 'AVAILABLE',
    donorName: 'Green Orchard Market',
    pickupBy: 'Tomorrow 5 PM'
  },
  {
    id: 105,
    title: 'Creamy Chicken Tikka & Rice',
    description: 'Rich tomato curry served with basmati rice. Portioned individually.',
    quantity: 2,
    unit: 'meals',
    distance: '0.6',
    category: 'prepared',
    status: 'AVAILABLE',
    donorName: 'Anika Rahman',
    pickupBy: 'Today 9 PM'
  },
  {
    id: 106,
    title: 'Slow-cooked Red Lentil Dahl',
    description: 'Mildly spiced coconut lentil dahl served with cumin steamed rice.',
    quantity: 4,
    unit: 'portions',
    distance: '2.0',
    category: 'prepared',
    status: 'AVAILABLE',
    donorName: 'Fatima Al-Mansoor',
    pickupBy: 'Tomorrow 2 PM'
  }
]

onMounted(() => {
  loadDonations()
})

const displayedDonations = computed(() => {
  let list = donations.value?.length > 0 ? donations.value : mockMeals

  if (activeCategory.value) {
    list = list.filter(d => (d.category || '').toLowerCase() === activeCategory.value)
  }

  if (filters.value.search) {
    const q = filters.value.search.toLowerCase()
    list = list.filter(d => d.title.toLowerCase().includes(q) || d.description.toLowerCase().includes(q))
  }

  if (filters.value.category) {
    list = list.filter(d => (d.category || '').toLowerCase() === filters.value.category.toLowerCase())
  }

  return list
})

function selectCategoryChip(catId) {
  activeCategory.value = catId
  filters.value.category = catId
}

function applyFilters() {
  setFilters(filters.value)
  loadDonations()
}

function resetAllFilters() {
  activeCategory.value = ''
  filters.value = { category: '', status: 'AVAILABLE', search: '' }
  setFilters(filters.value)
  loadDonations()
}

function handleRequest(donationId) {
  console.log('Request donation:', donationId)
}
</script>
