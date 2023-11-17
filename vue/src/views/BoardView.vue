<script setup>
import TheHeadingNavbar from "../components/layout/TheHeadingNavbar.vue";
</script>

<template>
  <div class="background"></div>
  <TheHeadingNavbar></TheHeadingNavbar>
  <div class="container text-center mt-3 board-container">
    <router-view></router-view>
  </div>
</template>

<style scoped>
@import "../assets/css/background.css";
@import "../assets/css/common.css";

@font-face {
  font-family: "GangwonEduHyeonokT_OTFMediumA";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2201-2@1.0/GangwonEduHyeonokT_OTFMediumA.woff")
    format("woff");
  font-weight: 600;
  font-style: 600;
}
* {
  font-family: "GangwonEduHyeonokT_OTFMediumA";
  font-weight: 600;
}
.board-container {
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
