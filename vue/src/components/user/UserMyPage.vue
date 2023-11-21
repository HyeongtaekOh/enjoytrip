<script setup>
import { useAuthStore } from "@/stores/auth";
const auth = useAuthStore();
console.log(auth.getUser);
</script>

<template>
  <div class="container mypage-container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">MY Page</h2>
      </div>
      <div class="col-lg-10">
        <div class="card mt-3 m-auto border-0" style="max-width: 900px">
          <div class="row g-0">
            <div class="col-md-4">
              <img
                src="https://source.unsplash.com/random/250x250/?food"
                class="img-fluid rounded-start"
                alt="..."
              />
            </div>
            <div class="col-md-8">
              <div class="card-body text-start">
                <ul class="list-group list-group-flush">
                  <li class="list-group-item">{{ auth.getUser.username }}</li>
                  <li class="list-group-item">ssafy@ssafy.com</li>
                  <!-- <li class="list-group-item">{{ auth.getUser.email }}</li> -->
                </ul>
              </div>
            </div>
          </div>
        </div>
        <div>
          <button type="button" class="btn btn-outline-secondary mt-2">수정</button>
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
* {
  font-family: "GangwonEduHyeonokT_OTFMediumA";
  font-weight: 600;
}

.mypage-container {
  height: 85vh;
  font-size: 150%;
}
</style>
