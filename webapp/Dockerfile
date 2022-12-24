#### Stage 1: Build the react application
FROM node:latest as build

WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . ./

ARG REACT_APP_API_BASE_URL
ENV REACT_APP_API_BASE_URL=${REACT_APP_API_BASE_URL}

RUN npm run build

#### Stage 2: Serve the React application from Nginx 
FROM nginx:1.17.0-alpine

COPY --from=build /app/build /var/www
COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 80

ENTRYPOINT ["nginx","-g","daemon off;"]