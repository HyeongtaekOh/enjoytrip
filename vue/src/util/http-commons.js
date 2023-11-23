import axios from "axios";
import router from "../router";
import Swal from "sweetalert2";
import { useAuthStore } from "@/stores/auth";

const { VITE_API_BASE_URL } = import.meta.env;

const authApi = () => {
  const instance = axios.create({
    baseURL: VITE_API_BASE_URL,
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
    withCredentials: true,
  });

  return instance;
};

const serviceApi = axios.create({
  baseURL: VITE_API_BASE_URL,
  headers: {
    "Content-Type": "application/json;charset=utf-8",
  },
  withCredentials: true,
});

export { authApi, serviceApi };
