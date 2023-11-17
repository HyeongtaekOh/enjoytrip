import axios from "axios";
import router from "../router";
import Swal from "sweetalert2";
import { useAuthStore } from "@/stores/auth";

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

const serviceApi = axios.create({
  baseURL: VITE_API_BASE_URL,
  headers: {
    "Content-Type": "application/json;charset=utf-8",
  },
});

serviceApi.interceptors.request.use(
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

export { authApi, serviceApi };
