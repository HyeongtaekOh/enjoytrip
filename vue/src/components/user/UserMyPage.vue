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
  type: "",
  profileImage: null,
});

onMounted(() => {
  getUser();
  // console.log("user:", user);
});

const getUser = () => {
  getMemberById(
    userId,
    ({ data }) => {
      user.value = data;
      console.log("user:", user);
    },
    (error) => {
      console.log(error);
    }
  );
};

async function modifyUseremail() {
  const { value: email } = await Swal.fire({
    title: "Input email address",
    input: "email",
    inputLabel: "Your email address",
    inputPlaceholder: "Enter your email address",
  });
  if (email) {
    Swal.fire(`Entered email: ${email}`);
  }
  console.log("form:", email);

  if (email) {
    user.value.email = email;
    console.log("updateuser:", user);

    updateMember(
      {
        userId,
        email: email,
      },
      ({ data }) => {
        // 서버 업데이트 성공 후에 user를 업데이트합니다.
        getUser();

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
    title: "새로운 비밀번호를 입력하세요",
    html: `
    <input id="swal-input1" class="swal2-input">
    <input id="swal-input2" class="swal2-input">
  `,
    focusConfirm: false,
    preConfirm: () => {
      return [
        document.getElementById("swal-input1").value,
        document.getElementById("swal-input2").value,
      ];
    },
  });
  if (formValues[0] !== formValues[1]) {
    Swal.fire({
      title: "비밀번호가 일치하지 않습니다",
      icon: "error",
      confirmButtonText: "확인",
      width: "280px",
    });
    return;
  }
  if (formValues) {
    user.value.password = formValues[0];
    console.log("updateuser:", user);

    updateMember(
      {
        userId,
        password: formValues[0],
      },
      ({ data }) => {
        getUser();

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

async function modifyUserProfile() {
  const { value: file } = await Swal.fire({
    title: "Select image",
    input: "file",
    inputAttributes: {
      accept: "image/*",
      "aria-label": "Upload your profile picture",
    },
  });
  if (file) {
    const reader = new FileReader();
    reader.onload = (e) => {
      Swal.fire({
        title: "Your uploaded picture",
        imageUrl: e.target.result,
        imageAlt: "The uploaded picture",
      });
      user.value.profileImage = reader.result;
      console.log("updateuser:", user);
      updateMember(
        {
          userId,
          profileImage: reader.result,
        },
        ({ data }) => {
          getUser();

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
    };
    reader.readAsDataURL(file);
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
              <img v-if="user.profileImage" :src="user.profileImage" class="timg" />
              <img
                v-else
                src="https://source.unsplash.com/random/250x250/?food"
                class="img"
                alt="..."
              />
              <button class="btn-profile" @click="modifyUserProfile">이미지 변경</button>
            </div>
            <div class="col-md-8">
              <div class="card-body text-start">
                <ul class="list-group list-group-flush">
                  <li class="list-group-item">
                    <span> ID : </span>
                    <span>
                      {{ user.username }}
                    </span>
                  </li>
                  <li class="list-group-item" style="display: flex; justify-content: space-between">
                    <span> 비밀번호 : ********</span>
                    <span>
                      <button class="btn" @click="modifyUserpassword">수정</button>
                    </span>
                  </li>
                  <li class="list-group-item" style="display: flex; justify-content: space-between">
                    <sapn>Email : {{ user.email }}</sapn>
                    <button class="btn" @click="modifyUseremail">수정</button>
                  </li>
                  <li class="list-group-item">{{ user.type }}</li>
                </ul>
              </div>
            </div>
          </div>
        </div>
        <div></div>
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

.btn-profile {
  /* flex: 1 1 auto; */
  border: none;
  margin: 10px;
  padding: 5px;
  width: 120x;
  height: 40px;
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

.btn-profile:hover {
  background-position: right center;
  /* change the direction of the change here */
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
  margin: 20px;
}

.timg {
  width: 250px;
  height: 250px;
  border-radius: 70%;
  margin: 20px;
}
</style>
