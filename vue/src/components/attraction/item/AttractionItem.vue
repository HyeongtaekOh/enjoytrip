<script setup>
import { defineProps } from "vue";

const { attraction, map } = defineProps(["attraction", "map"]);
const emit = defineEmits(["updateNewPlan"]);

function moveCenter(lat, lng) {
  map.setCenter(new kakao.maps.LatLng(lat, lng));
}

const updateNewPlan = () => {
  const plan = {
    title: attraction.title,
    addr1: attraction.addr1,
    addr2: attraction.addr2,
    latitude: attraction.latitude,
    longitude: attraction.longitude,
    firstImage: attraction.firstImage,
    contentId: attraction.contentId,
  };
  console.log("plan:", plan);
  emit("updateNewPlan", { plan });
};
</script>
<template>
  <div class="main">
    <div class="row" @click="moveCenter(attraction.latitude, attraction.longitude)">
      <div class="imgContainer">
        <img
          :src="
            attraction.firstImage
              ? attraction.firstImage
              : 'http://localhost:5173/src/assets/img/noImage.jpg'
          "
          class="rowImage"
        />
      </div>
      <div class="rowContents">
        <div class="plus" @click="updateNewPlan">+ 추가하기</div>
        <div>
          <h1>{{ attraction.title }}</h1>
          <div>{{ attraction.addr1 }} {{ attraction.addr2 }}</div>
        </div>
      </div>
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
.button-wrapper {
  position: absolute;
  left: 2px;
  bottom: 15px;
  z-index: 3;
}
.sidenav {
  height: 100%;
  width: 0;
  position: fixed;
  z-index: 1;
  top: 0;
  left: 0;
  background-color: #ecdfd4;
  overflow-x: hidden;
  padding-top: 60px;
  transition: 0.5s;
}

.sidenav a {
  padding: 8px 8px 8px 32px;
  text-decoration: none;
  font-size: 25px;
  color: #818181;
  display: block;
  transition: 0.3s;
}

.sidenav a:hover {
  color: #f1f1f1;
}

.sidenav .closebtn {
  position: absolute;
  top: 0;
  right: 25px;
  font-size: 36px;
  margin-left: 50px;
}

#main {
  transition: margin-left 0.5s;
  padding: 20px;
}

@media screen and (max-height: 450px) {
  .sidenav {
    padding-top: 15px;
  }

  .sidenav a {
    font-size: 18px;
  }
}
</style>
