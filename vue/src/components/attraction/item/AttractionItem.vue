<script setup>
const { attraction } = defineProps({ attraction: Object });
</script>
<template>
  <div>
    <div class="listItemImg"></div>
    <img
        :src="attraction.firstImage ? attraction.firstImage : altimg"
        alt="이미지가 없습니다."
      />
  </div>
  <div class="listItemInfo">
    <div class="listItemTitle">{{ attraction.title }}</div>
    <div class="listItemAddr">{{ attraction.addr1 }}</div>
    <div class="listItemTel">{{ attraction.tel }}</div>
  </div>
</template>
<style scoped>
@import "@/assets/css/common.css";
@import "@/assets/css/map.css";
</style>
