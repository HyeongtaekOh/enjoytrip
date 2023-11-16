<script setup>
import { ref, onMounted } from "vue";
import { registComment, listComment } from "@/api/comment";

import CommentListItem from "@/components/comment/item/CommentListItem.vue";

const { contentId } = defineProps({ contentId: Number });

const comments = ref([]);
const content = ref("");

onMounted(() => {
  getCommentList();
});

const getCommentList = () => {
  console.log("getComment");
  const condition = {
    contentId: contentId,
    type: "board",
  };
  listComment(
    condition,
    ({ data }) => {
      console.log("listComment ->", data);
      comments.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
};

function writeComment() {
  console.log("writeComment");
  registComment(
    {
      userId: "1",
      userName: "aa",
      type: "board",
      content: content.value,
      contentId: contentId,
    },
    ({ data }) => {
      console.log(data);
      alert("등록 성공");
      comments.value = data;
      getCommentList();
    },
    (error) => {
      console.log(error);
      alert("등록 실패");
    }
  );
}
</script>
<template>
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
        <CommentListItem v-for="comment in comments" :key="comment.commentId" :comment="comment" />
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
</style>
