import { authApi } from "@/util/http-commons";

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

const validateToken = async (token, success, fail) => {
  try {
    const response = await api.get("/validate-token", {
      headers: { Authorization: "Bearer " + token },
    });
    success(response);
  } catch (error) {
    fail(error);
  }
};

const getRefreshToken = async (token, success, fail) => {
  try {
    const response = await api.get("/refresh-token", {
      headers: { Authorization: "Bearer " + token },
    });
    success(response);
  } catch (error) {
    fail(error);
  }
};

export { login, signup, validateToken, getRefreshToken };
