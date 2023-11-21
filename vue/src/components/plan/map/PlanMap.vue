<script setup>
import { ref, onMounted, watch } from "vue";
import courseImage from "@/assets/img/course-marker.png";
const { VITE_KAKAO_JAVASCRIPT_APP_KEY } = import.meta.env;

let map = null;
const props = defineProps({ attractions: Array });
const dots = [];
const coursePositions = [];

const initMap = () => {
  const container = document.getElementById("map");
  const options = {
    center: new kakao.maps.LatLng(37.566826, 126.9786567),
    level: 10,
  };
  // 지도 객체를 등록합니다.
  // 지도 객체는 반응형 관리 대상이 아니므로 initMap에서 선언합니다.
  map = new kakao.maps.Map(container, options);
};

const drawCourse = (courses) => {
  let courseBounds = new kakao.maps.LatLngBounds();

  courses.forEach((attraction) => {
    let position = new kakao.maps.LatLng(attraction.latitude, attraction.longitude);
    courseBounds.extend(position);
    displayCourseMarker(position);
    coursePositions.push(position);
  });
  // 코스에 포함된 여행지를 기준으로 선을 생성하고 지도위에 표시합니다
  const courseLine = new kakao.maps.Polyline({
    // 선을 표시할 지도입니다
    path: coursePositions, // 선을 구성하는 좌표 배열입니다 클릭한 위치를 넣어줍니다
    strokeWeight: 3, // 선의 두께입니다
    strokeColor: "#db4040", // 선의 색깔입니다
    strokeOpacity: 1, // 선의 불투명도입니다 0에서 1 사이값이며 0에 가까울수록 투명합니다
    strokeStyle: "solid", // 선의 스타일입니다
    zIndex: 0,
  });

  map.setBounds(courseBounds);
  courseLine.setMap(map);
};

const displayCourseMarker = (position) => {
  let imageSize = new kakao.maps.Size(49, 50);
  let imgOptions = {
    offset: new kakao.maps.Point(25, 49),
  };
  let markerImage = new kakao.maps.MarkerImage(courseImage, imageSize, imgOptions);

  let courseMarker = new kakao.maps.Marker({
    position: position,
    image: markerImage,
  });

  courseMarker.setMap(map);
};

const displayCircleDot = (position) => {
  // 클릭 지점을 표시할 빨간 동그라미 커스텀오버레이를 생성합니다
  var circleOverlay = new kakao.maps.CustomOverlay({
    content: '<span class="dot"></span>',
    position: position,
    zIndex: 6,
  });
  console.log(circleOverlay.getVisible());
  // 지도에 표시합니다
  circleOverlay.setMap(map);

  // 배열에 추가합니다
  dots.push({ circle: circleOverlay });
};

onMounted(async () => {
  if (window.kakao && window.kakao.maps) {
    initMap();
    drawCourse(props.attractions);
  } else {
    const script = document.createElement("script");
    /* global kakao */
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${VITE_KAKAO_JAVASCRIPT_APP_KEY}&libraries=services`;
    document.head.appendChild(script);
    script.onload = () => {
      kakao.maps.load(() => {
        initMap();
        console.log("attractions =", props.attractions);
        drawCourse(props.attractions);
      });
    };
  }
});
</script>

<template>
  <div>
    <div id="map" style="width: 35vw; height: 100%; border-radius: 0 10px 10px 0"></div>
  </div>
</template>

<style>
/* 여행 코스 선 그리기 */
.dot {
  overflow: hidden;
  float: left;
  width: 12px;
  height: 12px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/mini_circle.png");
}
/* .dotOverlay {
  position: relative;
  bottom: 10px;
  border-radius: 6px;
  border: 1px solid #ccc;
  border-bottom: 2px solid #ddd;
  float: left;
  font-size: 12px;
  padding: 5px;
  background: #fff;
}
.dotOverlay:nth-of-type(n) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.number {
  font-weight: bold;
  color: #ee6152;
}
.dotOverlay:after {
  content: "";
  position: absolute;
  margin-left: -6px;
  left: 50%;
  bottom: -8px;
  width: 11px;
  height: 8px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white_small.png");
} */
</style>
