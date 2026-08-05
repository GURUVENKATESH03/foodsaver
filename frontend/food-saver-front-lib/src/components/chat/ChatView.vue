<template>
  <div class="h-[calc(100vh-10rem)] neu-card overflow-hidden flex">
    <!-- Conversations Sidebar -->
    <div class="w-80 border-r border-slate-200 flex flex-col bg-slate-100/60 flex-shrink-0">
      <div class="p-4 border-b border-slate-200">
        <h2 class="font-display text-xl font-bold text-[var(--color-text-dark)]">Messages</h2>
      </div>
      <div class="flex-1 overflow-y-auto">
        <ConversationList
          :conversations="conversations"
          :active-id="activeConversation?.id"
          @select="setActiveConversation"
        />
      </div>
    </div>

    <!-- Chat Area -->
    <div class="flex-1 flex flex-col bg-white">
      <div v-if="!activeConversation" class="flex-1 flex items-center justify-center bg-[var(--color-bg)]">
        <div class="text-center p-8 neu-card max-w-sm">
          <div class="w-16 h-16 rounded-2xl bg-white flex items-center justify-center mx-auto mb-4 text-3xl shadow-[var(--shadow-neu-sm)]">
            💬
          </div>
          <h3 class="font-display font-bold text-lg text-[var(--color-text-dark)] mb-1">Your Inbox</h3>
          <p class="text-sm text-[var(--color-text-muted)] font-body">Select a conversation from the sidebar to view and send messages</p>
        </div>
      </div>

      <template v-else>
        <!-- Header -->
        <div class="px-6 py-4 border-b border-slate-200 flex items-center gap-3 bg-white">
          <div class="w-10 h-10 rounded-full bg-[var(--color-primary)] text-white flex items-center justify-center text-sm font-bold font-mono shadow-sm">
            {{ (activeConversation.participantName || '?').charAt(0) }}
          </div>
          <div>
            <h3 class="text-sm font-semibold text-[var(--color-text-dark)] font-body">{{ activeConversation.participantName }}</h3>
            <p class="text-xs text-[var(--color-text-muted)] font-mono">{{ activeConversation.donationTitle }}</p>
          </div>
        </div>

        <!-- Messages -->
        <div class="flex-1 overflow-y-auto p-6 space-y-4 bg-slate-50/50" ref="messagesContainer">
          <MessageThread :messages="messages" />
        </div>

        <!-- Input -->
        <ChatInput @send="sendMessage" />
      </template>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, nextTick } from 'vue'
import ConversationList from '@/components/chat/ConversationList.vue'
import MessageThread from '@/components/chat/MessageThread.vue'
import ChatInput from '@/components/chat/ChatInput.vue'

const conversations = ref([])
const activeConversation = ref(null)
const messages = ref([])
const messagesContainer = ref(null)

onMounted(async () => {
  conversations.value = [
    {
      id: 1,
      participantName: 'Sarah Johnson',
      donationTitle: 'Fresh Vegetables Bundle',
      lastMessage: 'Can I pick up at 3 PM?',
      unread: 2,
      updatedAt: '2024-01-15T14:30:00Z'
    },
    {
      id: 2,
      participantName: 'Community Center',
      donationTitle: 'Bakery Items',
      lastMessage: 'Thank you for the donation!',
      unread: 0,
      updatedAt: '2024-01-14T10:00:00Z'
    }
  ]
})

async function setActiveConversation(conversation) {
  activeConversation.value = conversation
  messages.value = [
    { id: 1, senderId: 'other', content: 'Hi! Is this donation still available?', timestamp: '2024-01-15T14:00:00Z' },
    { id: 2, senderId: 'me', content: 'Yes, it is! When would you like to pick it up?', timestamp: '2024-01-15T14:15:00Z' },
    { id: 3, senderId: 'other', content: 'Can I pick up at 3 PM?', timestamp: '2024-01-15T14:30:00Z' }
  ]
  await nextTick()
  scrollToBottom()
}

async function sendMessage(content) {
  if (!content.trim() || !activeConversation.value) return
  messages.value.push({
    id: Date.now(),
    senderId: 'me',
    content,
    timestamp: new Date().toISOString()
  })
  await nextTick()
  scrollToBottom()
}

function scrollToBottom() {
  messagesContainer.value?.scrollTo({
    top: messagesContainer.value.scrollHeight,
    behavior: 'smooth'
  })
}
</script>
