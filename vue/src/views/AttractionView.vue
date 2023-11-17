<script setup>
import TheHeadingNavbar from "@/components/layout/TheHeadingNavbar.vue";
</script>

<template>
  <div class="background"></div>
  <TheHeadingNavbar></TheHeadingNavbar>
  <div class="contents">
    <div class="list">
      <form action="#" id="search" class="search">
        <select name="city" id="city" class="dropdown">
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
    <div id="map" class="map"></div>
  </div>
</template>

<style scoped>
@import "../assets/css/background.css";
@import "../assets/css/common.css";
@import "../assets/css/map.css";
</style>
