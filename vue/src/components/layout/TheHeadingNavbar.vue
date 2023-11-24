<script setup>
import { useAuthStore } from "@/stores/auth";
import { useRouter } from "vue-router";
import { login, signup } from "@/api/auth";
import { parseJwtPayload } from "@/util/jwt-utils";

const auth = useAuthStore();
const router = useRouter();

function logout() {
  localStorage.removeItem("jwt");
  auth.logoutUser();
  Swal.fire({
    position: "top-end",
    title: "로그아웃되었습니다",
    icon: "success",
    showConfirmButton: false,
    timer: 2000,
    width: "280px",
    toast: true,
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
      const { userId, username, userType } = parseJwtPayload(token);
      auth.loginUser({ userId, username, userType });
      Swal.fire({
        position: "top-end",
        title: "로그인되었습니다",
        icon: "success",
        showConfirmButton: false,
        timer: 2000,
        width: "280px",
        toast: true,
      });
    },
    (e) => {
      console.log(e);
      Swal.fire({
        position: "top-end",
        title: "로그인에 실패했습니다",
        icon: "error",
        showConfirmButton: false,
        timer: 2000,
        width: "300px",
        toast: true,
      });
    }
  );
};

const handleSignup = (username, password, email) => {
  signup(
    { username, password, email },
    () => {
      Swal.fire({
        position: "top-end",
        title: "회원가입되었습니다",
        icon: "success",
        showConfirmButton: false,
        timer: 2000,
        width: "280px",
        toast: true,
      });
    },
    () => {
      Swal.fire({
        position: "top-end",
        title: "회원가입에 실패했습니다",
        icon: "error",
        showConfirmButton: false,
        timer: 2000,
        width: "315px",
        toast: true,
      });
    }
  );
};

const showLoginForm = async () => {
  const { value: formValues } = await Swal.fire({
    title: "로그인하세요!",
    html: `
      <input type="text" id="username" class="swal2-input" placeholder="아이디">
      <input type="password" id="password" class="swal2-input" placeholder="비밀번호">
    `,
    footer: `<p>계정이 없으신가요? <span id="open-signup-modal" style="text-decoration: underline; cursor: pointer; color: #7b9c9a;"><b>회원가입</b></span>하세요!</p>`,
    confirmButtonText: "로그인",
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
    title: "회원가입하세요!",
    html: `
      <input type="text" id="signup-username" class="swal2-input" placeholder="아이디">
      <input type="password" id="signup-password" class="swal2-input" placeholder="비밀번호">
      <input type="email" id="signup-email" class="swal2-input" placeholder="이메일">
    `,
    confirmButtonText: "가입",
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
      const emailInput = Swal.getPopup().querySelector("#signup-email");
      if (!usernameInput || !passwordInput || !emailInput.value) {
        Swal.showValidationMessage(`모두 입력하세요`);
      }
      if (!emailInput.checkValidity()) {
        Swal.showValidationMessage("이메일 형식이 올바르지 않습니다");
      }
      return { username: usernameInput, password: passwordInput, email: emailInput.value };
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
</script>

<template>
  <nav class="navbar">
    <router-link :to="{ name: 'home' }">
      <div class="logo">
        <img class="logoSvg" src="@/assets/img/logo.png" alt="Enjoy Trip" />
        Enjoy Trip
      </div>
    </router-link>
    <div class="navbar_items">
      <router-link :to="{ name: 'attraction' }">
        <div class="navbar_item">관광지</div>
      </router-link>
      <div class="navbar_item">
        <router-link :to="{ name: 'plan' }">여행계획</router-link>
      </div>
      <div class="navbar_item">
        <router-link :to="{ name: 'qna' }">Q&A</router-link>
      </div>
    </div>
    <div class="loginLogoutContainer">
      <div class="logoutContainer" v-if="auth.getUser">
        <div class="navbar_item profileBtn">
          <router-link :to="{ name: 'user-mypage' }">사용자 정보</router-link>
        </div>
        <div class="navbar_item logoutBtn" @click="logout">로그아웃</div>
      </div>
      <div class="loginContainer" v-else>
        <div class="navbar_item loginBtn" @click="showLoginForm">로그인</div>
      </div>
    </div>
  </nav>
</template>

<style scoped>
@import "@/assets/css/common.css";
@import "@/assets/css/home.css";
</style>
