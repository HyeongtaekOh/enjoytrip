<script setup>
import { registArticle, modifyArticle } from "@/api/board";
import { ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { detailArticle } from "@/api/board";
import { useAuthStore } from "@/stores/auth";
import Swal from "sweetalert2";

const router = useRouter();
const route = useRoute();
const auth = useAuthStore();

const props = defineProps({ type: String, articleId: Number });
const { type, articleId } = props;

const article = ref({
  articleId: 0,
  userId: "",
  username: "",
  subject: "",
  content: "",
  hit: 0,
  createdTime: "",
  modifiedTime: "",
});

if (type === "modify") {
  console.log(articleId + "번글 얻으러 가자!!!");
  detailArticle(
    articleId,
    ({ data }) => {
      article.value = data;
      console.log("data =", data);
    },
    (e) => console.log(e)
  );
  console.log("article =", article.value);
} else {
  article.value.userId = auth.getUser.userId;
  article.value.username = auth.getUser.username;
}

const subjectErrMsg = ref("");
const contentErrMsg = ref("");
watch(
  () => article.value.subject,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 30) {
      subjectErrMsg.value = "제목을 확인해 주세요!!!";
    } else subjectErrMsg.value = "";
  },
  { immediate: true }
);
watch(
  () => article.value.content,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 500) {
      contentErrMsg.value = "내용을 확인해 주세요!!!";
    } else contentErrMsg.value = "";
  },
  { immediate: true }
);

function onSubmit() {
  // event.preventDefault();

  if (subjectErrMsg.value) {
    Swal.fire({
      title: subjectErrMsg.value,
      icon: "warning",
      showConfirmButton: false,
      timer: 1200,
      width: "280px",
      toast: true,
    });
  } else if (contentErrMsg.value) {
    Swal.fire({
      title: contentErrMsg.value,
      icon: "warning",
      showConfirmButton: false,
      timer: 1200,
      width: "280px",
      toast: true,
    });
  } else {
    type === "regist" ? writeArticle() : updateArticle();
  }
}

function writeArticle() {
  console.log("글등록하자!!", article.value);
  registArticle(
    article.value,
    ({ data }) => {
      console.log(data);
      Swal.fire({
        position: "top-end",
        title: "Q&A 등록 완료!",
        icon: "success",
        showConfirmButton: false,
        timer: 2000,
        width: "280px",
        toast: true,
      });
      moveDetail(data.articleId);
    },
    (error) => {
      console.log(error);
      Swal.fire({
        position: "top-end",
        title: "Q&A 등록 실패",
        icon: "error",
        showConfirmButton: false,
        timer: 2000,
        width: "280px",
        toast: true,
      });
    }
  );
}

function updateArticle() {
  let { articleno } = route.params;
  article.value.articleNo = articleno;
  console.log(articleno + "번글 수정하자!!", article.value);

  modifyArticle(
    article.value,
    ({ data }) => {
      console.log(data);
      Swal.fire({
        position: "top-end",
        title: "Q&A 수정 완료!",
        icon: "success",
        showConfirmButton: false,
        timer: 2000,
        width: "280px",
        toast: true,
      });
      moveDetail(articleId);
    },
    (error) => {
      console.log(error);
      Swal.fire({
        position: "top-end",
        title: "Q&A 수정 실패",
        icon: "error",
        showConfirmButton: false,
        timer: 2000,
        width: "280px",
        toast: true,
      });
    }
  );
}

function moveList() {
  router.push({ name: "qna-list" });
}

function moveDetail(id) {
  router.replace({ name: "qna-view", params: { articleId: id } });
}
</script>

<template>
  <form @submit.prevent="onSubmit">
    <div class="mb-3">
      <label for="userid" class="form-label">작성자 ID : </label>
      <input
        type="text"
        class="form-control"
        v-model="article.username"
        placeholder="작성자ID..."
        name="실험"
        readonly
      />
    </div>
    <div class="mb-3">
      <label for="subject" class="form-label">제목 : </label>
      <input type="text" class="form-control" v-model="article.subject" placeholder="제목..." />
    </div>
    <div class="mb-3">
      <label for="content" class="form-label">내용 : </label>
      <textarea class="form-control" v-model="article.content" rows="10"></textarea>
    </div>
    <div class="col-auto text-center">
      <button type="submit" class="btn btn-outline-primary mb-3" v-if="type === 'regist'">
        글작성
      </button>
      <button type="submit" class="btn btn-outline-success mb-3" v-else>글수정</button>
      <button type="button" class="btn btn-outline-danger mb-3 ms-1" @click="moveList">
        목록으로이동...
      </button>
    </div>
  </form>
</template>

<style scoped></style>
