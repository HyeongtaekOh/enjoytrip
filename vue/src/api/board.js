import { serviceApi } from "@/util/http-commons";

const local = serviceApi; // axios instance

const url = "qna";

/*
  condition 객체 형식
  {
    userId:   String,     // 게시자 ID
    keyword:  String,     // 검색어 (제목, 내용)
    page:     Integer,    // 페이지 번호
    pageSize: Integer     // 페이지 크기
  }
*/
function listArticle(condition, success, fail) {
  console.log("condition = ", condition);
  local.get("qna", { params: condition }).then(success).catch(fail);
}

function detailArticle(articleId, success, fail) {
  local.get(`${url}/${articleId}`).then(success).catch(fail);
}

/*
  article DTO 객체 형식
  {
    userId:   String,     // 게시자 ID
    subject:  String,     // Q&A 제목
    content:  String      // Q&A 내용
  }
*/
function registArticle(article, success, fail) {
  local.post(`${url}`, article).then(success).catch(fail);
}

function modifyArticle(article, success, fail) {
  local.put(`${url}/${article.articleId}`, article).then(success).catch(fail);
}

function deleteArticle(articleId, success, fail) {
  local.delete(`${url}/${articleId}`).then(success).catch(fail);
}

export { listArticle, detailArticle, registArticle, modifyArticle, deleteArticle };
