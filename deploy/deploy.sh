#!/bin/bash
#기존에 존재하는 컨테이너 정지
docker stop `docker ps -qa`
#기존에 존재하는 컨테이너 삭제
docker rm `docker ps -qa`
#기존에 존재하는 이미지 삭제
docker rmi `docker images -qa`
#새로운 컨테이너 실행
docker-compose -f healthner/docker-compose.yml up -d
