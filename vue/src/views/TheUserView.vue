<script setup>
import TheHeadingNavbar from "@/components/layout/TheHeadingNavbar.vue";
</script>

<template>
  <div class="background"></div>
  <TheHeadingNavbar></TheHeadingNavbar>
  <div class="container text-center mt-3 user-container">
    <router-view></router-view>
  </div>
</template>

<style scoped>
@import "../assets/css/background.css";
@import "../assets/css/common.css";

.container {
  background-color: white;
  border-radius: 10px;
  animation: fadeIn 0.8s ease-out;
}
@keyframes fadeIn {
  0% {
    transform: translateY(5%);
    opacity: 0;
  }
  100% {
    transform: translateY(0%);
    opacity: 1;
  }
}
</style>
