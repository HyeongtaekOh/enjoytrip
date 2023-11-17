import { serviceApi } from "@/util/http-commons";

const api = serviceApi();

const getAttractionById = (contentId, success, fail) => {
  api.get(`attraction/${contentId}`).then(success).catch(fail);
};

const getAttractionsByCondition = (condition, success, fail) => {
  api.get("attraction", { condition }).then(success).catch(fail);
};

const findSidoCode = (success, fail) => {
  api.get("attraction/sido").then(success).catch(fail);
};

const findGugunCode = (sidocode, success, fail) => {
  api.get(`attraction/sido/${sidocode}`).then(success).catch(fail);
};

export { getAttractionById, getAttractionsByCondition, findGugunCode, findSidoCode };
