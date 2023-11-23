<script setup>
import { ref, onMounted, watch } from "vue";
import { useRoute, useRouter, onBeforeRouteUpdate } from "vue-router";
import { getAttractionById, getAttractionDetailById } from "@/api/attraction";
import { getPlansWithAttraction } from "@/api/plan";
import { useAuthStore } from "@/stores/auth";
import Swal from "sweetalert2";

const route = useRoute();
const router = useRouter();
const auth = useAuthStore();

console.log(route.params);
const content = ref("");
const { contentId } = route.params;

const plans = ref([]);

const attraction = ref({
  contentId: 0,
  title: "",
  addr1: "",
  addr2: "",
  firstImage: "",
  latitude: 0,
  longitude: 0,
});

const info = ref({
  contentId: 0,
  overview: "",
});

onMounted(() => {
  getAttraction();
  getPlansWithAttraction();
});

if (attraction.value.firstImage == null) {
  attraction.value.firstImage =
    "https://www.thermaxglobal.com/wp-content/uploads/2020/05/image-not-found.jpg";
}
const getAttraction = () => {
  console.log("contentId", contentId);
  getAttractionById(
    contentId,
    ({ data }) => {
      attraction.value = data;
    },
    (e) => console.log(e)
  );

  getAttractionDetailById(
    contentId,
    ({ data }) => {
      info.value = data;
      console.log(info.value);
    },
    (e) => console.log(e)
  );
};

getPlansWithAttraction(
  contentId,
  ({ data }) => {
    plans.value = data;
    console.log("plan:", plans.value);
  },
  (e) => console.log(e)
);

const goMap = () => {
  router.push({
    name: "attraction-map",
    params: {
      latitude: attraction.value.latitude,
      longitude: attraction.value.longitude,
    },
  });
};

function movePlan(plan) {
  router.push({
    name: "plan-view",
    params: { planId: plan.planId },
  });
}

const refreshPage = () => {
  location.reload();
};

const moveAttraction = (contentId) => {
  console.log("contentId:", contentId);
  router
    .push({
      name: "attraction-detail",
      params: { contentId: contentId },
    })
    .then(() => {
      refreshPage();
    });
};
// onBeforeRouteUpdate((to, from) => {
//   console.log("toId:", to.params.contentId);
//   attraction.value = getAttractionById(to.params.contentId);
//   // attraction.value = to.params.contentId;
//   // to.params.attraction = getAttractionById(from.contentId);
//   info.value = getAttractionDetailById(to.params.contentId);
//   console.log("to:", attraction.value);
// });
</script>
<template>
  <div class="container attraction-detail">
    <a-page-header
      style="width: 100%"
      :title="attraction.title"
      :sub-title="description"
      @back="goMap"
    />
    <div class="info">
      <div class="imgContainer">
        <img class="AttractionDetailimg" :src="attraction.firstImage" />
      </div>
      <div class="infoContainer">
        <div class="script">
          <p class="sub">주소<br /></p>
          <p>{{ attraction.addr1 }}</p>
          <p>{{ attraction.addr2 }}</p>
        </div>
      </div>
      <div class="infoContainer">
        <div class="script">
          <p class="sub">설명<br /></p>
          <p>{{ info.overview }}</p>
        </div>
      </div>
    </div>
    <div class="attraction-plan">
      <p class="sub">이곳을 포함한 일정</p>
      <div class="plan">
        <div v-if="plans.length === 0">
          <p>일정이 없습니다.</p>
        </div>
        <div v-else>
          <div v-for="plan in plans" :key="plan.planId" class="tr-border">
            <tr class="plan-list">
              <div class="click-plan">
                예행 제목 : {{ plan.description }}
                <button class="btn" @click="() => movePlan(plan)">go</button>
              </div>
              <p>
                출발 : {{ plan.arrivalsName
                }}<button class="btn" @click="() => moveAttraction(plan.arrivalsId)">go</button> ->
                도착 : {{ plan.departuresName
                }}<button class="btn" @click="() => moveAttraction(plan.departuresId)">go</button>
              </p>
            </tr>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
@font-face {
  font-family: "GangwonEduHyeonokT_OTFMediumA";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2201-2@1.0/GangwonEduHyeonokT_OTFMediumA.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}

@font-face {
  font-family: "JalnanGothic";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_231029@1.1/JalnanGothic.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}

@font-face {
  font-family: "TheJamsil5Bold";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2302_01@1.0/TheJamsil5Bold.woff2")
    format("woff2");
  font-weight: 700;
  font-style: normal;
}

.attraction-detail {
  font-family: "GangwonEduHyeonokT_OTFMediumA";
  font-weight: 600;
}

.attraction-detail {
  // height: 85vh;
  font-size: 150%;
}
.container {
  justify-content: center;
  align-items: flex-start;
  flex-wrap: wrap;
  width: 80vw;
  margin: 0 auto;
  min-height: 100vh;
  position: relative;
  // height: 80vh;
  background-color: white;
  border-radius: 10px;
  animation: fadeIn 0.8s ease-out;
}
::v-deep .ant-page-header-heading {
  display: flex;
  height: 80px;
  align-items: center;
}
::v-deep .ant-page-header-heading-title {
  font-family: "TheJamsil5Bold";
  font-size: 40px;
  height: 50px;
  line-height: 50px;
}

::v-deep .ant-page-header-heading-sub-title {
  font-family: "JalnanGothic";
  font-size: 14px;
  height: 50px;
  line-height: 80px;
}
.imgContainer {
  width: 45%;
  height: 30%;
  display: flex;
  align-items: center;
  margin-bottom: 30px;
  .AttractionDetailimg {
    width: 100%;
    height: 100%;
    object-fit: cover;
    border-radius: 10px;
    margin-left: 20px;
    margin-right: 20px;
  }
}

.script {
  //좌우에 여백 두고 싶어
  margin-left: 20px;
  margin-right: 20px;
}
.sub {
  font-family: "TheJamsil5Bold";
  font-weight: 700;
  font-size: 20px;
  margin-bottom: 10px;
}

.attraction-plan {
  margin-left: 20px;
  margin-right: 20px;
}
.tr-border {
  border-top: 1px solid #ccc; /* 회색 위쪽 경계선 */
  border-bottom: 1px solid #ccc; /* 회색 아래쪽 경계선 */
}

.btn {
  /* flex: 1 1 auto; */
  margin: 10px;
  padding: 5px;
  width: 50px;
  height: 30px;
  text-align: center;
  text-transform: uppercase;
  transition: 0.5s;
  background-size: 200% auto;
  color: white;
  /* text-shadow: 0px 0px 10px rgba(0,0,0,0.2);*/
  box-shadow: 0 0 20px #eee;
  border-radius: 10px;
  background-image: linear-gradient(to right, #f6d365 0%, #fda085 51%, #f6d365 100%);
}
</style>
