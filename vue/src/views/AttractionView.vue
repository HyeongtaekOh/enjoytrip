<script setup>
// import { ref, onMounted } from "vue";
import TheHeadingNavbar from "@/components/layout/TheHeadingNavbar.vue";
// import AttractionMap from "@/components/attraction/AttractionMap.vue";
// import router from "../router";

// 공공 데이터 정보
// const script = document.createElement("script");
// const { VITE_KAKAO_JAVASCRIPT_APP_KEY } = import.meta.env;
// const selectedSido = ref("0");
// const selectedGugun = ref("0");
// const selectedType = ref("0");
// const selectedKeyword = ref("");

// onMounted(async () => {
//   // script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=f7627aabd5cfa2d2c78dd40f74607950&libraries=services,clusterer`;
//   // document.head.appendChild(script);
//   // await getAttractionByCondition({
//   //   areaCode: selectedSido.value,
//   //   gunguCode: selectedGugun.value,
//   //   contentTypeId: selectedType.value,
//   //   keyword: selectedKeyword.value,
//   // });
//   // makeSido();
// });

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

// function handlerSidoChange(event) {
//   selectedSido.value = event.target.value;
// }

// function handlerGugunChange(event) {
//   selectedSido.value = event.target.value;
//   if (selectedSido.value === "검색할 지역 선택") {
//     selectedSido.value = "0";
//   }
// }

// function handlerTypeChange(event) {
//   selectedType.value = event.target.value;
//   console.log(selectedType.value);
// }
</script>

<template>
  <div class="background"></div>
  <TheHeadingNavbar></TheHeadingNavbar>
  <router-view></router-view>
</template>

<style scoped>
@import "../assets/css/background.css";
</style>
