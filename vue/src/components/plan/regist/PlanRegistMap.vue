<script setup>
import { ref, onMounted } from "vue";

const { VITE_KAKAO_JAVASCRIPT_APP_KEY } = import.meta.env;

let map = null;
let infowindow = null;
let ps = null;

console.log("appkey =", VITE_KAKAO_JAVASCRIPT_APP_KEY);
const initMap = () => {
  const container = document.getElementById("map");
  const options = {
    center: new kakao.maps.LatLng(37.566826, 126.9786567),
    level: 5,
  };
  // 지도 객체를 등록합니다.
  // 지도 객체는 반응형 관리 대상이 아니므로 initMap에서 선언합니다.
  map = new kakao.maps.Map(container, options);

  infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });
  ps = new kakao.maps.services.Places(map);
  ps.categorySearch("BK9", placesSearchCB, { useMapBounds: true });
};

// 키워드 검색 완료 시 호출되는 콜백함수 입니다
function placesSearchCB(data, status, pagination) {
  if (status === kakao.maps.services.Status.OK) {
    for (let i = 0; i < data.length; i++) {
      displayMarker(data[i]);
    }
  }
}
// 지도에 마커를 표시하는 함수입니다
function displayMarker(place) {
  // 마커를 생성하고 지도에 표시합니다
  var marker = new kakao.maps.Marker({
    map: map,
    position: new kakao.maps.LatLng(place.y, place.x),
  });

  // 마커에 클릭이벤트를 등록합니다
  kakao.maps.event.addListener(marker, "click", function () {
    // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
    infowindow.setContent(
      '<div style="padding:5px;font-size:12px;">' + place.place_name + "</div>"
    );
    infowindow.open(map, marker);
  });
}

onMounted(async () => {
  if (window.kakao && window.kakao.maps) {
    initMap();
    console.log("if b", infowindow);
  } else {
    const script = document.createElement("script");
    /* global kakao */
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${VITE_KAKAO_JAVASCRIPT_APP_KEY}&libraries=services`;
    document.head.appendChild(script);
    script.onload = () => {
      kakao.maps.load(initMap);
    };
  }
});
</script>

<template>
  <div class="container">
    <div class="row justify-content-center p-0">
      <div id="map" style="width: 100%; height: 80vh"></div>
    </div>
  </div>
</template>

<style scoped></style>
