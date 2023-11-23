<script setup>
import { ref, onMounted, provide, inject, watch } from "vue";
import { registComment, listComment } from "@/api/comment";
import { useAuthStore } from "@/stores/auth";
import Swal from "sweetalert2";
import CommentListItem from "@/components/comment/item/CommentListItem.vue";

const { contentId, type } = defineProps({ contentId: String, type: String });
const auth = useAuthStore();

const comments = ref([]);
const content = ref("");
const newCommentId = ref(0);
const newCommentRef = ref(null);

onMounted(() => {});

const getCommentList = () => {
  const condition = {
    contentId: contentId,
    type: type,
  };
  listComment(
    condition,
    ({ data }) => {
      comments.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
};

getCommentList();

provide("getCommentList", getCommentList);

function onEnter(event) {
  if (!event.shiftKey) {
    event.preventDefault();
    writeComment();
  }
}

function writeComment() {
  console.log("writeComment :", content.value);
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
      type: type,
      content: content.value,
      contentId: contentId,
    },
    ({ data }) => {
      Swal.fire({
        position: "top-end",
        title: "댓글 등록 완료!",
        icon: "success",
        showConfirmButton: false,
        timer: 2000,
        width: "280px",
        toast: true,
      });
      newCommentId.value = data.commentId;
      content.value = "";
      getCommentList();
    },
    (error) => {
      console.log(error);
      alert("등록 실패");
    }
  );
}

const scrollToNewComment = () => {
  if (newCommentRef.value) {
    console.log("scroll event ref =", newCommentRef.value[0]);
    const nRef = newCommentRef.value[0];
    nRef.$el.scrollIntoView({ behavior: "smooth" });
  }
};

watch(
  newCommentRef,
  () => {
    console.log("update comment", newCommentRef.value[0]);
    if (newCommentRef.value) {
      scrollToNewComment();
    }
  },
  { deep: true }
);
</script>
<template>
  <div>
    <link
      href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css"
      rel="stylesheet"
    />
    <div class="container">
      <div class="col-md-12 bootstrap snippets">
        <div class="panel">
          <div class="panel-body">
            <textarea
              class="form-control"
              rows="2"
              placeholder="What are you thinking?"
              v-model="content"
              @keydown.enter="onEnter"
            ></textarea>
            <div class="mar-top clearfix">
              <button class="btn btn-sm btn-primary pull-right" @click="writeComment">
                <i class="fa fa-pencil fa-fw"></i> 등록
              </button>
              <!-- <a class="btn btn-trans btn-icon fa fa-video-camera add-tooltip" href="#"></a>
            <a class="btn btn-trans btn-icon fa fa-camera add-tooltip" href="#"></a>
            <a class="btn btn-trans btn-icon fa fa-file add-tooltip" href="#"></a> -->
            </div>
          </div>
        </div>
        <div>
          <h4>댓글</h4>
          <template v-for="comment in comments" v-if="comments">
            <CommentListItem
              v-if="comment.commentId == newCommentId"
              :key="`newComment-${comment.commentId}`"
              ref="newCommentRef"
              :comment="comment"
            />
            <CommentListItem v-else :key="`comment-${comment.commentId}`" :comment="comment" />
          </template>
          <template v-else>
            <p class="no-reply">아직 답변이 달리지 않았어요..</p>
          </template>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
* {
  text-align: left;
  margin: 3px;
}

.panel {
  padding-right: 3em;
}
.no-reply {
  padding: 60px 40px;
  text-align: center;
  color: darkgray;
  font-size: 200%;
}
</style>
