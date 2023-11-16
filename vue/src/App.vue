<script setup>
import { ref, provide } from "vue";
import { RouterView } from "vue-router";
import { validateToken } from "@/api/auth";
import { parseJwtPayload } from "@/util/jwt-utils";
import TheHeadingNavbar from "./components/layout/TheHeadingNavbar.vue";

const user = ref(null);

updateUserContext();

provide("user", user);

function updateUserContext() {
  const token = localStorage.getItem("jwt");

  validateToken(
    token,
    () => {
      const { userId, username, userType } = parseJwtPayload(token);
      user.value = { userId, username, userType };
    },
    () => {
      user.value = null;
      localStorage.removeItem("jwt");
    }
  );
}
</script>

<template>
  <div>
    <TheHeadingNavbar></TheHeadingNavbar>
    <router-view></router-view>
    <!-- <SwalModal v-if="showModal" /> -->
  </div>
</template>

<style scoped></style>
