<template>
  <section class="bg-[var(--color-bg)] py-20 md:py-28 border-t border-slate-200">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <div class="flex flex-col sm:flex-row sm:items-end justify-between gap-4 mb-10">
        <div>
          <span class="inline-flex items-center gap-2 px-3.5 py-1 rounded-full bg-[var(--color-primary)]/10 text-xs font-semibold text-[var(--color-primary)] tracking-wider uppercase mb-4 font-mono">
            Live Feed
          </span>
          <h2 class="font-display text-3xl sm:text-4xl font-bold text-[var(--color-text-dark)] tracking-tight">
            Available near you
          </h2>
          <p class="mt-2 text-sm text-[var(--color-text-muted)] font-body">
            Showing {{ filteredMeals.length }} meals within your community
          </p>
        </div>

        <router-link to="/donations" class="text-sm font-semibold text-[var(--color-primary)] hover:underline inline-flex items-center gap-1.5 font-body">
          View all meals
          <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round">
            <path d="M5 12h14" />
            <path d="m12 5 7 7-7 7" />
          </svg>
        </router-link>
      </div>

      <!-- Filter bar -->
      <div class="mb-8">
        <MealFilterBar @filter="onFilter" />
      </div>

      <!-- Meal grid -->
      <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6 md:gap-8">
        <MealCard
          v-for="meal in filteredMeals"
          :key="meal.id"
          :meal="meal"
        />
      </div>

      <!-- Empty state -->
      <div v-if="filteredMeals.length === 0" class="neu-card text-center py-16 px-6 max-w-md mx-auto my-10">
        <div class="text-4xl mb-4">🍲</div>
        <h3 class="font-display text-xl font-bold text-[var(--color-text-dark)] mb-2">No meals match your filters</h3>
        <p class="text-sm text-[var(--color-text-muted)] mb-6 font-body">Try adjusting your search query, dietary preferences, or distance filter.</p>
        <button @click="resetFilters" class="btn-outline text-sm">
          Reset filters
        </button>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref, computed } from 'vue'
import MealCard from '@/components/meal/MealCard.vue'
import MealFilterBar from '@/components/meal/MealFilterBar.vue'

const filters = ref({
  search: '',
  dietary: '',
  distance: '',
  readyNow: false
})

const meals = ref([
  {
    id: 1,
    title: 'Vegetable Biryani',
    description: 'Fragrant basmati rice layered with seasonal vegetables, saffron, and caramelized onions. Enough for 4.',
    image: 'https://images.unsplash.com/photo-1563379091339-03b21ab4a4f8?w=400&h=300&fit=crop',
    hostName: 'Priya K.',
    hostAvatar: '',
    portions: 4,
    distance: '0.3 mi',
    tags: ['Vegetarian', 'Spicy'],
    pickupBy: 'Today 7pm',
    readyNow: true,
    claimed: false
  },
  {
    id: 2,
    title: 'Minestrone Soup',
    description: 'Hearty Italian vegetable soup with beans, pasta, and fresh herbs. Made this morning.',
    image: 'https://images.unsplash.com/photo-1547592166-23ac45744acd?w=400&h=300&fit=crop',
    hostName: 'Marcus J.',
    hostAvatar: '',
    portions: 3,
    distance: '0.8 mi',
    tags: ['Vegan', 'Gluten-Free'],
    pickupBy: 'Today 8pm',
    readyNow: false,
    claimed: false
  },
  {
    id: 3,
    title: 'Sourdough Loaf',
    description: 'Freshly baked 24-hour fermentation sourdough. Crusty outside, soft inside. Baked this afternoon.',
    image: 'https://images.unsplash.com/photo-1509440159596-0249088772ff?w=400&h=300&fit=crop',
    hostName: 'Sarah L.',
    hostAvatar: '',
    portions: 1,
    distance: '1.2 mi',
    tags: ['Vegan', 'Dairy-Free'],
    pickupBy: 'Tomorrow 12pm',
    readyNow: false,
    claimed: false
  },
  {
    id: 4,
    title: 'Chicken Tikka Masala',
    description: 'Creamy tomato-spiced chicken curry with basmati rice. Restaurant-style, home-cooked.',
    image: 'https://images.unsplash.com/photo-1565557623262-b51c2513a641?w=400&h=300&fit=crop',
    hostName: 'Anika R.',
    hostAvatar: '',
    portions: 2,
    distance: '0.5 mi',
    tags: ['Halal'],
    pickupBy: 'Today 6:30pm',
    readyNow: true,
    claimed: false
  },
  {
    id: 5,
    title: 'Greek Salad',
    description: 'Crisp cucumbers, ripe tomatoes, kalamata olives, feta, and oregano dressing.',
    image: 'https://images.unsplash.com/photo-1540189549336-e6e99c3679fe?w=400&h=300&fit=crop',
    hostName: 'Dimitri P.',
    hostAvatar: '',
    portions: 2,
    distance: '2.1 mi',
    tags: ['Vegetarian', 'Gluten-Free'],
    pickupBy: 'Today 9pm',
    readyNow: false,
    claimed: false
  },
  {
    id: 6,
    title: 'Lentil Dahl & Rice',
    description: 'Slow-cooked red lentils with turmeric, cumin, and garlic. Served with steamed rice. Comfort food.',
    image: 'https://images.unsplash.com/photo-1540420773420-3366772f4999?w=400&h=300&fit=crop',
    hostName: 'Fatima A.',
    hostAvatar: '',
    portions: 4,
    distance: '0.7 mi',
    tags: ['Vegan', 'Nut-Free', 'Halal'],
    pickupBy: 'Tomorrow 2pm',
    readyNow: false,
    claimed: true
  }
])

const filteredMeals = computed(() => {
  return meals.value.filter(m => {
    if (filters.value.search) {
      const q = filters.value.search.toLowerCase()
      if (!m.title.toLowerCase().includes(q) && !m.description.toLowerCase().includes(q)) return false
    }
    if (filters.value.dietary && !m.tags.some(t => t.toLowerCase().includes(filters.value.dietary.toLowerCase()))) return false
    if (filters.value.readyNow && !m.readyNow) return false
    if (filters.value.distance) {
      const dist = parseFloat(m.distance)
      const maxDist = parseFloat(filters.value.distance)
      if (dist > maxDist) return false
    }
    return true
  })
})

function onFilter(f) {
  filters.value = f
}

function resetFilters() {
  filters.value = { search: '', dietary: '', distance: '', readyNow: false }
}
</script>
