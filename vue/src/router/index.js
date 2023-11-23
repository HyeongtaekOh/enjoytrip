import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
// import AppMyPage from "../views/AppMyPage.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/qna",
      name: "qna",
      component: () => import("@/views/BoardView.vue"),
      redirect: { name: "qna-list" },
      children: [
        {
          path: "list", // children에서는 /가 자동으로 붙기 때문에 빼야한다.
          name: "qna-list",
          component: () => import("@/components/board/BoardList.vue"),
        },
        {
          path: "view/:articleId",
          name: "qna-view",
          component: () => import("@/components/board/BoardDetail.vue"),
        },
        {
          path: "write",
          name: "qna-write",
          component: () => import("@/components/board/BoardWrite.vue"),
        },
        {
          path: "modify/:articleId",
          name: "qna-modify",
          component: () => import("@/components/board/BoardModify.vue"),
        },
      ],
    },
    {
      path: "/plan",
      name: "plan",
      component: () => import("@/views/PlanView.vue"),
      redirect: { name: "plan-list" },
      children: [
        {
          path: "list",
          name: "plan-list",
          component: () => import("@/components/plan/PlanList.vue"),
        },
        {
          path: "view/:planId",
          name: "plan-view",
          component: () => import("@/components/plan/PlanDetail.vue"),
        },
        {
          path: "regist",
          name: "plan-regist",
          component: () => import("@/components/plan/PlanRegist.vue"),
        },
        {
          path: "modify/:planId",
          name: "plan-modify",
          component: () => import("@/components/plan/PlanModify.vue"),
        },
      ],
    },
    {
      path: "/attraction",
      name: "attraction",
      component: () => import("@/views/AttractionView.vue"),
      redirect: { name: "attraction-map", params: { mode: "search" } },
      children: [
        {
          path: "map/:mode",
          name: "attraction-map",
          component: () => import("@/components/attraction/AttractionMap.vue"),
        },
        {
          path: "detail/:contentId",
          name: "attraction-detail",
          component: () => import("@/components/attraction/AttractionDetail.vue"),
        },
      ],
    },
    {
      path: "/user",
      name: "user",
      component: () => import("@/views/TheUserView.vue"),
      children: [
        {
          path: "mypage",
          name: "user-mypage",
          component: () => import("@/components/user/UserMyPage.vue"),
        },
      ],
    },
  ],
});

export default router;
