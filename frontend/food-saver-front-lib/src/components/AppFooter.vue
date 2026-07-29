<template>
  <footer class="bg-white border-t border-secondary-200 mt-auto">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <div class="grid grid-cols-1 md:grid-cols-3 gap-8">
        <div>
          <h3 class="text-sm font-semibold text-secondary-900 tracking-wider uppercase">Meal Share</h3>
          <p class="mt-4 text-sm text-secondary-500">
            Connecting communities to reduce food waste and fight hunger.
          </p>
        </div>
        <div>
          <h3 class="text-sm font-semibold text-secondary-900 tracking-wider uppercase">Quick Links</h3>
          <ul class="mt-4 space-y-2">
            <li><router-link to="/donations" class="text-sm text-secondary-500 hover:text-primary-600">Browse Food</router-link></li>
            <li><router-link to="/map" class="text-sm text-secondary-500 hover:text-primary-600">Food Map</router-link></li>
            <li><router-link to="/auth/register" class="text-sm text-secondary-500 hover:text-primary-600">Join Us</router-link></li>
          </ul>
        </div>
        <div>
          <h3 class="text-sm font-semibold text-secondary-900 tracking-wider uppercase">Contact</h3>
          <p class="mt-4 text-sm text-secondary-500">support@mealshare.org</p>
        </div>
      </div>
      <div class="mt-8 border-t border-secondary-200 pt-8 text-center">
        <p class="text-sm text-secondary-400"> Meal Share. All rights reserved.</p>
      </div>
    </div>
  </footer>
</template>