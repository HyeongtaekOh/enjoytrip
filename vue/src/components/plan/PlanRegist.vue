<script setup>
import { ref } from "vue";
import PlanRegistMap from "./regist/PlanRegistMap.vue";
import PlanRegistForm from "./regist/PlanRegistForm.vue";

const isMapMode = ref(true);
</script>

<template>
  <div>
    <PlanRegistMap v-show="isMapMode" />
    <PlanRegistForm v-show="!isMapMode" />
  </div>
</template>

<style scoped></style>
