<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import defaultImage from "@/assets/img/noImage.jpg";

const { plan } = defineProps({ plan: Object });
const router = useRouter();
const imgCarousel = ref(null);
const titleCarousel = ref(null);

const nextSlide = () => {
  imgCarousel.value.next();
  titleCarousel.value.next();
};

const prevSlide = () => {
  imgCarousel.value.prev();
  titleCarousel.value.prev();
};

const onImageError = (e) => {
  e.target.src = defaultImage;
};

const moveToDetail = () => {
  router.push({
    name: "plan-view",
    params: {
      planId: plan.planId,
    },
    state: {
      attractions: plan.attractions,
    },
  });
};
</script>

<template>
  <div class="grid-item plan-item-wrapper" @mouseenter="addDelay" @mouseleave="removeDelay">
    <img class="thumbnail" src="@/assets/img/plan-image-test.jpg" alt="test image" />
    <div class="plan-info-wrapper d-flex flex-column">
      <span class="span-description">{{ plan.name }}</span>
      <span class="span-username">{{ plan.username }}</span>
    </div>
    <div :class="{ 'hidden-wrapper': true }">
      <div class="carousel-wrapper">
        <a-carousel arrows :dots="false" ref="imgCarousel">
          <img
            v-for="(attraction, index) in plan.attractions"
            :src="attraction.firstImage || defaultImage"
            :key="attraction.contentId"
            @error="onImageError"
          />
        </a-carousel>
      </div>
      <div class="sub-wrapper">
        <div class="title-carousel-wrapper">
          <a-carousel class="title-carousel" arrows :dots="false" ref="titleCarousel">
            <p v-for="(attraction, index) in plan.attractions" :key="attraction.contentId">
              {{ attraction.title }}
            </p>
          </a-carousel>
        </div>
        <div class="carousel-button-wrapper">
          <button @click="prevSlide">왼쪽</button>
          <button @click="moveToDetail">계획 상세</button>
          <button @click="nextSlide">오른쪽</button>
        </div>
      </div>
    </div>
    <!-- <img :src="plan.attractions[0].firstImage" /> -->
  </div>
</template>

<style scoped lang="scss">
.plan-item-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  overflow: hidden;

  .thumbnail {
    width: 100%;
    height: auto;
    object-fit: cover;
    z-index: 3;
    transition: all 0.4s ease-in-out;
  }

  .plan-info-wrapper {
    display: flex;
    justify-content: center;
    align-items: center;
    position: absolute;
    width: 100%;
    height: 100%;
    z-index: 4;

    span {
      height: 40px;
      text-align: center;

      color: black;
      transition: all 0.4s ease-in-out 0.3s;
    }

    p:hover {
      cursor: default;
    }

    .span-description {
      transform: translateY(-7px);
      font-size: 350%;
      font-weight: 800;
      color: white;
    }
    .span-username {
      position: absolute;
      font-size: 220%;
      font-weight: 900;
      right: 15px;
      bottom: 5px;
    }
  }

  .hidden-wrapper {
    position: absolute;
    left: -10px;
    width: 100%;
    height: 100%;
    opacity: 0;
    z-index: 5;
    transition: all 0.4s ease-in-out;

    .carousel-wrapper {
      position: absolute;
      top: calc(50% - 105px);
      left: 9px;
      width: 300px;
      height: 210px;

      img {
        width: 300px;
        height: 210px;
        object-fit: cover;
        object-position: center;
        border-radius: 5px;
      }
    }

    .sub-wrapper {
      position: absolute;
      left: 300px;
      top: 60px;
      width: calc(100% - 300px);
      height: 160px;
      font-size: 200%;

      display: flex;
      flex-direction: column;
      justify-content: space-between;
      align-items: center;

      .title-carousel-wrapper {
        text-align: center;
        width: 200px;

        p {
          font-size: 120%;
          height: 80px;
        }
      }

      .carousel-button-wrapper {
        height: 70px;
        display: flex;
        justify-content: center;
        align-items: center;
      }
    }
  }

  &:hover {
    .thumbnail {
      width: 300px;
      height: 210px;
      position: absolute;
      top: calc(50% - 105px);
      left: 5px;
      border-radius: 5px;
      opacity: 0;
    }

    .span-description {
      transform: translate(10rem, -5.5rem);
      color: black;
      font-size: 200%;
      transition-delay: 0;
    }

    .span-username {
      transform: translateY(5px);
      font-size: 180%;
    }

    .hidden-wrapper {
      transform: translateX(10px);
      opacity: 1;
      transition-delay: 0.3s;
    }
  }
}
</style>
