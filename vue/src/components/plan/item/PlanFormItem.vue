<script setup>
import { ref, onMounted, watch } from "vue";
import { useAuthStore } from "@/stores/auth";
import { useRouter } from "vue-router";
import { registPlan } from "@/api/plan";
import RightArrowImage from "@/assets/img/right-arrow.png";
import Swal from "sweetalert2";

const { attractions, type } = defineProps({ attractions: Array, type: String });

const auth = useAuthStore();
const router = useRouter();

const plan = ref({
  userId: auth.getUser.userId,
  name: "",
  description: "",
  theme: "",
  departuresId: attractions[0].contentId,
  departuresName: attractions[0].title,
  arrivalsId: attractions[attractions.length - 1].contentId,
  arrivalsName: attractions[attractions.length - 1].title,
  attractionIds: attractions.map((attraction) => attraction.contentId),
});

const moveToAttraction = () => {
  router.push({
    name: "attraction-map",
    query: {
      attractionIds: JSON.stringify(plan.value.attractionIds),
    },
  });
};

const onSubmit = () => {
  if (type == "regist") {
    registPlan(
      plan.value,
      () => {
        Swal.fire({
          position: "top-end",
          title: "여행 계획 게시 완료!",
          icon: "success",
          showConfirmButton: false,
          timer: 2000,
          width: "280px",
          toast: true,
        });
        router.push({ name: "plan-list" });
      },
      (error) => {
        console.log(error);
        Swal.fire({
          position: "top-end",
          title: "여행 계획 게시 실패",
          icon: "error",
          showConfirmButton: false,
          timer: 2000,
          width: "280px",
          toast: true,
        });
      }
    );
  }
};
</script>

<template>
  <form @submit.prevent="onSubmit">
    <div class="da-container mb-3">
      <div class="da-box">
        <p class="da-label">출발지</p>
        <p class="da-content">{{ plan.departuresName }}</p>
      </div>
      <img class="right-arrow" :src="RightArrowImage" alt="오른쪽 화살표" />
      <div class="da-box">
        <p class="da-label">도착지</p>
        <p class="da-content">{{ plan.arrivalsName }}</p>
      </div>
    </div>
    <div class="form-item mb-3">
      <label for="userid" class="form-label">작성자 ID : </label>
      <input
        type="text"
        class="form-control"
        v-model="auth.getUser.username"
        placeholder="작성자ID..."
        name="실험"
        readonly
      />
    </div>
    <div class="form-item mb-3">
      <label for="plan-name" class="form-label">제목 : </label>
      <input
        id="plan-name"
        type="text"
        class="form-control"
        v-model="plan.name"
        placeholder="제목..."
      />
    </div>
    <div class="form-item mb-3">
      <label for="plan-theme" class="form-label">테마 : </label>
      <select id="plan-theme" class="form-select" v-model="plan.theme">
        <option value="자연">1. 자연</option>
        <option value="문화">2. 문화</option>
        <option value="역사">3. 역사</option>
        <option value="레포츠">4. 레포츠</option>
        <option value="테마파크">5. 테마파크</option>
        <option value="쇼핑">6. 쇼핑</option>
        <option value="음식">7. 음식</option>
        <option value="숙박">8. 숙박</option>
        <option value="축제">9. 축제</option>
        <option value="건축/조형물">10. 건축/조형물</option>
        <option value="공연/행사">11. 공연/행사</option>
        <option value="기타">12. 기타</option>
      </select>
    </div>
    <div class="form-item mb-3">
      <label for="plan-description" class="form-label">내용 : </label>
      <textarea
        id="plan-description"
        class="form-control"
        v-model="plan.description"
        rows="10"
      ></textarea>
    </div>
    <div class="col-auto text-center">
      <button type="submit" class="btn btn-outline-primary mb-3" v-if="type === 'regist'">
        글작성
      </button>
      <button type="submit" class="btn btn-outline-success mb-3" v-else>글수정</button>
      <button type="button" class="btn btn-outline-danger mb-3 ms-1" @click="moveToAttraction">
        코스 수정하기
      </button>
    </div>
  </form>
</template>

<style scoped lang="scss">
form {
  display: flex;
  flex-direction: column;
  width: 32vw;
  padding-right: 1vw;

  .da-container {
    width: 32vw;
    margin-top: 1vh;
    display: flex;
    justify-content: space-evenly;
    transform: translateX(10px);

    .da-box {
      width: 5vw;
      height: 4vw;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      .da-label {
        margin: 0;
        font-size: 100%;
        color: green;
      }
      .da-content {
        margin: 0;
        width: 15vw;
        font-size: 150%;
      }
    }

    .right-arrow {
      width: 4vw;
    }
  }

  .form-item {
    display: flex;
    transform: translateX(-5px);

    .form-label {
      width: 5vw;
      text-align: center;
      font-size: 120%;
    }

    #plan-description {
      height: 150px;
    }
  }
}
</style>
