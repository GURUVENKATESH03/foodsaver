<template>
  <article class="bg-white rounded-2xl border border-slate-200/90 overflow-hidden shadow-sm hover:shadow-lg hover:border-slate-300 hover:-translate-y-1 transition-all duration-300 group flex flex-col justify-between">
    <div>
      <!-- Image -->
      <div class="relative aspect-[4/3] bg-slate-100 overflow-hidden">
        <img
          v-if="meal.image"
          :src="meal.image"
          :alt="meal.title"
          class="w-full h-full object-cover transition-transform duration-300 group-hover:scale-105"
          loading="lazy"
        />
        <div v-else class="w-full h-full flex items-center justify-center text-4xl bg-slate-100">
          🍲
        </div>

        <!-- Status badge -->
        <span
          v-if="meal.readyNow"
          class="absolute top-3 left-3 inline-flex items-center gap-1.5 px-3 py-1 rounded-full bg-red-500 text-white text-[11px] font-semibold font-mono shadow-sm"
        >
          <span class="w-1.5 h-1.5 rounded-full bg-white animate-pulse" />
          Ready now
        </span>

        <!-- Dietary tags -->
        <div v-if="meal.tags?.length" class="absolute bottom-3 left-3 flex flex-wrap gap-1.5">
          <span
            v-for="tag in meal.tags.slice(0, 2)"
            :key="tag"
            class="px-2.5 py-0.5 rounded-full bg-white/95 backdrop-blur-md text-[11px] font-semibold text-teal-700 font-body shadow-xs border border-slate-200/60"
          >
            {{ tag }}
          </span>
          <span v-if="meal.tags.length > 2" class="px-2 py-0.5 rounded-full bg-slate-900/80 text-white text-[10px] font-mono">
            +{{ meal.tags.length - 2 }}
          </span>
        </div>
      </div>

      <!-- Content -->
      <div class="p-5">
        <div class="flex items-start justify-between gap-3 mb-2">
          <h3 class="font-display text-base font-bold text-slate-900 leading-tight">
            {{ meal.title }}
          </h3>
          <span class="font-mono text-xs font-semibold text-slate-500 whitespace-nowrap shrink-0">
            {{ meal.distance }}
          </span>
        </div>

        <p class="text-sm text-slate-500 leading-relaxed mb-4 line-clamp-2 font-body">
          {{ meal.description }}
        </p>
      </div>
    </div>

    <div class="p-5 pt-0">
      <div class="flex items-center justify-between pt-4 border-t border-slate-100">
        <div class="flex items-center gap-2">
          <!-- Host avatar -->
          <div class="w-7 h-7 rounded-full bg-teal-600 text-white flex items-center justify-center text-xs font-bold font-mono overflow-hidden shadow-xs">
            <img v-if="meal.hostAvatar" :src="meal.hostAvatar" :alt="meal.hostName" class="w-full h-full object-cover" />
            <span v-else>{{ meal.hostName?.charAt(0) || '?' }}</span>
          </div>
          <span class="text-xs font-medium text-slate-700 font-body">{{ meal.hostName }}</span>
        </div>

        <div class="flex items-center gap-3">
          <!-- Portions -->
          <span class="font-mono text-xs font-semibold text-teal-600">
            {{ meal.portions }}
            <span class="font-body text-slate-500 font-normal">{{ meal.portions === 1 ? 'portion' : 'portions' }}</span>
          </span>

          <!-- Claim button -->
          <button
            v-if="!meal.claimed"
            class="px-3.5 py-1.5 bg-teal-600 hover:bg-teal-700 text-white text-xs font-semibold rounded-lg shadow-xs transition-all font-body"
          >
            Claim
          </button>
          <span v-else class="text-xs font-semibold text-slate-400 font-mono">Claimed</span>
        </div>
      </div>

      <!-- Pickup countdown -->
      <div v-if="meal.pickupBy" class="mt-3 text-xs font-mono text-slate-400 flex items-center gap-1.5">
        <svg xmlns="http://www.w3.org/2000/svg" width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <circle cx="12" cy="12" r="10" />
          <polyline points="12 6 12 12 16 14" />
        </svg>
        <span>Pickup by {{ meal.pickupBy }}</span>
      </div>
    </div>
  </article>
</template>

<script setup>
defineProps({
  meal: {
    type: Object,
    required: true
  }
})
</script>
