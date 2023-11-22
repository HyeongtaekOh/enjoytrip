<script setup>
import { ref, onMounted, inject, watch, provide } from "vue";
import { listComment, registComment, modifyComment, removeComment } from "@/api/comment";
import { useAuthStore } from "@/stores/auth";
import Swal from "sweetalert2";
import ReCommentListItem from "./ReCommentListItem.vue";

const auth = useAuthStore();

const { comment } = defineProps({ comment: Object });
const { commentId } = comment;
const getCommentList = inject("getCommentList");
const recomments = ref([]);
const modifyContent = ref(comment.content);
const replyContent = ref("");
const showReplyInput = ref(false);
const showModify = ref(false);
const isUser = ref(false);
const modifyCommentInputRef = ref(null);
const recommentInputRef = ref(null);
const newReCommentId = ref(0);
const newReCommentRef = ref(null);

onMounted(() => {
  isUser.value = auth.getUser.userId === comment.userId;
});

const getReCommentList = () => {
  const condition = {
    contentId: commentId,
    type: "comment",
  };
  listComment(
    condition,
    ({ data }) => {
      recomments.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
};

provide("getReCommentList", getReCommentList);

getReCommentList();

// 댓글 입력창을 나타내는 함수
function showReply() {
  showReplyInput.value = true;
  setTimeout(() => {
    recommentInputRef.value.focus();
  }, 0);
}

function hideReply() {
  showReplyInput.value = false;
}

function clickModify() {
  showModify.value = !showModify.value;
  setTimeout(() => {
    modifyCommentInputRef.value.focus();
  }, 0);
}

function onEnterWrite(event) {
  if (!event.shiftKey) {
    event.preventDefault();
    writeReComment();
  }
}

function onEnterModify(event) {
  if (!event.shiftKey) {
    event.preventDefault();
    onModifyComment();
  }
}

function onModifyComment() {
  console.log("modifyComment");
  modifyComment(
    {
      commentId,
      content: modifyContent.value,
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
      showModify.value = !showModify.value;
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
function writeReComment() {
  console.log("writeReComment");

  if (!replyContent.value) {
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
      content: replyContent.value,
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
      newReCommentId.value = data.commentId;
      replyContent.value = "";
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

const scrollToNewComment = () => {
  if (newReCommentRef.value[0]) {
    console.log("scroll event ref =", newReCommentRef.value[0]);
    const nRef = newReCommentRef.value[0];
    nRef.$el.scrollIntoView({ behavior: "smooth" });
  }
};

watch(
  newReCommentRef,
  () => {
    console.log("update recomment", newReCommentRef.value[0]);
    if (newReCommentRef.value) {
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
            ref="modifyCommentInputRef"
            placeholder="What are you thinking??"
            v-model="modifyContent"
            @keydown.enter="onEnterModify"
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
            v-model="replyContent"
            ref="recommentInputRef"
            @keydown.enter="onEnterWrite"
          ></textarea>
          <div class="mar-top clearfix">
            <button class="btn btn-sm btn-primary pull-right" @click="writeReComment">
              <i class="fa fa-pencil fa-fw"></i> 등록
            </button>
          </div>
        </div>
      </div>
    </div>
    <template v-for="recomment in recomments">
      <ReCommentListItem
        v-if="recomment.commentId == newReCommentId"
        :key="`newRecomment-${recomment.commentId}`"
        ref="newReCommentRef"
        :recomment="recomment"
      /><ReCommentListItem
        v-else
        :key="`recomment-${recomment.commentId}`"
        :recomment="recomment"
      />
    </template>
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
