function parseJwtPayload(token) {
  // JWT의 페이로드 부분(두 번째 부분) 추출
  const base64Payload = token.split(".")[1];
  // Base64로 인코딩된 문자열을 디코딩
  const jsonPayload = atob(base64Payload);
  // JSON 문자열을 객체로 변환
  return JSON.parse(jsonPayload);
}

export { parseJwtPayload };
