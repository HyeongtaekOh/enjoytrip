<script setup>
import { ref, computed } from "vue";
import { useRoute } from "vue-router";
import { getAttractionsByIds } from "@/api/attraction";
import { getPlanById, deletePlan } from "../../api/plan";
import InterParkImage from "@/assets/img/interpark-1.png";
import PlanMap from "@/components/plan/map/PlanMap.vue";
import PlanFormItem from "@/components/plan/item/PlanFormItem.vue";

const route = useRoute();
const attractionIds = JSON.parse(route.query.attractionIds);

const { planId } = route.params;

const plan = ref({
  planId: planId,
  name: "",
  userId: "",
  username: "",
  description: "",
  theme: "",
  departuresId: "",
  departuresName: "",
  arrivalsId: "",
  arrivalsName: "",
  createdTime: "",
  modifiedTime: "",
});
const attractions = ref([]);

getPlanById(
  planId,
  ({ data }) => {
    plan.value = data;
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
  },
  (e) => {
    console.log("getPlan error :", e);
  }
);
</script>

<template>
  <div class="modify-container">
    <div v-if="plan.name.length > 0 && attractions.length > 0" class="modify-content-container">
      <div class="modify-content">
        <div class="modify-content-header">
          <h1 class="modify-content-title">여행 계획 수정</h1>
        </div>

        <div class="modify-content-body">
          <PlanFormItem :attractions="attractions" type="modify" :plan="plan" />
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
.modify-container {
  background-color: white;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 70vw;
  height: 80vh;
  border-radius: 10px;

  .modify-content-container {
    width: 100%;
    height: 80vh;
    background-color: white;
    display: flex;
    padding-right: 0;
    border-radius: 10px;

    .modify-content {
      width: 50%;
      height: 100%;
      padding: 1.5rem;
      display: flex;
      flex-direction: column;
      justify-content: start;
      align-items: center;
      padding-top: 2.5rem;
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
