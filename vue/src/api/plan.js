import { serviceApi } from "@/util/http-commons";

const api = serviceApi;

const getPlanById = (planId, success, fail) => {
  api.get(`plan/${planId}`).then(success).catch(fail);
};

const getPlansWithAttraction = (attractionId, success, fail) => {
  api.get("plan/course", { params: { attractionId } }).then(success).catch(fail);
};

const getPlansByCondition = (condition, success, fail) => {
  api.get("plan", { params: condition }).then(success).catch(fail);
};

const registPlan = (plan, success, fail) => {
  api.post("plan", plan).then(success).catch(fail);
};

const updatePlan = (plan, success, fail) => {
  api.put(`plan/${plan.planId}`, plan).then(success).catch(fail);
};

const deletePlan = (planId, success, fail) => {
  api.delete(`plan/${planId}`).then(success).catch(fail);
};

export {
  getPlanById,
  getPlansWithAttraction,
  getPlansByCondition,
  registPlan,
  updatePlan,
  deletePlan,
};
