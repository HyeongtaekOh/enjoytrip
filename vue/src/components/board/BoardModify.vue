<script setup>
import BoardFormItem from "./item/BoardFormItem.vue";
import { useRoute, useRouter } from "vue-router";

const route = useRoute();

// const articleId = ref(route.params.articleId);
console.log(route.params);
const { articleId } = route.params;
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">글수정</mark>
        </h2>
      </div>
      <div class="col-lg-10 text-start">
        <BoardFormItem type="modify" :articleId="articleId" />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
