<script setup>
import { ref, reactive, defineProps } from 'vue';

const { attraction, map, newPlan} = defineProps(['attraction', 'map', 'newPlan']);
const emit = defineEmits(['updateNewPlan'])
let isSidebarOpen = false;

const state = reactive({
  list: newPlan,
  drag: false,
});

const handleButtonClick = () => {
  console.log("newPlan:",newPlan);
  if (isSidebarOpen) {
    closeNav();
  } else {
    openNav();
  }
  isSidebarOpen = !isSidebarOpen;
};

const openNav = () => {
  document.getElementById("mySidenav").style.width = "250px";
};

const closeNav = () => {
  document.getElementById("mySidenav").style.width = "0";
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
  console.log("plan:",plan);
  // `emit`을 사용할 때는 `context` 객체를 이용
  emit('updateNewPlan', { plan });
};
</script>
<template>
  <div class="main">
    <div class="button-wrapper">
      <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css" />
      <link rel="stylesheet" href="https://www.w3schools.com/lib/w3-colors-2020.css" />

      <div id="app" class="w3-container">
        <button class="w3-button w3-xlarge w3-circle w3-2020-amberglow" @click="handleButtonClick">
          +
        </button>
      </div>
      <div id="mySidenav" class="sidenav">
        <a href="javascript:void(0)" class="closebtn" @click="closeNav">&times;</a>
        <div class="list">
          <h3>New Plan</h3>
          <!-- <draggable 
          class="list-group" 
          :component-data="componentData" 
          v-model="state.list"
          v-bind="dragOptions"
          @start="state.drag = true"
        @end="state.drag = false"
        item-key="order"
        ></draggable> -->
        </div>
      </div>
    </div>
    <div class="row" @click="moveCenter(attraction.latitude, attraction.longitude)">
      <div class="imgContainer">
        <img :src="
          attraction.firstImage
            ? attraction.firstImage
            : 'http://localhost:5173/src/assets/img/noImage.jpg'
        " class="rowImage" />
      </div>
      <div class="rowContents">
        <div class="plus" @click="updateNewPlan">+ 추가하기</div>
        <div>
          <h1>{{ attraction.title }}</h1>
          <div>{{ attraction.addr1 }} {{ attraction.addr2 }}</div>
        </div>
        <div>{{ attraction.latitude }}</div>
        <div>{{ attraction.longitude }}</div>
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
