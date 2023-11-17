import { computed, ref } from "vue";
import { defineStore } from "pinia";

export const useAuthStore = defineStore("auth", () => {
  const user = ref(null);

  const getUser = computed(() => user.value);

  function loginUser(u) {
    user.value = u;
  }

  function logoutUser() {
    user.value = null;
  }

  return { user, getUser, loginUser, logoutUser };
});
