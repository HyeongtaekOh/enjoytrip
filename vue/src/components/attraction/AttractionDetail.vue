<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { getAttractionById, getAttractionDetailById } from "@/api/attraction";
import { useAuthStore } from "@/stores/auth";
import Swal from "sweetalert2";

const route = useRoute();
const router = useRouter();
const auth = useAuthStore();

console.log(route.params);
const { contentId } = route.params;

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
});

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
</script>
<template>
  <div class="container attraction-detail">
    <div class="title">
      <h1>{{ attraction.title }}</h1>
    </div>
    <div class="info">
      <div class="imgContainer">
        <img :src="attraction.firstImage" alt="attraction image" />
      </div>
      <div class="infoContainer">
        <div class="addr">
          <p>{{ attraction.addr1 }}</p>
          <p>{{ attraction.addr2 }}</p>
        </div>
      </div>
      <div class="infoContainer">
        <div class="addr">
          <p>{{ info.overview }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
@font-face {
  font-family: "GangwonEduHyeonokT_OTFMediumA";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2201-2@1.0/GangwonEduHyeonokT_OTFMediumA.woff")
    format("woff");
  font-weight: 600;
  font-style: 600;
}

.attraction-detail {
  font-family: "GangwonEduHyeonokT_OTFMediumA";
  font-weight: 600;
}

.attraction-detail {
  height: 85vh;
  font-size: 150%;
}
.container {
  justify-content: center;
  align-items: flex-start;
  flex-wrap: wrap;
  width: 80vw;
  margin: 0 auto;
  min-height: 100vh;
}
</style>
