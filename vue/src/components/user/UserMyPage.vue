<script setup>
import { onMounted, ref } from "vue";
import { useAuthStore } from "@/stores/auth";
import { getMemberById, updateMember } from "@/api/member";
import Swal from "sweetalert2";

const auth = useAuthStore();
const userId = auth.getUser.userId;
const user = ref({
  userId: "",
  username: "",
  email: "",
  password: "",
  type:"",
});

onMounted(() => {
  getUser();
  console.log("user:", user);
});

const getUser = () => {
  getMemberById(userId,
    ({ data }) => {
      user.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
};

async function modifyUseremail() {
  const { value: formValues } = await Swal.fire({
    title: "사용자 정보 변경",
    html: `
      <input id="swal-username" class="swal2-input" value="${user.username}" placeholder="사용자 이름">
      <input id="swal-email" class="swal2-input" value="${user.email}" placeholder="이메일">
      <!-- 필요한 만큼 더 많은 입력 필드를 추가하세요 -->
    `,
    focusConfirm: false,
    preConfirm: () => {
      return {
        username: document.getElementById("swal-username").value,
        email: document.getElementById("swal-email").value,
        // 필요한 경우 더 많은 속성을 추가하세요
      };
    },
  });
  console.log("form:", formValues);

  if (formValues) {
    // 사용자 객체의 새로운 속성을 추가하거나 갱신하세요
    user.value.username = formValues.username;
    user.value.email = formValues.email;
    console.log("updateuser:", user);

    updateMember(
      { 
        userId,
        username: formValues.username,
        email: formValues.email,
      },
      ({ data }) => {
        // 서버 업데이트 성공 후에 user를 업데이트합니다.
        user.value = data;

        Swal.fire({
          position: "top-end",
          title: "사용자 정보가 변경되었습니다",
          icon: "success",
          showConfirmButton: false,
          timer: 2000,
          width: "280px",
          toast: true,
        });
      },
      (error) => {
        console.log(error);

        // 필요에 따라 에러를 처리하세요
        Swal.fire({
          position: "top-end",
          title: "사용자 정보 변경에 실패했습니다",
          icon: "error",
          showConfirmButton: false,
          timer: 2000,
          width: "280px",
          toast: true,
        });
      }
    );
  }
}

async function modifyUserpassword() {
  const { value: formValues } = await Swal.fire({
    title: "사용자 정보 변경",
    html: `
      <input id="swal-username" class="swal2-input" value="${user.username}" placeholder="사용자 이름">
      <input id="swal-password" class="swal2-input" value="${user.password}" placeholder="비밀번호">
      <!-- 필요한 만큼 더 많은 입력 필드를 추가하세요 -->
    `,
    focusConfirm: false,
    preConfirm: () => {
      return {
        username: document.getElementById("swal-username").value,
        password: document.getElementById("swal-password").value,
        // 필요한 경우 더 많은 속성을 추가하세요
      };
    },
  });
  console.log("form:", formValues);

  if (formValues) {
    // 사용자 객체의 새로운 속성을 추가하거나 갱신하세요
    user.value.username = formValues.username;
    user.value.password = formValues.password;
    console.log("updateuser:", user);

    updateMember(
      { 
        userId,
        username: formValues.username,
        password: formValues.password,
      },
      ({ data }) => {
        // 서버 업데이트 성공 후에 user를 업데이트합니다.
        user.value = data;

        Swal.fire({
          position: "top-end",
          title: "사용자 정보가 변경되었습니다",
          icon: "success",
          showConfirmButton: false,
          timer: 2000,
          width: "280px",
          toast: true,
        });
      },
      (error) => {
        console.log(error);

        // 필요에 따라 에러를 처리하세요
        Swal.fire({
          position: "top-end",
          title: "사용자 정보 변경에 실패했습니다",
          icon: "error",
          showConfirmButton: false,
          timer: 2000,
          width: "280px",
          toast: true,
        });
      }
    );
  }
}
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
              <img src="https://source.unsplash.com/random/250x250/?food" class="img" alt="..." />
            </div>
            <div class="col-md-8">
              <div class="card-body text-start">
                <ul class="list-group list-group-flush">
                  <li class="list-group-item">{{ user.username }}</li>
                  <li class="list-group-item" style="display: flex; justify-content: space-between;">{{ user.password }} <button class="btn" @click="modifyUserpassword"> 수정</button></li>
                  <li class="list-group-item" style="display: flex; justify-content: space-between;">{{ user.email }} <button class="btn" @click="modifyUseremail"> 수정</button></li>
                  <li class="list-group-item">{{ user.type }}</li>
                </ul>
                
              </div>
            </div>
          </div>
        </div>
        <div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
@font-face {
  font-family: "GangwonEduHyeonokT_OTFMediumA";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2201-2@1.0/GangwonEduHyeonokT_OTFMediumA.woff") format("woff");
  font-weight: 600;
  font-style: 600;
}

.mypage-container {
  font-family: "GangwonEduHyeonokT_OTFMediumA";
  font-weight: 600;
}

.mypage-container {
  height: 85vh;
  font-size: 150%;
}

.container {
  justify-content: center;
  align-items: flex-start;
  /* or align-items: baseline; */
  flex-wrap: wrap;
  width: 80vw;
  margin: 0 auto;
  min-height: 100vh;
}

.btn {
  /* flex: 1 1 auto; */
  margin: 10px;
  padding: 5px;
  width: 50px;
  height: 30px;
  text-align: center;
  text-transform: uppercase;
  transition: 0.5s;
  background-size: 200% auto;
  color: white;
  /* text-shadow: 0px 0px 10px rgba(0,0,0,0.2);*/
  box-shadow: 0 0 20px #eee;
  border-radius: 10px;
  background-image: linear-gradient(to right, #f6d365 0%, #fda085 51%, #f6d365 100%);
}

.btn:hover {
  background-position: right center;
  /* change the direction of the change here */
}

.list-group-item div {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.img {
  width: 250px;
  height: 250px;
  border-radius: 70%;
  margin: 10px;
}
</style>
