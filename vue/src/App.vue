<script setup>
import { ref, provide } from "vue";
import { RouterView, useRouter } from "vue-router";
import { validateToken } from "@/api/auth";
import { parseJwtPayload } from "@/util/jwt-utils";
import { serviceApi } from "./util/http-commons";
import { useAuthStore } from "@/stores/auth";
import Swal from "sweetalert2";

const auth = useAuthStore();
const router = useRouter();

updateUserContext();

function updateUserContext() {
  const token = localStorage.getItem("jwt");

  validateToken(
    token,
    () => {
      const { userId, username, userType } = parseJwtPayload(token);
      auth.loginUser({ userId, username, userType });
    },
    () => {
      auth.logoutUser();
      localStorage.removeItem("jwt");
    }
  );
}

serviceApi.interceptors.response.use(
  (response) => {
    // 응답이 성공적으로 처리된 경우
    return response;
  },
  (error) => {
    // 오류가 발생한 경우
    if (error.response && error.response.status == 401) {
      // 401 Unauthorized 응답 처리
      if (localStorage.getItem("jwt")) {
        localStorage.removeItem("jwt");
      }
      auth.logoutUser();
      Swal.fire({
        title: "로그인하셨나요?",
        text: "로그인 후에 서비스를 이용하세요!",
        icon: "warning",
        timer: 3000,
      });
      router.push({
        name: "home",
      });
    } else {
      Swal.fire({
        title: "개발자야 서버 켰니?",
        text: "서버 켜고 테스트해라 ㅎㅎ",
        icon: "warning",
        timer: 3000,
      });
      router.push({
        name: "home",
      });
    }
  }
);
</script>

<template>
  <router-view></router-view>
</template>

<style scoped>
@font-face {
  font-family: "GangwonEduHyeonokT_OTFMediumA";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2201-2@1.0/GangwonEduHyeonokT_OTFMediumA.woff")
    format("woff");
  font-weight: 600;
  font-style: 600;
}
* {
  font-family: "GangwonEduHyeonokT_OTFMediumA";
  font-weight: 600;
}
</style>
