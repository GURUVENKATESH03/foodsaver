<template>
  <footer class="bg-[var(--color-bg-deep)] border-t border-[var(--color-border-deep)]">
    <div class="container-wide py-16">
      <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-10">
        <!-- Brand -->
        <div class="sm:col-span-2 lg:col-span-1">
          <router-link to="/" class="flex items-center gap-2.5 mb-4">
            <span class="flex items-center justify-center w-8 h-8 rounded-lg bg-[var(--color-primary)] text-[var(--color-bg-deep)] font-bold text-base">
              M
            </span>
            <span class="font-display text-base font-bold text-[var(--color-text-light)]">Meal Share</span>
          </router-link>
          <p class="text-sm text-[var(--color-text-light-muted)] leading-relaxed max-w-xs">
            A community-powered platform reducing food waste by connecting people with surplus meals to those nearby who need them.
          </p>
        </div>

        <!-- Quick links -->
        <div>
          <h4 class="text-xs font-semibold tracking-widest uppercase text-[var(--color-text-light-muted)] mb-4">Browse</h4>
          <ul class="space-y-3">
            <li><router-link to="/donations" class="text-sm text-[var(--color-text-light)] hover:text-[var(--color-primary)] transition-colors">Available meals</router-link></li>
            <li><router-link to="/map" class="text-sm text-[var(--color-text-light)] hover:text-[var(--color-primary)] transition-colors">Food map</router-link></li>
            <li><router-link to="/donations/create" class="text-sm text-[var(--color-text-light)] hover:text-[var(--color-primary)] transition-colors">Share a meal</router-link></li>
          </ul>
        </div>

        <!-- Community -->
        <div>
          <h4 class="text-xs font-semibold tracking-widest uppercase text-[var(--color-text-light-muted)] mb-4">Community</h4>
          <ul class="space-y-3">
            <li><router-link to="/auth/signup" class="text-sm text-[var(--color-text-light)] hover:text-[var(--color-primary)] transition-colors">Join as donor</router-link></li>
            <li><router-link to="/auth/signup" class="text-sm text-[var(--color-text-light)] hover:text-[var(--color-primary)] transition-colors">Join as receiver</router-link></li>
            <li><a href="#" class="text-sm text-[var(--color-text-light)] hover:text-[var(--color-primary)] transition-colors">Safety guidelines</a></li>
          </ul>
        </div>

        <!-- Contact -->
        <div>
          <h4 class="text-xs font-semibold tracking-widest uppercase text-[var(--color-text-light-muted)] mb-4">Connect</h4>
          <ul class="space-y-3">
            <li><a href="mailto:hello@mealshare.org" class="text-sm text-[var(--color-text-light)] hover:text-[var(--color-primary)] transition-colors">hello@mealshare.org</a></li>
            <li><span class="text-sm text-[var(--color-text-light-muted)]">Built for communities</span></li>
          </ul>
        </div>
      </div>

      <div class="mt-12 pt-8 border-t border-[var(--color-border-deep)] flex flex-col sm:flex-row items-center justify-between gap-4">
        <p class="text-xs text-[var(--color-text-light-muted)]">
          Meal Share — reducing food waste, one meal at a time.
        </p>
        <p class="text-xs text-[var(--color-text-light-muted)]">
          &copy; {{ new Date().getFullYear() }} Meal Share. Sample data for demo.
        </p>
      </div>
    </div>
  </footer>
</template>

<script setup>
</script>
