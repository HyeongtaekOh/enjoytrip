<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { getPlanById } from "../../api/plan";

const route = useRoute();
const router = useRouter();

const { planId } = route.params;

const plan = ref({
  planId: planId,
  name: "",
  userId: "",
  username: "",
  description: "",
  theme: "",
  departuresId: "",
  departuresName: "",
  arrivalsId: "",
  arrivalsName: "",
  attractions: [],
  createdTime: "",
  modifiedTime: "",
});

const getPlan = () => {
  getPlanById(
    planId,
    ({ data }) => {
      plan.value = data;
    },
    (e) => {
      console.log("getPlan error :", e);
    }
  );
};

const moveToList = () => {
  router.push({
    name: "plan-list",
  });
};

getPlan();
</script>

<template>
  <div class="container detail-container">
    <div class="row justify-content-center w-100">
      <div class="col-lg-12 text-start">
        <div class="row my-2">
          <h2 class="text-secondary px-5" style="font-size: 250%">
            {{ plan.planId }}. {{ plan.name }}
          </h2>
        </div>
        <div class="row">
          <div class="col-md-8">
            <div class="clearfix align-content-center">
              <img
                class="avatar me-2 float-md-start bg-light p-2"
                src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
              />
              <p style="font-size: 170%">
                <span class="fw-bold">{{ plan.username }}</span> <br />
                <span class="text-secondary fw-light">
                  {{ plan.createdTime }} 조회 : {{ plan.hit }}
                </span>
              </p>
            </div>
          </div>
          <div class="col-md-4 align-self-center text-end" style="font-size: 175%">댓글 : 17</div>
          <div class="divider mb-3"></div>
          <div class="text-secondary" style="font-size: 200%">
            {{ plan.description }}
          </div>
          <div class="divider mt-3 mb-3">
            <p v-for="(attraction, index) in plan.attractions" :key="attraction.contentId">
              {{ attraction.title }}
            </p>
          </div>
          <div class="d-flex justify-content-end">
            <button
              type="button"
              class="btn btn-outline-primary mb-3"
              style="font-size: 130%"
              @click="moveToList"
            >
              글목록
            </button>
            <button
              type="button"
              class="btn btn-outline-success mb-3 ms-1"
              style="font-size: 130%"
              @click="moveModify"
            >
              글수정
            </button>
            <button
              type="button"
              class="btn btn-outline-danger mb-3 ms-1"
              style="font-size: 130%"
              @click="onDeleteArticle"
            >
              글삭제
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.detail-container {
  height: 75vh;
}
</style>
