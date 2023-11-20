<script setup>
import { ref, reactive, computed } from "vue";
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

// Reactive state
const state = reactive({
  list: message.map((name, index) => ({ name, order: index + 1 })),
  drag: false,
});

// Methods
const sort = () => {
  state.list.sort((a, b) => a.order - b.order);
};

// Computed properties
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
  <div class="row">
    <div class="col-2">
      <button class="btn btn-secondary button" @click="sort">To original order</button>
    </div>

    <div class="col-6">
      <h3>Transition</h3>
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
.button {
  margin-top: 35px;
}

.flip-list-move {
  transition: transform 0.5s;
}

.no-move {
  transition: transform 0s;
}

.ghost {
  opacity: 0.5;
  background: #c8ebfb;
}

.list-group {
  min-height: 20px;
}

.list-group-item {
  cursor: move;
}

.list-group-item i {
  cursor: pointer;
}
</style>
