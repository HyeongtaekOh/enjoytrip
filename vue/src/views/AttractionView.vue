<script setup>
import { ref, onMounted } from "vue";
import TheHeadingNavbar from "@/components/layout/TheHeadingNavbar.vue";
import AttractionMap from "@/components/attraction/AttractionMap.vue";

// 공공 데이터 정보
const script = document.createElement("script");
const { VITE_KAKAO_JAVASCRIPT_APP_KEY } = import.meta.env;
const selectedCity = ref("검색할 지역 선택");

onMounted(async () => {
  script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${VITE_KAKAO_JAVASCRIPT_APP_KEY}&libraries=services,clusterer`;
  document.head.appendChild(script);

  const areaUrl =
    "https://apis.data.go.kr/B551011/KorService1/areaCode1?serviceKey=j%2FE9ABC0KUMH52FgTNkIMQEJIzw1gceEs%2F%2FAoU8GM%2FVlH9HttcHsU60RG%2BhVx3FQXtinkq24sosklGt2mSQ7fw%3D%3D&numOfRows=20&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json";
  const response = await fetch(areaUrl, { method: "GET" });
  const data = await response.json();

  makeOption(data);
});

function makeOption(data) {
  let areas = data.response.body.items.item;
  let sel = document.getElementById("city");
  areas.forEach((area) => {
    let opt = document.createElement("option");
    opt.setAttribute("value", area.code);
    opt.appendChild(document.createTextNode(area.name));
    sel.appendChild(opt);
  });
}

function handlerCityChange(event) {
  selectedCity.value = event.target.value;
  console.log(selectedCity.value);
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
          v-model="selectedCity"
          @change="handlerCityChange"
        >
          <option value="검색할 지역 선택">검색할 지역 선택</option>
        </select>
        <select name="type" id="type" class="dropdown">
          <option value="관광지 유형">관광지 유형</option>
          <option value="관광지">관광지</option>
          <option value="문화시설">문화시설</option>
          <option value="축제공연행사">축제공연행사</option>
          <option value="여행코스">여행코스</option>
          <option value="레포츠">레포츠</option>
          <option value="숙박">숙박</option>
          <option value="쇼핑">쇼핑</option>
          <option value="음식점">음식점</option>
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
