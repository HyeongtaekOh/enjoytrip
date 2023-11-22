<script setup>
import { ref, computed } from "vue";
import { useRoute, useRouter } from "vue-router";
import { getPlanById, deletePlan } from "../../api/plan";
import { useAuthStore } from "@/stores/auth";
import InterParkImage from "@/assets/img/interpark-1.png";
import PlanMap from "./map/PlanMap.vue";
import CommentList from "../comment/CommentList.vue";
import Swal from "sweetalert2";

const auth = useAuthStore();
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

const moveList = () => {
  router.push({
    name: "plan-list",
  });
};

const moveModify = () => {
  router.push({
    name: "plan-modify",
    params: {
      planId: planId,
    },
    query: {
      attractionIds: JSON.stringify(
        plan.value.attractions.map((attraction) => attraction.contentId)
      ),
    },
  });
};

const removePlan = () => {
  Swal.fire({
    title: "여행 계획을 삭제하시겠어요?",
    width: "460px",
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "삭제",
    cancelButtonText: `취소`,
  }).then((result) => {
    if (result.isConfirmed) {
      deletePlan(
        planId,
        ({ data }) => {
          console.log(data);
          Swal.fire({
            position: "top-end",
            title: "계획 삭제 완료!",
            icon: "success",
            showConfirmButton: false,
            timer: 2000,
            width: "280px",
            toast: true,
          });
          moveList();
        },
        (e) => {
          Swal.fire({
            position: "top-end",
            title: "계획 삭제 실패",
            icon: "error",
            showConfirmButton: false,
            timer: 2000,
            width: "280px",
            toast: true,
          });
          console.log(e);
        }
      );
    }
  });
};

getPlan();
const isUser = computed(() => {
  console.log(auth.getUser, plan.value.userId);
  return auth.getUser.userId === plan.value.userId;
});
</script>

<template>
  <div class="detail-container">
    <div v-if="plan.attractions.length > 0" class="container detail-content-container">
      <div class="row justify-content-center w-100">
        <div class="col-lg-12 text-start">
          <div class="row my-2">
            <h2 class="text-secondary px-5" style="font-size: 250%">
              {{ plan.name }}
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
            <div class="divider mb-3"></div>
            <div class="text-secondary" style="font-size: 200%">
              {{ plan.description }}
            </div>
            <div class="divider mt-3 mb-3">
              <p v-for="(attraction, index) in plan.attractions" :key="attraction.contentId">
                {{ attraction.title }}
              </p>
            </div>
            <div class="d-flex justify-content-center">
              <button
                type="button"
                class="btn btn-outline-primary mb-3"
                style="font-size: 130%"
                @click="moveList"
              >
                목록으로
              </button>
              <button
                v-if="isUser"
                type="button"
                class="btn btn-outline-success mb-3 ms-1"
                style="font-size: 130%"
                @click="moveModify"
              >
                수정
              </button>
              <button
                v-if="isUser"
                type="button"
                class="btn btn-outline-danger mb-3 ms-1"
                style="font-size: 130%"
                @click="removePlan"
              >
                삭제
              </button>
            </div>
          </div>
        </div>
      </div>
      <PlanMap :attractions="plan.attractions"></PlanMap>
    </div>
    <div v-else class="loading-container">
      <img :src="InterParkImage" class="loading" />
    </div>
    <div v-if="plan.attractions.length > 0" class="detail-comment-container w-100">
      <CommentList :content-id="plan.planId" type="plan"></CommentList>
    </div>
  </div>
</template>

<style scoped lang="scss">
.detail-container {
  .detail-content-container {
    background-color: white;
    display: flex;
    padding-right: 0;
    border-radius: 10px 10px 0 0;
  }

  .loading-container {
    background-color: white;
    width: 100%;
    height: 80vh;
    display: flex;
    justify-content: center;
    align-items: center;

    .loading {
      height: 65vh;
    }
  }

  .detail-comment-container {
    margin-top: -3px;
    padding-top: 15px;
    background-color: white;
  }
}
</style>
