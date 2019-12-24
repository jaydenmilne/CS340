FROM node:alpine AS builder

WORKDIR /app

COPY . .

RUN npm install && \
    ng build --prod=true

RUN tar -czvf /app/marylou-client.tar.gz /app/dist

# todo - figure out a way to copy output out
FROM nginx:alpine

COPY --from=builder /app/dist/* /usr/share/nginx/html/
COPY --from=builder /app/marylou-client.tar.gz /