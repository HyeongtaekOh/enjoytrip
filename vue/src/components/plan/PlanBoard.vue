<script setup>
const formSubmit = (e) => {
  //   e.preventDefault();
  console.log("submit");
};
</script>

<template>
  <div>
    <form @submit.prevent="formSubmit">
      <input type="text" name="hey" id="hey" />
      <button>버튼</button>
    </form>
  </div>
</template>

<style scoped></style>
