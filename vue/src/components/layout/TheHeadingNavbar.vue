<script setup>
import { inject } from "vue";
import { useRouter } from "vue-router";
import { validateToken, login, signup } from "@/api/auth";
import { parseJwtPayload } from "@/util/jwt-utils";

const user = inject("user");
const router = useRouter();

function goHome() {
  router.push({ name: "home" });
}
function logout() {
  localStorage.removeItem("jwt");
  user.value = null;
  Swal.fire({
    title: "성공적으로 로그아웃되었어요",
    text: "다음 방문을 기다릴게요!!",
    icon: "success",
  });
  router.push({ name: "home" });
}

import Swal from "sweetalert2";

const handleLogin = (username, password) => {
  // 로그인 로직 구현, 예: API 호출
  login(
    { username, password },
    (res) => {
      const header = res.headers.authorization;
      const token = extractToken(header);
      localStorage.setItem("jwt", token);
      updateUserContext();
      Swal.fire({
        title: "성공적으로 로그인되었어요",
        text: "EnjoyTrip과 함께 행복한 여행을 즐겨주세요!!",
        icon: "success",
      });
    },
    () => {
      Swal.fire({
        title: "로그인에 실패했어요",
        text: "잠시 후 다시 시도하세요!!",
        icon: "error",
      });
    }
  );
};

const handleSignup = (username, password, email) => {
  signup(
    { username, password, email },
    () => {
      Swal.fire({
        title: "성공적으로 가입되었어요",
        text: "서비스 이용을 위해 로그인하세요!!",
        icon: "success",
      });
    },
    (error) => {
      Swal.fire({
        title: "회원가입에 실패했어요",
        text: "잠시 후 다시 시도하세요!!",
        icon: "error",
      });
    }
  );
};

const showLoginForm = async () => {
  const { value: formValues } = await Swal.fire({
    title: "로그인",
    html: `
      <input type="text" id="username" class="swal2-input" placeholder="아이디">
      <input type="password" id="password" class="swal2-input" placeholder="비밀번호">
    `,
    footer: `<p>계정이 없으신가요? <span id="open-signup-modal" style="text-decoration: underline; cursor: pointer;">회원가입</span>하세요!</p>`,
    confirmButtonText: "Sign in",
    focusConfirm: false,
    didOpen: () => {
      // 모달이 열렸을 때, 입력 필드에 엔터키 이벤트 리스너 추가
      [
        Swal.getPopup().querySelector("#username"),
        Swal.getPopup().querySelector("#password"),
      ].forEach((input) => {
        input.addEventListener("keypress", (e) => {
          if (e.key === "Enter") {
            Swal.clickConfirm();
          }
        });
      });

      document.getElementById("open-signup-modal").addEventListener("click", () => {
        Swal.close();
        showSignupModal(); // 다른 모달을 여는 함수 호출
      });
    },
    preConfirm: () => {
      const usernameInput = Swal.getPopup().querySelector("#username").value;
      const passwordInput = Swal.getPopup().querySelector("#password").value;
      if (!usernameInput || !passwordInput) {
        Swal.showValidationMessage(`모두 입력하세요`);
      }
      return { username: usernameInput, password: passwordInput };
    },
  });
  if (formValues) {
    const { username, password } = formValues;
    handleLogin(username, password);
  }
};

const showSignupModal = async () => {
  const { value: formValues } = await Swal.fire({
    title: "회원 가입",
    html: `
      <input type="text" id="signup-username" class="swal2-input" placeholder="아이디">
      <input type="password" id="signup-password" class="swal2-input" placeholder="비밀번호">
      <input type="email" id="signup-email" class="swal2-input" placeholder="이메일">
    `,
    confirmButtonText: "Sign up",
    focusConfirm: false,
    didOpen: () => {
      // 모달이 열렸을 때, 입력 필드에 엔터키 이벤트 리스너 추가
      [
        Swal.getPopup().querySelector("#signup-username"),
        Swal.getPopup().querySelector("#signup-password"),
        Swal.getPopup().querySelector("#signup-email"),
      ].forEach((input) => {
        input.addEventListener("keypress", (e) => {
          if (e.key === "Enter") {
            Swal.clickConfirm();
          }
        });
      });
    },
    preConfirm: () => {
      const usernameInput = Swal.getPopup().querySelector("#signup-username").value;
      const passwordInput = Swal.getPopup().querySelector("#signup-password").value;
      const emailInput = Swal.getPopup().querySelector("#signup-email").value;
      if (!usernameInput || !passwordInput || !emailInput) {
        Swal.showValidationMessage(`모두 입력하세요`);
      }
      return { username: usernameInput, password: passwordInput, email: emailInput };
    },
  });

  if (formValues) {
    const { username, password, email } = formValues;
    handleSignup(username, password, email);
  }
};

function extractToken(header) {
  if (header.startsWith("Bearer ")) {
    return header.substring(7);
  }

  return null;
}

function updateUserContext() {
  const token = localStorage.getItem("jwt");

  validateToken(
    token,
    () => {
      const { userId, username, userType } = parseJwtPayload(token);
      user.value = { userId, username, userType };
    },
    () => {
      user.value = null;
      localStorage.removeItem("jwt");
    }
  );
}
</script>

<template>
  <div>
    <nav class="navbar">
      <a href="#" @click="goHome">
        <div class="logo">
          <img class="logoSvg" src="@/assets/img/logo.png" alt="Enjoy Trip" />
          Enjoy Trip
        </div>
      </a>
      <div class="navbar_items">
        <a href="map.html">
          <div class="navbar_item">관광지</div>
        </a>
        <a href="plan.html">
          <div class="navbar_item">여행계획</div>
        </a>
        <div class="navbar_item">
          <router-link :to="{ name: 'qna' }">게시판</router-link>
        </div>
      </div>
      <div class="loginLogoutContainer">
        <div class="logoutContainer" v-if="user">
          <div class="navbar_item profileBtn">사용자 정보</div>
          <div class="navbar_item logoutBtn" @click="logout">로그아웃</div>
        </div>
        <div class="loginContainer" v-else>
          <div class="navbar_item loginBtn" @click="showLoginForm">로그인</div>
        </div>
      </div>
    </nav>
  </div>
</template>

<style scoped>
@import "@/assets/css/common.css";
@import "@/assets/css/home.css";
</style>
