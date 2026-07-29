<template>
  <div class="h-[calc(100vh-8rem)] card overflow-hidden flex">
    <!-- Conversations Sidebar -->
    <div class="w-80 border-r border-secondary-200 flex flex-col bg-secondary-50">
      <div class="p-4 border-b border-secondary-200">
        <h2 class="text-lg font-semibold text-secondary-900">Messages</h2>
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
      <div v-if="!activeConversation" class="flex-1 flex items-center justify-center">
        <div class="text-center">
          <div class="w-16 h-16 bg-secondary-100 rounded-full flex items-center justify-center mx-auto mb-4">
            <svg class="w-8 h-8 text-secondary-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 12h.01M12 12h.01M16 12h.01M21 12c0 4.418-4.03 8-9 8a9.863 9.863 0 01-4.255-.949L3 20l1.395-3.72C3.512 15.042 3 13.574 3 12c0-4.418 4.03-8 9-8s9 3.582 9 8z" />
            </svg>
          </div>
          <p class="text-secondary-500">Select a conversation to start messaging</p>
        </div>
      </div>
      
      <template v-else>
        <!-- Header -->
        <div class="px-6 py-4 border-b border-secondary-200 flex items-center">
          <img 
            :src="activeConversation.participantAvatar" 
            class="w-10 h-10 rounded-full mr-3"
          />
          <div>
            <h3 class="text-sm font-semibold text-secondary-900">{{ activeConversation.participantName }}</h3>
            <p class="text-xs text-secondary-500">{{ activeConversation.donationTitle }}</p>
          </div>
        </div>
        
        <!-- Messages -->
        <div class="flex-1 overflow-y-auto p-6 space-y-4" ref="messagesContainer">
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
import { chatService } from '@/services/chat.service'
import ConversationList from '@/components/chat/ConversationList.vue'
import MessageThread from '@/components/chat/MessageThread.vue'
import ChatInput from '@/components/chat/ChatInput.vue'

const conversations = ref([])
const activeConversation = ref(null)
const messages = ref([])
const messagesContainer = ref(null)

onMounted(async () => {
  // Mock conversations for demo
  conversations.value = [
    {
      id: 1,
      participantName: 'Sarah Johnson',
      participantAvatar: 'https://ui-avatars.com/api/?name=Sarah+Johnson&background=random',
      donationTitle: 'Fresh Vegetables Bundle',
      lastMessage: 'Can I pick up at 3 PM?',
      unread: 2,
      updatedAt: '2024-01-15T14:30:00Z'
    },
    {
      id: 2,
      participantName: 'Community Center',
      participantAvatar: 'https://ui-avatars.com/api/?name=Community+Center&background=random',
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
  
  // API call would go here
  // await chatService.sendMessage(activeConversation.value.id, content)
}

function scrollToBottom() {
  messagesContainer.value?.scrollTo({
    top: messagesContainer.value.scrollHeight,
    behavior: 'smooth'
  })
}
</script>