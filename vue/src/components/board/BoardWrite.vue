<script setup>
import BoardFormItem from "./item/BoardFormItem.vue";
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          글쓰기
        </h2>
      </div>
      <div class="col-lg-10 text-start">
        <BoardFormItem type="regist" />
      </div>
    </div>
  </div>
</template>

<style scoped>
@font-face {
  font-family: "GangwonEduHyeonokT_OTFMediumA";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2201-2@1.0/GangwonEduHyeonokT_OTFMediumA.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
* {
  font-family: "GangwonEduHyeonokT_OTFMediumA";
}
</style>
