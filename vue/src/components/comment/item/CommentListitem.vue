<!-- eslint-disable no-unused-vars -->
<script setup>
import { ref, onMounted, inject, watch } from "vue";
import { listComment, registComment, modifyComment, removeComment } from "@/api/comment";
import { useAuthStore } from "@/stores/auth";
import Swal from "sweetalert2";

const auth = useAuthStore();

const { comment } = defineProps({ comment: Object });
const { commentId } = comment;
const getCommentList = inject("getCommentList");
const recomments = ref([]);
const content = ref("");
const showReplyInput = ref(false);
const showModify = ref(false);
const reshowModify = ref(false);
const isUser = ref(false);
const newRecommentId = ref(0);
const newRecommentRef = ref(null);

onMounted(() => {
  isUser.value = auth.getUser.userId === comment.userId;
  getReCommentList();
});

// console.log("comment =", comment);
const getReCommentList = () => {
  // console.log("get_ReComment");
  const condition = {
    contentId: commentId,
    type: "comment",
  };
  listComment(
    condition,
    ({ data }) => {
      // console.log("data :", data);
      recomments.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
};

function writeReComment() {
  console.log("writeReComment");

  if (!content.value) {
    Swal.fire({
      title: "내용을 입력하세요",
      icon: "warning",
      showConfirmButton: false,
      timer: 1200,
      width: "246px",
      toast: true,
    });
    return;
  }

  registComment(
    {
      userId: auth.getUser.userId,
      userName: auth.getUser.username,
      type: "comment",
      content: content.value,
      contentId: commentId,
    },
    ({ data }) => {
      console.log(data);
      Swal.fire({
        position: "top-end",
        title: "댓글 등록 완료!",
        icon: "success",
        showConfirmButton: false,
        timer: 2000,
        width: "280px",
        toast: true,
      });
      showReplyInput.value = false; // 댓글 등록 후 창 숨기기
      newRecommentId.value = data.commentId;
      content.value = "";
      getReCommentList();
    },
    (error) => {
      console.log(error);
      Swal.fire({
        position: "top-end",
        title: "댓글 등록 실패",
        icon: "error",
        showConfirmButton: false,
        timer: 2000,
        width: "280px",
        toast: true,
      });
    }
  );
}

// 댓글 입력창을 나타내는 함수
function showReply() {
  showReplyInput.value = true;
}

function hideReply() {
  showReplyInput.value = false;
}

function clickModify() {
  showModify.value = !showModify.value;
}

function reclickModify() {
  reshowModify.value = !reshowModify.value;
}

function onModifyComment() {
  console.log("modifyComment");
  modifyComment(
    {
      commentId,
      content: content.value,
      userId: comment.userId,
    },
    ({ data }) => {
      console.log(data);
      Swal.fire({
        position: "top-end",
        title: "댓글 수정 완료!",
        icon: "success",
        showConfirmButton: false,
        timer: 2000,
        width: "250px",
        toast: true,
      });
      getCommentList();
    },
    (error) => {
      console.log(error);
      Swal.fire({
        position: "top-end",
        title: "댓글 수정 실패",
        icon: "error",
        showConfirmButton: false,
        timer: 2000,
        width: "280px",
        toast: true,
      });
      getCommentList();
    }
  );
}

function onRemoveComment() {
  console.log("removeComment");
  removeComment(
    commentId,
    ({ data }) => {
      console.log(data);
      Swal.fire({
        position: "top-end",
        title: "댓글 삭제 완료!",
        icon: "success",
        showConfirmButton: false,
        timer: 2000,
        width: "250px",
        toast: true,
      });
      getCommentList();
    },
    (error) => {
      console.log(error);
      Swal.fire({
        position: "top-end",
        title: "댓글 삭제 실패",
        icon: "error",
        showConfirmButton: false,
        timer: 2000,
        width: "280px",
        toast: true,
      });
    }
  );
}

const onModifyReComment = (recommentId) => {
  modifyComment(
    {
      commentId: recommentId,
      content: content.value,
      userId: "1",
    },
    ({ data }) => {
      console.log(data);
      Swal.fire({
        position: "top-end",
        title: "댓글 수정 완료!",
        icon: "success",
        showConfirmButton: false,
        timer: 2000,
        width: "250px",
        toast: true,
      });
      location.reload();
    },
    (error) => {
      console.log(error);
      alert("수정 실패");
      // location.reload();
    }
  );
};

const onRemoveReComment = (recommentId) => {
  removeComment(
    recommentId,
    ({ data }) => {
      Swal.fire({
        position: "top-end",
        title: "댓글 삭제 완료!",
        icon: "success",
        showConfirmButton: false,
        timer: 2000,
        width: "250px",
        toast: true,
      });
      location.reload();
    },
    (error) => {
      console.log(error);
      Swal.fire({
        position: "top-end",
        title: "댓글 삭제 실패",
        icon: "error",
        showConfirmButton: false,
        timer: 2000,
        width: "280px",
        toast: true,
      });
    }
  );
};

const scrollToNewComment = () => {
  if (newRecommentRef.value[0]) {
    console.log("scroll event ref =", newRecommentRef.value[0]);
    const nRef = newRecommentRef.value[0];
    nRef.scrollIntoView({ behavior: "smooth" });
  }
};

watch(
  newRecommentRef,
  () => {
    console.log("update", newRecommentRef.value[0]);
    if (newRecommentRef.value) {
      scrollToNewComment();
    }
  },
  { deep: true }
);
</script>

<template>
  <a-comment>
    <template #actions>
      <span
        key="comment-nested-reply-to"
        v-show="!showReplyInput"
        @click="showReply"
        style="font-size: 130%"
      >
        Reply to
      </span>
      <span
        key="comment-nested-reply-to"
        v-show="showReplyInput"
        @click="hideReply"
        style="font-size: 130%"
      >
        숨기기
      </span>
      <span @click="clickModify" style="font-size: 130%" v-if="isUser">{{
        showModify ? "숨기기" : "수정"
      }}</span>
      <span @click="onRemoveComment" style="font-size: 130%" v-if="isUser">삭제</span>
    </template>
    <template #author>
      <a style="font-size: 130%">{{ comment.userName }}</a>
    </template>
    <template #avatar>
      <img src="@/assets/img/noImage.jpg" alt="@/assets/img/noImage.jpg" />
    </template>
    <template #content>
      <p v-show="!showModify" style="font-size: 130%">{{ comment.content }}</p>
      <div class="panel" v-show="showModify">
        <div class="panel-body">
          <textarea
            class="form-control"
            rows="2"
            placeholder="What are you thinking?"
            v-model="content"
          ></textarea>
          <div class="mar-top clearfix">
            <button class="btn btn-sm btn-primary pull-right" @click="onModifyComment">
              <i class="fa fa-pencil fa-fw"></i> 수정
            </button>
          </div>
        </div>
      </div>
    </template>
    <div v-if="showReplyInput">
      <!-- 댓글 창의 내용 -->
      <div class="panel">
        <div class="panel-body">
          <textarea
            class="form-control"
            rows="2"
            placeholder="What are you thinking?"
            v-model="content"
          ></textarea>
          <div class="mar-top clearfix">
            <button class="btn btn-sm btn-primary pull-right" @click="writeReComment">
              <i class="fa fa-pencil fa-fw"></i> 등록
            </button>
          </div>
        </div>
      </div>
    </div>
    <a-comment v-for="recomment in recomments" :key="recomment.commentId">
      <template #actions>
        <span @click="reclickModify" style="font-size: 130%" v-if="isUser">{{
          reshowModify ? "숨기기" : "수정"
        }}</span>
        <span @click="onRemoveReComment(recomment.commentId)" style="font-size: 130%" v-if="isUser"
          >삭제</span
        >
      </template>
      <template #author>
        <a
          v-if="recomment.commentId == newRecommentId"
          ref="newRecommentRef"
          style="font-size: 130%"
          >{{ recomment.userName }}</a
        >
        <a v-else style="font-size: 130%">{{ recomment.userName }}</a>
      </template>
      <template #avatar>
        <img src="@/assets/img/noImage.jpg" alt="@/assets/img/noImage.jpg" />
      </template>
      <template #content>
        <p v-show="!reshowModify" style="font-size: 130%">{{ recomment.content }}</p>
        <div class="panel" v-show="reshowModify">
          <div class="panel-body">
            <textarea
              class="form-control"
              rows="2"
              placeholder="What are you thinking?"
              v-model="content"
            ></textarea>
            <div class="mar-top clearfix">
              <button
                class="btn btn-sm btn-primary pull-right"
                @click="onModifyReComment(recomment.commentId)"
              >
                <i class="fa fa-pencil fa-fw"></i> 수정
              </button>
            </div>
          </div>
        </div>
      </template>
    </a-comment>
  </a-comment>
</template>
<style scoped>
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
.btn {
  margin: 5px;
}
.panel {
  padding-right: 5em;
}
</style>
