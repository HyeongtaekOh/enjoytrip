<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";

const { plan } = defineProps({ plan: Object });
const router = useRouter();

const moveToDetail = () => {
  router.push({
    name: "plan-view",
    params: {
      planId: plan.planId,
    },
  });
};
</script>

<template>
  <div class="grid-item plan-item" @click="moveToDetail">
    <p>{{ plan.name }}</p>
    <p>{{ plan.userId }}</p>
    <p>{{ plan.createdTime }}</p>
    <p>{{ plan.username }}</p>
    <!-- <img :src="plan.attractions[0].firstImage" /> -->
  </div>
</template>

<style scoped>
.plan-item:hover {
  cursor: pointer;
}
</style>
