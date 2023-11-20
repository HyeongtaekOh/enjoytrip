<script setup>
import { LeftCircleOutlined, RightCircleOutlined } from "@ant-design/icons-vue";
import PlanRegistList from "./regist/PlanRegistList.vue";
import { ref } from "vue";

const carousel = ref(null);

const nextSlide = () => {
  carousel.value.next();
};

const prevSlide = () => {
  carousel.value.prev();
};
</script>

<template>
  <div class="carousel-wrapper">
    <PlanRegistList></PlanRegistList>
    <!-- <a-carousel arrows :dots="false" ref="carousel">
      <template #prevArrow>
        <div class="custom-slick-arrow" style="left: 10px; z-index: 10">
          <left-circle-outlined />
        </div>
      </template>
      <template #nextArrow>
        <div class="custom-slick-arrow" style="right: 10px">
          <right-circle-outlined />
        </div>
      </template>
      <img src="http://tong.visitkorea.or.kr/cms/resource/77/2704577_image2_1.jpg" />
      <img src="@/assets/img/plan-image-test.jpg" />
    </a-carousel>

    <button @click="prevSlide">왼쪽</button>
    <button @click="nextSlide">오른쪽</button> -->
  </div>
</template>

<style scoped>
/* For demo */
.carousel-wrapper {
  width: 500px;
}

.ant-carousel :deep(.slick-slide) {
  text-align: center;
  width: 500px;
  height: 300px;
  line-height: 160px;
  background: saddlebrown;
  overflow: hidden;
}

.ant-carousel :deep(.slick-arrow.custom-slick-arrow) {
  width: 25px;
  height: 25px;
  font-size: 25px;
  color: #fff;
  background-color: rgba(31, 45, 61, 0.11);
  opacity: 0.3;
}
.ant-carousel :deep(.custom-slick-arrow:before) {
  display: none;
}
.ant-carousel :deep(.custom-slick-arrow:hover) {
  opacity: 0.5;
}

.ant-carousel :deep(.slick-slide h3) {
  color: #fff;
}
</style>
