<template>
  <section class="bg-[var(--color-bg-warm)] section-padding">
    <div class="container-narrow">
      <div class="text-center mb-14">
        <span class="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-[var(--color-primary)]/15 text-xs font-semibold text-[var(--color-primary)] tracking-wider uppercase mb-4">
          Real stories
        </span>
        <h2 class="font-display text-3xl sm:text-4xl md:text-5xl font-bold text-[var(--color-text-dark)] tracking-tight">
          From the community
        </h2>
        <p class="mt-4 text-base text-[var(--color-text-muted)] max-w-lg mx-auto">
          Placeholder testimonials showing how Meal Share brings people together.
        </p>
      </div>

      <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div
          v-for="(item, i) in testimonials"
          :key="i"
          class="bg-white border border-[var(--color-border)] rounded-2xl p-6 reveal"
          :style="{ transitionDelay: `${i * 100}ms` }"
        >
          <div class="flex items-center gap-1 mb-4">
            <svg v-for="n in 5" :key="n" xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" :fill="n <= item.rating ? 'var(--color-primary)' : 'none'" :stroke="n <= item.rating ? 'var(--color-primary)' : 'var(--color-border)'" stroke-width="2">
              <polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2" />
            </svg>
          </div>
          <blockquote class="text-sm text-[var(--color-text-dark)] leading-relaxed mb-4">
            "{{ item.quote }}"
          </blockquote>
          <div class="flex items-center gap-3">
            <div class="w-9 h-9 rounded-full bg-[var(--color-border)] flex items-center justify-center text-xs font-semibold text-[var(--color-text-muted)]">
              {{ item.name.charAt(0) }}
            </div>
            <div>
              <div class="text-sm font-semibold text-[var(--color-text-dark)]">{{ item.name }}</div>
              <div class="text-xs text-[var(--color-text-muted)]">{{ item.role }}</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
const testimonials = [
  {
    quote: 'I always cook too much for one person. Instead of letting it sit in the fridge, I post it here and someone picks it up within an hour. Feels so much better than throwing food away.',
    name: 'Elena R.',
    role: 'Home cook · Donor',
    rating: 5
  },
  {
    quote: 'As a student, groceries were always tight. This platform helped me find warm meals nearby when I needed them most. Now I share meals whenever I can.',
    name: 'Carlos M.',
    role: 'Student · Receiver',
    rating: 5
  },
  {
    quote: 'Our community center partners with local hosts to redistribute surplus. It is simple, dignified, and has become a vital part of how we support families.',
    name: 'Aisha K.',
    role: 'Community organiser',
    rating: 5
  }
]
</script>
