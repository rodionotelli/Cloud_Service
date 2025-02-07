FROM node:19.7 as node

ENV GIT_REPO_URL=https://github.com/netology-code/jd-homeworks.git
ENV GIT_REPO_SUBDIR=diploma/netology-diplom-frontend

WORKDIR /src
RUN git clone --filter=blob:none --sparse $GIT_REPO_URL git-repo
WORKDIR /src/git-repo
RUN git sparse-checkout add $GIT_REPO_SUBDIR

WORKDIR /src/git-repo/$GIT_REPO_SUBDIR
RUN npm install

ENV VUE_APP_BASE_URL=http://localhost:8080
RUN npm run build

CMD ["npm",  "run", "serve"]
