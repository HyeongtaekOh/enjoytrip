<script setup>
import { defineProps } from "vue";

const { newPlan } = defineProps(["newPlan"]);

let isSidebarOpen = false;

const handleButtonClick = () => {
  if (isSidebarOpen) {
    closeNav();
  } else {
    openNav();
  }
  isSidebarOpen = !isSidebarOpen;
};

const openNav = () => {
  document.getElementById("mySidenav").style.width = "250px";
};

const closeNav = () => {
  document.getElementById("mySidenav").style.width = "0";
};
</script>

<template>
  <div class="button-wrapper">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css" />
    <link rel="stylesheet" href="https://www.w3schools.com/lib/w3-colors-2020.css" />

    <div id="app" class="w3-container">
      <button class="w3-button w3-xlarge w3-circle w3-2020-amberglow" @click="handleButtonClick">
        +
      </button>
    </div>
    <div id="mySidenav" class="sidenav">
      <a href="javascript:void(0)" class="closebtn" @click="closeNav">&times;</a>
      <draggable
        class="list-group"
        :component-data="componentData"
        v-model="state.list"
        v-bind="dragOptions"
        @start="state.drag = true"
        @end="state.drag = false"
        item-key="order"
      >
        <template #item="{ element }">
          <li class="list-group-item">
            <i
              :class="element.fixed ? 'fa fa-anchor' : 'glyphicon glyphicon-pushpin'"
              @click="element.fixed = !element.fixed"
              aria-hidden="true"
            ></i>
            {{ element.name }}
          </li>
        </template>
      </draggable>
    </div>
  </div>
</template>

<style scoped>
.button-wrapper {
  position: absolute;
  left: 2px;
  bottom: 15px;
  z-index: 3;
}

.sidenav {
  height: 100%;
  width: 0;
  position: fixed;
  z-index: 1;
  top: 0;
  left: 0;
  background-color: #ecdfd4;
  overflow-x: hidden;
  padding-top: 60px;
  transition: 0.5s;
}

.sidenav a {
  padding: 8px 8px 8px 32px;
  text-decoration: none;
  font-size: 25px;
  color: #818181;
  display: block;
  transition: 0.3s;
}

.sidenav a:hover {
  color: #f1f1f1;
}

.sidenav .closebtn {
  position: absolute;
  top: 0;
  right: 25px;
  font-size: 36px;
  margin-left: 50px;
}

#main {
  transition: margin-left 0.5s;
  padding: 20px;
}

@media screen and (max-height: 450px) {
  .sidenav {
    padding-top: 15px;
  }
  .sidenav a {
    font-size: 18px;
  }
}
</style>

<!-- <script setup>
import { defineProps, ref, watch, computed } from "vue";
import draggable from "vuedraggable";

const message = [
  "vue.draggable",
  "draggable",
  "component",
  "for",
  "vue.js 2.0",
  "based",
  "on",
  "Sortablejs",
];

const { newPlan } = defineProps(["newPlan"]);

let isSidebarOpen = false;

const handleButtonClick = () => {
  if (isSidebarOpen) {
    closeNav();
  } else {
    openNav();
  }
  isSidebarOpen = !isSidebarOpen;
};

const openNav = () => {
  document.getElementById("mySidenav").style.width = "250px";
};

const closeNav = () => {
  document.getElementById("mySidenav").style.width = "0";
};

// Reactive state
const state = reactive({
  list: message.map((name, index) => ({ name, order: index + 1 })),
  drag: false,
});

// Methods
const sort = () => {
  state.list.sort((a, b) => a.order - b.order);
};

// Watch for changes in newPlan and update the list
watch(newPlan, (newVal) => {
  state.list = newVal.map((item, index) => ({
    name: item.title,
    fixed: false,
    order: index,
  }));
});

const dragOptions = computed(() => ({
  animation: 200,
  group: "description",
  disabled: false,
  ghostClass: "ghost",
}));

const componentData = computed(() => ({
  tag: "ul",
  type: "transition-group",
  name: !state.drag.value ? "flip-list" : null,
}));
</script>

<template>
  <div class="button-wrapper">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css" />
    <link rel="stylesheet" href="https://www.w3schools.com/lib/w3-colors-2020.css" />

    <div id="app" class="w3-container">
      <button class="w3-button w3-xlarge w3-circle w3-2020-amberglow" @click="handleButtonClick">
        +
      </button>
    </div>
    <div id="mySidenav" class="sidenav">
      <a href="javascript:void(0)" class="closebtn" @click="closeNav">&times;</a>
      <draggable
        class="list-group"
        :component-data="componentData"
        v-model="state.list"
        v-bind="dragOptions"
        @start="state.drag = true"
        @end="state.drag = false"
        item-key="order"
      >
        <template #item="{ element }">
          <li class="list-group-item">
            <i
              :class="element.fixed ? 'fa fa-anchor' : 'glyphicon glyphicon-pushpin'"
              @click="element.fixed = !element.fixed"
              aria-hidden="true"
            ></i>
            {{ element.name }}
          </li>
        </template>
      </draggable>
    </div>
  </div>
</template>

<style scoped>
.button-wrapper {
  position: absolute;
  left: 2px;
  bottom: 15px;
  z-index: 3;
}

.sidenav {
  height: 100%;
  width: 0;
  position: fixed;
  z-index: 1;
  top: 0;
  left: 0;
  background-color: #ecdfd4;
  overflow-x: hidden;
  padding-top: 60px;
  transition: 0.5s;
}

.sidenav a {
  padding: 8px 8px 8px 32px;
  text-decoration: none;
  font-size: 25px;
  color: #818181;
  display: block;
  transition: 0.3s;
}

.sidenav a:hover {
  color: #f1f1f1;
}

.sidenav .closebtn {
  position: absolute;
  top: 0;
  right: 25px;
  font-size: 36px;
  margin-left: 50px;
}

#main {
  transition: margin-left 0.5s;
  padding: 20px;
}

@media screen and (max-height: 450px) {
  .sidenav {
    padding-top: 15px;
  }
  .sidenav a {
    font-size: 18px;
  }
}
</style> -->
