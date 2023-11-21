<script setup>
import { ref, onMounted, watch, computed } from "vue";
import { findSidoCode, findGugunCode, getAttractionsByCondition } from "@/api/attraction.js";
import { useRoute, useRouter } from "vue-router";
import AttractionItem from "@/components/attraction/item/AttractionItem.vue";
import draggable from "vuedraggable";

const router = useRouter();

const selectedSido = ref("0");
const selectedGugun = ref("0");
const selectedType = ref("0");
const selectedKeyword = ref("");
const attractions = ref([]);

const newPlan = ref([]);
const drag = ref(false);

let map = null;
const infoWindow = ref(null);
const info = ref({
  title: "",
  addr1: "",
  addr2: "",
  tel: "",
  firstImage: "",
});
const positions = ref([]);
const pos = ref(null);
const markers = ref([]);

const route = useRoute();
const keyword = ref(route.query.selectedKeyword);
if (keyword.value) {
  selectedKeyword.value = keyword.value;
  searchAttraction();
}

watch(
  () => attractions.value,
  () => {
    const moveLatLon = new kakao.maps.LatLng(attractions.value.lat, attractions.value.lng);

    map.panTo(moveLatLon);
  },
  { deep: true }
);

onMounted(() => {
  if (window.kakao && window.kakao.maps) {
    initMap();
  } else {
    const script = document.createElement("script");
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${
      import.meta.env.VITE_KAKAO_JAVASCRIPT_APP_KEY
    }&libraries=services,clusterer`;
    script.onload = () => kakao.maps.load(() => initMap());
    document.head.appendChild(script);
  }
  makeSido();
  watch([selectedSido, selectedGugun, selectedType, selectedKeyword], searchAttraction);
});

watch(
  () => attractions.value,
  () => {
    positions.value = [];
    attractions.value.forEach((attraction) => {
      let obj = {};
      obj.latlng = new kakao.maps.LatLng(attraction.latitude, attraction.longitude);
      obj.title = attraction.title;
      obj.addr1 = attraction.addr1;
      obj.firstImage = attraction.firstImage;
      obj.tel = attraction.tel;

      positions.value.push(obj);
    });
    loadMarkers();
  },
  { deep: true }
);

const initMap = () => {
  const container = document.getElementById("map");
  const options = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 3,
  };
  map = new kakao.maps.Map(container, options);
  infoWindow.value = new kakao.maps.CustomOverlay({
    position: null,
    content: null,
  });
};

const markerStates = ref({}); // Track marker click states

const loadMarkers = () => {
  deleteMarkers();

  markers.value = [];
  positions.value.forEach((position) => {
    const marker = new kakao.maps.Marker({
      map: map,
      position: position.latlng,
      title: position.title,
      img: position.firstImage,
      addr: position.addr1,
      tel: position.tel,
      clickable: true,
    });
    markers.value.push(marker);

    marker.addListener("click", function () {
      if (markerStates.value[position.title]) {
        infoWindow.value.setMap(null);
        markerStates.value[position.title] = false;
      } else {
        pos.value = position.latlng;
        info.value = {
          title: position.title,
          addr1: position.addr1,
          tel: position.tel,
          firstImage: position.firstImage,
        };
        if (!info.value.firstImage.includes("tong")) {
          info.value.firstImage = "http://http://localhost:5173/src/assets/img/noImage.jpg";
        }
        setInfoWindow();
        infoWindow.value.setMap(map);
        markerStates.value[position.title] = true;
      }
    });
  });

  const bounds = positions.value.reduce(
    (bounds, position) => bounds.extend(position.latlng),
    new kakao.maps.LatLngBounds()
  );

  map.setBounds(bounds);
};

const setInfoWindow = () => {
  infoWindow.value.setPosition(pos.value);
  infoWindow.value.setContent(`
    <div class="wrap">
      <div class="info">
        <div class="title">${info.value.title}</div>
        <div class="body">
          <div class="img">
            <img
              src="${info.value.firstImage}"
            />
          </div>
          <div class="desc">
            <div class="ellipsis">${info.value.addr1}</div>
            <div class="jibun ellipsis">${info.value.tel}</div>
            <div class="link">마커 클릭 시 상세 정보</div>
            <div class="plus">+ 추가하기</div>
          </div>
        </div>
      </div>
    </div>
  `);
};

const deleteMarkers = () => {
  if (markers.value.length > 0) {
    markers.value.forEach((marker) => marker.setMap(null));
  }
};

function makeSido() {
  findSidoCode(
    (res) => {
      const sidoList = res.data;
      const selectSido = document.getElementById("sido");
      sidoList.forEach((sido) => {
        const option = document.createElement("option");
        option.value = sido.sidoCode;
        option.innerText = sido.sidoName;
        selectSido.appendChild(option);
      });
    },
    (error) => {
      console.log(error);
    }
  );
}

function makeGugun() {
  findGugunCode(
    selectedSido.value,
    ({ data }) => {
      const gugunList = data;
      const selectGugun = document.getElementById("gugun");
      gugunList.forEach((gugun) => {
        const option = document.createElement("option");
        option.value = gugun.gugunCode;
        option.innerText = gugun.gugunName;
        selectGugun.appendChild(option);
      });
    },
    (error) => {
      console.log(error);
    }
  );
}

function searchAttraction() {
  const condition = {
    sidoCode: selectedSido.value,
    gugunCode: selectedGugun.value,
    type: selectedType.value,
    keyword: selectedKeyword.value,
  };

  getAttractionsByCondition(condition, ({ data }) => {
    if (data) {
      attractions.value = data;
    }
  });
}

function handlerSidoChange(event) {
  selectedSido.value = event.target.value;
  if (selectedSido.value != 0) {
    selectedGugun.value = 0;
    makeGugun(selectedSido.value);
  }
}

function handlerGugunChange(event) {
  selectedGugun.value = event.target.value;
}

function handlerTypeChange(event) {
  selectedType.value = event.target.value;
}

function updateNewPlan(data) {
  console.log("data:", data);
  newPlan.value.push({
    plan: data.plan,
    index: newPlan.value.length,
  });
  console.log("newPlan:", newPlan.value);
}

let isSidebarOpen = false;

const dragOptions = computed(() => ({
  animation: 200,
  group: "description",
  disabled: false,
  ghostClass: "ghost",
}));

const componentData = computed(() => ({
  tag: "ul",
  type: "transition-group",
  name: !drag.value ? "flip-list" : null,
}));

const handleButtonClick = () => {
  console.log("newPlan:", newPlan);
  if (isSidebarOpen) {
    closeNav();
  } else {
    openNav();
  }
  isSidebarOpen = !isSidebarOpen;
};

const handlePlanButtonClick = () => {
  const contentIds = newPlan.value.map((plan) => plan.plan.contentId);

  console.log("contentIds:", contentIds);

  router.push({
    name: "plan-regist",
    query: { attractionIds: JSON.stringify(contentIds) },
  });
};

const openNav = () => {
  document.getElementById("mySidenav").style.width = "250px";
};

const closeNav = () => {
  document.getElementById("mySidenav").style.width = "0";
};

const handleDragEnd = () => {
  const updatedOrder = newPlan.value.map((plan) => ({ ...plan, order: plan.index }));
  newPlan.value = updatedOrder;
  console.log("newPlan.order:", newPlan.value);
};

const handleDeletePlan = (element) => {
  // 삭제 버튼 클릭 시 실행되는 함수
  const index = newPlan.value.findIndex((plan) => plan.order === element.order);
  if (index !== -1) {
    newPlan.value.splice(index, 1);
  }
};
</script>

<template>
  <div class="contents">
    <div class="button-wrapper">
      <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css" />
      <link rel="stylesheet" href="https://www.w3schools.com/lib/w3-colors-2020.css" />

      <div id="app" class="w3-container">
        <button class="w3-button w3-xlarge w3-circle w3-2020-amberglow" @click="handleButtonClick">
          +
        </button>
      </div>
      <div id="mySidenav" class="sidenav">
        <h3>NewPlan</h3>
        <a href="javascript:void(0)" class="closebtn" @click="closeNav">&times;</a>
        <div class="list">
          <draggable
            class="list-group"
            :component-data="componentData"
            v-model="newPlan"
            v-bind="dragOptions"
            @start="drag = true"
            @end="handleDragEnd"
            item-key="order"
          >
            <template #item="{ element }">
              <li class="list-group-item">
                <i
                  :class="element.fixed ? 'fa fa-anchor' : 'glyphicon glyphicon-pushpin'"
                  @click="element.fixed = !element.fixed"
                  aria-hidden="true"
                ></i>
                {{ element.plan.title }}
                <span class="delete-button" @click="handleDeletePlan(element)"> -삭제 </span>
              </li>
            </template>
          </draggable>
        </div>
        <button
          class="plan-button w3-xlarge w3-circle w3-2020-amberglow"
          @click="handlePlanButtonClick"
        >
          계획 등록
        </button>
      </div>
    </div>
    <div class="list">
      <form action="#" id="search" class="search">
        <select
          name="sido"
          id="sido"
          class="dropdown"
          v-model="selectedSido"
          @change="handlerSidoChange"
        >
          <option value="0">전체 지역</option>
        </select>
        <select
          name="gugun"
          id="gugun"
          class="dropdown"
          v-model="selectedGugun"
          @change="handlerGugunChange"
        >
          <option value="0">전체 구군</option>
        </select>
        <select
          name="type"
          id="type"
          class="dropdown"
          v-model="selectedType"
          @change="handlerTypeChange"
        >
          <option value="0">전체 유형</option>
          <option value="12">관광지</option>
          <option value="14">문화시설</option>
          <option value="15">축제공연행사</option>
          <option value="25">여행코스</option>
          <option value="28">레포츠</option>
          <option value="32">숙박</option>
          <option value="38">쇼핑</option>
          <option value="39">음식점</option>
        </select>
        <input
          type="text"
          name="key"
          id="key"
          class="searchBar"
          placeholder="관광지를 검색하세요..."
          v-bind:value="selectedKeyword"
          @input="selectedKeyword = $event.target.value"
        />
      </form>
      <div class="row">
        <div class="listContainer" style="display: flex">
          <div class="trip-list">
            <AttractionItem
              v-for="attraction in attractions"
              :key="attraction.contentId"
              :attraction="attraction"
              :map="map"
              :newPlan="newPlan"
              @updateNewPlan="updateNewPlan"
            ></AttractionItem>
          </div>
        </div>
      </div>
    </div>
    <div id="map" class="map"></div>
  </div>
</template>

<style scoped>
@import "@/assets/css/common.css";
@import "@/assets/css/map.css";
.contents {
  position: relative;
}
@font-face {
  font-family: "GangwonEduHyeonokT_OTFMediumA";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2201-2@1.0/GangwonEduHyeonokT_OTFMediumA.woff")
    format("woff");
  font-weight: 600;
  font-style: 600;
}
* {
  font-family: "GangwonEduHyeonokT_OTFMediumA";
}

.plan-button {
  background-color: #dc793e;
  border: none;
  color: white;
  padding: 12px 12px;
  text-align: center;
  font-size: 16px;
  margin: 4px 2px;
  opacity: 0.6;
  transition: 0.3s;
  display: inline-block;
  text-decoration: none;
  cursor: pointer;
  border-radius: 12px;
  width: 100px;
  height: 50px;
  justify-content: center; /* 추가 */
  transform: translateX(50%); /* 추가 */
}
.plan-button:hover {
  opacity: 1;
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
  color: #5085bb;
}

.sidenav {
  height: 100%;
  width: 0;
  position: fixed;
  z-index: 3;
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

.button-wrapper {
  position: absolute;
  left: 2px;
  bottom: 15px;
  z-index: 3;
}
.flip-list-move {
  transition: transform 0.5s;
}

.no-move {
  transition: transform 0s;
}

.ghost {
  opacity: 0.5;
  background: #c8ebfb;
}

.list-group {
  min-height: 20px;
}

.list-group-item {
  position: relative;
  cursor: move;
  font-size: 130%;
}

.list-group-item i {
  cursor: pointer;
}

.w3-button {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 50px;
  height: 50px;
}
</style>
