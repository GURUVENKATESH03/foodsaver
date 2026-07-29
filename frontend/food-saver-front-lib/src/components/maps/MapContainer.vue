<template>
  <div ref="mapContainer" class="w-full h-full relative z-0"></div>
</template>

<script setup>
import { ref } from 'vue'
import { useMap } from '@/composables/useMap'

const props = defineProps({
  center: {
    type: Array,
    default: () => [40.7128, -74.0060]
  },
  zoom: {
    type: Number,
    default: 12
  }
})

const mapContainer = ref(null)

const { addMarker, clearMarkers } = useMap(mapContainer, {
  center: props.center,
  zoom: props.zoom
})

defineExpose({
  addMarker,
  clearMarkers
})
</script>