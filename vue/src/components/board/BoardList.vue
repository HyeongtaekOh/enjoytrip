<script setup>
import { ref, onMounted, computed } from "vue";
import { useRoute, useRouter, onBeforeRouteUpdate } from "vue-router";
import { listArticle } from "@/api/board";
import Swal from "sweetalert2";

import VSelect from "@/components/common/VSelect.vue";
import BoardListItem from "@/components/board/item/BoardListItem.vue";
import PrevArrowImage from "@/assets/img/prev-arrow.png";
import NextArrowImage from "@/assets/img/next-arrow.png";

const route = useRoute();
const router = useRouter();

const selectOption = ref([
  { text: "검색조건", value: "" },
  { text: "글번호", value: "article_no" },
  { text: "제목", value: "subject" },
  { text: "작성자아이디", value: "user_id" },
]);

const qnaSearchResult = ref({
  qnas: [],
  count: 0,
  page: 1,
  pageSize: 0,
  totalCount: 0,
  totalPage: 1,
});
const currentPage = ref(parseInt(route.query.page) || 1);

onBeforeRouteUpdate((to, from, next) => {
  currentPage.value = parseInt(to.query.page) || 1;
  getQnaList();
  next();
});

const param = ref({
  userId: "",
  keyword: "",
  page: computed(() => currentPage.value),
  pageSize: 9,
});

onMounted(() => {
  getQnaList();
});

const changeKey = (val) => {
  console.log("BoarList에서 선택한 조건 : " + val);
  param.value.key = val;
};

const getQnaList = () => {
  console.log("서버에서 글목록 얻어오자!!!", param.value);
  // API 호출
  listArticle(
    param.value,
    ({ data }) => {
      console.log("response data =", data);
      qnaSearchResult.value = data;
    },
    (error) => {
      console.log("BoardList getArticle List : error =", error);
    }
  );
};

const moveWrite = () => {
  router.push({ name: "qna-write" });
};

const onClickPrevPage = () => {
  if (currentPage.value == 1) {
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
    name: "qna-list",
    query: {
      page: currentPage.value - 1,
    },
  });
};

const onClickNextPage = () => {
  if (currentPage.value == qnaSearchResult.value.totalPage) {
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
    name: "qna-list",
    query: {
      page: currentPage.value + 1,
    },
  });
};
</script>

<template>
  <div class="container board-list-container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">Q&A</h2>
      </div>
      <div class="col-lg-10">
        <div class="row align-self-center mb-2">
          <div class="col-md-2 text-start">
            <button type="button" class="btn btn-outline-primary btn-sm" @click="moveWrite">
              글쓰기
            </button>
          </div>
          <div class="col-md-5 offset-5">
            <form class="d-flex">
              <VSelect :selectOption="selectOption" @onKeySelect="changeKey" />
              <div class="input-group input-group-sm">
                <input
                  type="text"
                  class="form-control"
                  v-model="param.word"
                  placeholder="검색어..."
                />
                <button class="btn btn-dark" type="button" @click="getArticleList">검색</button>
              </div>
            </form>
          </div>
        </div>
        <table class="table table-hover">
          <thead>
            <tr class="text-center">
              <th scope="col">글번호</th>
              <th scope="col">제목</th>
              <th scope="col">작성자</th>
              <th scope="col">조회수</th>
              <th scope="col">작성일</th>
            </tr>
          </thead>
          <tbody>
            <BoardListItem
              v-for="article in qnaSearchResult.qnas"
              :key="article.articleId"
              :article="article"
            ></BoardListItem>
          </tbody>
        </table>
      </div>
      <div class="page-util-wrapper">
        <a-button type="text" shape="round" size="large" @click="onClickPrevPage">
          <img :src="PrevArrowImage" alt="이전 페이지" style="width: 16px; border-radius: 50%" />
        </a-button>
        <span style="margin: 0px 10px 0px 10px; font-size: 180%; font-weight: 900">{{
          currentPage
        }}</span>
        <a-button type="text" shape="round" size="large" @click="onClickNextPage">
          <img :src="NextArrowImage" alt="이전 페이지" style="width: 16px; border-radius: 50%" />
        </a-button>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.board-list-container {
  position: relative;
  height: 80vh;

  .page-util-wrapper {
    position: absolute;
    bottom: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top: 20px;

    span {
      transform: translateY(6px);
    }
  }
}
</style>
