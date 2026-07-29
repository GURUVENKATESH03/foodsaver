<template>
  <section class="bg-[var(--color-bg-warm)] section-padding">
    <div class="container-narrow">
      <div class="text-center mb-14">
        <span class="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-[var(--color-secondary)]/10 text-xs font-semibold text-[var(--color-secondary)] tracking-wider uppercase mb-4">
          How it works
        </span>
        <h2 class="font-display text-3xl sm:text-4xl md:text-5xl font-bold text-[var(--color-text-dark)] tracking-tight">
          Three steps to share
        </h2>
        <p class="mt-4 text-base text-[var(--color-text-muted)] max-w-lg mx-auto">
          Whether you have extra portions or need a meal, the process is simple.
        </p>
      </div>

      <div class="grid grid-cols-1 md:grid-cols-3 gap-8 md:gap-12">
        <div
          v-for="(step, index) in steps"
          :key="index"
          class="reveal text-center"
          :style="{ transitionDelay: `${index * 150}ms` }"
        >
          <!-- Step number -->
          <div class="inline-flex items-center justify-center w-14 h-14 rounded-2xl bg-[var(--color-bg-deep)] text-[var(--color-primary)] text-2xl font-bold font-display mb-6">
            {{ String(index + 1).padStart(2, '0') }}
          </div>

          <h3 class="font-display text-xl font-bold text-[var(--color-text-dark)] mb-3">
            {{ step.title }}
          </h3>
          <p class="text-sm text-[var(--color-text-muted)] leading-relaxed max-w-xs mx-auto">
            {{ step.description }}
          </p>

          <!-- Connector (desktop) -->
          <div v-if="index < steps.length - 1" class="hidden md:block absolute top-7 left-[calc(50%+3.5rem)] w-[calc(100%-7rem)] h-px bg-[var(--color-border)]" />
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { onMounted } from 'vue'

const steps = [
  {
    title: 'Post what you have',
    description: 'Snap a photo, add a few details — portions, pickup window, dietary tags. Posting takes under a minute.'
  },
  {
    title: 'Someone nearby claims it',
    description: 'Neighbors, students, or local community members browse available meals and request what they need.'
  },
  {
    title: 'Meet and share',
    description: 'Coordinate a quick pickup. Food gets rescued, hunger gets addressed, nothing goes to waste.'
  }
]

onMounted(() => {
  if ('IntersectionObserver' in window) {
    const observer = new IntersectionObserver(
      (entries) => {
        entries.forEach(entry => {
          if (entry.isIntersecting) {
            entry.target.classList.add('visible')
            observer.unobserve(entry.target)
          }
        })
      },
      { threshold: 0.2 }
    )
    document.querySelectorAll('.reveal').forEach(el => observer.observe(el))
  } else {
    document.querySelectorAll('.reveal').forEach(el => el.classList.add('visible'))
  }
})
</script>
