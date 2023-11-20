<script setup>
import { ref, onMounted, inject } from "vue";
import { modifyComment, removeComment } from "@/api/comment";
import { useAuthStore } from "@/stores/auth";
import Swal from "sweetalert2";

const auth = useAuthStore();

const { recomment } = defineProps({ recomment: Object });
const { commentId } = recomment;
const content = ref("");
const showModify = ref(false);
const isUser = ref(false);
const getReCommentList = inject("getReCommentList");

onMounted(() => {
  isUser.value = auth.getUser.userId === recomment.userId;
});

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
      getReCommentList();
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

function clickModify() {
  showModify.value = !showModify.value;
}

function onModifyComment() {
  console.log("modifyComment");
  modifyComment(
    {
      commentId,
      content: content.value,
      userId: recomment.userId,
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
      showModify.value = false;
      getReCommentList();
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
      getReCommentList();
    }
  );
}
</script>

<template>
  <a-comment>
    <template #actions>
      <span @click="clickModify" style="font-size: 130%" v-if="isUser">{{
        showModify ? "숨기기" : "수정"
      }}</span>
      <span @click="onRemoveComment" style="font-size: 130%" v-if="isUser">삭제</span>
    </template>
    <template #author>
      <a style="font-size: 130%">{{ recomment.userName }}</a>
    </template>
    <template #avatar>
      <img src="@/assets/img/noImage.jpg" alt="@/assets/img/noImage.jpg" />
    </template>
    <template #content>
      <p v-show="!showModify" style="font-size: 130%">{{ recomment.content }}</p>
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
  </a-comment>
</template>

<style scoped></style>
