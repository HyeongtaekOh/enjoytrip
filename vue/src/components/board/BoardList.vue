<script setup>
import { ref, onMounted, computed } from "vue";
import { useRoute, useRouter, onBeforeRouteUpdate } from "vue-router";
import { listArticle } from "@/api/board";
import Swal from "sweetalert2";

import BoardListItem from "@/components/board/item/BoardListItem.vue";
import PrevArrowImage from "@/assets/img/prev-arrow.png";
import NextArrowImage from "@/assets/img/next-arrow.png";

const route = useRoute();
const router = useRouter();

const selected = ref("");
const keyword = ref("");

const qnaSearchResult = ref({
  qnas: [],
  count: 0,
  page: 1,
  pageSize: 0,
  totalCount: 0,
  totalPage: 1,
});

const qnaSearchCondition = ref({
  username: "",
  keyword: "",
  page: parseInt(route.query.page) || 1,
  pageSize: 6,
});
const currentPage = ref(parseInt(route.query.page) || 1);

onBeforeRouteUpdate((to, from, next) => {
  qnaSearchCondition.value.page = parseInt(to.query.page) || 1;
  selected.value = to.query.selected || "";
  keyword.value = to.query.keyword || "";

  if (selected.value == "keyword") {
    qnaSearchCondition.value.username = "";
    qnaSearchCondition.value.keyword = keyword.value;
  } else if (selected.value == "username") {
    qnaSearchCondition.value.keyword = "";
    qnaSearchCondition.value.username = keyword.value;
  }
  console.log("qnaSearchCondition.value123 :", qnaSearchCondition.value);
  listArticle(
    qnaSearchCondition.value,
    ({ data }) => {
      qnaSearchResult.value = data;
      console.log("qnaSearchResult.value111 :", qnaSearchResult.value);
      Swal.fire({
        position: "top-end",
        title: "검색 완료",
        text: `총 ${
          qnaSearchResult.value.totalCount > 0 ? qnaSearchResult.value.totalCount : 0
        }건의 게시글이 검색되었습니다`,
        icon: "success",
        showConfirmButton: false,
        timer: 1500,
        width: "310px",
        toast: true,
      });
    },
    (error) => {
      console.log("BoardList getArticle List : error =", error);
    }
  );
  next();
});

const param = ref({
  userId: "",
  keyword: "",
  page: computed(() => currentPage.value),
  pageSize: 9,
});

onMounted(() => {
  qnaSearchCondition.value.page = parseInt(route.query.page) || 1;
  selected.value = route.query.selected || "";
  keyword.value = route.query.keyword || "";

  if (selected.value == "keyword") {
    qnaSearchCondition.value.username = "";
    qnaSearchCondition.value.keyword = keyword.value;
  } else if (selected.value == "username") {
    qnaSearchCondition.value.keyword = "";
    qnaSearchCondition.value.username = keyword.value;
  }
  getQnaList();
});

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

  qnaSearchCondition.value.page--;
  router.push({
    name: "qna-list",
    query: {
      page: qnaSearchCondition.value.page,
      selected: selected.value,
      keyword: keyword.value,
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

  qnaSearchCondition.value.page++;
  router.push({
    name: "qna-list",
    query: {
      page: qnaSearchCondition.value.page,
      selected: selected.value,
      keyword: keyword.value,
    },
  });
};

const searchBoardByCondition = () => {
  console.log("searchBoardByCondition");

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
  router.push({
    name: "qna-list",
    query: {
      page: 1,
      selected: selected.value,
      keyword: keyword.value,
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
          <div class="search-wrapper">
        <a-select
          v-model:value="selected"
          placeholder="검색 조건"
          size="large"
          style="width: 130px"
        >
          <a-select-option value="keyword">제목</a-select-option>
          <a-select-option value="username">작성자</a-select-option>
        </a-select>
        <a-input-search
          v-model:value="keyword"
          placeholder="검색어를 입력하세요"
          enter-button="Search"
          size="large"
          style="width: 500px; margin-left: 20px"
          @search="searchBoardByCondition"
        />
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
            <BoardListItem v-for="article in qnaSearchResult.qnas" :key="article.articleId" :article="article">
            </BoardListItem>
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
</style>
