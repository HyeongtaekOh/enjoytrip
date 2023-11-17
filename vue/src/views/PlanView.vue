<script setup>
import { RouterView } from "vue-router";
import { getAttractionById, getAttractionsByCondition } from "@/api/attraction";
import { getPlanById, getPlansWithAttraction, getPlansByCondition, registPlan } from "@/api/plan";
import { deletePlan, updatePlan } from "../api/plan";
import TheHeadingNavbar from "@/components/layout/TheHeadingNavbar.vue";

const getAttractions = () => {
  const condition = {
    sidoCode: 32,
    gugunCode: 0,
    type: 12,
    keyword: "휴양림",
    page: 1,
  };

  getAttractionsByCondition(
    condition,
    (res) => console.log(res),
    (e) => console.log(e)
  );
};

const getAttraction = () => {
  getAttractionById(
    125406,
    (res) => console.log(res),
    (e) => console.log(e)
  );
};

const planById = () => {
  getPlanById(
    46,
    (res) => console.log(res),
    (e) => console.log(e)
  );
};

const plansWithAttraction = () => {
  getPlansWithAttraction(
    125406,
    (res) => console.log(res),
    (e) => console.log(e)
  );
};

const plansByCondition = () => {
  const condition = {
    userId: "aa",
    keyword: "plan",
    theme: "Trip",
    attractionIds: [],
    page: 1,
  };

  getPlansByCondition(
    condition,
    (res) => console.log(res),
    (e) => console.log(e)
  );
};

const regist = () => {
  const attractionIds = [125406, 125408, 125410];

  const plan = {
    userId: "aa",
    name: "new plan",
    description: "axios plan",
    theme: "Trip",
    departuresId: 125406,
    departuresName: "비슬산자연휴양림",
    arrivalsId: 125410,
    arrivalsName: "국립 청옥산자연휴양림",
    attractionIds: attractionIds,
  };

  registPlan(
    plan,
    (res) => console.log(res),
    (e) => console.log(e)
  );
};

const update = () => {
  const plan = {
    planId: 46,
    description: "axios update plan",
    departuresId: 125410,
    departuresName: "국립 청옥산자연휴양림",
    arrivalsId: 125406,
    arrivalsName: "비슬산자연휴양림",
    attractionIds: [125410, 125408, 125407, 125406],
  };

  updatePlan(
    plan,
    (res) => console.log(res),
    (e) => console.log(e)
  );
};

const deleteP = () => {
  deletePlan(
    45,
    (res) => console.log(res),
    (e) => console.log(e)
  );
};
</script>

<template>
  <div class="background"></div>
  <TheHeadingNavbar></TheHeadingNavbar>
  <div class="container text-center mt-3 fade-container plan-container">
    <!-- <button @click="getAttractions">관광지 조건 검색 눌러!!</button>
    <button @click="getAttraction">관광지 id 검색 눌러!!</button>
    <button @click="planById">여행 계획 id 검색 눌러!!</button>
    <button @click="plansWithAttraction">여행 계획 관광지 id 검색 눌러!!</button>
    <button @click="plansByCondition">여행 계획 조건 검색 눌러!!</button>
    <button @click="regist">여행 계획 등록 눌러!!</button>
    <button @click="update">여행 계획 수정 눌러!!</button>
    <button @click="deleteP">여행 계획 삭제 눌러!!</button> -->
    <RouterView></RouterView>
  </div>
</template>

<style scoped>
@import "../assets/css/background.css";
@import "../assets/css/common.css";

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

.plan-container {
  background-color: white;
  border-radius: 10px;
}
</style>
