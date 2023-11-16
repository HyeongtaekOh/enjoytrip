import { serviceApi } from "@/util/http-commons";

const local = serviceApi(); // axios instance

const url = "comment";

function listComment(condition, success, fail) {
  console.log("condition = ", condition);
  local.get("comment", { params: condition }).then(success).catch(fail);
}

function detailComment(commentId, success, fail) {
  local.get(`${url}/${commentId}`).then(success).catch(fail);
}

function registComment(comment, success, fail) {
  console.log("registComment = ", comment);
  local.post(`${url}`, comment).then(success).catch(fail);
}

function modifyComment(comment, success, fail) {
  console.log("modifyComment = ", comment);
  local.put(`${url}/${comment.commentId}`, comment).then(success).catch(fail);
}

function removeComment(commentId, success, fail) {
  local.delete(`${url}/${commentId}`).then(success).catch(fail);
}

export { listComment, detailComment, registComment, modifyComment, removeComment };
