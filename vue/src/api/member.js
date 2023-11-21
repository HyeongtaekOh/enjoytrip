import { serviceApi } from "@/util/http-commons";

const api = serviceApi;

const duplicateUsernameCheck = (username, success, fail) => {
  api.post("/member/duplicate-check", { username }).then(success).catch(fail);
};

const updateMember = (member, success, fail) => {
  api.put(`/member/${member.userId}`, member).then(success).catch(fail);
};

const deleteMember = (userId, success, fail) => {
  api.delete(`/member/${member.userId}`).then(success).catch(fail);
};

export { duplicateUsernameCheck, updateMember, deleteMember };
