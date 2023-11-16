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

* {
  font-size: 118%;
}

.container {
  padding: 0 !important;
}
</style>
