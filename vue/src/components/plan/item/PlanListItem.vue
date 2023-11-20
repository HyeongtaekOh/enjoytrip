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
  <div class="grid-item plan-item-wrapper" @click="moveToDetail">
    <img src="@/assets/img/plan-image-test.jpg" alt="test image" />
    <div class="plan-info-wrapper d-flex flex-column">
      <span class="span-description">{{ plan.description }}</span>
      <span class="span-username">{{ plan.username }}</span>
    </div>
    <div class="hidden-wrapper"></div>
    <!-- <img :src="plan.attractions[0].firstImage" /> -->
  </div>
</template>

<style scoped lang="scss">
.plan-item-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  overflow: hidden;

  img {
    width: 100%;
    height: auto;
    object-fit: cover;
    z-index: 3;
    transition: all 0.4s ease-in-out, opacity 0.55s cubic-bezier(0.6, 0.04, 0.98, 0.335);
  }

  .plan-info-wrapper {
    display: flex;
    justify-content: center;
    align-items: center;
    position: absolute;
    width: 100%;
    height: 100%;
    z-index: 4;

    span {
      height: 40px;
      text-align: center;

      color: black;
      transition: all 0.4s ease-in-out 0.5s;
    }

    p:hover {
      cursor: default;
    }

    .span-description {
      transform: translateY(-7px);
      font-size: 350%;
      font-weight: 800;
      color: white;
    }
    .span-username {
      position: absolute;
      font-size: 220%;
      font-weight: 900;
      right: 15px;
      bottom: 5px;
    }
  }

  &:hover {
    img {
      width: 300px;
      height: 13rem;
      transform: translateX(-50%);
      border-radius: 5px;
      opacity: 0;
    }

    .span-description {
      transform: translate(10rem, -4rem);
      color: black;
      font-size: 200%;
    }

    .span-username {
      transform: translateY(5px);
      font-size: 180%;
    }
  }
}
</style>
