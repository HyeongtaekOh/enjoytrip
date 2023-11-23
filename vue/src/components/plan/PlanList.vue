<script setup>
import { EditOutlined } from "@ant-design/icons-vue";
import { ref } from "vue";
import { useRoute, useRouter, onBeforeRouteUpdate } from "vue-router";
import { getPlansByCondition } from "../../api/plan";
import PlanListItem from "./item/PlanListItem.vue";
import PrevArrowImage from "@/assets/img/prev-arrow.png";
import NextArrowImage from "@/assets/img/next-arrow.png";
import Swal from "sweetalert2";

const route = useRoute();
const router = useRouter();

const moveToRegist = () => {
  router.push({ name: "attraction" });
};

const planSearchCondition = ref({
  username: "",
  keyword: "",
  page: parseInt(route.query.page) || 1,
  pageSize: 6,
});
const selected = ref("");
const keyword = ref("");

const planSearchResult = ref({
  plans: [],
  count: 0,
  page: 1,
  pageSize: 0,
  totalCount: 0,
  totalPage: 1,
});

onBeforeRouteUpdate((to, from, next) => {
  planSearchCondition.value.page = parseInt(to.query.page) || 1;
  selected.value = to.query.selected || "";
  keyword.value = to.query.keyword || "";

  getPlanList();
  next();
});

const getPlanList = () => {
  getPlansByCondition(
    planSearchCondition.value,
    ({ data }) => {
      console.log("get plan list :", data.plans);
      planSearchResult.value = data;
    },
    (error) => console.log(error)
  );
};

getPlanList();

const onClickPrevPage = () => {
  if (planSearchCondition.value.page == 1) {
    Swal.fire({
      position: "center",
      title: "첫 페이지입니다",
      icon: "warning",
      showConfirmButton: false,
      timer: 2000,
      width: "260px",
      toast: true,
    });
    return;
  }
  router.push({
    name: "plan-list",
    query: {
      page: planSearchCondition.value.page - 1,
    },
  });
};

const onClickNextPage = () => {
  if (planSearchCondition.value.page == planSearchResult.value.totalPage) {
    Swal.fire({
      position: "center",
      title: "마지막 페이지입니다",
      icon: "warning",
      showConfirmButton: false,
      timer: 2000,
      width: "290px",
      toast: true,
    });
    return;
  }
  router.push({
    name: "plan-list",
    query: {
      page: planSearchCondition.value.page + 1,
    },
  });
};

const searchPlansByCondition = () => {
  console.log("searchPlansByCondition");

  console.log("selected.value :", selected.value);
  console.log("keyword.value :", keyword.value);
  if (selected.value == "") {
    Swal.fire({
      position: "top-end",
      title: "검색 조건을 선택하세요",
      icon: "warning",
      showConfirmButton: false,
      timer: 1200,
      width: "310px",
      toast: true,
    });
    return;
  }

  if (keyword.value == "") {
    Swal.fire({
      position: "top-end",
      title: "검색어를 입력하세요",
      icon: "warning",
      showConfirmButton: false,
      timer: 1200,
      width: "310px",
      toast: true,
    });
    return;
  }

  if (selected.value == "keyword") {
    planSearchCondition.value.username = "";
    planSearchCondition.value.keyword = keyword.value;
  } else if (selected.value == "username") {
    planSearchCondition.value.keyword = "";
    planSearchCondition.value.username = keyword.value;
  }
  getPlansByCondition(
    planSearchCondition.value,
    ({ data }) => {
      planSearchResult.value = data;
      Swal.fire({
        position: "top-end",
        title: "검색 완료",
        text: `총 ${planSearchResult.value.totalCount}건의 계획이 검색되었습니다`,
        icon: "success",
        showConfirmButton: false,
        timer: 1200,
        width: "380px",
        toast: true,
      });
    },
    (error) => console.log(error)
  );

  router.push({
    name: "plan-list",
    query: {
      page: 1,
      selected: selected.value,
      keyword: keyword.value,
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
        <a-select
          v-model:value="selected"
          placeholder="검색 조건"
          size="large"
          style="width: 130px"
        >
          <a-select-option value="keyword">제목+내용</a-select-option>
          <a-select-option value="username">작성자</a-select-option>
        </a-select>
        <a-input-search
          v-model:value="keyword"
          placeholder="검색어를 입력하세요"
          enter-button="Search"
          size="large"
          style="width: 500px; margin-left: 20px"
          @search="searchPlansByCondition"
        />
      </div>
    </div>
    <div class="grid-container">
      <PlanListItem v-for="plan in planSearchResult.plans" :key="plan.planId" :plan="plan" />
    </div>
    <div class="page-util-wrapper">
      <a-button type="text" shape="round" size="large" @click="onClickPrevPage">
        <img :src="PrevArrowImage" alt="이전 페이지" style="width: 30px; border-radius: 50%" />
      </a-button>
      <span style="margin: 10px 10px 0 10px; font-size: 250%; font-weight: 900">{{
        planSearchCondition.page
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

    .search-wrapper {
      width: 40vw;
      display: flex;
      justify-content: flex-end;
      align-items: center;

      input {
        width: 200px;
        height: 45px;
        font-size: 125%;
      }
    }
  }
  .grid-container {
    height: calc(80vh - 55px);
    display: grid;
    background-color: white;
    border-radius: 10px;
    grid-template-columns: repeat(auto-fill, minmax(600px, 1fr));
    grid-template-rows: repeat(3, 232px);
    grid-auto-flow: row; /* 오른쪽에서 시작하여 자리가 없으면 아래로 이어지도록 */
    grid-auto-rows: minmax(0, 1fr); /* 각 행의 높이를 1fr로 설정 (빈틈없이 쌓임) */
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
