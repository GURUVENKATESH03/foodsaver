<template>
  <section class="bg-[#0F172A] relative overflow-hidden text-slate-200">
    <!-- Subtle background gradient accent -->
    <div class="absolute -top-40 -right-40 w-96 h-96 bg-teal-500/10 rounded-full blur-3xl pointer-events-none" />
    <div class="absolute -bottom-40 -left-40 w-96 h-96 bg-emerald-500/10 rounded-full blur-3xl pointer-events-none" />

    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 relative py-20 md:py-28 lg:py-32">
      <div class="max-w-3xl mx-auto text-center">
        <!-- Eyebrow pill -->
        <span class="fade-in-up inline-flex items-center gap-2 px-4 py-1.5 rounded-full border border-slate-800 bg-slate-900/90 text-xs font-semibold text-teal-400 tracking-wider uppercase mb-6 font-mono shadow-sm">
          <span class="w-2 h-2 rounded-full bg-teal-400 animate-pulse" />
          Community Powered Food Sharing
        </span>

        <!-- Headline -->
        <h1 class="fade-in-up font-display text-4xl sm:text-5xl md:text-6xl lg:text-7xl font-bold text-white leading-[1.1] tracking-tight mb-6">
          Share extra food.<br />
          <span class="text-teal-400">Feed someone nearby.</span>
        </h1>

        <p class="fade-in-up max-w-xl mx-auto text-base md:text-lg text-slate-300 leading-relaxed mb-10 font-body">
          Got extra meals or home-cooked food? Connect directly with neighbors to share surplus food, reduce waste, and build community.
        </p>

        <!-- CTAs with clean solid background & crisp borders -->
        <div class="fade-in-up flex flex-col sm:flex-row items-center justify-center gap-4 mb-12">
          <router-link
            to="/donations/create"
            class="w-full sm:w-auto inline-flex items-center justify-center gap-2 px-8 py-4 bg-teal-600 hover:bg-teal-500 text-white font-semibold text-base rounded-xl border border-teal-500/30 shadow-md hover:shadow-lg transition-all duration-200"
          >
            <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round">
              <line x1="12" y1="5" x2="12" y2="19" />
              <line x1="5" y1="12" x2="19" y2="12" />
            </svg>
            Share a meal
          </router-link>

          <router-link
            to="/donations"
            class="w-full sm:w-auto inline-flex items-center justify-center gap-2 px-8 py-4 bg-slate-800 hover:bg-slate-700 text-slate-100 hover:text-white font-semibold text-base rounded-xl border border-slate-700 shadow-sm transition-all duration-200"
          >
            <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <circle cx="11" cy="11" r="8" />
              <line x1="21" y1="21" x2="16.65" y2="16.65" />
            </svg>
            Find a meal near me
          </router-link>
        </div>

        <!-- Metric pill -->
        <div class="fade-in-up inline-flex items-center gap-4 px-6 py-3.5 rounded-2xl bg-slate-900/90 border border-slate-800 shadow-md">
          <span class="font-mono text-3xl font-bold text-teal-400">1,240+</span>
          <div class="text-left">
            <span class="font-body text-sm font-semibold text-white block">meals shared</span>
            <span class="font-mono text-xs text-slate-400">this month in your community</span>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
</script>
