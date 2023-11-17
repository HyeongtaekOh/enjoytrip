<script setup>
import { EditOutlined } from "@ant-design/icons-vue";
import { ref } from "vue";
import { useRouter } from "vue-router";
import { getPlansByCondition } from "../../api/plan";
import PlanListItem from "./item/PlanListItem.vue";

const size = ref("large");
const router = useRouter();
const moveToRegist = () => {
  router.push({ name: "plan-regist" });
};

const plans = ref([]);

const getPlanList = () => {
  getPlansByCondition(
    {},
    ({ data }) => {
      console.log("get plan list :", data);
      plans.value = data;
    },
    (error) => console.log(error)
  );
};

getPlanList();
</script>

<template>
  <div class="button-wrapper">
    <a-button class="to-map-button" type="primary" @click="moveToRegist">
      <template #icon>
        <EditOutlined />
      </template>
      계획 짜기
    </a-button>
  </div>
  <div class="grid-container">
    <PlanListItem v-for="plan in plans" :key="plan.planId" :plan="plan" />
  </div>
</template>

<style scoped>
.grid-container {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(400px, 1fr));
  grid-auto-flow: row; /* 오른쪽에서 시작하여 자리가 없으면 아래로 이어지도록 */
  gap: 20px; /* 각 아이템 사이의 간격 */
  padding: 10px 7px 50px 7px;
}

.button-wrapper {
  padding-top: 5px;
  width: 100%;
  display: flex;
  flex-direction: row-reverse;
}

.grid-item {
  /* 게시물 스타일링 */
  background-color: cadetblue;
  border-radius: 10px;
  height: 30rem;
}

/* 화면이 작을 때 (예: 600px 미만) 한 열로 표시 */
@media (max-width: 600px) {
  .grid-container {
    grid-template-columns: 1fr;
  }
}
</style>
