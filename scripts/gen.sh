#!/bin/bash

set -e

APP_ID=$1

GIT_HASH=`git rev-parse --short HEAD`
ARTIFACT_VERSION=${BUILD_NUMBER}-${GIT_HASH}

echo "[INFO] setting environment variable ARTIFACT VERSION: ${ARTIFACT_VERSION} for Teamcity"

mkdir -p target
rm -f target/*.jar

mv build/libs/${APP_ID}-*-*.jar target/${APP_ID}-${ARTIFACT_VERSION}.jar

echo "Generating artifact manifest for audit/verification"
rm -f artifact_manifest.sh
cat > artifact_manifest.sh <<EOL
export ARTIFACT_VERSION="${ARTIFACT_VERSION}"
EOL

chmod 700 artifact_manifest.sh
cat artifact_manifest.sh
