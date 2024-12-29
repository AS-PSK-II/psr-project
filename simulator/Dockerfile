FROM node:22.9.0-slim AS build
WORKDIR /app
COPY . .

RUN npm i && npm run build

FROM node:22.9.0-slim AS runtime
WORKDIR /app
COPY --from=build /app/dist /app
COPY package.json .
COPY package-lock.json .

RUN NODE_ENV=production npm install

ENTRYPOINT ["node", "main.js"]