<template>
  <article
    class="recipe-card group"
    :style="{ '--tilt': (Math.random() * 2 - 1).toFixed(1) + 'deg' }"
    :class="{ 'steam-active': meal.readyNow }"
  >
    <!-- Image -->
    <div class="relative aspect-[4/3] bg-[var(--color-border)] overflow-hidden">
      <img
        v-if="meal.image"
        :src="meal.image"
        :alt="meal.title"
        class="w-full h-full object-cover transition-transform duration-[400ms] ease group-hover:scale-[1.03]"
        loading="lazy"
      />
      <div v-else class="w-full h-full flex items-center justify-center text-4xl">
        🍽️
      </div>

      <!-- Status badge -->
      <span
        v-if="meal.readyNow"
        class="absolute top-3 left-3 tag tag-red text-[11px] px-2.5 py-1"
      >
        <span class="w-1.5 h-1.5 rounded-full bg-[var(--color-urgent)] animate-pulse" />
        Ready now
      </span>

      <!-- Dietary tags -->
      <div v-if="meal.tags?.length" class="absolute bottom-3 left-3 flex flex-wrap gap-1.5">
        <BaseTag v-for="tag in meal.tags.slice(0, 2)" :key="tag" :color="tagColor(tag)">
          {{ tag }}
        </BaseTag>
        <span v-if="meal.tags.length > 2" class="tag tag-neutral text-[10px]">
          +{{ meal.tags.length - 2 }}
        </span>
      </div>
    </div>

    <!-- Content -->
    <div class="p-5">
      <div class="flex items-start justify-between gap-3 mb-2">
        <h3 class="font-display text-lg font-bold text-[var(--color-text-dark)] leading-tight">
          {{ meal.title }}
        </h3>
        <span class="font-mono text-xs font-semibold text-[var(--color-text-muted)] whitespace-nowrap shrink-0">
          {{ meal.distance }}
        </span>
      </div>

      <p class="text-sm text-[var(--color-text-muted)] leading-relaxed mb-4 line-clamp-2">
        {{ meal.description }}
      </p>

      <div class="flex items-center justify-between">
        <div class="flex items-center gap-2">
          <!-- Host avatar -->
          <div class="w-7 h-7 rounded-full bg-[var(--color-border)] flex items-center justify-center text-xs font-semibold text-[var(--color-text-muted)] overflow-hidden">
            <img v-if="meal.hostAvatar" :src="meal.hostAvatar" :alt="meal.hostName" class="w-full h-full object-cover" />
            <span v-else>{{ meal.hostName?.charAt(0) || '?' }}</span>
          </div>
          <span class="text-xs font-medium text-[var(--color-text-dark)]">{{ meal.hostName }}</span>
        </div>

        <div class="flex items-center gap-3">
          <!-- Portions -->
          <span class="font-mono text-xs font-semibold text-[var(--color-secondary)]">
            {{ meal.portions }}
            <span class="font-body text-[var(--color-text-muted)] font-normal">{{ meal.portions === 1 ? 'portion' : 'portions' }}</span>
          </span>

          <!-- Claim button -->
          <button
            v-if="!meal.claimed"
            class="text-xs font-semibold text-[var(--color-primary)] hover:text-[var(--color-primary-hover)] transition-colors"
          >
            Claim →
          </button>
          <span v-else class="text-xs font-medium text-[var(--color-secondary)]">Claimed</span>
        </div>
      </div>

      <!-- Pickup countdown -->
      <div v-if="meal.pickupBy" class="mt-3 pt-3 border-t border-[var(--color-border)]">
        <div class="flex items-center gap-1.5">
          <svg xmlns="http://www.w3.org/2000/svg" width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="text-[var(--color-text-muted)]">
            <circle cx="12" cy="12" r="10" />
            <polyline points="12 6 12 12 16 14" />
          </svg>
          <span class="font-mono text-xs text-[var(--color-text-muted)]">
            Pickup by {{ meal.pickupBy }}
          </span>
        </div>
      </div>
    </div>
  </article>
</template>

<script setup>
import BaseTag from '@/components/ui/BaseTag.vue'

defineProps({
  meal: {
    type: Object,
    required: true
  }
})

function tagColor(tag) {
  const map = {
    'Vegetarian': 'green',
    'Vegan': 'green',
    'Gluten-Free': 'gold',
    'Dairy-Free': 'gold',
    'Nut-Free': 'gold',
    'Spicy': 'red',
    'Halal': 'green',
    'Kosher': 'green'
  }
  return map[tag] || 'neutral'
}
</script>
