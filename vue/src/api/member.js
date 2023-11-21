import { serviceApi } from "@/util/http-commons";

const api = serviceApi;

const getMemberById = (userId, success, fail) => {
  api.get(`/member/${userId}`).then(success).catch(fail);
};

const duplicateUsernameCheck = (username, success, fail) => {
  api.post("/member/duplicate-check", { username }).then(success).catch(fail);
};

const updateMember = (member, success, fail) => {
  api.put(`/member/${member.userId}`, member).then(success).catch(fail);
};

const deleteMember = (userId, success, fail) => {
  api.delete(`/member/${userId}`).then(success).catch(fail);
};

export { getMemberById, duplicateUsernameCheck, updateMember, deleteMember };
