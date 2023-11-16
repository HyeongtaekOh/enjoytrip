<script setup>
defineProps({ article: Object });
</script>

<template>
  <tr class="text-center">
    <th scope="row">{{ article.articleId }}</th>
    <td>
      <router-link
        :to="{ name: 'qna-view', params: { articleId: article.articleId } }"
        class="article-title link-dark"
      >
        {{ article.subject }}
      </router-link>
    </td>
    <td>{{ article.userId }}</td>
    <td>{{ article.hits }}</td>
    <td>{{ article.createdTime }}</td>
  </tr>
</template>

<style scoped>
a {
  text-decoration: none;
}
@font-face {
  font-family: "GangwonEduHyeonokT_OTFMediumA";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2201-2@1.0/GangwonEduHyeonokT_OTFMediumA.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
* {
  font-family: "GangwonEduHyeonokT_OTFMediumA";
  font-weight: 600;
  font-size: 118%;
}

.container {
  padding: 0 !important;
}
</style>
