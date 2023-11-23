<script setup>
import { ref, defineProps, defineEmits, onMounted } from "vue";

const { attraction, map, newPlan, handleDeletePlan, markers, infoWindow } = defineProps([
  "attraction",
  "map",
  "newPlan",
  "handleDeletePlan",
  "markers",
  "infoWindow",
]);

const emit = defineEmits(["showOverlay", "updateNewPlan", "handleDeletePlan"]);

const moveCenter = (lat, lng) => {
  map.setCenter(new kakao.maps.LatLng(lat, lng));

  // console.log("overlay.value !!!!", overlay.value);
  // if (overlay.value) {
  //   console.log("Closing overlay.value:", overlay.value);
  //   overlay.value.setMap(null);
  // }
  showOverlay(lat, lng, attraction);
};

const closeOverlay = () => {
  infoWindow.close();
};

const showOverlay = (lat, lng, attraction) => {
  emit("showOverlay", { lat, lng, attraction });
};

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

const handleDelete = () => {
  handleDeletePlan(attraction);
};
</script>
<template>
  <div class="main">
    <div class="row">
      <div class="imgContainer">
        <img :src="
          attraction.firstImage
            ? attraction.firstImage
            : 'http://localhost:5173/src/assets/img/noImage.jpg'
        " class="rowImage" />
      </div>
      <div class="rowContents">
        <div class="plus" @click="updateNewPlan" v-if="!newPlan.some((plan) => plan.contentId === attraction.contentId)">
          + 추가하기
        </div>
        <div class="plus" @click="handleDelete" v-else>- 삭제하기</div>
        <div class="attraction-content" @click="moveCenter(attraction.latitude, attraction.longitude)">
          <h1>{{ attraction.title }}</h1>
          <div>{{ attraction.addr1 }} {{ attraction.addr2 }}</div>
        </div>
        <div class="attraction-detail">
          <button class="click-detail">
            <router-link :to="{
              name: 'attraction-detail',
              params: { contentId: attraction.contentId },
            }">
              자세히 보기</router-link>
          </button>
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
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2201-2@1.0/GangwonEduHyeonokT_OTFMediumA.woff") format("woff");
  font-weight: 600;
  font-style: 600;
}

* {
  font-family: "GangwonEduHyeonokT_OTFMediumA";
  font-weight: 600;
}

.click-detail {
  /* flex: 1 1 auto; */
  border: none;
  margin: 10px;
  padding: 5px;
  width: 120x;
  height: 40px;
  text-align: center;
  text-transform: uppercase;
  transition: 0.5s;
  background-size: 200% auto;
  color: white;
  /* text-shadow: 0px 0px 10px rgba(0,0,0,0.2);*/
  box-shadow: 0 0 20px #eee;
  border-radius: 10px;
  background-image: linear-gradient(to right, #f6d365 0%, #fda085 51%, #f6d365 100%);
}

.click-detail:hover {
  background-position: right center;
  /* change the direction of the change here */
}

.attraction-detail {
  font-color: #ca3939;
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

.clear-all-btn {
  position: absolute;
  top: 10px;
  right: 10px;
  background-color: #fff;
  padding: 5px 10px;
  border: 1px solid #ccc;
  cursor: pointer;
  z-index: 2;
}
</style>

<style>
.wrap {
  position: absolute;
  left: 0;
  bottom: 40px;
  width: 300px;
  height: 150px;
  margin-left: -144px;
  text-align: left;
  overflow: hidden;
  font-size: 20px;
  font-family: "GangwonEduHyeonokT_OTFMediumA";
  /* font-family: "Malgun Gothic", dotum, "돋움", sans-serif; */
  line-height: 1.5;
}

.wrap * {
  padding: 0;
  margin: 0;
}

.wrap .body {
  z-index: 2;
}

.wrap .info {
  width: 300px;
  height: 200px;
  border-radius: 5px;
  border-bottom: 2px solid #ccc;
  border-right: 1px solid #ccc;
  overflow: hidden;
  background: #fff;
}

.wrap .info:nth-child(1) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}

.info .title {
  padding: 5px 0 0 10px;
  height: 40px;
  background: #eee;
  color: #333;
  border-bottom: 1px solid #ddd;
  font-size: 27px;
  font-weight: bold;
}

.info .close {
  position: absolute;
  top: 10px;
  right: 10px;
  color: #888;
  width: 17px;
  height: 17px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/overlay_close.png");
}

.info .close:hover {
  cursor: pointer;
}

.info .body {
  position: relative;
  overflow: hidden;
  height: 150px;
}

.info .desc {
  position: absolute;
  top: 10px;
  left: 110px;
  width: 200px;
  height: 130px;
}

.desc .ellipsis {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.desc .jibun {
  font-size: 11px;
  color: #888;
  margin-top: -2px;
}

.info .img {
  position: absolute;
  top: 10px;
  left: 10px;
  width: 90px;
  height: 90px;
  border: 1px solid #ddd;
  color: #888;
  overflow: hidden;
}

.info .img img {
  width: 100%;
  height: 100%;
}

.info .link {
  color: #03c75a;
}

.attraction-content.clicked {
  background-color: #e0e0e0;
  /* 클릭되었을 때의 배경 색상 */
  transition: background-color 0.3s;
  /* 부드러운 전환을 위한 트랜지션 */
}

.attraction-content:hover {
  background-color: #f0f0f0;
  /* hover 시의 배경 색상 */
  transition: background-color 0.3s;
  /* 부드러운 전환을 위한 트랜지션 */
}

.attraction-content {
  display: flex;
  flex-direction: column;
  justify-content: center;
  /* align-items: center; */
  padding: 10px 0;
  cursor: pointer;
}

.attraction-content .link {
  color: #3cc1d8;
  font-size: 15px;
  margin-top: 10px;
}
</style>
