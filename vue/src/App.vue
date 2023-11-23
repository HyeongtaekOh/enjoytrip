<script setup>
import { ref, provide } from "vue";
import { RouterView, useRouter } from "vue-router";
import { validateToken, getRefreshToken } from "@/api/auth";
import { parseJwtPayload } from "@/util/jwt-utils";
import { serviceApi } from "./util/http-commons";
import { useAuthStore } from "@/stores/auth";
import Swal from "sweetalert2";

const auth = useAuthStore();
const router = useRouter();

(function () {
  var w = window;
  if (w.ChannelIO) {
    return w.console.error("ChannelIO script included twice.");
  }
  var ch = function () {
    ch.c(arguments);
  };
  ch.q = [];
  ch.c = function (args) {
    ch.q.push(args);
  };
  w.ChannelIO = ch;
  function l() {
    if (w.ChannelIOInitialized) {
      return;
    }
    w.ChannelIOInitialized = true;
    var s = document.createElement("script");
    s.type = "text/javascript";
    s.async = true;
    s.src = "https://cdn.channel.io/plugin/ch-plugin-web.js";
    var x = document.getElementsByTagName("script")[0];
    if (x.parentNode) {
      x.parentNode.insertBefore(s, x);
    }
  }
  if (document.readyState === "complete") {
    l();
  } else {
    w.addEventListener("DOMContentLoaded", l);
    w.addEventListener("load", l);
  }
})();
ChannelIO("boot", {
  pluginKey: "02c74b2d-a219-4252-9fcc-14d622bfe67e",
});

const updateUserContext = async () => {
  let token = localStorage.getItem("jwt");

  if (token == null) {
    return;
  }

  let expired = false;
  await validateToken(
    token,
    () => {
      console.log("setup validate 성공");
      const { userId, username, userType } = parseJwtPayload(token);
      auth.loginUser({ userId, username, userType });
    },
    (error) => {
      expired = true;
      console.log("setup validate 실패");
    }
  );

  if (expired) {
    await getRefreshToken(
      token,
      (res) => {
        token = extractToken(res.headers.authorization);
        const { userId, username, userType } = parseJwtPayload(token);
        auth.loginUser({ userId, username, userType });
        localStorage.setItem("jwt", token);
      },
      (error) => {
        if (error.response && error.response.status == 400) {
          auth.logoutUser();
          localStorage.removeItem("jwt");
          Swal.fire({
            title: "세션이 만료되었습니다",
            text: "다시 로그인하세요!",
            icon: "warning",
            timer: 3000,
          });
        } else if (error.response.status == 500) {
          Swal.fire({
            title: "개발자야 서버 켰니?",
            text: "서버 켜고 테스트해라 ㅎㅎ",
            icon: "warning",
            timer: 3000,
          });
        }
      }
    );
  }
};

serviceApi.interceptors.request.use(
  async (config) => {
    // 요청이 전송되기 전에 실행될 코드
    let token = localStorage.getItem("jwt"); // 로컬 스토리지에서 토큰을 가져옵니다.
    let expired = false;
    await validateToken(
      token,
      () => {
        console.log("serviceApi request interceptor validate 성공");
      },
      (error) => {
        expired = true;
        console.log("request interceptor validate 실패");
      }
    );

    if (expired) {
      await getRefreshToken(
        token,
        (res) => {
          token = extractToken(res.headers.authorization);
          localStorage.setItem("jwt", token);
        },
        (error) => {
          if (error.response && error.response.status == 400) {
            auth.logoutUser();
            localStorage.removeItem("jwt");
            Swal.fire({
              title: "세션이 만료되었습니다",
              text: "다시 로그인하세요!",
              icon: "warning",
              timer: 3000,
            });
          } else if (error.response.status == 500) {
            Swal.fire({
              title: "개발자야 서버 켰니?",
              text: "서버 켜고 테스트해라 ㅎㅎ",
              icon: "warning",
              timer: 3000,
            });
          }
          router.push({
            name: "home",
          });
        }
      );
    }
    if (token) {
      config.headers.Authorization = `Bearer ${token}`; // 토큰이 있다면 요청 헤더에 추가합니다.
    }
    return config;
  },
  (error) => {
    console.error("request interceptor error :", error);
    // 요청 오류가 발생했을 때 실행될 코드
    return Promise.reject(error);
  }
);

serviceApi.interceptors.response.use(
  (response) => {
    // 응답이 성공적으로 처리된 경우
    return response;
  },
  (error) => {
    // 오류가 발생한 경우
    if (error.response && error.response.status == 401) {
      console.log("serviceApi response interceptor error :", error);
      // 401 Unauthorized 응답 처리

      const token = localStorage.getItem("jwt");
      console.log("그냥 api 요청 중 401 error 발생");
      auth.logoutUser();
      Swal.fire({
        title: token ? "세션이 만료되었습니다" : "로그인하셨나요?",
        text: token ? "다시 로그인하세요!" : "로그인 후에 서비스를 이용하세요!",
        icon: "warning",
        timer: 3000,
      });
      if (token) {
        localStorage.removeItem("jwt");
      }
    } else if (error.response.status == 500) {
      Swal.fire({
        title: "개발자야 서버 켰니?",
        text: "서버 켜고 테스트해라 ㅎㅎ",
        icon: "warning",
        timer: 3000,
      });
    }

    router.push({
      name: "home",
    });
  }
);

function extractToken(header) {
  if (header.startsWith("Bearer ")) {
    return header.substring(7);
  }

  return null;
}

updateUserContext();
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
