<script setup>
import { ref, onMounted } from "vue";
import { findSidoCode, findGugunCode, getAttractionsByCondition } from "@/api/attraction.js";
const { VITE_KAKAO_JAVASCRIPT_APP_KEY } = import.meta.env;

var map;
const selectedSido = ref("0");
const selectedGugun = ref("0");
const selectedType = ref("0");
const selectedKeyword = ref("");
const altimg = "이미지가 없습니다.";

onMounted(() => {
  if (window.kakao && window.kakao.maps) {
    initMap();
  } else {
    const script = document.createElement("script");
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${import.meta.env.VITE_KAKAO_JAVASCRIPT_APP_KEY
      }&libraries=services,clusterer`;
    /* global kakao */
    script.onload = () => kakao.maps.load(() => initMap());
    document.head.appendChild(script);
  }
  makeSido();
  searchAttraction();
});

const initMap = () => {
  const container = document.getElementById("map");
  const options = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 3,
  };
  map = new kakao.maps.Map(container, options);

  // loadMarkers();
};

function makeSido() {
  findSidoCode(
    (res) => {
      console.log("res:", res)
      console.log(res.data);
      const sidoList = res.data;
      console.log("sidoList:", sidoList)
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
      console.log(data);
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

// function searchAttraction() {
//   console.log("searchAttraction");
//   getAttractionsByCondition(
//     {
//       sidoCode: selectedSido.value,
//       gugunCode: selectedGugun.value,
//       contentTypeId: selectedType.value,
//       keyword: selectedKeyword.value,
//     },
//     ({ data }) => {
//       console.log(data);
//       const attractions = data;
//       const listContainer = document.querySelector(".listContainer");
//       listContainer.style.display = "block";
//       const list = document.getElementById("trip-list");
//       list.innerHTML = "";
//       attractions.forEach((attraction) => {
//         const item = document.createElement("div");
//         item.classList.add("item");
//         item.innerHTML = `
//           <div class="img">
//             <img src="${attraction.firstImage || "@/assets/img/noImage.jpg"}"/>
//           </div>
//           <div class="info">
//             <h2>${attraction.title}</h2>
//             <p>${attraction.addr1}</p>
//           </div>
//         `;
//         list.appendChild(item);
//       });
//     },
//   )
// }
function searchAttraction() {
  console.log("searchAttraction");
  getAttractionsByCondition(
    console.log("sidoCode:", selectedSido.value),
    console.log("gugunCode:", selectedGugun.value),
    console.log("Type:", selectedType.value),
    console.log("keyword:", selectedKeyword.value),
    {
      sidoCode: selectedSido.value,
      gugunCode: selectedGugun.value,
      contentTypeId: selectedType.value,
      keyword: selectedKeyword.value,
    },
    ({ data }) => {
      console.log(data);
      const attractions = data;
      const listContainer = document.querySelector(".listContainer");
      listContainer.style.display = "block";
      const list = document.getElementById("trip-list");
      list.innerHTML = "";
      console.log("attractions:",attractions);
      attractions.forEach((attraction) => {
        const item = document.createElement("div");
        item.classList.add("item");
        item.classList.add("custom-class"); // Add a new class
        item.classList.add("triplist"); // Add the triplist class

        item.innerHTML = `
          <div class="img">
            <img src="${attraction.firstImage || "@/assets/img/noImage.jpg"}"/>
          </div>
          <div class="info">
            <h2>${attraction.title}</h2>
            <p>${attraction.addr1}</p>
          </div>
        `;
        console.log("item:",item);
        list.appendChild(item);
      });
      console.log("list:",list);
    },
  );
}

function handlerSidoChange(event) {
  selectedSido.value = event.target.value;
  if (selectedSido.value != 0)
    makeGugun(selectedSido.value);
}

function handlerGugunChange(event) {
  selectedGugun.value = event.target.value;
}

function handlerTypeChange(event) {
  selectedType.value = event.target.value;
  console.log(selectedType.value);
}
</script>

<template>
  <div class="contents">
    <div class="list">
      <form action="#" id="search" class="search">
        <select name="sido" id="sido" class="dropdown" v-model="selectedSido" @change="handlerSidoChange">
          <option value="0">전체 지역</option>
        </select>
        <select name="gugun" id="gugun" class="dropdown" v-model="selectedGugun" @change="handlerGugunChange">
          <option value="0">전체 구군</option>
        </select>
        <select name="type" id="type" class="dropdown" v-model="selectedType" @change="handlerTypeChange">
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
        <form action="#" id="search" class="search" @submit.prevent="searchAttraction">
          <input type="text" name="key" id="key" class="searchBar" placeholder="관광지를 검색하세요..."
            v-model="selectedKeyword" />
        </form>
      </form>
      <div class="row">
        <div class="listContainer" style="display: none">
          <div id="trip-list"></div>
        </div>
      </div>
    </div>
    <div id="map" class="map"></div>
  </div>
</template>

<style scoped>
@import "@/assets/css/common.css";
@import "@/assets/css/map.css";
</style>
