<script setup>
import { ref, onMounted, watch } from "vue";
import AttractionItem from "@/components/attraction/item/AttractionItem.vue";
import { findSidoCode, findGugunCode, getAttractionsByCondition } from "@/api/attraction.js";

const selectedSido = ref("0");
const selectedGugun = ref("0");
const selectedType = ref("0");
const selectedKeyword = ref("");
const attractions = ref([]);

let map = null;
let infowindow = null;
const positions = ref([]);
const markers = ref([]);

watch(
  () => attractions.value,
  () => {
    // 이동할 위도 경도 위치를 생성합니다
    var moveLatLon = new kakao.maps.LatLng(attractions.value.lat, attractions.value.lng);

    // 지도 중심을 부드럽게 이동시킵니다
    // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
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
    /* global kakao */
    script.onload = () => kakao.maps.load(() => initMap());
    document.head.appendChild(script);
  }
  makeSido();
  watch([selectedSido, selectedGugun, selectedType, selectedKeyword], () => {
    searchAttraction();
  });
});

watch(
  () => attractions.value,
  () => {
    positions.value = [];
    attractions.value.forEach((attraction) => {
      console.log("attractionmap:", attraction);
      let obj = {};
      obj.latlng = new kakao.maps.LatLng(attraction.latitude, attraction.longitude);
      obj.title = attraction.title;
      obj.addr1 = attraction.addr1;
      obj.firstImage = attraction.firstImage;

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
  infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });

  //   loadMarkers();
};

const loadMarkers = () => {
  // 현재 표시되어있는 marker들이 있다면 map에 등록된 marker를 제거한다.
  deleteMarkers();

  // 마커 이미지를 생성합니다
  //   const imgSrc = require("@/assets/map/markerStar.png");
  // 마커 이미지의 이미지 크기 입니다
  //   const imgSize = new kakao.maps.Size(24, 35);
  //   const markerImage = new kakao.maps.MarkerImage(imgSrc, imgSize);

  // 마커를 생성합니다
  markers.value = [];
  positions.value.forEach((position) => {
    console.log("position:", position);
    const marker = new kakao.maps.Marker({
      map: map, // 마커를 표시할 지도
      position: position.latlng, // 마커를 표시할 위치
      title: position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됨.
      img: position.firstImage,
      addr: position.addr1,

      clickable: true, // // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
      // image: markerImage, // 마커의 이미지
    });
    marker.title = position.title;
    marker.img = position.firstImage;
    marker.addr = position.addr1;
    // 마커에 클릭이벤트를 등록합니다
    kakao.maps.event.addListener(marker, "click", function () {
      // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
      infowindow.setContent(
        '<img src="${marker.img}">' +
          '<div style="padding:5px;font-size:12px;">' +
          marker.title +
          "</div>"
      );
      infowindow.open(map, marker);
    });
    markers.value.push(marker);
  });

  // 4. 지도를 이동시켜주기
  // 배열.reduce( (누적값, 현재값, 인덱스, 요소)=>{ return 결과값}, 초기값);
  const bounds = positions.value.reduce(
    (bounds, position) => bounds.extend(position.latlng),
    new kakao.maps.LatLngBounds()
  );

  map.setBounds(bounds);
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
      console.log("sidoList:", sidoList);
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

function searchAttraction() {
  console.log("searchAttraction");
  const condition = {
    sidoCode: selectedSido.value,
    gugunCode: selectedGugun.value,
    contentTypeId: selectedType.value,
    keyword: selectedKeyword.value,
  };

  console.log("condition:", condition);

  getAttractionsByCondition(condition, ({ data }) => {
    console.log("data:", data);
    attractions.value = data;
    console.log("attractions:", attractions.value);
  });
}

function handlerSidoChange(event) {
  selectedSido.value = event.target.value;
  if (selectedSido.value != 0) makeGugun(selectedSido.value);
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
.custom-overlay-box {
  position: absolute;
  left: 0;
  bottom: 40px;
  width: 288px;
  height: 132px;
  margin-left: -144px;
  text-align: left;
  overflow: hidden;
  font-size: 12px;
  font-family: "Malgun Gothic", dotum, "돋움", sans-serif;
  line-height: 1.5;
}

.custom-overlay-content {
  width: 100%;
  height: 100%;
  border-radius: 5px;
  border-bottom: 2px solid #ccc;
  border-right: 1px solid #ccc;
  overflow: hidden;
  background: #fff;
}

.custom-overlay-content .title {
  padding: 5px 0 0 10px;
  height: 30px;
  background: #eee;
  border-bottom: 1px solid #ddd;
  font-size: 18px;
  font-weight: bold;
}

.custom-overlay-content .body {
  position: relative;
  overflow: hidden;
}

.custom-overlay-content .desc {
  position: relative;
  margin: 13px 0 0 90px;
  height: 75px;
}

.custom-overlay-content .ellipsis {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.custom-overlay-content .jibun {
  font-size: 11px;
  color: #888;
  margin-top: -2px;
}

.custom-overlay-content .img {
  position: absolute;
  top: 6px;
  left: 5px;
  width: 73px;
  height: 71px;
  border: 1px solid #ddd;
  color: #888;
  overflow: hidden;
}

.custom-overlay-box .close {
  position: absolute;
  top: 10px;
  right: 10px;
  color: #888;
  width: 17px;
  height: 17px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/overlay_close.png");
  cursor: pointer;
}
</style>
