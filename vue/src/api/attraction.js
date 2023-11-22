import { serviceApi } from "@/util/http-commons";

const api = serviceApi;

const getAttractionById = (contentId, success, fail) => {
  api.get(`attraction/${contentId}`).then(success).catch(fail);
};

const getAttractionsByIds = (ids, success, fail) => {
  ids = ids.join(",");
  api.get(`attraction/all?attractionIds=${ids}`).then(success).catch(fail);
};

const getAttractionsByCondition = (condition, success, fail) => {
  console.log("condition:", condition);
  api.get("attraction", { params: condition }).then(success).catch(fail);
};

const findSidoCode = (success, fail) => {
  api.get("attraction/sido").then(success).catch(fail);
};

const findGugunCode = (sidocode, success, fail) => {
  api.get(`attraction/sido/${sidocode}`).then(success).catch(fail);
};

const getAttractionDetailById = (contentId, success, fail) => {
  api.get(`attraction/detail/${contentId}`).then(success).catch(fail);
}
export {
  getAttractionById,
  getAttractionsByIds,
  getAttractionsByCondition,
  findGugunCode,
  findSidoCode,
  getAttractionDetailById
};
