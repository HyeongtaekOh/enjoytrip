<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { detailArticle, deleteArticle } from "@/api/board";
import CommentList from "@/components/comment/CommentList.vue";

const route = useRoute();
const router = useRouter();

// const articleId = ref(route.params.articleId);
console.log(route.params);
const { articleId } = route.params;

const article = ref({});

onMounted(() => {
  getArticle();
});

const getArticle = () => {
  // const { articleId } = route.params;
  console.log(articleId + "번글 얻으러 가자!!!");
  // API 호출
  detailArticle(
    articleId,
    ({ data }) => {
      article.value = data;
    },
    (e) => console.log(e)
  );
};

function moveList() {
  router.push({ name: "qna-list" });
}

function moveModify() {
  router.push({ name: "qna-modify", params: { articleId } });
}

function onDeleteArticle() {
  // const { articleId } = route.params;
  console.log(articleId + "번글 삭제하러 가자!!!");
  deleteArticle(
    articleId,
    ({ data }) => {
      console.log(data);
      alert("삭제 성공!");
      moveList();
    },
    (e) => {
      alert("삭제 실패");
      console.log(e);
    }
  );
  // API 호출
}
</script>

<template>
  <div class="container">
    <div class="row justify-content-center w-100">
      <div class="col-lg-12">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center" style="font-size: 300%">Q&A</h2>
      </div>
      <div class="col-lg-12 text-start">
        <div class="row my-2">
          <h2 class="text-secondary px-5" style="font-size: 250%">
            {{ article.articleId }}. {{ article.subject }}
          </h2>
        </div>
        <div class="row">
          <div class="col-md-8">
            <div class="clearfix align-content-center">
              <img
                class="avatar me-2 float-md-start bg-light p-2"
                src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
              />
              <p style="font-size: 170%">
                <span class="fw-bold">{{ article.userId }}</span> <br />
                <span class="text-secondary fw-light">
                  {{ article.createdTime }} 조회 : {{ article.hits }}
                </span>
              </p>
            </div>
          </div>
          <div class="col-md-4 align-self-center text-end" style="font-size: 175%">댓글 : 17</div>
          <div class="divider mb-3"></div>
          <div class="text-secondary" style="font-size: 200%">
            {{ article.content }}
          </div>
          <div class="divider mt-3 mb-3"></div>
          <div class="d-flex justify-content-end">
            <button
              type="button"
              class="btn btn-outline-primary mb-3"
              style="font-size: 130%"
              @click="moveList"
            >
              글목록
            </button>
            <button
              type="button"
              class="btn btn-outline-success mb-3 ms-1"
              style="font-size: 130%"
              @click="moveModify"
            >
              글수정
            </button>
            <button
              type="button"
              class="btn btn-outline-danger mb-3 ms-1"
              style="font-size: 130%"
              @click="onDeleteArticle"
            >
              글삭제
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
  <br />
  <br />
  <br />
  <div>
    <CommentList :contentId="articleId"></CommentList>
  </div>
</template>

<style scoped></style>
