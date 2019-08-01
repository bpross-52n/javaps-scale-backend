#!/bin/sh
# Copyright 2019-2019 52°North Initiative for Geospatial Open Source
# Software GmbH
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#   http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
#
VERSION="1.4.0-SNAPSHOT"
IMAGE="52n-javaps:$VERSION"
BINARY_SOURCE="/home/eike/Code/git/ogc-tb-15/javaps-scale-backend/target/javaps-scale-backend-$VERSION-binaries/"
BINARY_TARGET="/var/lib/jetty/webapps/ROOT/WEB-INF/lib/"
	#--rm \
docker run \
	-it \
	-p 8080:8080 \
    -v ${BINARY_SOURCE}javaps-scale-backend-${VERSION}.jar:${BINARY_TARGET}javaps-scale-backend-${VERSION}.jar \
	-v ${BINARY_SOURCE}retrofit-2.6.0.jar:${BINARY_TARGET}retrofit-2.6.0.jar \
	-v ${BINARY_SOURCE}converter-jackson-2.6.0.jar:${BINARY_TARGET}converter-jackson-2.6.0.jar \
	-v ${BINARY_SOURCE}okhttp-3.12.0.jar:${BINARY_TARGET}okhttp-3.12.0.jar \
	-v ${BINARY_SOURCE}okio-1.15.0.jar:${BINARY_TARGET}okio-1.15.0.jar \
	-v ${BINARY_SOURCE}javaps-iohandler-1.3.0.jar:${BINARY_TARGET}javaps-iohandler-1.3.0.jar \
	-v ${BINARY_SOURCE}xmlbeans-3.1.0.jar:${BINARY_TARGET}xmlbeans-3.1.0.jar \
	$IMAGE