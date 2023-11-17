<script setup>
import { ref, onMounted } from "vue";
// import { findSidoCode } from "@/api/attraction";
import TheHeadingNavbar from "@/components/layout/TheHeadingNavbar.vue";
import AttractionMap from "@/components/attraction/AttractionMap.vue";

// 공공 데이터 정보
// const script = document.createElement("script");
// const { VITE_KAKAO_JAVASCRIPT_APP_KEY } = import.meta.env;
const selectedSido = ref("0");
const selectedGugun = ref("0");
const selectedType = ref("0");
const selectedKeyword = ref("");

onMounted(async () => {
  // script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=f7627aabd5cfa2d2c78dd40f74607950&libraries=services,clusterer`;
  // document.head.appendChild(script);
  // await getAttractionByCondition({
  //   areaCode: selectedSido.value,
  //   gunguCode: selectedGugun.value,
  //   contentTypeId: selectedType.value,
  //   keyword: selectedKeyword.value,
  // });
  // makeSido();
});

// function makeSido(data) {
//   console.log(data);
//   let areas = data.response.body.items.item;
//   let sel = document.getElementById("city");

//   // 다른 옵션들 추가
//   areas.forEach((area) => {
//     let opt = document.createElement("option");
//     opt.setAttribute("value", area.code);
//     opt.appendChild(document.createTextNode(area.name));
//     sel.appendChild(opt);
//   });
// }
// const makeSido = () => {
//   findSidoCode(
//     ({ data }) => {
//       console.log(data);
//     },
//     (e) => {
//       console.log(e);
//     }
//   ); // Assuming findSidoCode returns the desired data
// try {
//   findSidoCode(
//     ({ data }) => {
//       console.log(data);
//       areas = data;
//     },
//     (e) => {
//       console.log(e);
//     }
//   ); // Assuming findSidoCode returns the desired data
//   let sel = document.getElementById("city");

//   // Clear existing options
//   sel.innerHTML = "";

//   // Add default option
//   let optAll = document.createElement("option");
//   optAll.setAttribute("value", "0");
//   optAll.appendChild(document.createTextNode("전체 지역"));
//   sel.appendChild(optAll);

//   // Add other options
//   // areas.forEach((area) => {
//   //   let opt = document.createElement("option");
//   //   opt.setAttribute("value", area.code);
//   //   opt.appendChild(document.createTextNode(area.name));
//   //   sel.appendChild(opt);
//   // });
// } catch (error) {
//   console.error("Error fetching sido data:", error);
// }
// };

function handlerSidoChange(event) {
  selectedSido.value = event.target.value;
}

function handlerGugunChange(event) {
  selectedSido.value = event.target.value;
  if (selectedSido.value === "검색할 지역 선택") {
    selectedSido.value = "0";
  }
}

function handlerTypeChange(event) {
  selectedType.value = event.target.value;
  console.log(selectedType.value);
}
</script>

<template>
  <div class="background"></div>
  <TheHeadingNavbar></TheHeadingNavbar>
  <div class="contents">
    <div class="list">
      <form action="#" id="search" class="search">
        <select
          name="city"
          id="city"
          class="dropdown"
          v-model="selectedSido"
          @change="handlerSidoChange"
        >
          <option value="0">전체 지역</option>
        </select>
        <select
          name="city"
          id="city"
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
          v-moddel="selectedType"
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
        />
      </form>
      <div class="row">
        <div class="listContainer" style="display: none">
          <div id="trip-list"></div>
        </div>
      </div>
    </div>
    <div id="map" class="map">
      <AttractionMap></AttractionMap>
    </div>
  </div>
</template>

<style scoped>
@import "../assets/css/background.css";
@import "../assets/css/common.css";
@import "../assets/css/map.css";
</style>
