import axios from "axios";
import router from "../router";
import Swal from "sweetalert2";

const { VITE_API_BASE_URL } = import.meta.env;

function authApi() {
  const instance = axios.create({
    baseURL: VITE_API_BASE_URL,
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
  });

  return instance;
}

function serviceApi() {
  const instance = authApi();

  /* 인터셉터 추가 */

  // 서비스 api 요청시 localStorage에서 jwt를 요청 헤더에 추가
  instance.interceptors.request.use(
    (config) => {
      // 요청이 전송되기 전에 실행될 코드
      const token = localStorage.getItem("jwt"); // 로컬 스토리지에서 토큰을 가져옵니다.
      if (token) {
        config.headers.Authorization = `Bearer ${token}`; // 토큰이 있다면 요청 헤더에 추가합니다.
      }
      return config;
    },
    (error) => {
      console.error("interceptor error :", error);
      // 요청 오류가 발생했을 때 실행될 코드
      return Promise.reject(error);
    }
  );

  // 서비스 api 응답코드가 401 Unauthorized일 경우 토큰이 유효하지 않으므로 localStorage에서 삭제 후 메인 페이지로 리다이렉트
  instance.interceptors.response.use(
    (response) => {
      // 응답이 성공적으로 처리된 경우
      return response;
    },
    (error) => {
      // 오류가 발생한 경우
      if (error.response && error.response.status == 401) {
        // 401 Unauthorized 응답 처리
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
        console.log("interceptor error =", error);
      }
    }
  );

  return instance;
}

export { authApi, serviceApi };
