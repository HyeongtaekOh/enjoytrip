<script setup>
import { ref } from "vue";
import { useRoute } from "vue-router";
import { getAttractionsByIds } from "@/api/attraction";
import InterParkImage from "@/assets/img/interpark-1.png";
import PlanMap from "@/components/plan/map/PlanMap.vue";
import PlanFormItem from "@/components/plan/item/PlanFormItem.vue";

const route = useRoute();
const attractionIds = JSON.parse(route.query.attractionIds);
const attractions = ref([]);

getAttractionsByIds(
  attractionIds,
  ({ data }) => {
    console.log("data :", data);
    attractions.value = data;
    console.log("attractions :", attractions.value);
  },
  (e) => {
    console.log("getAttractions error :", e);
  }
);
</script>

<template>
  <div class="write-container">
    <div v-if="attractions.length > 0" class="write-content-container">
      <div class="write-content">
        <div class="write-content-header">
          <h1 class="write-content-title">여행 계획 작성</h1>
        </div>

        <div class="write-content-body">
          <PlanFormItem :attractions="attractions" type="regist" />
        </div>
      </div>

      <PlanMap :attractions="attractions" />
    </div>
    <div v-else class="loading-container">
      <img :src="InterParkImage" class="loading" />
    </div>
  </div>
</template>

<style scoped lang="scss">
.write-container {
  background-color: white;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 70vw;
  height: 80vh;
  border-radius: 10px;

  .write-content-container {
    width: 100%;
    height: 80vh;
    background-color: white;
    display: flex;
    padding-right: 0;
    border-radius: 10px;

    .write-content {
      width: 50%;
      height: 100%;
      padding: 1rem;
      display: flex;
      flex-direction: column;
      justify-content: start;
      align-items: center;
    }
  }

  .loading-container {
    background-color: white;
    width: 100%;
    height: 80vh;
    display: flex;
    justify-content: center;
    align-items: center;

    .loading {
      height: 65vh;
    }
  }
}
</style>
