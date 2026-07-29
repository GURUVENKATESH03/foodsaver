export const USER_ROLES = {
  ADMIN: 'ADMIN',
  DONOR: 'DONOR',
  RECEIVER: 'RECEIVER'
}

export const DONATION_STATUS = {
  AVAILABLE: 'AVAILABLE',
  RESERVED: 'RESERVED',
  PICKED_UP: 'PICKED_UP',
  EXPIRED: 'EXPIRED',
  CANCELLED: 'CANCELLED'
}

export const REQUEST_STATUS = {
  PENDING: 'PENDING',
  APPROVED: 'APPROVED',
  REJECTED: 'REJECTED',
  PICKUP_SCHEDULED: 'PICKUP_SCHEDULED',
  COMPLETED: 'COMPLETED',
  CANCELLED: 'CANCELLED'
}

export const FOOD_CATEGORIES = [
  { id: 'FRUITS_VEGETABLES', label: 'Fruits & Vegetables', icon: '🍎' },
  { id: 'BAKERY', label: 'Bakery & Bread', icon: '🥖' },
  { id: 'DAIRY', label: 'Dairy Products', icon: '🥛' },
  { id: 'MEAT', label: 'Meat & Poultry', icon: '🍗' },
  { id: 'PREPARED', label: 'Prepared Meals', icon: '🍱' },
  { id: 'CANNED', label: 'Canned & Packaged', icon: '🥫' },
  { id: 'BEVERAGES', label: 'Beverages', icon: '🥤' },
  { id: 'OTHER', label: 'Other', icon: '📦' }
]

export const DONATION_CATEGORIES = FOOD_CATEGORIES

export const APP_CONFIG = {
  MAX_FILE_SIZE: 5 * 1024 * 1024, // 5MB
  SUPPORTED_IMAGE_TYPES: ['image/jpeg', 'image/png', 'image/webp'],
  DEFAULT_MAP_CENTER: { lat: 40.7128, lng: -74.0060 }, // NYC
  DEFAULT_MAP_ZOOM: 12,
  ITEMS_PER_PAGE: 12
}