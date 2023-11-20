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
  <div class="plan-list-container">
    <div class="button-wrapper">
      <a-button class="to-map-button" type="primary" @click="moveToRegist">
        <template #icon>
          <EditOutlined class="regist-icon" />
        </template>
        계획 짜기
      </a-button>
    </div>
    <div class="grid-container">
      <PlanListItem v-for="plan in plans" :key="plan.planId" :plan="plan" />
    </div>
  </div>
</template>

<style scoped lang="scss">
@font-face {
  font-family: "GangwonEduHyeonokT_OTFMediumA";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2201-2@1.0/GangwonEduHyeonokT_OTFMediumA.woff")
    format("woff");
  font-weight: 600;
  font-style: 600;
}
* {
  font-family: "GangwonEduHyeonokT_OTFMediumA";
  font-weight: 600;
}
.plan-list-container {
  margin-top: 20px;
  height: 80vh;
}

.grid-container {
  height: calc(100% - 55px);
  display: grid;
  background-color: white;
  border-radius: 10px;
  grid-template-columns: repeat(auto-fill, minmax(600px, 1fr));
  grid-auto-flow: row; /* 오른쪽에서 시작하여 자리가 없으면 아래로 이어지도록 */
  gap: 20px; /* 각 아이템 사이의 간격 */
  padding: 10px 7px 10px 7px;
}

.button-wrapper {
  width: 100%;
  height: 45px;
  margin-bottom: 10px;
  display: flex;
  flex-direction: row;

  button {
    height: 45px;
    font-size: 125%;

    .regist-icon {
      transform: translateY(-4px);
    }
  }
}

.grid-item {
  /* 게시물 스타일링 */
  background-color: cadetblue;
  border-radius: 10px;
  height: 14rem;
}

/* 화면이 작을 때 (예: 600px 미만) 한 열로 표시 */
@media (max-width: 600px) {
  .grid-container {
    grid-template-columns: 1fr;
  }
}
</style>
