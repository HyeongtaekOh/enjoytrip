<script setup>
import { defineProps } from "vue";

const { attraction, map } = defineProps(["attraction", "map"]);
const moveCenter = (lat, lng) => {
  const center = new kakao.maps.LatLng(lat, lng);
  map.setCenter(center);
};
</script>
<template>
  <div class="row" @click="moveCenter(attraction.latitude, attraction.longitude)">
    <div class="imgContainer">
      <!-- <img src="@/assets/img/noImage.jpg" alt="@/assets/img/noImage.jpg" class="rowImage" /> -->
      <img
        :src="attraction.firstImage ? attraction.firstImage : '@/assets/img/noImage.jpg'"
        class="rowImage"
      />
    </div>
    <div class="rowContents">
      <div class="plus">+ 추가하기</div>
      <div>
        <h1>{{ attraction.title }}</h1>
        <div>{{ attraction.addr1 }} {{ attraction.addr2 }}</div>
      </div>
      <div>{{ attraction.latitude }}</div>
      <div>{{ attraction.longitude }}</div>
    </div>
  </div>
</template>
<style scoped>
@import "@/assets/css/common.css";
@import "@/assets/css/map.css";
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
</style>
