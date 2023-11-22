<script setup>
import { EditOutlined } from "@ant-design/icons-vue";
import { ref } from "vue";
import { useRoute, useRouter, onBeforeRouteUpdate } from "vue-router";
import { getPlansByCondition } from "../../api/plan";
import PlanListItem from "./item/PlanListItem.vue";
import PrevArrowImage from "@/assets/img/prev-arrow.png";
import NextArrowImage from "@/assets/img/next-arrow.png";

const route = useRoute();
const router = useRouter();

const moveToRegist = () => {
  router.push({ name: "attraction" });
};

const plans = ref([]);
const currentPage = ref(parseInt(route.query.page) || 1);

onBeforeRouteUpdate((to, from, next) => {
  currentPage.value = parseInt(to.query.page) || 1;
  getPlanList();
  next();
});

const getPlanList = () => {
  getPlansByCondition(
    {
      page: currentPage.value,
    },
    ({ data }) => {
      console.log("get plan list :", data);
      plans.value = data;
    },
    (error) => console.log(error)
  );
};

getPlanList();

const onClickPrevPage = () => {
  if (currentPage.value == 1) return;
  router.push({
    name: "plan-list",
    query: {
      page: currentPage.value - 1,
    },
  });
};

const onClickNextPage = () => {
  router.push({
    name: "plan-list",
    query: {
      page: currentPage.value + 1,
    },
  });
};
</script>

<template>
  <div class="plan-list-container">
    <div class="util-wrapper">
      <a-button class="to-map-button" type="primary" @click="moveToRegist">
        <template #icon>
          <EditOutlined class="regist-icon" />
        </template>
        여행지 검색하고 계획 짜기
      </a-button>
      <div class="search-wrapper">
        <a-input-search
          placeholder="여행지 검색"
          enter-button="Search"
          size="large"
          @search="search"
        />
      </div>
    </div>
    <div class="grid-container">
      <PlanListItem v-for="plan in plans" :key="plan.planId" :plan="plan" />
    </div>
    <div class="page-util-wrapper">
      <a-button type="text" shape="round" size="large" @click="onClickPrevPage">
        <img :src="PrevArrowImage" alt="이전 페이지" style="width: 30px; border-radius: 50%" />
      </a-button>
      <span style="margin: 10px 10px 0 10px; font-size: 250%; font-weight: 900">{{
        currentPage
      }}</span>
      <a-button type="text" shape="round" size="large" @click="onClickNextPage">
        <img :src="NextArrowImage" alt="이전 페이지" style="width: 30px; border-radius: 50%" />
      </a-button>
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
  width: 80vw;
  height: calc(80vh + 45px);
  display: flex;
  flex-direction: column;
  justify-content: start;

  .util-wrapper {
    width: 80vw;
    height: 45px;
    margin-bottom: 10px;
    display: flex;
    justify-content: space-between;
    // flex-direction: row;

    button {
      height: 45px;
      font-size: 125%;

      .regist-icon {
        transform: translateY(-4px);
      }
    }
  }
  .grid-container {
    height: calc(80vh - 55px);
    display: grid;
    background-color: white;
    border-radius: 10px;
    grid-template-columns: repeat(auto-fill, minmax(600px, 1fr));
    grid-auto-flow: row; /* 오른쪽에서 시작하여 자리가 없으면 아래로 이어지도록 */
    gap: 0 20px; /* 각 아이템 사이의 간격 */
    padding: 10px 10px 0 10px;
    overflow-y: auto;
    .grid-item {
      /* 게시물 스타일링 */
      background-color: aliceblue;
      // border: 1px solid ghostwhite;
      border-radius: 10px;
      height: 14rem;
    }

    &::-webkit-scrollbar {
      width: 2px;
    }
    &::-webkit-scrollbar-thumb {
      background-color: rgb(184, 184, 184);
    }
    &::-webkit-scrollbar-track {
      background-color: #fdfdfd;
    }
  }

  .page-util-wrapper {
    width: 80vw;
    height: 45px;
    margin-top: 10px;
    display: flex;
    justify-content: center;
    align-items: center;
    button {
      height: 45px;
      font-size: 125%;
    }
  }
}

/* 화면이 작을 때 (예: 600px 미만) 한 열로 표시 */
@media (max-width: 600px) {
  .grid-container {
    grid-template-columns: 1fr;
  }
}
</style>
