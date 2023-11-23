<script setup>
document.body.style.overflow = "hidden";
import TheHeadingNavbar from "../components/layout/TheHeadingNavbar.vue";
import { ref } from "vue";
import { useRouter } from "vue-router";
const router = useRouter();

const keyword = ref("");
const onEnter = () => {
  console.log(keyword.value);
  router.push({
    name: "attraction-map",
    params: { mode: "search" },
    query: { selectedKeyword: keyword.value },
  });
};
</script>

<template>
  <TheHeadingNavbar></TheHeadingNavbar>
  <title>EnjoyTrip</title>
  <div class="backgroundContainer">
    <div class="background"></div>
    <div class="tower"></div>
    <div class="cloudContainer">
      <div class="cloud"></div>
      <div class="cloud"></div>
    </div>
  </div>
  <div class="cover">
    <div class="titleContainer">
      <h1 class="title">ENJOY TRIP</h1>
      <input
        type="text"
        name="search"
        id="search"
        class="searchbar"
        placeholder="관광지를 검색하세요..."
        v-model="keyword"
        @keydown.enter.prevent="onEnter"
      />
    </div>
  </div>
</template>

<style scoped>
@import "../assets/css/common.css";
@import "../assets/css/home.css";
body {
  overflow: hidden;
}
</style>
