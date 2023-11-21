import { authApi } from "../util/http-commons";

const api = authApi();

/**
 * @param loginForm : { username: String, password: String }
 * @param success   : api call success handler
 * @param fail      : api call failure handler
 */
const login = (loginForm, success, fail) => {
  api.post("/login", loginForm).then(success).catch(fail);
};

/**
 * @param signupForm: { username: String, password: String, email: String }
 * @param success   : api call success handler
 * @param fail      : api call failure handler
 */
const signup = (signupForm, success, fail) => {
  api.post("/signup", signupForm).then(success).catch(fail);
};

const validateToken = (token, success, fail) => {
  api
    .get("/validate-token", { headers: { Authorization: "Bearer " + token } })
    .then(success)
    .catch(fail);
};

const update = (user, success, fail) => {
  api.put("/update", user).then(success).catch(fail);
};
export { login, signup, validateToken, update };
